package com.simplexservers.minecraft.promptutils;

import com.simplexservers.minecraft.commandutils.CommandInvoker;

/**
 * A prompt a player can converse with.
 *
 * @author Zach Abney
 */
public abstract class Prompt {

	/**
	 * The player conversing with the prompt.
	 */
	private CommandInvoker participant;

	/**
	 * Creates a new prompt the player converses with.
	 *
	 * @param participant The player conversing with the prompt.
	 * @throws IllegalArgumentException If the participant is not a player.
	 */
	public Prompt(CommandInvoker participant) throws IllegalArgumentException {
		if (!participant.isPlayer()) {
			throw new IllegalArgumentException("The participant must be a player.");
		}

		this.participant = participant;
	}

	/**
	 * Gets the player conversing with the prompt.
	 *
	 * @return The conversing player.
	 */
	public final CommandInvoker getParticipant() {
		return participant;
	}

	/**
	 * Begins the prompt conversation with the player.
	 */
	public abstract void begin();

}
