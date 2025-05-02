package com.swagteam360.dungeonadventure.model;

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

            buildWalls(theTop, theLeft, theBottom, theRight, randRow, randCol); // Build the wall

            // Recursive Calls
            generateDungeon(theTop, theLeft, randRow - 1, randCol - 1);
            generateDungeon(theTop, randCol + 1, randRow - 1, theRight);
            generateDungeon(randRow + 1, theLeft, theBottom, randCol - 1);
            generateDungeon(randRow + 1, randCol + 1, theBottom, theRight);

        }
    }

    private void buildWalls(final int theTop, final int theLeft,
                            final int theBottom, final int theRight,
                            final int randRow, final int randCol) {
        // Randomly determine where to place gaps in walls
        final int rowGap1 = randomGen(theLeft, randCol);                // Pick a gap between left extreme and the vertical wall
        final int rowGap2 = randomGen(randCol + 1, theRight);   // Pick a gap between the vertical wall and the right extreme
        final int colGap1 = randomGen(theTop, randRow);                 // Pick a gap between the top extreme and the horizontal wall
        final int colGap2 = randomGen(randRow + 1, theBottom);  // Pick a gap between the horizontal wall and the bottom extreme

        // Determine if the walls are on one of the edges using gap calculations
        // When walls are on an edge, some of the gaps will equal the wall coordinates
        final boolean colIsOnEdge = (rowGap1 == randCol || rowGap2 == randCol);
        final boolean rowIsOnEdge = (colGap1 == randRow || colGap2 == randRow);

        for (int i = theLeft; i <= theRight; i++) {
            if (i != rowGap1 && i != rowGap2) {
                myRoomList[randRow][i] = 1;
            }
        }

        for (int j = theTop; j <= theBottom; j++) {
            if (j != colGap1 && j != colGap2) {
                myRoomList[j][randCol] = 1;
            }
        }
    }

    private int randomGen(final int theStart, final int theEnd) {
        return (int)(Math.random() * (theEnd - theStart)) + theStart;
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
