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

public class GameCompletionController {

    @FXML
    private GridPane myDungeonGrid;

    @FXML
    private void initialize() {

        final Dungeon dungeon = GameManager.getInstance().getDungeon();

        final int rows = dungeon.getRowSize();
        final int cols = dungeon.getColSize();

        final int cellSize = 30;

        myDungeonGrid.getColumnConstraints().clear();
        myDungeonGrid.getRowConstraints().clear();
        myDungeonGrid.getChildren().clear();

        for (int col = 0; col < cols; col++) {
            ColumnConstraints cc = new ColumnConstraints(cellSize);
            myDungeonGrid.getColumnConstraints().add(cc);
        }

        for (int row = 0; row < rows; row++) {
            RowConstraints rc = new RowConstraints(cellSize);
            myDungeonGrid.getRowConstraints().add(rc);
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                Room room = dungeon.getRoom(row, col);
                StackPane roomPane = getStackPane(cellSize, room);

                // Room contents
                Label label = new Label();
                label.setStyle("-fx-font-size: 14;");

                if (room.isEntrance()) {
                    label.setText("🟩");
                } else if (room.hasPillar()) {
                    label.setText("🔷");
                } else if (room.hasMonster()) {
                    label.setText("👹");
                } else if (room.isExit()) {
                    label.setText("🚪");
                } else {
                    label.setText(""); // Or some placeholder
                }

                roomPane.getChildren().add(label);
                myDungeonGrid.add(roomPane, col, row);
            }
        }

    }

    private static StackPane getStackPane(final int theCellSize, final Room theRoom) {
        StackPane roomPane = new StackPane();
        roomPane.setPrefSize(theCellSize, theCellSize);

        // Use wall info to adjust borders
        Set<Direction> available = theRoom.getAvailableDirections();

        String top = available.contains(Direction.NORTH) ? "0" : "1";
        String right = available.contains(Direction.EAST) ? "0" : "1";
        String bottom = available.contains(Direction.SOUTH) ? "0" : "1";
        String left = available.contains(Direction.WEST) ? "0" : "1";

        String borderStyle = String.format(
                "-fx-border-color: black; -fx-border-width: %s %s %s %s;",
                top, right, bottom, left
        );
        roomPane.setStyle(borderStyle);
        return roomPane;
    }

    @FXML
    private void newGame(final ActionEvent theActionEvent) {
        final FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/swagteam360/dungeonadventure/game-customization.fxml"));
        GUIUtils.switchScene(theActionEvent, loader);
    }

    @FXML
    private void quitGame() {
        Platform.exit();
    }

}
