package com.swagteam360.dungeonadventure.model;

public interface CellFactory {
    Cell createCell(final int theRow, final int theCol);
    int getEntranceRow();
    int getEntranceCol();
    int getExitRow();
    int getExitCol();
}
