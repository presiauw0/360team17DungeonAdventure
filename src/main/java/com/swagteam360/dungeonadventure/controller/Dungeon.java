package com.swagteam360.dungeonadventure.controller;

import java.util.Arrays;

public final class Dungeon {

    private static final int DEFAULT_WIDTH = 10;
    private static final int DEFAULT_HEIGHT = 10;

    private final int[][] myRoomList; // temporary
    private final int myDungeonWidth;
    private final int myDungeonHeight;

    public Dungeon() {
        myRoomList = new int[DEFAULT_HEIGHT][DEFAULT_WIDTH];
        myDungeonWidth = DEFAULT_WIDTH;
        myDungeonHeight = DEFAULT_HEIGHT;
        generateDungeon(0, 0, myDungeonHeight - 1, myDungeonWidth - 1);
    }

    private void generateDungeon(final int theTop, final int theLeft,
                            final int theBottom, final int theRight) {
        //final int size = Math.max((theBottom - theTop), (theRight - theLeft));
        final int rowSize = theBottom - theTop;
        final int colSize = theRight - theLeft;

        if (rowSize > 1 && colSize > 1) {
            final int randRow = (int)(Math.random() * rowSize) + theTop; // where to put horizontal wall
            final int randCol = (int)(Math.random() * colSize) + theLeft; // where to put vertical wall
            int xGap; // x coordinate of random gap to be placed in the horizontal wall
            int yGap; // y coordinate of random gap to be placed in the vertical wall

            // determine random gaps but make sure they don't overlap with the other walls
            do {
                xGap = (int)(Math.random() * colSize) + theLeft;
                yGap = (int)(Math.random() * rowSize) + theTop;
            } while(xGap == randCol || yGap == randRow);

            // construct horizontal wall
            for (int i = theLeft; i <= theRight; i++) {
                if (i != xGap) {
                    myRoomList[randRow][i] = 1;
                }
            }

            // construct vertical wall
            for (int j = theTop; j <= theBottom; j++) {
                if (j != yGap) {
                    myRoomList[j][randCol] = 1;
                }
            }

            // debug
            System.out.println(this);
            System.out.println(xGap + ", " + yGap);

            // Recursive Calls
            generateDungeon(theTop, theLeft, randRow - 1, randCol - 1);
            generateDungeon(theTop, randCol + 1, randRow - 1, theRight);
            generateDungeon(randRow + 1, theLeft, theBottom, randCol - 1);
            generateDungeon(randRow + 1, randCol + 1, theBottom, theRight);

        }
    }

    private boolean validateMaze() {
        return false;
    }

    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        for (int[] row : myRoomList) {
            for (int space : row) {
                if (space == 1) {
                    s.append("#");
                } else {
                    s.append(".");
                }
            }

            s.append("\n");
        }
        return s.toString();
    }
}
