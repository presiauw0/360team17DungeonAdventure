package com.swagteam360.dungeonadventure.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * The StartMenuView class serves as the (temporary) entry point for a JavaFX application
 * that displays a start menu for the Dungeon Adventure game.
 * <p>
 * This class is responsible for loading the start menu UI from an FXML file,
 * applying associated styles, and initializing the primary stage of the application.
 *
 * @author Jonathan Hernandez
 * @version 1.0 (May 4th, 2025)
 */
public class StartMenuView extends Application {

    /**
     * Entry point method for JavaFX.
     * @param theArgs Command line arguments, which are ignored in this application.
     */
    public static void main(String[] theArgs) {
        launch(theArgs);
    }

    /**
     * Initializes the primary stage of the application and loads the start menu UI.
     * @param thePrimaryStage The primary stage of the application.
     */
    @Override
    public void start(Stage thePrimaryStage) {

        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/swagteam360/dungeonadventure/start-menu.fxml")));
            Scene scene = new Scene(root, 600, 400);
            scene.getStylesheets().add(Objects.requireNonNull(getClass()
                    .getResource("/com/swagteam360/dungeonadventure/light-theme.css")).toExternalForm());

            thePrimaryStage.setScene(scene);
            thePrimaryStage.setResizable(false);
            thePrimaryStage.show();

        } catch (Exception e) {
            throw new RuntimeException(e); // Might want to log this or implement UI feedback.
        }

    }
}
