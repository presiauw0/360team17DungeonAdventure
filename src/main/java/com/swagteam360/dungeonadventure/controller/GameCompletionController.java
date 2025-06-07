package com.swagteam360.dungeonadventure.controller;

import com.swagteam360.dungeonadventure.model.*;
import com.swagteam360.dungeonadventure.utility.GUIUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

import java.util.Set;

/**
 * GameCompletionController handles the user interaction and event logic for the final scene of the game.
 * This scene displays the dungeon layout along with a New Game button and a Quit Game button.
 *
 * @author Jonathan Hernandez
 * @version 1.1 (June 5th, 2025)
 */
public class GameCompletionController {

    /**
     * Cell size for each room of the dungeon. This accommodates up to the size for harder dungeons (9x9).
     */
    private final static int CELL_SIZE = 30;

    /**
     * FXML instance field for the GridPane that displays the dungeon layout.
     */
    @FXML
    private GridPane myDungeonGrid;

    /**
     * Initializes the GridPane with the dungeon layout.
     */
    @FXML
    private void initialize() {

        final Dungeon dungeon = GameManager.getInstance().getDungeon();

        final int rows = dungeon.getRowSize();
        final int cols = dungeon.getColSize();

        myDungeonGrid.getColumnConstraints().clear();
        myDungeonGrid.getRowConstraints().clear();
        myDungeonGrid.getChildren().clear();

        for (int col = 0; col < cols; col++) {
            final ColumnConstraints cc = new ColumnConstraints(CELL_SIZE);
            myDungeonGrid.getColumnConstraints().add(cc);
        }

        for (int row = 0; row < rows; row++) {
            final RowConstraints rc = new RowConstraints(CELL_SIZE);
            myDungeonGrid.getRowConstraints().add(rc);
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                final Room room = dungeon.getRoom(row, col);
                final StackPane roomPane = getStackPane(room);

                // Room contents
                final Label label = new Label();
                label.setStyle("-fx-font-size: 14;");

                // If there are items, we can represent them here
                if (room.isEntrance()) {
                    label.setText("🚪");
                } else if (room.hasPillar()) {
                    label.setText("💎");
                } else if (room.hasMonster()) {
                    label.setText("👹");
                } else if (room.isExit()) {
                    label.setText("🔚");
                } else if (room.hasPit()){
                    label.setText("🌀");
                } else if (room.hasItems()) {
                    label.setText("📦");
                }

                roomPane.getChildren().add(label);
                myDungeonGrid.add(roomPane, col, row);
            }
        }

    }

    /**
     * Returns the StackPane (room/cell) to be added to the GridPane, which represents the dungeon layout.
     * Borders are determined according to whether the room has a door or not.
     * @param theRoom The room to be examined, which influences the room's border style.
     * @return The StackPane (room/cell) to be added to the GridPane.
     */
    private static StackPane getStackPane(final Room theRoom) {
        final StackPane roomPane = new StackPane();
        roomPane.setPrefSize(CELL_SIZE, CELL_SIZE);

        // Use wall info to adjust borders
        final Set<Direction> available = theRoom.getAvailableDirections();

        final String top = available.contains(Direction.NORTH) ? "0" : "1";
        final String right = available.contains(Direction.EAST) ? "0" : "1";
        final String bottom = available.contains(Direction.SOUTH) ? "0" : "1";
        final String left = available.contains(Direction.WEST) ? "0" : "1";

        final String borderStyle = String.format(
                "-fx-border-color: black; -fx-border-width: %s %s %s %s;",
                top, right, bottom, left
        );
        roomPane.setStyle(borderStyle + " -fx-background-color: white;"); // To make cells white
        return roomPane;
    }

    /**
     * Handles the event where the user starts a new game. They are redirected to the game customization screen.
     *
     * @param theActionEvent The event to be examined and handled.
     */
    @FXML
    private void newGame(final ActionEvent theActionEvent) {
        final FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/swagteam360/dungeonadventure/game-customization.fxml"));
        GUIUtils.switchScene(theActionEvent, loader);
    }

    /**
     * Handles the event where the user clicks the Quit Game button. This method promptly exits the application.
     */
    @FXML
    private void quitGame() {
        Platform.exit();
    }

}
