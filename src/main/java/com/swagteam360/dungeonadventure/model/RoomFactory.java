package com.swagteam360.dungeonadventure.model;

/**
 * A concrete class that creates Room instances.
 * Objects are constructed with information on
 * where the entrance and exit is located, and
 * creates the proper room type accordingly.
 *
 * @author Preston Sia (psia97)
 * @version 1.00, 10 May 2025
 */
public class RoomFactory implements CellFactory {
    /**
     * The row that the entrance room will reside.
     */
    private final int myEntranceRow;
    /**
     * The column that the entrance room will reside.
     */
    private final int myEntranceCol;
    /**
     * The row that the exit room will reside.
     */
    private final int myExitRow;
    /**
     * The column where the exit room will reside.
     */
    private final int myExitCol;

    /**
     * Creates a room factory which helps to generate rooms.
     * @param theEntranceRow Entrance room row
     * @param theEntranceCol Entrance room column
     * @param theExitRow Exit room row
     * @param theExitCol Exit room column
     */
    public RoomFactory(final int theEntranceRow, final int theEntranceCol,
                       final int theExitRow, final int theExitCol) {

        super();

        myEntranceRow = theEntranceRow;
        myEntranceCol = theEntranceCol;
        myExitRow = theExitRow;
        myExitCol = theExitCol;
    }

    @Override
    public Cell createCell(final int theRow, final int theCol) {
        Cell roomToReturn;
        if (theRow == myEntranceRow && theCol == myEntranceCol) { // create an entrance room
            roomToReturn = new Room(IRoom.PROPERTY_ENTRANCE, theRow, theCol);
        } else if (theRow == myExitRow && theCol == myExitCol) { // create an exit room
            roomToReturn = new Room(IRoom.PROPERTY_EXIT, theRow, theCol);
        } else { // create a normal room
            roomToReturn = new Room(IRoom.PROPERTY_NORMAL, theRow, theCol);
        }

        return roomToReturn;
    }

    @Override
    public int getEntranceRow() {
        return myEntranceRow;
    }

    @Override
    public int getEntranceCol() {
        return myEntranceCol;
    }

    @Override
    public int getExitRow() {
        return myExitRow;
    }

    @Override
    public int getExitCol() {
        return myExitCol;
    }
}
