package com.swagteam360.dungeonadventure.view;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RoomView extends Canvas {

//    @Override
//    public void update(Room theRoom, Hero theHero, List<Item> theInventory) {
//        // TODO: IMPLEMENT THIS
//    }

    public RoomView(final int theWidth, final int theHeight) {
        super((double)theWidth, (double)theHeight);
        drawRoom(getGraphicsContext2D());
    }

    private void drawRoom(GraphicsContext theGc) {
        // Clear Canvas
        theGc.clearRect(0, 0, getWidth(), getHeight());

        // Draw stuff
        theGc.setFill(Color.RED);
        theGc.setStroke(Color.BLUE);
        theGc.setLineWidth(5);
        theGc.strokeLine(40, 10, 10, 40);
        theGc.fillOval(10, 60, 30, 30);
        theGc.fillRoundRect(110, 60, 30, 30, 10, 10);
    }

}
