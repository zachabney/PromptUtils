package com.simplexservers.minecraft.promptutils;

import java.io.Serializable;

/**
 * A representation of a given amount of time.
 *
 * @author Zach Abney
 */
public class Time implements Serializable {

	/**
	 * The amount of seconds the time represents.
	 */
	private long seconds;

	/**
	 * Creates a Time object that represents the given amount of seconds.
	 *
	 * @param seconds The amount of seconds the time represents.
	 */
	public Time(long seconds) {
		this.seconds = seconds;
	}

	/**
	 * Creates a Time object that represents the given amount of time.
	 *
	 * @param time The amount of units.
	 * @param unit The time unit.
	 */
	public Time(long time, TimeUnit unit) {
		this.seconds = time * unit.seconds;
	}

	/**
	 * Gets the number of seconds the time represents.
	 *
	 * @return The time in seconds.
	 */
	public long getSeconds() {
		return seconds;
	}

	/**
	 * Gets the time in a human readable format.
	 *
	 * @return The human readable format of the time.
	 */
	public String format() {
		StringBuilder string = new StringBuilder();
		long mod = seconds;
		TimeUnit[] units = TimeUnit.values();
		for(int i = units.length-1; i >= 0; i--) {
			TimeUnit unit = units[i];
			long time = mod / unit.seconds;
			if(time > 0) {
				if(string.length() > 0) string.append(" ");
				string.append(time + " " + unit.getName(time != 1, false));
			}

			mod %= unit.seconds;
		}
		return string.toString();
	}

	/**
	 * Gets the rounded time in a human readable format.
	 * Shows the smallest whole value time unit.
	 *
	 * @return The human readable rounded format of the time.
	 */
	public String formatRounded() {
		long mod = seconds;
		TimeUnit[] units = TimeUnit.values();
		for (int i = units.length - 1; i >= 0; i--) {
			TimeUnit unit = units[i];
			long time = mod / unit.seconds;
			if (time > 0) {
				return time + " " + unit.getName(time != 1, false);
			}
		}

		return format();
	}

	/**
	 * Gets the epoch time stamp for after the duration time has elapsed.
	 *
	 * @return The epoch time stamp for after the duration.
	 */
	public long durationToEpochTime() {
		return System.currentTimeMillis() / 1000L + getSeconds();
	}

	/**
	 * Parses the time string into a Time object.
	 *
	 * @param string The string to parse.
	 * @return The Time object that represents the time entered.
	 * @throws IllegalArgumentException If there's a format error with the string.
	 */
	public static Time parseTime(String string) throws IllegalArgumentException {
		String[] args = string.split(" ");
		if (args.length % 2 != 0) {
			throw new IllegalArgumentException("Invalid format. Odd number of arguments.");
		}

		long totalSeconds = 0;
		for (int i = 0; i < args.length; i += 2) {
			int value;
			try {
				value = Integer.parseInt(args[i]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid format. Non integer unit value.");
			}

			if (value < 0) {
				throw new IllegalArgumentException("Invalid format. Negative time.");
			}

			TimeUnit unit = TimeUnit.getTimeUnit(args[i + 1]);
			if (unit == null) {
				throw new IllegalArgumentException("Invalid format. Invalid unit.");
			}

			totalSeconds += value * unit.seconds;
		}

		return new Time(totalSeconds);
	}

	/**
	 * Converts an epoch time stamp to a Time duration.
	 *
	 * @param epochTimestamp The timestamp to convert.
	 * @return The duration until the timestamp.
	 * @throws IllegalArgumentException If the timestamp has already passed.
	 */
	public static Time epochTimeToDuration(long epochTimestamp) {
		long currentTime = System.currentTimeMillis() / 1000L;
		if (currentTime > epochTimestamp) {
			throw new IllegalArgumentException("Negative time duration");
		}

		long secondsDuration = epochTimestamp - currentTime;
		return new Time(secondsDuration);
	}

	/**
	 * The units of time.
	 *
	 * @author Zach Abney
	 */
	public enum TimeUnit {
		SECOND(1, "sec"),
		MINUTE(60, "min"),
		HOUR(3600, "hr"),
		DAY((long) 8.64e4, "day"),
		WEEK((long) 6.048e5, "wk"),
		MONTH((long) 2.4192e6, "mth"),
		YEAR((long) 2.90304e7, "yr");

		/**
		 * The number of seconds the unit represents.
		 */
		public final long seconds;
		/**
		 * The abbreviation of the unit name.
		 */
		public final String abbreviation;

		/**
		 * Creates a TimeUnit with for the given seconds and abbreviation.
		 *
		 * @param seconds The number of seconds the unit represents.
		 * @param abbreviation The abbreviation of the time unit.
		 */
		TimeUnit(long seconds, String abbreviation) {
			this.seconds = seconds;
			this.abbreviation = abbreviation;
		}

		/**
		 * Gets the name of the time unit based on plurality and abbreviation.
		 *
		 * @param plural Should the name be in plural form.
		 * @param useAbbreviation Should the name be an abbreviation.
		 * @return The name of the time unit.
		 */
		public String getName(boolean plural, boolean useAbbreviation) {
			String string;
			if (useAbbreviation) {
				string = abbreviation;
			} else {
				string = name().toLowerCase();
			}

			if (plural) {
				string += "s";
			}

			return string;
		}

		/**
		 * Gets the time unit based on the name.
		 * Can be an abbreviation or plural.
		 *
		 * @param unitName The name of the time unit to parse.
		 * @return The corresponding TimeUnit for the unit name.
		 */
		public static TimeUnit getTimeUnit(String unitName) {
			for (TimeUnit unit : TimeUnit.values()) {
				if (unit.getName(true, false).equalsIgnoreCase(unitName)
						|| unit.getName(false, false).equalsIgnoreCase(unitName)
						|| unit.getName(true, true).equalsIgnoreCase(unitName)
						|| unit.getName(false, true).equalsIgnoreCase(unitName)) {
					return unit;
				}
			}

			return null;
		}

	}

}
