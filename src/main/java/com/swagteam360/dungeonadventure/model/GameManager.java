package com.swagteam360.dungeonadventure.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameManager class serves as a singleton responsible for managing the game's lifecycle.
 * It handles game initialization, including the setup of game settings and the creation
 * of hero characters based on the specified configurations in the GameSettings object.
 *
 * @author Jonathan Hernandez
 * @version 1.1 (May 20, 2025)
 */
public class GameManager {

    public static final int PIT_DAMAGE = 10;

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
     * Represents the dungeon instance currently used in the game.
     * The dungeon contains the maze structure, rooms, and relevant game logic.
     * It is responsible for managing the game's maze and interacting with
     * various dungeon-related operations.
     */
    private Dungeon myDungeon;

    /**
     * Represents the currently active room in use by the game.
     * This room will initially be set to the entrance coordinates
     * specified in the Dungeon class.
     */
    private Room myCurrentRoom;

    private final List<DungeonObserver> myObservers = new ArrayList<>();

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

    /**
     * Starts a new game session by initializing game settings, creating a hero,
     * and generating a dungeon based on the specified game settings.
     * The game is prepared using the configurations provided in the supplied
     * GameSettings object.
     *
     * @param theGameSettings an instance of GameSettings containing the player's name,
     *                        selected hero, and chosen difficulty level for the game session.
     */
    public void startNewGame(final GameSettings theGameSettings) {

        // Initialize the game.
        myGameSettings = theGameSettings;
        myHero = createHero(theGameSettings);
        myDungeon = createDungeon(theGameSettings);
        myCurrentRoom = myDungeon.getRoom(
                myDungeon.getEntranceRow(),
                myDungeon.getEntranceCol()
        );

        //FIXME DEBUG
        System.out.println(myDungeon.toStringWithPlayer(myCurrentRoom.getRow(), myCurrentRoom.getCol()));
        System.out.println();
        System.out.println(myDungeon.toDetailedString(myCurrentRoom.getRow(), myCurrentRoom.getCol()));

        // notifyObservers();

    }

    /**
     * Moves the player to a specified position in the dungeon by updating the
     * current row, column, and room based on the provided coordinates.
     * Validates that the specified position is within the bounds of the dungeon.
     * Notifies all observers of the updated game state after the player's position
     * has been updated. Throws an exception if the inputs are invalid.
     *
     * @param theDirection Direction enumeration type - either NORTH, SOUTH, WEST, or EAST
     * @throws IllegalArgumentException if the specified row or column is out of bounds.
     */
    public void movePlayer(final Direction theDirection) {
        int row = myCurrentRoom.getRow(); // store the current row
        int col = myCurrentRoom.getCol(); // store the current column
        final int maxRow = myDungeon.getRowSize() - 1; // maximum row index inclusive
        final int maxCol = myDungeon.getColSize() - 1; // maximum column index inclusive

        // Get coordinates and the corresponding new room based on the direction
        int[] newCoordinates = getNewCoordinates(row, col, maxRow, maxCol, theDirection);
        row = newCoordinates[0];
        col = newCoordinates[1];
        Room newRoom = validateAndGetRoom(row, col, maxRow, maxCol);

        chanceToSpawnMonster(newRoom); // May or may not spawn a monster

        myCurrentRoom = newRoom; // moves the character by updating the room

        debugPrintDungeon(row, col);

        notifyObservers();

        handleEvents();

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
     * Creates and initializes a Dungeon instance based on the difficulty level provided
     * in the given GameSettings. The dungeon dimensions are determined based on the
     * difficulty level: "easy", "normal", or "hard". If an invalid or unknown difficulty
     * level is specified, an IllegalArgumentException is thrown.
     *
     * @param theGameSettings an instance of GameSettings containing the difficulty
     *                        level and other configuration details for the game.
     * @return a new Dungeon instance with dimensions corresponding to the specified
     *         difficulty level.
     * @throws IllegalArgumentException if the difficulty level from GameSettings is
     *                                  unknown or invalid.
     */
    private Dungeon createDungeon(final GameSettings theGameSettings) {
        return switch (theGameSettings.getDifficulty().toLowerCase()) {
            case "easy" -> new Dungeon(5, 5);
            case "normal" -> new Dungeon(7, 7);
            case "hard" -> new Dungeon(9, 9);
            default -> throw new IllegalArgumentException("Unknown/Invalid Difficulty level.");
        };
    }

    private int[] getNewCoordinates(final int theRow, final int theCol, final int theMaxRow,
                                    final int theMaxCol, final Direction theDirection) {

        int newRow = theRow;
        int newCol = theCol;

        switch (theDirection) {
            case NORTH -> { if (theRow > 0) newRow--; }
            case SOUTH -> { if (theRow < theMaxRow) newRow++; }
            case EAST -> {if (theCol < theMaxCol) newCol++; }
            case WEST -> { if (theCol > 0) newCol--; }
        }

        return new int[] {newRow, newCol};

    }

    private Room validateAndGetRoom(final int theRow, final int theCol, final int theMaxRow, final int theMaxCol) {

        if (theRow < 0 || theRow > theMaxRow || theCol < 0 || theCol > theMaxCol) {
            throw new IllegalArgumentException("Row and/or column coordinates of the given directional step are invalid");
        }

        return myDungeon.getRoom(theRow, theCol);

    }

    private void chanceToSpawnMonster(final Room theRoom) {

        if (theRoom.isEntranceOrExit()) {
            return;
        }

        double monsterSpawnChance = Math.random();
        final String difficulty = myGameSettings.getDifficulty().toLowerCase();
        boolean spawnMonster = switch (difficulty) {
            case "easy" -> monsterSpawnChance > 0.75;
            case "normal" -> monsterSpawnChance > 0.66;
            case "hard" -> monsterSpawnChance > 0.50;
            default -> false;
        };

        if (spawnMonster) {
            theRoom.addMonster();
        }

    }

    private void debugPrintDungeon(final int theRow, final int theCol) {

        // FIXME DEBUGGING
        System.out.println(myDungeon.toStringWithPlayer(theRow, theCol));
        System.out.println();
        System.out.println(myDungeon.toDetailedString(theRow, theCol));
        System.out.println();

    }

    private void handleEvents() {

        // To clear the label if present. If there is an event, a message will appear anyway
        // from the following if conditions.
        for (final DungeonObserver observer : myObservers) {
            observer.onRoomEntered(myCurrentRoom, myHero);
        }

        if (myCurrentRoom.hasMonster()) {
            final Monster monster = myCurrentRoom.getMonster();
            for (final DungeonObserver observer : myObservers) {
                // GameViewController will be notified and update the UI accordingly.
                // This means movement buttons are disabled and battle buttons are enabled.
                observer.onBattleStart(myCurrentRoom, myHero, monster);
            }
            return;
        }

        if (myCurrentRoom.hasPit()) {
            myHero.takeDamage(PIT_DAMAGE); // Take damage from the pit and update the UI.
            for (final DungeonObserver observer : myObservers) {
                observer.onPitDamageTaken(myCurrentRoom, myHero, PIT_DAMAGE);
            }
        }

        if (myCurrentRoom.hasItems()) {
            // Preston will handle this
        }

        // Check if we are at the exit room of the dungeon
        if (myCurrentRoom.getCenterSymbol() == 'O') {

            for (final DungeonObserver observer : myObservers) {
                observer.onExitRoomEntered(myCurrentRoom, myHero);
            }

        }

    }

    /**
     * Retrieves the current game settings for the game session.
     * The returned GameSettings object encapsulates details such as
     * the player's name, the selected hero, and the difficulty level.
     *
     * @return the current instance of GameSettings representing the
     *         configuration settings for the game session.
     */
    public GameSettings getGameSettings() {return myGameSettings;}

    public Room getCurrentRoom() {return myCurrentRoom;}

    public Dungeon getDungeon() {return myDungeon;}

    public Hero getHero() {return myHero;}

    /**
     * Adds a DungeonObserver to the list of observers.
     * The added observer will be notified of changes in the dungeon state,
     * such as updates to the current room, hero, or inventory.
     *
     * @param theObserver the DungeonObserver to be added to the list of observers
     */
    public void addObserver(final DungeonObserver theObserver) {myObservers.add(theObserver);}

    /**
     * Removes a specified DungeonObserver from the list of observers.
     * The removed observer will no longer be notified of changes in the dungeon state.
     *
     * @param theObserver the DungeonObserver to be removed from the list of observers
     */
    public void removeObserver(final DungeonObserver theObserver) {myObservers.remove(theObserver);}

    /**
     * Notifies all registered observers of changes in the current game state.
     * This method retrieves the current room from the dungeon based on the
     * player's current position and is intended to be called whenever a significant
     * update occurs that observers need to be informed about.
     */
    private void notifyObservers() {
        final Room currentRoom = getCurrentRoom();
        for (DungeonObserver observer : myObservers) {
            observer.update(currentRoom, myHero, myHero.getInventory());
        }
    }

    public void notifyBattleEnd(final boolean theHeroWon) {
        for (DungeonObserver observer : myObservers) {
            observer.onBattleEnd(myCurrentRoom, myHero, theHeroWon);
        }
    }

}