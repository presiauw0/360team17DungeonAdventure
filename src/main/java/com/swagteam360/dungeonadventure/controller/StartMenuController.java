package com.swagteam360.dungeonadventure.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * The StartMenuController class manages the interactions and navigation
 * between the scenes within the application. It handles user inputs
 * and events triggered by various UI elements such as buttons, checkboxes,
 * and menu items. It also provides support for toggling between themes
 * (dark mode and light mode) and facilitates scene switching.
 *
 * @author Jonathan Hernandez
 * @version 1.0 (May 4th, 2025)
 */
public class StartMenuController {

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
     * A CheckBox UI component used to toggle between dark mode and light mode.
     * This variable is linked to the corresponding element in the FXML file for the Start Menu.
     * The state of this checkbox determines whether the application uses the dark or light theme.
     */
    @FXML
    private CheckBox darkModeToggle;

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
     * Event handler for the Start button in the application's user interface.
     * This method loads the secondary menu scene and switches the application's
     * current view to it. The scene is loaded from the corresponding FXML file.
     *
     * @param theActionEvent the ActionEvent triggered by the user's interaction with the Start button
     */
    @FXML
    private void startButtonEvent(ActionEvent theActionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/swagteam360/dungeonadventure/secondary-menu.fxml"));
        switchScene(theActionEvent, loader);
    }

    /**
     * Event handler for the Quit button in the application's user interface.
     * Displays a confirmation dialog prompting the user to confirm if they wish to quit
     * the application. If the user confirms, the current application window is closed.
     *
     * @param theActionEvent the ActionEvent triggered by the user's interaction with the Quit button
     */
    @FXML
    private void quitButtonEvent(ActionEvent theActionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("Are you sure you want to quit?");
        alert.setContentText("Click OK to exit.");

        javafx.scene.control.ButtonType result = alert.showAndWait().orElse(javafx.scene.control.ButtonType.CANCEL);

        if (result == javafx.scene.control.ButtonType.OK) {
            Stage stage = (Stage) ((javafx.scene.Node) theActionEvent.getSource()).getScene().getWindow();
            stage.close();
        }

    }

    /**
     * Event handler for creating a new game in the application's user interface.
     * This method loads the game customization screen and switches the application's
     * current view to it using an FXML file.
     *
     * @param theActionEvent the ActionEvent triggered by the user's interaction, typically
     *                       a button press that indicates the start of a new game.
     */
    @FXML
    private void newGameEvent(ActionEvent theActionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/swagteam360/dungeonadventure/game-customization.fxml"));
        switchScene(theActionEvent, loader);
    }

    /**
     * Event handler that transitions back to the start screen of the application.
     * This method loads the start menu scene from its respective FXML file and switches
     * the application's current scene to it. If loading the FXML file fails, an exception
     * is thrown.
     *
     * @param theActionEvent the ActionEvent triggered by the user's interaction, typically
     *                       a button press to navigate back to the start screen.
     */
    @FXML
    private void backButtonToStartScreenEvent(ActionEvent theActionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/swagteam360/dungeonadventure/start-menu.fxml"));
        switchScene(theActionEvent, loader);
    }

    /**
     * Event handler for navigating back to the secondary menu in the application's user interface.
     * This method loads the secondary menu scene and switches the application's current view to
     * it. The scene is loaded from the corresponding FXML file. Any IOException encountered during
     * the loading process results in a runtime exception being thrown.
     *
     * @param theActionEvent the ActionEvent triggered by the user's interaction, typically
     *                       a button press to navigate back to the secondary menu.
     */
    @FXML
    private void backButtonToSecondaryMenuEvent(ActionEvent theActionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/swagteam360/dungeonadventure/secondary-menu.fxml"));
        switchScene(theActionEvent, loader); // Might be refactored because this method is identical
                                             // to the startButtonEvent method.
    }

    @FXML
    private void aboutUsMenuEvent() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Us");
        alert.setHeaderText("Dungeon Adventure");
        alert.setContentText("""
                Created by Preston Sia, Jonathan Hernandez, and Luke Willis \
                for course project for TCSS360 Spring 2025.
                
                Version 1.0
                
                Enjoy your quest.""");
        alert.showAndWait();

    }

    /**
     * Event handler for the Options button in the application's user interface.
     * This method loads the options screen and switches the application's current view to it.
     * The scene is loaded from the corresponding FXML file. If loading the FXML file fails,
     * this will throw a runtime exception.
     *
     * @param theActionEvent the ActionEvent triggered by the user's interaction, typically
     *                       a button press to navigate to the options screen.
     */
    @FXML
    private void optionsButtonEvent(ActionEvent theActionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/swagteam360/dungeonadventure/options.fxml"));
        switchScene(theActionEvent, loader);
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
     * Initializes the controller's state after the associated FXML file has been loaded.
     * This setup method is automatically invoked by the JavaFX framework.
     * <p>
     * If the dark mode toggle control is not null, it sets the toggle's selected state
     * based on the current dark mode setting. This ensures that the toggle reflects the
     * application's current theme preference.
     */
    @FXML
    private void initialize() {
        if (darkModeToggle != null) {
            darkModeToggle.setSelected(darkMode);
        }
    }

    /**
     * Toggles the application's dark mode setting and updates the current theme applied to the scene.
     * <p>
     * This method checks the state of the dark mode toggle control and updates the internal
     * dark mode preference accordingly. It then clears all current stylesheets from the scene
     * and applies the stylesheet corresponding to the updated theme.
     * <p>
     * The method fetches the appropriate stylesheet using the {@link #getCurrentTheme()} method,
     * which determines the active theme based on the dark mode state. The new theme stylesheet
     * is then applied to the scene for immediate visual changes.
     */
    @FXML
    private void toggleDarkMode() {
        darkMode = darkModeToggle.isSelected();
        Scene scene = darkModeToggle.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(getCurrentTheme())).toExternalForm());
    }

    /**
     * Switches the current scene in the application to a new scene. This method loads
     * the specified FXML file using the provided FXMLLoader, creates a new scene using
     * the loaded layout, applies the current theme stylesheet, and sets it on the current
     * stage. An IOException during the loading process will result in a runtime exception.
     *
     * @param theActionEvent the ActionEvent triggered by the user's interaction, used to
     *                       retrieve the current stage.
     * @param theLoader      the FXMLLoader instance pre-configured with the FXML file to
     *                       be loaded for the new scene transition.
     */
    private void switchScene(ActionEvent theActionEvent, FXMLLoader theLoader) {
        try {
            Parent root = theLoader.load();
            Scene scene = new Scene(root, 600, 400);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(getCurrentTheme())).toExternalForm());
            Stage stage = (Stage) ((javafx.scene.Node) theActionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e); // Might want to log this or implement UI feedback.
        }

    }
}
