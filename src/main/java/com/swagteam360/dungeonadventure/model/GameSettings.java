package com.swagteam360.dungeonadventure.model;

/**
 * The GameSettings class is designed to encapsulate and manage the
 * configuration settings related to a player's game session, such as
 * player name, chosen hero, and selected difficulty level.
 *
 * @author Jonathan Hernandez
 * @version 1.0 (May 11, 2025)
 */
public class GameSettings {

    /**
     * Represents the name of the player in the game settings.
     * This variable stores the player's chosen name for their game session.
     */
    private String myName;

    /**
     * Represents the name of the hero chosen by the player for the game session.
     * This variable stores the player's selected hero, which is a key character
     * in the gameplay and determines certain abilities and traits during the session.
     */
    private String myHero;

    /**
     * Represents the difficulty level selected by the player for the game session.
     * This variable stores the current difficulty setting, which can impact
     * various aspects of the gameplay, such as enemy strength, resource availability,
     * and overall challenge.
     */
    private String myDifficulty;

    /**
     * Constructs a new GameSettings object with a specified player name, hero, and difficulty level.
     *
     * @param theName the name of the player for the game session.
     * @param theHero the name of the hero selected by the player.
     * @param theDifficulty the selected difficulty level for the game session.
     */
    public GameSettings(final String theName, final String theHero, final String theDifficulty) {
        myName = theName;
        myHero = theHero;
        myDifficulty = theDifficulty;
    }

    /**
     * Retrieves the name of the player associated with the current game settings.
     *
     * @return the name of the player.
     */
    public String getName() {
        return myName;
    }

    /**
     * Retrieves the name of the hero selected by the player for the game session.
     *
     * @return the name of the selected hero.
     */
    public String getHero() {
        return myHero;
    }

    /**
     * Retrieves the selected difficulty level for the game session.
     *
     * @return the difficulty level as a String.
     */
    public String getDifficulty() {
        return myDifficulty;
    }

    /**
     * Sets the name of the player for the game session.
     *
     * @param theName the name to be associated with the player.
     */
    public void setName(final String theName) {
        myName = theName;
    }

    /**
     * Sets the name of the hero selected by the player for the game session.
     */
    public void setHero(final String theHero) {
        myHero = theHero;
    }

    /**
     * Sets the difficulty level for the game session.
     * This determines the challenge level the player will encounter during gameplay.
     *
     * @param theDifficulty the difficulty level to be set, expressed as a String.
     */
    public void setDifficulty(final String theDifficulty) {
        myDifficulty = theDifficulty;
    }

}
