package com.simplexservers.minecraft.promptutils.prompts;

import java.util.Random;

import com.simplexservers.minecraft.commandutils.CommandInvoker;
import com.simplexservers.minecraft.promptutils.*;
import com.simplexservers.minecraft.promptutils.Time.TimeUnit;

/**
 * The prompt handling the input of a time response.
 *
 * @author Zach Abney
 */
public abstract class TimePrompt extends ParsedPrompt<Time> {

	/**
	 * Creates a new prompt that requires an answer from the previous prompt's player.
	 *
	 * @param previousPrompt The previous answer prompt to derive from.
	 */
	public TimePrompt(AnswerPrompt previousPrompt) {
		super(previousPrompt, Time::parseTime);
	}

	/**
	 * Creates a new TimePrompt to handle Time input.
	 *
	 * @param participant The player conversing with the prompt.
	 * @param listener The ChatListener handling the native chat hook implementation.
	 */
	public TimePrompt(CommandInvoker participant, ChatListener listener) {
		super(participant, listener, Time::parseTime);
	}

	/**
	 * Gets a help message to assist the player in formatting the time input.
	 */
	public static String getFormatHelp() {
		StringBuilder message = new StringBuilder();
		message.append(ChatColor.BLUE + "Ex: " + ChatColor.WHITE + getRandomTimeExample());
		message.append("\n" + ChatColor.BLUE + "Time Units: ");

		TimeUnit[] units = TimeUnit.values();
		for (int i = 0; i < units.length; i++) {
			if (i != 0) {
				message.append(ChatColor.BLUE + ", ");
			}

			message.append(ChatColor.GOLD + units[i].getName(true, false));
		}

		return message.toString();
	}

	/**
	 * Gets a random time input to show to the player
	 * as an example.
	 *
	 * @return The random time input example.
	 */
	public static String getRandomTimeExample() {
		Random random = new Random();
		TimeUnit[] units = TimeUnit.values();

		int value1 = random.nextInt(5) + 1;
		TimeUnit unit1 = units[random.nextInt(units.length)];
		long value1Sec = value1 * unit1.seconds;

		int value2 = random.nextInt(5) + 1;
		TimeUnit unit2 = units[random.nextInt(units.length)];
		long value2Sec = value2 * unit2.seconds;

		if (value1Sec == value2Sec) {
			return getRandomTimeExample();
		}

		if (value1Sec < value2Sec) {
			return value2 + " " + unit2.getName(value2 != 1, true) + " " + value1 + " " + unit1.getName(value1 != 1, true);
		}

		return value1 + " " + unit1.getName(value1 != 1, true) + " " + value2 + " " + unit2.getName(value2 != 1, true);
	}

}
