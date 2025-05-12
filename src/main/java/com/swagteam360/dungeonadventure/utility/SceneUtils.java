package com.swagteam360.dungeonadventure.utility;

import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * The SceneUtils class provides utility methods for managing JavaFX scenes
 * within the application. It includes methods for switching between scenes
 * with predefined dimensions and applying a specified CSS stylesheet to the new scene.
 * <p>
 * This class is designed to streamline the process of changing scenes in a JavaFX
 * application, ensuring consistent dimensions and theming across different views.
 * It is intended to be used in conjunction with event handlers to handle user
 * interactions that trigger scene transitions.
 *
 * @author Jonathan Hernandez
 * @version 1.0 (May 11, 2025)
 */
public class SceneUtils {

    /**
     * Represents the fixed width of the scene in pixels.
     * Used for specifying the width when creating a new {@link Scene}.
     * This value is constant and cannot be modified.
     */
    private static final double WIDTH = 600;

    /**
     * Represents the fixed height of the scene in pixels.
     * Used for specifying the height when creating a new {@link Scene}.
     * This value is constant and cannot be modified.
     */
    private static final double HEIGHT = 400;

    /**
     * Switches the current scene in the JavaFX application to a new scene defined by the provided
     * FXMLLoader and theme stylesheet. The new scene is created with fixed dimensions and includes
     * a specified CSS stylesheet for customization.
     *
     * @param theEvent  the ActionEvent that triggered the scene switch; typically an event from a JavaFX Node.
     * @param theLoader the FXMLLoader used to load the new FXML layout for the scene.
     * @param theTheme  the path to the CSS stylesheet to be applied to the new scene.
     * @throws RuntimeException if an IOException occurs while loading the FXML layout.
     */
    public static void switchScene(final ActionEvent theEvent, final FXMLLoader theLoader,
                                    final String theTheme) {

        try {
            final Parent root = theLoader.load();
            final Scene scene = new Scene(root, WIDTH, HEIGHT);
            scene.getStylesheets().add(Objects.requireNonNull
                    (SceneUtils.class.getResource(theTheme)).toExternalForm());

            final Stage stage = (Stage) ((javafx.scene.Node) theEvent.getSource())
                    .getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
