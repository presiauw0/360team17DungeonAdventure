package com.swagteam360.dungeonadventure.model;

public class RoomFactory implements CellFactory {
    public Cell createCell(final int theRow, final int theCol) {
        return new Room(false, false,
                theRow, theCol);
    }
}
