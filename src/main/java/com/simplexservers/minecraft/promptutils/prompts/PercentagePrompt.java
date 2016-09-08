package com.simplexservers.minecraft.promptutils.prompts;

import com.simplexservers.minecraft.commandutils.CommandInvoker;
import com.simplexservers.minecraft.promptutils.*;

/**
 * The prompt handling the input for a percentage response.
 *
 * @author Zach Abney
 */
public abstract class PercentagePrompt extends ParsedPrompt<Float> {

	/**
	 * The parser for a percentage input.
	 */
	private static final InputParser<Float> PERCENTAGE_PARSER = input -> {
		input = input.replace("%", "");
		try {
			float percent = Float.parseFloat(input) / 100F;
			if (percent <= 0 || percent > 100) {
				throw new IllegalArgumentException("Invalid percentage range. Must be greater than 0 and less than or equal to 100.");
			}

			return percent;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid format. " + e.getMessage());
		}
	};

	/**
	 * Creates a new prompt that requires an answer from the previous prompt's player.
	 *
	 * @param previousPrompt The previous answer prompt to derive from.
	 */
	public PercentagePrompt(AnswerPrompt previousPrompt) {
		super(previousPrompt, PERCENTAGE_PARSER);
	}

	/**
	 * Creates a new prompt to handle a percent input.
	 *
	 * @param participant The player conversing with the prompt.
	 * @param listener The ChatListener handling the native chat hook implementation.
	 */
	public PercentagePrompt(CommandInvoker participant, ChatListener listener) {
		super(participant, listener, PERCENTAGE_PARSER);
	}

}
