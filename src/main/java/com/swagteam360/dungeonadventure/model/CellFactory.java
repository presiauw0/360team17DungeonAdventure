package com.swagteam360.dungeonadventure.model;

/**
 * Defines the behavior of a cell factory
 * used to create CellFactory-implementing
 * objects. In addition to creating cell objects,
 * The cell factory is responsible for holding
 * the starting and ending coordinates in terms
 * of row and column numbers in order to build
 * objects.
 *
 * @author Preston Sia (psia97)
 * @version 1.00, 10 May 2025
 */
public interface CellFactory {
    /**
     * Creates an instance of the cell-implementing class
     * @param theRow Row coordinate
     * @param theCol Column coordinate
     * @return A cell object created from the factory
     */
    Cell createCell(final int theRow, final int theCol);

    /**
     * Get the entrance row coordinate used by the cell factory.
     * @return Row coordinate
     */
    int getEntranceRow();

    /**
     * Get the entrance column coordinate used by the cell factory.
     * @return Column coordinate
     */
    int getEntranceCol();

    /**
     * Get the exit row coordinate used by the cell factory.
     * @return Row coordinate
     */
    int getExitRow();

    /**
     * Get the exit column coordinate used by the cell factory.
     * @return Column coordinate
     */
    int getExitCol();
}
