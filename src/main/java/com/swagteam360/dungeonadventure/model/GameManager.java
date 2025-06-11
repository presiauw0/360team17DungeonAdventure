package com.swagteam360.dungeonadventure.model;

import java.io.*;
import java.util.List;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The GameManager class serves as a singleton responsible for managing the game's lifecycle.
 * It handles game initialization, including the setup of game settings and the creation
 * of hero characters based on the specified configurations in the GameSettings object.
 * Moving and handling events fire property changes to the controller to update the GUI.
 *
 * @author Jonathan Hernandez
 * @version 1.2 (7 June, 2025)
 */
public final class GameManager {

    /**
     * Constant that represents the damage dealt from a pit.
     */
    private static final int PIT_DAMAGE = 10;
    /**
     * The maximum number of rooms the player can
     * visit before the vision potion runs out.
     */
    private static final int MAX_SUPER_VISION_ROOMS = 3;

    /**
     * The spawn chance of monsters in easy mode.
     */
    private static final double MONSTER_SPAWN_CHANCE_EASY = 0.25;

    /**
     * The spawn chance of monsters in normal mode.
     */
    private static final double MONSTER_SPAWN_CHANCE_NORMAL = 0.34;

    /**
     * The spawn chance of monsters in hard mode.
     */
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

     /**
     * Fires property changes to listeners (primarily controller classes) of GameManager to update the GUI.
     */
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
     * GameSettings object. The current room is retrieved and marked as visited.
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

        myCurrentRoom.setVisited(true);

        debugPrintDungeon(myCurrentRoom.getRow(), myCurrentRoom.getCol()); // DEBUGGING PURPOSES

    }

    /**
     * Moves the player to a specified position in the dungeon by updating the
     * current row, column, and room based on the provided coordinates.
     * Validates that the specified position is within the bounds of the dungeon.
     * A call to a private helper method handleEvents is made in case the room
     * has anything noteworthy to handle.
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
        final int[] newCoordinates = getNewCoordinates(row, col, maxRow, maxCol, theDirection);
        row = newCoordinates[0];
        col = newCoordinates[1];
        final Room newRoom = validateAndGetRoom(row, col, maxRow, maxCol);

        chanceToSpawnMonster(newRoom); // May spawn a monster

        myCurrentRoom = newRoom; // Moves the character by updating the room. That room is then set as visited.
        myCurrentRoom.setVisited(true);

        // Update super vision counters if a vision potion is applied
        updateSuperVision();

        debugPrintDungeon(row, col); // FOR DEBUGGING PURPOSES

        handleEvents();

    }

    // The following two methods were written based on the Serializable example from the modules.

    /**
     * Handles saving logic when called from the controller. Serializable objects are saved to the specified file.
     *
     * @param theFile The file to be written to.
     */
    public void saveGame(final File theFile) {

        try {

            final FileOutputStream file = new FileOutputStream(theFile);
            final ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(myGameSettings);
            out.writeObject(myHero);
            out.writeObject(myDungeon);
            out.writeObject(myCurrentRoom);

            out.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace(); // Might want to log this exception.
        }

    }

    /**
     * Handles loading logic when called from the controller. Serializable objects are loaded from the specified file.
     *
     * @param theFile The file to be read from.
     */
    public void loadGame(final File theFile) {

        try {

            final FileInputStream file = new FileInputStream(theFile);
            final ObjectInputStream in = new ObjectInputStream(file);

            myGameSettings = (GameSettings) in.readObject();
            myHero = (Hero) in.readObject();
            myDungeon = (Dungeon) in.readObject();
            myCurrentRoom = (Room) in.readObject();
            in.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Might want to log this exception
        }
    }

    /**
     * Enable vision powers for the player.
     * Super vision will be active for
     * a certain number of room moves.
     */
    public void enableSuperVision() {
        mySuperVision = true;
        mySuperVisionCounter = 0;
        // Fire property change
        myPCS.firePropertyChange("VISION_POWERS", false, true);
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

    /**
     * Takes in coordinates, bounds, and a direction to return a new set of coordinates.
     *
     * @param theRow The current row.
     * @param theCol The current column.
     * @param theMaxRow The max row size of the dungeon.
     * @param theMaxCol The max column size of the dungeon.
     * @param theDirection The direction in which theRow and theCol may be adjusted to.
     * @return A new set of coordinates that are adjusted from theRow and theCol.
     */
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

    /**
     * Takes in coordinates (in row and column) and bounds and validates them.
     *
     * @param theRow The row to be examined.
     * @param theCol The column to be examined.
     * @param theMaxRow The row bound.
     * @param theMaxCol The column bound.
     * @return The room of the examined rows and columns as long as they are valid.
     */
    private Room validateAndGetRoom(final int theRow, final int theCol, final int theMaxRow, final int theMaxCol) {

        if (theRow < 0 || theRow > theMaxRow || theCol < 0 || theCol > theMaxCol) {
            throw new IllegalArgumentException("Row and/or column coordinates of the given directional step are invalid");
        }

        return myDungeon.getRoom(theRow, theCol);

    }

    /**
     * Temporary method that generates a random percentage and compares it to the monster spawn chance, which may
     * change, according to the selected difficulty. This method may not do anything at all if the monster spawn chance
     * is too low.
     *
     * @param theRoom The room in which the monster may spawn.
     */
    private void chanceToSpawnMonster(final Room theRoom) {

        if (theRoom.isEntranceOrExit()) {
            return; // TODO: Would we like to add a monster at the exit?
        }

        final double monsterSpawnChance = Math.random();
        final String difficulty = myGameSettings.getDifficulty().toLowerCase();
        final boolean spawnMonster = switch (difficulty) {
            case "easy" -> monsterSpawnChance > (1 - MONSTER_SPAWN_CHANCE_EASY);
            case "normal" -> monsterSpawnChance > (1 - MONSTER_SPAWN_CHANCE_NORMAL);
            case "hard" -> monsterSpawnChance > (1 - MONSTER_SPAWN_CHANCE_HARD);
            default -> false;
        };

        if (spawnMonster) {
            theRoom.addMonster();
        }

    }

    private void updateSuperVision() {
        if (mySuperVision) {
            if (mySuperVisionCounter < MAX_SUPER_VISION_ROOMS){
                mySuperVisionCounter++;
            } else {
                // DISABLE super vision
                mySuperVision = false;
                mySuperVisionCounter = 0;
                // Fire property change
                myPCS.firePropertyChange("VISION_POWERS", true, false);
            }
        }
    }

    /**
     * Prints out the toString() methods of the Dungeon to the console for debugging purposes.
     *
     * @param theRow The row in which the Hero may exist, which is outputted to the console.
     * @param theCol The column in which the Hero may exist, which is outputted to the console.
     */
    private void debugPrintDungeon(final int theRow, final int theCol) {

        // FIXME DEBUGGING
        System.out.println(myDungeon.toStringWithPlayer(theRow, theCol));
        System.out.println();
        System.out.println(myDungeon.toDetailedString(theRow, theCol));
        System.out.println();

    }

    /**
     * Fires property changes to listeners according to what the room may have or to pass Hero information.
     */
    private void handleEvents() {

        myPCS.firePropertyChange("Clear Label", null, null);

        myPCS.firePropertyChange("ROOM_CHANGE", null,
                myDungeon.getAdjacentRoomViewModels(myCurrentRoom.getRow(), myCurrentRoom.getCol()));

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

        if (myCurrentRoom.hasItems() || myCurrentRoom.hasPillar()) {
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

    /**
     * Returns the current room.
     *
     * @return The current room.
     */
    public Room getCurrentRoom() {return myCurrentRoom;}

    /**
     * Return an immutable Java Record representing the
     * state of the current room.
     * @return RoomViewModel Java Record of the current room
     */
    public IRoom.RoomViewModel getCurrentRoomViewModel() {
        return myCurrentRoom.getRoomViewModel();
    }

    /**
     * Returns the Dungeon
     *
     * @return The dungeon.
     */
    public Dungeon getDungeon() {return myDungeon;}

    /**
     * Returns the Hero of the game.
     *
     * @return The hero.
     */
    public Hero getHero() {return myHero;}

    /**
     * Adds listeners to GameManager.
     *
     * @param theListener A listener of GameManager.
     */
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.addPropertyChangeListener(theListener);
        //handleEvents(); // Immediately SEND INVENTORY property updates to the registered listener.
        myPCS.firePropertyChange("INVENTORY_CHANGE", null, myHero.getInventory());
        myPCS.firePropertyChange("VISION_POWERS", null, mySuperVision);
        myPCS.firePropertyChange("ROOM_CHANGE", null,
                myDungeon.getAdjacentRoomViewModels(myCurrentRoom.getRow(), myCurrentRoom.getCol()));
    }

    /**
     * Removes listeners of GameManager
     *
     * @param theListener A listener of GameManager
     */
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.removePropertyChangeListener(theListener);
    }

}