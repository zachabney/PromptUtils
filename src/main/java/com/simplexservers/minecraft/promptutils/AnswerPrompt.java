package com.simplexservers.minecraft.promptutils;

import com.simplexservers.minecraft.commandutils.CommandInvoker;

/**
 * A prompt that shows a message and requires a player response.
 *
 * @author Zach Abney
 */
public abstract class AnswerPrompt extends Prompt {

	/**
	 * The ChatListener handling the native chat hook implementation.
	 */
	private final ChatListener listener;

	/**
	 * Creates a new prompt that requires an answer from the previous prompt's player.
	 *
	 * @param previousPrompt The previous answer prompt to derive from.
	 */
	public AnswerPrompt(AnswerPrompt previousPrompt) {
		this(previousPrompt.getParticipant(), previousPrompt.listener);
	}

	/**
	 * Creates a new prompt that requires an answer from the given player.
	 *
	 * @param participant The player to start the prompt with.
	 * @param listener The ChatListener handling the native chat hook implementation.
	 */
	public AnswerPrompt(CommandInvoker participant, ChatListener listener) {
		super(participant);
		this.listener = listener;
	}

	@Override
	public void begin() {
		getParticipant().sendMessage(getMessage());
		listener.registerParticipant(getParticipant(), this);
	}

	/**
	 * Should the chat event for the player's answer be canceled.
	 *
	 * @return true if the chat should be canceled.
	 */
	public boolean cancelInputChat() {
		return true;
	}

	/**
	 * Gets the message to show the player that they need to
	 * enter a response to.
	 *
	 * @return The message to show the player.
	 */
	public abstract String getMessage();

	/**
	 * Called when the player enters a response to the prompt.
	 *
	 * @param input The answer the player entered.
	 */
	public abstract void onInput(String input);

}
