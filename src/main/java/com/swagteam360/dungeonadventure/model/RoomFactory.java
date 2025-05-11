package com.swagteam360.dungeonadventure.model;

public class RoomFactory implements CellFactory {
    private final int myEntranceRow;
    private final int myEntranceCol;
    private final int myExitRow;
    private final int myExitCol;

    public RoomFactory(final int theEntranceRow, final int theEntranceCol,
                       final int theExitRow, final int theExitCol) {

        super();

        myEntranceRow = theEntranceRow;
        myEntranceCol = theEntranceCol;
        myExitRow = theExitRow;
        myExitCol = theExitCol;
    }

    public Cell createCell(final int theRow, final int theCol) {
        Cell roomToReturn;
        /*
        return new Room(false, false,
                theRow, theCol);*/
        if (theRow == myEntranceRow && theCol == myEntranceCol) { // create an entrance room
            roomToReturn = new Room(IRoom.PROPERTY_ENTRANCE, theRow, theCol);
        } else if (theRow == myExitRow && theCol == myExitCol) { // create an exit room
            roomToReturn = new Room(IRoom.PROPERTY_EXIT, theRow, theCol);
        } else { // create a normal room
            roomToReturn = new Room(IRoom.PROPERTY_NORMAL, theRow, theCol);
        }

        return roomToReturn;
    }

    public int getEntranceRow() {
        return myEntranceRow;
    }

    public int getEntranceCol() {
        return myEntranceCol;
    }

    public int getExitRow() {
        return myExitRow;
    }

    public int getExitCol() {
        return myExitCol;
    }
}
