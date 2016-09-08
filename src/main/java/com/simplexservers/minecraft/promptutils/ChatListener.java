package com.simplexservers.minecraft.promptutils;

import com.simplexservers.minecraft.commandutils.CommandInvoker;

/**
 * The listener to handle players entering responses for AnswerPrompts.
 *
 * @author Zach Abney
 */
public interface ChatListener {

	/**
	 * Registers the player as entering a response for an AnswerPrompt.
	 *
	 * @param participant The player enter the response.
	 * @param prompt The prompt the player is answering.
	 */
	void registerParticipant(CommandInvoker participant, AnswerPrompt prompt);

}
