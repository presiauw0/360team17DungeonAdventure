package com.swagteam360.dungeonadventure.model;

import java.util.ArrayList;
import java.util.List;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static com.swagteam360.dungeonadventure.model.PillarType.*;

/**
 * The GameManager class serves as a singleton responsible for managing the game's lifecycle.
 * It handles game initialization, including the setup of game settings and the creation
 * of hero characters based on the specified configurations in the GameSettings object.
 *
 * @author Jonathan Hernandez
 * @version 1.1 (May 20, 2025)
 */
public class GameManager {

    private static final int PIT_DAMAGE = 10;
    /**
     * The maximum number of rooms the player can
     * visit before the vision potion runs out.
     */
    private static final int MAX_SUPER_VISION_ROOMS = 3;

    private static final double MONSTER_SPAWN_CHANCE_EASY = 0.25;
    private static final double MONSTER_SPAWN_CHANCE_NORMAL = 0.34;
    private static final double MONSTER_SPAWN_CHANCE_HARD = 0.50;

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

    /**
     * Indicates if vision powers given by the vision
     * potion should apply.
     */
    private boolean mySuperVision;

    /**
     * Keep track of how many rooms have been visited before
     * the vision powers wear off.
     */
    private int mySuperVisionCounter;

    private final PropertyChangeSupport myPCS = new PropertyChangeSupport(this);

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
        mySuperVision = false;
        mySuperVisionCounter = 0;

        // Debugging
        Pillar b = new Pillar(BRONZE);
        Pillar s = new Pillar(SILVER);
        Pillar g = new Pillar(GOLD);
        Pillar p = new Pillar(PLATINUM);
        List<Item> list = new ArrayList<>();
        list.add(b);
        list.add(s);
        list.add(g);
        list.add(p);

        myHero.addToInventory(list);

        //FIXME DEBUG
        System.out.println(myDungeon.toStringWithPlayer(myCurrentRoom.getRow(), myCurrentRoom.getCol()));
        System.out.println();
        System.out.println(myDungeon.toDetailedString(myCurrentRoom.getRow(), myCurrentRoom.getCol()));

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

        // Update super vision counters if a vision potion is applied
        updateSuperVision();

        debugPrintDungeon(row, col);

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
            case "warrior" -> HeroFactory.createHero("Warrior");
            case "priestess" -> HeroFactory.createHero("Priestess");
            case "thief" -> HeroFactory.createHero("Thief");
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
            return; // TODO: Would we like to add a monster at the exit?
        }

        double monsterSpawnChance = Math.random();
        final String difficulty = myGameSettings.getDifficulty().toLowerCase();
        boolean spawnMonster = switch (difficulty) {
            case "easy" -> monsterSpawnChance > (1 - MONSTER_SPAWN_CHANCE_EASY);
            case "normal" -> monsterSpawnChance > (1 - MONSTER_SPAWN_CHANCE_NORMAL);
            case "hard" -> monsterSpawnChance > (1 - MONSTER_SPAWN_CHANCE_HARD);
            default -> false;
        };

        if (spawnMonster) {
            theRoom.addMonster();
        }

    }

    public void enableSuperVision() {
        mySuperVision = true;
        mySuperVisionCounter = 0;
        // Fire property change
        myPCS.firePropertyChange("VISION_POWERS", false, true);
    }

    private void updateSuperVision() {
        if (mySuperVision && mySuperVisionCounter < MAX_SUPER_VISION_ROOMS) {
            mySuperVisionCounter++;
        } else {
            // DISABLE super vision
            mySuperVision = false;
            mySuperVisionCounter = 0;
            // Fire property change
            myPCS.firePropertyChange("VISION_POWERS", true, false);
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

        myPCS.firePropertyChange("Clear Label", null, null);

        if (myCurrentRoom.hasMonster()) {
            final Monster monster = myCurrentRoom.getMonster();
            myPCS.firePropertyChange("Fight", null, monster);
            return;
        }

        if (myCurrentRoom.hasPit()) {
            myHero.takeDamage(PIT_DAMAGE); // Take damage from the pit and update the UI.
            myPCS.firePropertyChange("Pit", null, PIT_DAMAGE);
        }

        // In case we die from the pit
        if (myHero.getHP() <= 0) {
            myPCS.firePropertyChange("Dead", null, myHero);
        }

        if (myCurrentRoom.hasItems()) {
            List<Item> roomItems = myCurrentRoom.collectAllItems();
            myHero.addToInventory(roomItems);
            myPCS.firePropertyChange("INVENTORY_CHANGE", null, myHero.getInventory());
        }

        // Check if we are at the exit room of the dungeon
        if (myCurrentRoom.isExit()) {
            myPCS.firePropertyChange("Exit", null, myCurrentRoom);
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

    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.addPropertyChangeListener(theListener);
        //handleEvents(); // Immediately SEND INVENTORY property updates to the registered listener.
        myPCS.firePropertyChange("INVENTORY_CHANGE", null, myHero.getInventory());
    }
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.removePropertyChangeListener(theListener);
    }

}