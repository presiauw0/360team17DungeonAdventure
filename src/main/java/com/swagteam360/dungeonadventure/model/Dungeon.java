package com.swagteam360.dungeonadventure.model;

import java.util.Stack;

/**
 * The Dungeon class contains logic related to the
 * operations of the maze.
 *
 * @author Preston Sia (psia97), Jonathan Hernandez
 * @version 1.01, 20 May 2025
 */
public final class Dungeon {

    /**
     * Represents the number of rows making up the maze.
     * A value of y means there are y rows (1, 2, ..., y).
     */
    private final int myRowSize;
    /**
     * Represents the number of columns making up the maze.
     * A value of x means there are x rows (1, 2, ..., x).
     */
    private final int myColSize;
    /**
     * Instance of the dungeon maze which generates
     * the maze structure and stores the rooms.
     */
    private final DungeonMaze myDungeonMaze;

    /**
     * Represents the row index of the dungeon's entrance.
     * This value is set during the dungeon's creation and
     * defines the starting point for traversing or exploring
     * the dungeon.
     */
    private final int myEntranceRow;

    /**
     * Represents the column index of the entrance in the dungeon maze.
     * This value is set when the dungeon is initialized and remains constant
     * throughout the lifetime of the Dungeon instance.
     */
    private final int myEntranceCol;

    public Dungeon(final int theRowSize, final int theColSize) {
        super(); // explicit call to super

        if (theRowSize < 0 || theColSize < 0) {
            throw new IllegalArgumentException("Row and column size cannot be negative");
        }

        // Set row and column size
        myRowSize = theRowSize;
        myColSize = theColSize;

        // Determine start and end coordinates
        final int startCol = randomGen(0, theColSize);
        final int endCol = randomGen(0, theColSize);

        myEntranceRow = 0;
        myEntranceCol = startCol;

        // Create room factory with start and end coordinates
        final CellFactory roomFactory = new RoomFactory(0, startCol,
                theRowSize - 1, endCol);

        // Create new maze using the room factory
        myDungeonMaze = new DungeonMaze(myRowSize, myColSize, roomFactory);

        // Place pillars in the maze
        placePillarsInRooms();
    }

    public Room getRoom(final int theRow, final int theCol) {
        Cell mazeCell = myDungeonMaze.getCell(theRow, theCol);
        if (mazeCell instanceof Room) {
            return (Room) mazeCell;
        } else {
            throw new ClassCastException("Incorrect cell type stored in the maze. Failed to cast type.");
        }
    }

    private Stack<Pillar> generatePillars() {
        final Stack<Pillar> pillarStack = new Stack<>();

        for (PillarType ptype : PillarType.values()) {
            pillarStack.push(new Pillar(ptype));
        }

        return pillarStack;
    }

    private void placePillarsInRooms() {
        final Stack<Pillar> pillarStack = generatePillars();

        while (!pillarStack.isEmpty()) {
            int randomRow = randomGen(0, myRowSize);
            int randomCol = randomGen(0, myColSize);

            if (getRoom(randomRow, randomCol).isEntranceOrExit()
                || getRoom(randomRow, randomCol).hasPillar()) {
                continue;
            } else {
                getRoom(randomRow, randomCol).setPillar(pillarStack.pop());
            }
        }
    }

    /**
     * Generates a random number between values
     * theStart and (theEnd - 1) inclusive.
     * @param theStart Starting value (inclusive)
     * @param theEnd Ending value (non-inclusive)
     * @return A random integer between the specified range
     */
    private int randomGen(final int theStart, final int theEnd) {
        return (int)(Math.random() * (theEnd-theStart)) + theStart;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < myRowSize; i++) {
            final StringBuilder top = new StringBuilder();      // Store upper wall characters
            final StringBuilder middle = new StringBuilder();   // store left, right and center data

            for (int j = 0; j < myColSize; j++) {
                Room room = getRoom(i, j);
                top.append(room.hasTopWall() ? "##" : "# ");   // append top wall chars
                middle.append(room.hasLeftWall() ? "#" : " "); // append left, right and middle data
                middle.append(room.getCenterSymbol());

                // add right wall characters
                if (j == myColSize - 1) {
                    top.append("#");
                    middle.append(room.hasRightWall() ? "#" : " ");
                }
            }

            sb.append(top).append("\n");
            sb.append(middle).append("\n");

            // append the bottom wall info for the bottom row
            if (i == myRowSize - 1) {
                for (int k = 0; k < myColSize; k++) {
                    sb.append("#").append(getRoom(i, k).hasBottomWall() ? "#" : " ");
                }
                sb.append("#");
            }
        }

        return sb.toString();
    }

    /**
     * Retrieves the row coordinate of the entrance in the dungeon.
     *
     * @return An integer representing the row index of the dungeon's entrance.
     */
    public int getEntranceRow() {
        return myEntranceRow;
    }

    /**
     * Retrieves the column coordinate of the entrance in the dungeon.
     *
     * @return An integer representing the column index of the dungeon's entrance.
     */
    public int getEntranceCol() {
        return myEntranceCol;
    }

    /**
     * Retrieves the number of rows in the dungeon.
     *
     * @return An integer representing the total number of rows in the dungeon.
     */
    public int getRowSize() {
        return myRowSize;
    }

    /**
     * Retrieves the number of columns in the dungeon.
     *
     * @return An integer representing the total number of columns in the dungeon.
     */
    public int getColSize() {
        return myColSize;
    }
}
