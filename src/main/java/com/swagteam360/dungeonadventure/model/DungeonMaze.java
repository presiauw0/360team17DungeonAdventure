package com.swagteam360.dungeonadventure.model;

import java.util.Stack;

public final class DungeonMaze {
    private static final int DEFAULT_WIDTH = 8;
    private static final int DEFAULT_HEIGHT = 8;

    private final Cell[][] myRoomGrid;

    public DungeonMaze() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public DungeonMaze(final int theRows, final int theCols) {
        myRoomGrid = new Cell[theRows][theCols];
        generateField();
        buildMaze();
    }

    private void generateField() {
        for (int i = 0; i < myRoomGrid.length; i++) {
            for (int j = 0; j < myRoomGrid[i].length; j++) {
                myRoomGrid[i][j] = new Cell(i, j);
            }
        }
    }

    private void buildMaze() {
        Cell current = myRoomGrid[0][0];
        final Stack<Cell> cellStack = new Stack<>();

        current.myVisited = true;
        cellStack.push(current);

        while (!cellStack.isEmpty()) { // Continue processing until the stack is empty (all are visited)
            current = cellStack.pop(); // pop stack, set as current
            if (hasUnvisitedNeighbors(current.myRow, current.myCol)) {
                cellStack.push(current); // push it back onto the stack if unvisited neighbors exist
                Cell randomUnvisitedCell = pickRandomUnvisitedCell(current.myRow, current.myCol);
                removeWalls(current, randomUnvisitedCell);
                randomUnvisitedCell.myVisited = true;
                cellStack.push(randomUnvisitedCell);
            }
        }
    }

    private boolean hasUnvisitedNeighbors(final int theRow, final int theCol) {
        // will be set as true if a room is visited, false if not. True is the default
        // in the case that a coordinate is not valid (on an edge).
        boolean top = true;
        boolean bottom = true;
        boolean left = true;
        boolean right = true;
        if (theRow - 1 >= 0) { // check top if in bounds
            top = myRoomGrid[theRow - 1][theCol].myVisited;
        }
        if (theRow + 1 < myRoomGrid.length) { // check bottom if in range
            bottom = myRoomGrid[theRow + 1][theCol].myVisited;
        }
        if (theCol - 1 >= 0) {
            left = myRoomGrid[theRow][theCol - 1].myVisited;
        }
        if (theCol + 1 < myRoomGrid[theRow].length) {
            right = myRoomGrid[theRow][theCol + 1].myVisited;
        }
        return !(top && bottom && left && right);
    }

    private Cell pickRandomUnvisitedCell(final int theRow, final int theCol) {
        final int[][] cellOffsets = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        int random;
        int randRowCoord;
        int randColCoord;

        do { // regenerate coordinates if they aren't valid or don't map to an unvisited square
            random = (int)(Math.random() * cellOffsets.length);
            randRowCoord = theRow + cellOffsets[random][0];
            randColCoord = theCol + cellOffsets[random][1];
        } while (!isValidCoord(randRowCoord, randColCoord)
                || myRoomGrid[randRowCoord][randColCoord].myVisited);

        return myRoomGrid[randRowCoord][randColCoord];
    }

    private void removeWalls(final Cell theCellCurrent, final Cell theCellAdj) {
        final int dx = theCellAdj.myCol - theCellCurrent.myCol;
        final int dy = theCellAdj.myRow - theCellCurrent.myRow;
        if (dx == -1 && dy == 0) { // remove left wall
            theCellCurrent.myLeftWall = false;
            theCellAdj.myRightWall = false;
        } else if (dx == 1 && dy == 0) { // remove right wall
            theCellCurrent.myRightWall = false;
            theCellAdj.myLeftWall = false;
        } else if (dx == 0 && dy == -1) { // remove upper wall
            theCellCurrent.myTopWall = false;
            theCellAdj.myBottomWall = false;
        } else if (dx == 0 && dy == 1) { // remove lower wall
            theCellCurrent.myBottomWall = false;
            theCellAdj.myTopWall = false;
        }
    }

    private boolean isValidCoord(final int theRow, final int theCol) {
        final boolean rowValid = theRow >= 0 && theRow < myRoomGrid.length;
        final boolean colValid = theCol >= 0 && theCol < myRoomGrid[0].length;
        return rowValid && colValid;
    }

    @Override
    public String toString() {
        final StringBuilder mainString = new StringBuilder();
        for (Cell[] row : myRoomGrid) {
            final StringBuilder top = new StringBuilder();
            final StringBuilder middle = new StringBuilder();
            final StringBuilder bottom = new StringBuilder();

            for (Cell cell : row) {
                top.append(cell.myTopWall ? "###" : "   ");
                middle.append(cell.myLeftWall ? "#." : " .");
                middle.append(cell.myRightWall ? "#" : " ");
                bottom.append(cell.myBottomWall ? "###" : "   ");
            }
            mainString.append(top).append("\n");
            mainString.append(middle).append("\n");
            mainString.append(bottom).append("\n");
        }
        return mainString.toString();
    }


    private class Cell {
        private boolean myLeftWall;
        private boolean myRightWall;
        private boolean myTopWall;
        private boolean myBottomWall;
        private boolean myVisited;
        private final int myRow;
        private final int myCol;

        public Cell(final int theRow, final int theCol) {
            myLeftWall = true;
            myRightWall = true;
            myTopWall = true;
            myBottomWall = true;
            myVisited = false;

            myRow = theRow;
            myCol = theCol;
        }

        @Override
        public String toString() {
            return "Visited: " + myVisited + "\n" +
                    "Row: " + myRow + "; Column: " + myCol + ";\n" +
                    (myLeftWall ? "Left Wall" : "No Left Wall") + "\n" +
                    (myRightWall ? "Right Wall" : "No Right Wall") + "\n" +
                    (myTopWall ? "Upper Wall" : "No Upper Wall") + "\n" +
                    (myBottomWall ? "Lower Wall" : "No Lower Wall") + "\n";
        }
    }
}
