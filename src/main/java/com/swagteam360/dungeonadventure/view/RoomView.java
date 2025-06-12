package com.swagteam360.dungeonadventure.view;


import com.swagteam360.dungeonadventure.model.IRoom;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RoomView extends Canvas {
    /**
     * Color of the doors.
     */
    private static final Color DOOR_COLOR = Color.WHITE;
    /**
     * Color of the walls.
     */
    private static final Color WALL_COLOR = Color.rgb(136, 108, 108);
    /**
     * Color of null rooms - call it the "void."
     */
    private static final Color VOID_COLOR = Color.BLACK;
    /**
     * Color of the rooms which have not been visited yet.
     */
    private static final Color UNVISITED_COLOR = Color.rgb(40, 40, 40);
    /**
     * Width of the walls.
     */
    private static final int WALL_WIDTH = 8;

    /**
     * Stores the player's current room and all surrounding rooms.
     */
    private IRoom.RoomViewModel[][] myRoomMatrix;

    /**
     * String representing the type of hero character.
     */
    private final String myCharType;

    /**
     * Flag indicating whether the player
     * can see all surrounding rooms.
     */
    private boolean myVisionPowers;


    /**
     * Create a new room view with a specified width and height.
     * @param theWidth Width
     * @param theHeight Height
     */
    public RoomView(final int theWidth, final int theHeight, final String theCharType) {
        super((double)theWidth, (double)theHeight);
        myCharType = theCharType;
        myVisionPowers = false;
    }

    /**
     * Updates the state of RoomView with a new 2D matrix of rooms
     * with the room in the center being the room that the player is
     * currently in.
     * @param theRooms 2D array of rooms.
     */
    public void updateRoom(final IRoom.RoomViewModel[][] theRooms) {
        myRoomMatrix = theRooms;
        drawRoom(getGraphicsContext2D());
    }

    public void setVisionPowers(final boolean theValue) {
        myVisionPowers = theValue;
        if (myRoomMatrix != null) {
            drawRoom(getGraphicsContext2D());
        }
    }

    /**
     * Delegates tasks for drawing rooms.
     * @param theGc 2D graphics context
     */
    private void drawRoom(GraphicsContext theGc) {
        // Clear Canvas
        theGc.clearRect(0, 0, getWidth(), getHeight());

        // Draw null spaces
        for (int i = 0; i < myRoomMatrix.length; i++) {
            for (int j = 0; j < myRoomMatrix[i].length; j++) {
                // Draw a room if it's not null
                if (myRoomMatrix[i][j] == null) {
                    drawNullSpace(theGc, i, j);
                }
            }
        }

        // Draw unvisited rooms
        for (int i = 0; i < myRoomMatrix.length; i++) {
            for (int j = 0; j < myRoomMatrix[i].length; j++) {
                if (myRoomMatrix[i][j] != null && !myRoomMatrix[i][j].visited()) {
                    // If vision powers are enabled (vision potion used),
                    // draw the unvisited rooms as visited rooms.
                    if (myVisionPowers) {
                        drawDoors(theGc, i, j);
                    } else {
                        drawUnvisitedRoom(theGc, i, j);
                    }
                }
            }
        }

        // Draw visited rooms
        for (int i = 0; i < myRoomMatrix.length; i++) {
            for (int j = 0; j < myRoomMatrix[i].length; j++) {
                if (myRoomMatrix[i][j] != null && myRoomMatrix[i][j].visited()) {
                    drawDoors(theGc, i, j);
                }
            }
        }

        drawCharacter(theGc);
    }

    /**
     * Draws the doors for the room at the specified location.
     * @param theGc GraphicsContext to use
     * @param theRow Row coordinate in the room matrix where the target room is
     * @param theCol Column coordinate in the room matrix where the target room is
     */
    private void drawDoors(final GraphicsContext theGc, final int theRow, final int theCol) {
        theGc.setLineWidth(WALL_WIDTH);
        theGc.setStroke(WALL_COLOR);

        // x coordinates 0, 1 representing the vertices of the current room
        double[] xBounds = getXBounds(theCol);
        // y coordinates 0, 1 representing the vertices of the current room
        double[] yBounds = getYBounds(theRow);

        // x coordinates 1/4, 3/4
        double[] xStops = {xBounds[0] + (1.0/4) * (xBounds[1] - xBounds[0]),
                            xBounds[0] + (3.0/4) * (xBounds[1] - xBounds[0])};

        // y coordinates 1/4, 3/4
        double[] yStops = {yBounds[0] + (1.0/4) * (yBounds[1] - yBounds[0]),
                            yBounds[0] + (3.0/4) * (yBounds[1] - yBounds[0])};

        // top line - e.g. |---    ---| (wall without the door)
        theGc.strokeLine(xBounds[0], yBounds[0], xStops[0], yBounds[0]);
        theGc.strokeLine(xStops[1], yBounds[0], xBounds[1], yBounds[0]);

        // bottom line - e.g. |---   ---| (wall without the door)
        theGc.strokeLine(xBounds[0], yBounds[1], xStops[0], yBounds[1]);
        theGc.strokeLine(xStops[1], yBounds[1], xBounds[1], yBounds[1]);

        // left line - wall without the door, but vertical
        theGc.strokeLine(xBounds[0], yBounds[0], xBounds[0], yStops[0]);
        theGc.strokeLine(xBounds[0], yStops[1], xBounds[0], yBounds[1]);

        // right line - wall without the door, but vertical
        theGc.strokeLine(xBounds[1], yBounds[0], xBounds[1], yStops[0]);
        theGc.strokeLine(xBounds[1], yStops[1], xBounds[1], yBounds[1]);

        // add the middle thirds if the walls are up
        if (myRoomMatrix[theRow][theCol].topWall()) {
            theGc.setStroke(WALL_COLOR);
            theGc.strokeLine(xStops[0], yBounds[0], xStops[1], yBounds[0]);
        } else {
            theGc.setStroke(DOOR_COLOR);
            theGc.strokeLine(xStops[0], yBounds[0], xStops[1], yBounds[0]);
        }

        if (myRoomMatrix[theRow][theCol].bottomWall()) {
            theGc.setStroke(WALL_COLOR);
            theGc.strokeLine(xStops[0], yBounds[1], xStops[1], yBounds[1]);
        } else {
            theGc.setStroke(DOOR_COLOR);
            theGc.strokeLine(xStops[0], yBounds[1], xStops[1], yBounds[1]);
        }

        if (myRoomMatrix[theRow][theCol].leftWall()) {
            theGc.setStroke(WALL_COLOR);
            theGc.strokeLine(xBounds[0], yStops[0], xBounds[0], yStops[1]);
        } else {
            theGc.setStroke(DOOR_COLOR);
            theGc.strokeLine(xBounds[0], yStops[0], xBounds[0], yStops[1]);
        }

        if (myRoomMatrix[theRow][theCol].rightWall()) {
            theGc.setStroke(WALL_COLOR);
            theGc.strokeLine(xBounds[1], yStops[0], xBounds[1], yStops[1]);
        } else {
            theGc.setStroke(DOOR_COLOR);
            theGc.strokeLine(xBounds[1], yStops[0], xBounds[1], yStops[1]);
        }
    }

    private void drawUnvisitedRoom(final GraphicsContext theGc, final int theRow, final int theCol) {
        final double[] xBounds = getXBounds(theCol);
        final double[] yBounds = getYBounds(theRow);
        final double width = getWidth() * (1.0 / myRoomMatrix[0].length);
        final double height = getHeight() * (1.0 / myRoomMatrix.length);
        theGc.setLineWidth(WALL_WIDTH);
        theGc.setStroke(WALL_COLOR);
        theGc.setFill(UNVISITED_COLOR);
        theGc.fillRect(xBounds[0], yBounds[0], width, height);
        theGc.strokeRect(xBounds[0], yBounds[0], width, height);
    }

    private void drawNullSpace(final GraphicsContext theGc, final int theRow, final int theCol) {
        double[] xBounds = getXBounds(theCol);
        double[] yBounds = getYBounds(theRow);
        final double width = getWidth() * (1.0 / myRoomMatrix[0].length);
        final double height = getHeight() * (1.0 / myRoomMatrix.length);

        theGc.setFill(VOID_COLOR);
        theGc.fillRect(xBounds[0], yBounds[0], width, height);
    }

    private double[] getXBounds(final int theCol) {
        int roomCols = myRoomMatrix[0].length; // TOTAL number of room columns (X)
        return new double[]{getWidth() * ((double)(theCol)/roomCols), getWidth() * ((double)(theCol + 1)/roomCols)};
    }

    private double[] getYBounds(final int theRow) {
        int roomRows = myRoomMatrix.length; // TOTAL number of room rows (Y)
        return new double[]{getHeight() * ((double)(theRow)/roomRows), getHeight() * ((double)(theRow + 1)/roomRows)};
    }

    private void drawCharacter(final GraphicsContext theGc) {

        Image image;

        // find specified image
        try (final FileInputStream imageStream = new FileInputStream("src/main/resources/images/" + myCharType + "_model.png")) {
            image = new Image(imageStream);
            theGc.drawImage(image, getWidth()/2 - image.getWidth()/2, getHeight()/2 - image.getHeight()/2);

        } catch (FileNotFoundException e) {
            System.out.println("Image not found: " + e);
        } catch (IOException e) {
            System.out.println("Image not loaded: " + e);
        }

    }

}
