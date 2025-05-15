package com.swagteam360.dungeonadventure.utility;

import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
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
 * @version 1.1 (May 14, 2025)
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
     * The file path for the CSS file used to apply the dark theme in the application.
     * This is a constant value that represents the resource location of the dark theme stylesheet.
     * It is used to set the application's styles when dark mode is enabled.
     */
    private static final String DARK_THEME = "/com/swagteam360/dungeonadventure/dark-theme.css";

    /**
     * Represents the file path to the light theme CSS file used in the application's graphical interface.
     * The theme is applied to the user interface when the application is in light mode.
     */
    private static final String LIGHT_THEME = "/com/swagteam360/dungeonadventure/light-theme.css";

    /**
     * A static boolean variable indicating whether the application is in dark mode.
     * When set to true, the application uses a dark theme. If set to false, the
     * application uses a light theme.
     * <p>
     * This variable is used to determine the current theme and affects the
     * behavior of theme-related methods such as {@code getCurrentTheme()} and
     * {@code toggleDarkMode()}.
     */
    private static boolean darkMode = false;

    /**
     * Switches the current scene in the JavaFX application to a new scene defined by the provided
     * FXMLLoader and theme stylesheet. The new scene is created with fixed dimensions and includes
     * a specified CSS stylesheet for customization.
     *
     * @param theEvent  the ActionEvent that triggered the scene switch; typically an event from a JavaFX Node.
     * @param theLoader the FXMLLoader used to load the new FXML layout for the scene.
     * @throws RuntimeException if an IOException occurs while loading the FXML layout.
     */
    public static void switchScene(final ActionEvent theEvent, final FXMLLoader theLoader) {

        try {
            final Parent root = theLoader.load();
            final Scene scene = new Scene(root, WIDTH, HEIGHT);
            scene.getStylesheets().add(Objects.requireNonNull
                    (SceneUtils.class.getResource(getCurrentTheme())).toExternalForm());

            final Stage stage = (Stage) ((javafx.scene.Node) theEvent.getSource())
                    .getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Retrieves the path to the current theme used in the application.
     * This method determines the theme based on the application's dark mode setting.
     *
     * @return the string representation of the file path to the current theme stylesheet.
     */
    private static String getCurrentTheme() {
        return darkMode ? DARK_THEME : LIGHT_THEME;
    }

    /**
     * Applies the current theme to the specified JavaFX scene.
     * Clears any existing stylesheets applied to the scene and adds the stylesheet
     * corresponding to the active theme.
     *
     * @param theScene the JavaFX Scene to which the theme should be applied.
     *                 This scene will have its current stylesheets cleared, and the new theme stylesheet will be added.
     * @throws NullPointerException if the resource for the current theme is not found.
     */
    public static void applyTheme(final Scene theScene) {
        theScene.getStylesheets().clear();
        theScene.getStylesheets().add(Objects.requireNonNull(SceneUtils.class.getResource(getCurrentTheme()))
                .toExternalForm());
    }

    /**
     * Initializes the dark mode toggle button to reflect the current application theme.
     * If the toggle button is not null, its selected state is set based on the current dark mode setting.
     *
     * @param theToggleButton the ToggleButton that represents the dark mode toggle in the user interface.
     *                        This button's state will be updated to match the current dark mode preference.
     */
    public static void initializeDarkModeToggle(final ToggleButton theToggleButton) {
        if (theToggleButton != null) {
            theToggleButton.setSelected(darkMode);
        }
    }

    /**
     * Toggles the application's theme between dark mode and light mode.
     * Updates the dark mode setting based on the selected state of the provided toggle button
     * and applies the appropriate theme to the current user interface.
     *
     * @param theToggleButton the ToggleButton representing the dark mode toggle in the user interface.
     *                        The button's selected state is used to determine whether dark mode or light mode
     *                        should be activated. The current theme of the application is updated accordingly.
     */
    public static void toggleDarkMode(final ToggleButton theToggleButton) {
        darkMode = theToggleButton.isSelected();
        applyTheme(theToggleButton.getScene());
    }

}
