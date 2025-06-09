package com.swagteam360.dungeonadventure.controller;

import com.swagteam360.dungeonadventure.model.Direction;
import com.swagteam360.dungeonadventure.model.Dungeon;
import com.swagteam360.dungeonadventure.model.GameManager;
import com.swagteam360.dungeonadventure.model.Room;
import javafx.fxml.FXML;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Set;

/**
 * Controller class for the minimap embedded in the GameView scene. This class updates the minimap according to
 * player movement across rooms. Visited rooms are set visible in the minimap, while unvisited rooms are hidden.
 *
 * @author Jonathan Hernandez
 * @version 1.0 5 June 2025
 */
public final class MinimapController implements PropertyChangeListener {

    /**
     * A reference to a GridPane which represents the dungeon layout to be filled out dynamically in playthrough.
     */
    @FXML
    private GridPane myMinimapGrid;

    /**
     * A reference to the cells of the dungeon minimap.
     */
    private StackPane[][] myCells;

    /**
     * A reference to the dungeon, retrieved when we get the instance of GameManager's dungeon.
     */
    private Dungeon myDungeon;

    /**
     * Constant that represents the max size of the minimap area. Cell size is computed with regard to this constant.
     */
    private static final int MAX_SIZE = 100;

    /**
     * Retrieves dungeon information and constructs the dungeon cell by cell. More logic is contained
     * in the updateMinimap method.
     */
    @FXML
    private void initialize() {

        myDungeon = GameManager.getInstance().getDungeon();
        int rows = myDungeon.getRowSize();
        int cols = myDungeon.getColSize(); // Get dungeon information.

        int CELL_SIZE = MAX_SIZE / Math.max(rows, cols);

        myMinimapGrid.getColumnConstraints().clear();
        myMinimapGrid.getRowConstraints().clear();
        myMinimapGrid.getChildren().clear();

        for (int col = 0; col < cols; col++) {
            myMinimapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        }
        for (int row = 0; row < rows; row++) {
            myMinimapGrid.getRowConstraints().add(new RowConstraints(CELL_SIZE));
        } // Clear constraints

        myCells = new StackPane[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                StackPane cell = new StackPane();
                cell.setPrefSize(CELL_SIZE, CELL_SIZE);
                myCells[row][col] = cell;
                myMinimapGrid.add(cell, col, row);

            }
        } // Set cells

        GameManager.getInstance().addPropertyChangeListener(this);
        updateMinimap();

    }

    /**
     * Handles the event in which the player moves.
     *
     * @param theEvent A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals("Player Moved")) {
            updateMinimap();
        }
    }

    /**
     * Constructs the minimap when called upon. Black cells represent unvisited rooms, white cells represent visited
     * rooms, and a red cell denotes the player's current position within the dungeon.
     */
    private void updateMinimap() {

        int rows = myDungeon.getRowSize();
        int cols = myDungeon.getColSize();
        Room currentRoom = GameManager.getInstance().getCurrentRoom(); // Get dungeon information

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                Room room = myDungeon.getRoom(row, col);
                StackPane cell = myCells[row][col];

                Set<Direction> doors = room.getAvailableDirections();

                final String top = doors.contains(Direction.NORTH) ? "0" : "1";
                final String right = doors.contains(Direction.EAST) ? "0" : "1";
                final String bottom = doors.contains(Direction.SOUTH) ? "0" : "1";
                final String left = doors.contains(Direction.WEST) ? "0" : "1"; // To construct a maze layout

                cell.getChildren().clear();
                StringBuilder style = new StringBuilder();
                style.append(String.format("-fx-border-color: black; -fx-border-width: %s %s %s %s;",
                        top, right, bottom, left));

                if (room == currentRoom) {
                    style.append(" -fx-background-color: red;"); // The current room cell will be red
                } else if (room.isVisited()) {
                    if (room.isEntrance() || room.isExit()) {
                        style.append(" -fx-background-color: green;");
                    } else if (room.hasPit()) {
                        style.append(" -fx-background-color: gray;");
                    } else {
                        style.append(" -fx-background-color: white;");
                    }
                } else {
                    style.append(" -fx-background-color: black;");
                }

                cell.setStyle(style.toString());
            }
        }
    }

}
