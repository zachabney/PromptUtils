package com.simplexservers.minecraft.promptutils;

/**
 * A parser to convert from user input to the given parameter type.
 *
 * @param <T> The class type the parser converts Strings to.
 */
public interface InputParser<T> {
	/**
	 * Attempts to parse the given input to the object type.
	 *
	 * @param input The String input to parse.
	 * @return The parsed value of the input in the form of T.
	 * @throws IllegalArgumentException If there is a format issue parsing the input to the class type.
	 */
	T parseInput(String input) throws IllegalArgumentException;
}
