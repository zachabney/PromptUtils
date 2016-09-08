package com.simplexservers.minecraft.promptutils;

import com.simplexservers.minecraft.commandutils.CommandInvoker;

/**
 * A prompt that handles parsing input of an AnswerPrompt.
 *
 * @param <T> The type the input is being parsed to.
 */
public abstract class ParsedPrompt<T> extends AnswerPrompt {

	/**
	 * The handler for parsing input.
	 */
	private InputParser<T> parser;

	/**
	 * Creates a new prompt that requires an answer from the previous prompt's player.
	 *
	 * @param previousPrompt The previous answer prompt to derive from.
	 * @param parser The handler for parsing input.
	 */
	public ParsedPrompt(AnswerPrompt previousPrompt, InputParser<T> parser) {
		super(previousPrompt);
		this.parser = parser;
	}

	/**
	 * Creates a new ParsedPrompt to handle the input of a given type.
	 *
	 * @param participant The player conversing with the prompt.
	 * @param listener The ChatListener handling the native chat hook implementation.
	 * @param parser The handler for parsing input.
	 */
	public ParsedPrompt(CommandInvoker participant, ChatListener listener, InputParser<T> parser) {
		super(participant, listener);
		this.parser = parser;
	}

	@Override
	public final void onInput(String input) {
		T parsedInput;
		try {
			parsedInput = parser.parseInput(input);
		} catch (IllegalArgumentException e) {
			getParticipant().sendMessage(ChatColor.RED + e.getMessage());
			begin();
			return;
		}

		onParsedInput(parsedInput);
	}

	/**
	 * Called when the player enters a valid parsed response to the prompt.
	 *
	 * @param input The parsed answer the player entered.
	 */
	public abstract void onParsedInput(T input);

}
