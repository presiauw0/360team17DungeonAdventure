package com.swagteam360.dungeonadventure.view;


import com.swagteam360.dungeonadventure.model.IRoom;
import com.swagteam360.dungeonadventure.model.Room;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RoomView extends Canvas {

    private IRoom.RoomViewModel[][] myRoomMatrix;
    private IRoom.RoomViewModel myCurrentRoom;

    public RoomView(final int theWidth, final int theHeight) {
        super((double)theWidth, (double)theHeight);
    }

    public void updateRoom(final IRoom.RoomViewModel[][] theRooms) {
        myRoomMatrix = theRooms;
        myCurrentRoom = theRooms[1][1];
        drawRoom(getGraphicsContext2D());
    }

    private void drawRoom(GraphicsContext theGc) {
        // Clear Canvas
        theGc.clearRect(0, 0, getWidth(), getHeight());

        // Draw stuff
        drawDoors(theGc);
    }

    private void drawDoors(final GraphicsContext theGc) {
        theGc.setLineWidth(6);
        theGc.setStroke(Color.GRAY);

        // x coordinates 0, 1
        double[] xBounds = {getWidth() * (1.0/3), getWidth() * (2.0/3)};
        // y coordinates 0, 1
        double[] yBounds = {getHeight() * (1.0/3), getHeight() * (2.0/3)};

        // x coordinates 1/4, 3/4
        double[] xStops = {xBounds[0] + (1.0/4) * (xBounds[1] - xBounds[0]),
                            xBounds[0] + (3.0/4) * (xBounds[1] - xBounds[0])};

        // y coordinates 1/4, 3/4
        double[] yStops = {yBounds[0] + (1.0/4) * (yBounds[1] - yBounds[0]),
                            yBounds[0] + (3.0/4) * (yBounds[1] - yBounds[0])};

        // top line
        theGc.strokeLine(xBounds[0], yBounds[0], xStops[0], yBounds[0]);
        theGc.strokeLine(xStops[1], yBounds[0], xBounds[1], yBounds[0]);

        // bottom line
        theGc.strokeLine(xBounds[0], yBounds[1], xStops[0], yBounds[1]);
        theGc.strokeLine(xStops[1], yBounds[1], xBounds[1], yBounds[1]);

        // left line
        theGc.strokeLine(xBounds[0], yBounds[0], xBounds[0], yStops[0]);
        theGc.strokeLine(xBounds[0], yStops[1], xBounds[0], yBounds[1]);

        // right line
        theGc.strokeLine(xBounds[1], yBounds[0], xBounds[1], yStops[0]);
        theGc.strokeLine(xBounds[1], yStops[1], xBounds[1], yBounds[1]);

        // add the middle thirds if the walls are up
        if (myCurrentRoom.topWall()) {
            theGc.strokeLine(xStops[0], yBounds[0], xStops[1], yBounds[0]);
        }
        if (myCurrentRoom.bottomWall()) {
            theGc.strokeLine(xStops[0], yBounds[1], xStops[1], yBounds[1]);
        }
        if (myCurrentRoom.leftWall()) {
            theGc.strokeLine(xBounds[0], yStops[0], xBounds[0], yStops[1]);
        }
        if (myCurrentRoom.rightWall()) {
            theGc.strokeLine(xBounds[1], yStops[0], xBounds[1], yStops[1]);
        }
    }

}
