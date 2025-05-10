package com.swagteam360.dungeonadventure.model;

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
