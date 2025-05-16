package com.swagteam360.dungeonadventure.model;

/**
 * The GameManager class serves as a singleton responsible for managing the game's lifecycle.
 * It handles game initialization, including the setup of game settings and the creation
 * of hero characters based on the specified configurations in the GameSettings object.
 *
 * @author Jonathan Hernandez
 * @version 1.0 (May 11, 2025)
 */
public class GameManager {

    /**
     * A singleton instance of the GameManager class. This instance ensures that only one
     * instance of the GameManager exists in the application, providing a global access point
     * to manage the game's lifecycle, including initialization, configuration, and hero creation.
     */
    private static final GameManager mySingleton = new GameManager();

    /**
     * The GameSettings object that holds the configuration for the current game session.
     * It includes information such as the player's chosen hero, the hero's name, and
     * the game's difficulty level. This field is updated when starting a new game and is
     * used for hero creation and game setup.
     */
    private GameSettings myGameSettings;

    /**
     * Represents the player's chosen hero in the game. This field is initialized
     * based on the hero configuration specified in the game's settings and determines
     * the character that the player controls during gameplay. It holds a reference
     * to an instance of a specific Hero subclass, such as Warrior, Priestess, or Thief.
     */
    private Hero myHero;

    /**
     * Constructs a new instance of the GameManager class.
     * <p>
     * This constructor is declared private to adhere to the Singleton design pattern,
     * ensuring only one instance of GameManager is created in the application.
     * Access to the GameManager instance is provided through the static `getInstance` method.
     */
    private GameManager() {}

    /**
     * Provides access to the singleton instance of the GameManager class.
     * Ensures that only one instance of GameManager exists within the application,
     * adhering to the Singleton design pattern.
     *
     * @return the singleton instance of GameManager
     */
    public static synchronized GameManager getInstance() {
        return mySingleton;
    }

    public void startNewGame(final GameSettings theGameSettings) {

        myGameSettings = theGameSettings;
        myHero = createHero(theGameSettings);

        // TODO: Add more logic here

    }

    /**
     * Creates and returns a Hero instance based on the provided game settings.
     * The hero type is determined by the value of the hero field in the supplied
     * GameSettings object. If the hero type is not recognized, an IllegalArgumentException
     * is thrown.
     *
     * @param theGameSettings an instance of GameSettings containing the hero type
     *                        and other game configuration details.
     * @return a Hero instance corresponding to the type specified in the game settings,
     *         such as Warrior, Priestess, or Thief.
     * @throws IllegalArgumentException if the hero type provided in the game settings
     *                                  is unknown or invalid.
     */
    private Hero createHero(final GameSettings theGameSettings) {
        return switch (theGameSettings.getHero().toLowerCase()) {
            case "warrior" -> new Warrior(
                        theGameSettings.getName(), 50,
                        125, 4, 35,
                        60, 80, 20);

            case "priestess" -> new Priestess(
                        theGameSettings.getName(), 35, 75,
                        5, 25, 45,
                        70, 30);

            case "thief" -> new Thief(
                        theGameSettings.getName(), 30, 75,
                        6, 20, 40,
                        80, 40);
            default -> throw new IllegalArgumentException("Unknown/Invalid Hero type.");
        };
    }

    /**
     * Retrieves the current game settings for the game session.
     * The returned GameSettings object encapsulates details such as
     * the player's name, the selected hero, and the difficulty level.
     *
     * @return the current instance of GameSettings representing the
     *         configuration settings for the game session.
     */
    public GameSettings getGameSettings() {
        return myGameSettings;
    }

}
