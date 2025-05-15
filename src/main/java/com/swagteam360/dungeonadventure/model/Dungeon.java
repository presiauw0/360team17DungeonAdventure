package com.swagteam360.dungeonadventure.model;

/**
 * The Dungeon class contains logic related to the
 * operations of the maze.
 *
 * @author Preston Sia (psia97)
 * @version 1.00, 09 May 2025
 */
public final class Dungeon {

    private final int myRowSize;
    private final int myColSize;
    /**
     * Instance of the dungeon maze which generates
     * the maze structure and stores the rooms.
     */
    private final DungeonMaze myDungeonMaze;

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

        // Create room factory with start and end coordinates
        final CellFactory roomFactory = new RoomFactory(0, startCol,
                theRowSize - 1, endCol);

        // Create new maze using the room factory
        myDungeonMaze = new DungeonMaze(myRowSize, myColSize, roomFactory);

    }

    public Room getRoom(final int theRow, final int theCol) {
        Cell mazeCell = myDungeonMaze.getCell(theRow, theCol);
        if (mazeCell instanceof Room) {
            return (Room) mazeCell;
        } else {
            throw new ClassCastException("Incorrect cell type stored in the maze. Failed to cast type.");
        }
    }

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
}
