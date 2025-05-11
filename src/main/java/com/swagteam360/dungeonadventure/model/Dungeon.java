package com.swagteam360.dungeonadventure.model;

/**
 * The Dungeon class contains logic related to the
 * operations of the maze.
 *
 * @author Preston Sia (psia97)
 * @version 1.00, 09 May 2025
 */
public final class Dungeon {
    /**
     * Instance of the dungeon maze which generates
     * the maze structure and stores the rooms.
     */
    private final DungeonMaze myDungeonMaze;

    public Dungeon(final int theRowSize, final int theColSize) {
        super(); // explicit call to super

        // Determine start and end coordinates
        final int startCol = randomGen(0, theColSize);
        final int endCol = randomGen(0, theColSize);

        // Create room factory with start and end coordinates
        final CellFactory roomFactory = new RoomFactory(0, startCol,
                theColSize - 1, endCol);

        // Create new maze using the room factory
        myDungeonMaze = new DungeonMaze(theRowSize, theColSize, roomFactory);

        myDungeonMaze.printMaze();
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
}
