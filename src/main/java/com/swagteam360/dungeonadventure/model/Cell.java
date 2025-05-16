package com.swagteam360.dungeonadventure.model;

/**
 * Defines the behavior of a maze cell.
 * A maze cell has four mutable walls,
 * and a row and column describing its location.
 * A cell also must have a mechanism to
 * keep track of whether it has been visited
 * by the maze generation algorithm.
 *
 * @author Preston Sia (psia97)
 * @version 1.00, 10 May 2025
 */
public interface Cell {
    /**
     * Indicates if the left wall is up.
     * @return True or false, depending on the wall status
     */
    boolean hasLeftWall();

    /**
     * Indicates if the right wall is up.
     * @return True or false, depending on the wall status
     */
    boolean hasRightWall();

    /**
     * Indicates if the upper wall is up.
     * @return True or false, depending on the wall status
     */
    boolean hasTopWall();

    /**
     * Indicates if the lower wall is up.
     * @return True or false, depending on the wall status
     */
    boolean hasBottomWall();

    /**
     * Set the status of the left wall.
     */
    void setLeftWall(final boolean theStatus);

    /**
     * Set the status of the right wall.
     */
    void setRightWall(final boolean theStatus);

    /**
     * Set the status of the upper wall.
     */
    void setTopWall(final boolean theStatus);

    /**
     * Set the status of the lower wall.
     */
    void setBottomWall(final boolean theStatus);

    /**
     * Get the row coordinate.
     * @return Integer representing the row coordinate
     */
    int getRow();

    /**
     * Get the column coordinate.
     * @return Integer representing the column coordinate
     */
    int getCol();

    /**
     * Indicates if the cell has been traversed or not
     * @return True or false, indicating whether the cell has been visited
     *  by the traversal algorithm.
     */
    boolean traversalVisitFlag();

    /**
     * Mark that the cell has been visited.
     */
    void markTraversalVisit();
}
