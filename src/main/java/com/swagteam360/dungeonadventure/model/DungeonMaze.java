package com.swagteam360.dungeonadventure.model;

import java.util.Stack;

/**
 * DungeonMaze auto-generates a maze
 * using an implementation of a randomized
 * depth-first search algorithm. This class
 * represents the maze of rooms used for
 * the game.
 *
 * @author Preston Sia (psia97)
 * @version 1.00, 02 May 2025
 */
public final class DungeonMaze {
    /**
     * Default width of the maze
     */
    private static final int DEFAULT_WIDTH = 6;
    /**
     * Default height of the maze
     */
    private static final int DEFAULT_HEIGHT = 4;
    /**
     * Grid of Cells representing the maze
     */
    private final Cell[][] myRoomGrid;
    /**
     * Reference to the cell factory
     */
    private final CellFactory myCellFactory;

    /**
     * Constructor for the maze using default size parameters
     */
    public DungeonMaze(final CellFactory theCellFactory) {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, theCellFactory);
    }

    /**
     * Constructs a new Dungeon Maze using
     * a randomized depth-first search algorithm.
     *
     * @param theRows The desired height (number of rows) of the maze
     * @param theCols The desired width (number of columns) or the maze
     * @param theCellFactory The factory for creating Cell objects
     */
    public DungeonMaze(final int theRows, final int theCols,
                       final CellFactory theCellFactory) {
        // Explicit call to superclass
        super();

        myCellFactory = theCellFactory;

        myRoomGrid = new Cell[theRows][theCols];    // Create a new 2D array
        generateField();            // Generate an empty field
        buildMaze();                // Build a maze from the empty field
    }

    /**
     * Print a prettier "collapsed"
     * version of the maze.
     */
    public void printMaze() {
        final StringBuilder mainString = new StringBuilder();

        // This will "collapse" the maze representation
        // by reading the top and left wall of each cell
        // to avoid duplicates
        for (int i = 0; i < myRoomGrid.length; i++) {
            final StringBuilder top = new StringBuilder();      // Store chars representing the upper walls
            final StringBuilder middle = new StringBuilder();   // Left and right walls, path chars

            for (int j = 0; j < myRoomGrid[i].length; j++) {
                top.append(myRoomGrid[i][j].hasTopWall() ? "##" : "# "); // append top wall status
                middle.append(myRoomGrid[i][j].hasLeftWall() ? "# " : "  "); // append left wall status

                // add extra characters at the end
                if (j == myRoomGrid[i].length - 1) {
                    top.append("#");
                    middle.append(myRoomGrid[i][j].hasRightWall() ? "#" : " "); // check right wall status at the end
                }
            }

            mainString.append(top).append("\n");
            mainString.append(middle).append("\n");

            // print the last row of rows
            if (i == myRoomGrid.length - 1) {
                for (int k = 0; k < myRoomGrid[myRoomGrid.length - 1].length; k++) {
                    mainString.append("#");
                    // add characters for bottom wall status
                    mainString.append(myRoomGrid[myRoomGrid.length - 1][k].hasBottomWall() ? "#" : " ");
                }
                mainString.append("#");
            }
        }

        System.out.println(mainString);
    }


    /**
     * Generate an empty field with walls surrounding
     * every cell.
     */
    private void generateField() {
        for (int i = 0; i < myRoomGrid.length; i++) {
            for (int j = 0; j < myRoomGrid[i].length; j++) {
                // Create a new instance of a cell (enclosed by 4 walls by default)
                // for each position of the 2D array/grid
                myRoomGrid[i][j] = new Room(false, false, i, j); // FIXME
            }
        }
    }

    /**
     * Build a maze out of the grid of cells
     * using an implementation of the randomized
     * depth-first search algorithm.
     */
    private void buildMaze() {
        Cell current = myRoomGrid[0][0];                // Pick the top-left corner to be the starting point
        final Stack<Cell> cellStack = new Stack<>();    // Create a stack to track cells

        current.markTraversalVisit();   // Set the corner cell to be marked as visited
        cellStack.push(current);        // push the corner cell to the stack


        while (!cellStack.isEmpty()) { // Continue processing until the stack is empty (all are visited)
            current = cellStack.pop(); // pop stack, set as current

            if (hasUnvisitedNeighbors(current.getRow(), current.getCol())) {
                cellStack.push(current); // push it back onto the stack if unvisited neighbors exist

                // Pick a random cell that hasn't been visited yet
                Cell randomUnvisitedCell = pickRandomUnvisitedCell(current.getRow(), current.getCol());
                // remove the walls between the current cell and randomly picked cell
                removeWalls(current, randomUnvisitedCell);
                // Mark the unvisited cell as visited
                randomUnvisitedCell.markTraversalVisit();
                // Push the selected cell onto the stack
                cellStack.push(randomUnvisitedCell);
            }
        }
    }

    /**
     * Check if a cell at the specified position has
     * unvisited neighbors by checking the cells
     * above, below, and to the left and right.
     *
     * @param theRow Row of the cell to be evaluated.
     * @param theCol Column of the cell to be evaluated.
     * @return Boolean representing whether unvisited neighbors exist.
     */
    private boolean hasUnvisitedNeighbors(final int theRow, final int theCol) {
        // will be set as true if a room is visited, false if not. True is the default
        // in the case that a coordinate is not valid (on an edge).
        boolean top = true;
        boolean bottom = true;
        boolean left = true;
        boolean right = true;
        if (theRow - 1 >= 0) { // check top if in bounds
            top = myRoomGrid[theRow - 1][theCol].traversalVisitFlag();
        }
        if (theRow + 1 < myRoomGrid.length) { // check bottom if in range
            bottom = myRoomGrid[theRow + 1][theCol].traversalVisitFlag();
        }
        if (theCol - 1 >= 0) { // check if the left is in range
            left = myRoomGrid[theRow][theCol - 1].traversalVisitFlag();
        }
        if (theCol + 1 < myRoomGrid[theRow].length) { // check if the right is in range
            right = myRoomGrid[theRow][theCol + 1].traversalVisitFlag();
        }
        return !(top && bottom && left && right); // Either all have been visited, or at least one has not
    }

    /**
     * Randomly pick an unvisited cell the neighbors
     * a cell at the specified coordinate. The output
     * is either the top, left, bottom or right neighboring
     * cell, selected at random.
     *
     * @param theRow The row of the cell to be evaluated.
     * @param theCol The column of the cell to be evaluated.
     * @return The randomly selected neighboring cell that is marked as unvisited.
     */
    private Cell pickRandomUnvisitedCell(final int theRow, final int theCol) {
        // offset coordinates representing top, left, bottom, right
        final int[][] cellOffsets = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        int random;         // Store a random value between 0 and the length of cellOffsets
        int randRowCoord;   // Store the coordinate of the randomly selected neighbor
        int randColCoord;   // Store the coordinate of the randomly selected neighbor

        do { // regenerate coordinates if they aren't valid or don't map to an unvisited square
            random = (int)(Math.random() * cellOffsets.length);
            randRowCoord = theRow + cellOffsets[random][0];
            randColCoord = theCol + cellOffsets[random][1];
        } while (!isValidCoord(randRowCoord, randColCoord)
                || myRoomGrid[randRowCoord][randColCoord].traversalVisitFlag());

        return myRoomGrid[randRowCoord][randColCoord]; // return the cell at the randomly selected coordinate
    }

    /**
     * Remove the walls between 2 cells.
     *
     * @param theCellCurrent The current cell.
     * @param theCellAdj The neighboring cell evaluated relative to the current cell.
     */
    private void removeWalls(final Cell theCellCurrent, final Cell theCellAdj) {
        final int dx = theCellAdj.getCol() - theCellCurrent.getCol(); // change in x (columns)
        final int dy = theCellAdj.getRow() - theCellCurrent.getRow(); // change in y (rows)
        if (dx == -1 && dy == 0) { // remove left wall
            theCellCurrent.setLeftWall(false);
            theCellAdj.setRightWall(false);
        } else if (dx == 1 && dy == 0) { // remove right wall
            theCellCurrent.setRightWall(false);
            theCellAdj.setLeftWall(false);
        } else if (dx == 0 && dy == -1) { // remove upper wall
            theCellCurrent.setTopWall(false);
            theCellAdj.setBottomWall(false);
        } else if (dx == 0 && dy == 1) { // remove lower wall
            theCellCurrent.setBottomWall(false);
            theCellAdj.setTopWall(false);
        }
    }

    /**
     * Check if a pair of row/column coordinates are valid.
     * Coordinates are valid if they are greater than 0 and
     * less than the size of the grid.
     * @param theRow Row coordinate
     * @param theCol Column coordinate
     * @return Boolean value corresponding to whether the coordinate pair is valid.
     */
    private boolean isValidCoord(final int theRow, final int theCol) {
        final boolean rowValid = theRow >= 0 && theRow < myRoomGrid.length;
        final boolean colValid = theCol >= 0 && theCol < myRoomGrid[0].length;
        return rowValid && colValid;
    }

    /**
     * {@inheritDoc}
     *
     * Returns the state of the maze.
     * Dots (.) represent a valid path,
     * while a number/hash (#) symbol represents
     * a wall.
     *
     * @return String representation of the maze.
     */
    @Override
    public String toString() {
        final StringBuilder mainString = new StringBuilder();
        for (Cell[] row : myRoomGrid) {
            final StringBuilder top = new StringBuilder();      // Store chars representing the upper walls
            final StringBuilder middle = new StringBuilder();   // Left and right walls, path chars
            final StringBuilder bottom = new StringBuilder();   // lower walls

            for (Cell cell : row) {
                top.append(cell.hasTopWall() ? "###" : "# #");
                middle.append(cell.hasLeftWall() ? "#." : " .");
                middle.append(cell.hasRightWall() ? "#" : " ");
                bottom.append(cell.hasBottomWall() ? "###" : "# #");
            }
            mainString.append(top).append("\n");
            mainString.append(middle).append("\n");
            mainString.append(bottom).append("\n");
        }
        return mainString.toString();
    }



}
