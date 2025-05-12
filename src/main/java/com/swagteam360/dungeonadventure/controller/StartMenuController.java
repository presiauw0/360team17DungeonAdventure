package com.swagteam360.dungeonadventure.controller;

import com.swagteam360.dungeonadventure.model.GameManager;
import com.swagteam360.dungeonadventure.model.GameSettings;
import com.swagteam360.dungeonadventure.utility.SceneUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * The StartMenuController class manages the interactions and navigation
 * between the scenes within the application. It handles user inputs
 * and events triggered by various UI elements such as buttons, checkboxes,
 * and menu items. It also provides support for toggling between themes
 * (dark mode and light mode) and facilitates scene switching.
 *
 * @author Jonathan Hernandez
 * @version 1.1 (May 11th, 2025)
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
     * A TextField component in the application's user interface used for inputting the hero's name.
     * This field allows the user to enter or modify the name of their selected hero before starting the game.
     * It is expected to be initialized and managed by the FXMLLoader and is directly tied to the FXML layout of the start menu.
     */
    @FXML
    private TextField heroNameTextField;

    /**
     * Represents a ToggleGroup that manages the selection of hero types in the user interface.
     * This group allows the user to choose one specific hero type among multiple options,
     * typically represented by RadioButtons in the FXML layout.
     * <p>
     * This field is linked to the FXML file using the @FXML annotation to support
     * interaction with the corresponding UI components.
     * <p>
     * The currently selected hero type can be retrieved using the {@code getSelectedHeroType} method.
     */
    @FXML
    private ToggleGroup HeroButtons;

    /**
     * Represents a ToggleGroup for selecting the difficulty level in the game's user interface.
     * This group contains radio buttons corresponding to various difficulty options and allows
     * the user to choose one.
     * <p>
     * The selected difficulty is used by the application logic to determine the difficulty
     * level for the game. It is accessed through methods such as {@code getSelectedDifficulty()}
     * in the containing class to retrieve the currently selected option.
     * <p>
     * This field is linked to an FXML definition and is automatically injected at runtime.
     */
    @FXML
    private ToggleGroup DifficultyButtons;

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
    private void startButtonEvent(final ActionEvent theActionEvent) {
        final FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/swagteam360/dungeonadventure/secondary-menu.fxml"));
        SceneUtils.switchScene(theActionEvent, loader, getCurrentTheme());
    }

    /**
     * Event handler for the Quit button in the application's user interface.
     * Displays a confirmation dialog prompting the user to confirm if they wish to quit
     * the application. If the user confirms, the current application window is closed.
     *
     * @param theActionEvent the ActionEvent triggered by the user's interaction with the Quit button
     */
    @FXML
    private void quitButtonEvent(final ActionEvent theActionEvent) {

        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("Are you sure you want to quit?");
        alert.setContentText("Click OK to exit.");

        final javafx.scene.control.ButtonType result = alert.showAndWait().orElse(javafx.scene.control.ButtonType.CANCEL);

        if (result == javafx.scene.control.ButtonType.OK) {
            final Stage stage = (Stage) ((javafx.scene.Node) theActionEvent.getSource()).getScene().getWindow();
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
    private void newGameEvent(final ActionEvent theActionEvent) {
        final FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/swagteam360/dungeonadventure/game-customization.fxml"));
        SceneUtils.switchScene(theActionEvent, loader, getCurrentTheme());
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
    private void backButtonToStartScreenEvent(final ActionEvent theActionEvent) {
        final FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/swagteam360/dungeonadventure/start-menu.fxml"));
        SceneUtils.switchScene(theActionEvent, loader, getCurrentTheme());
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
    private void backButtonToSecondaryMenuEvent(final ActionEvent theActionEvent) {
        final FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/swagteam360/dungeonadventure/secondary-menu.fxml"));
        SceneUtils.switchScene(theActionEvent, loader, getCurrentTheme());
    }

    /**
     * Event handler for the "About Us" menu item in the application's user interface.
     * Displays an informational dialog that provides details about the Dungeon Adventure project,
     * including its creators, purpose, version, and a short message for the user.
     * <p>
     * This method is invoked via FXML when the "About Us" menu item is clicked.
     */
    @FXML
    private void aboutUsMenuEvent() {

        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
    private void optionsButtonEvent(final ActionEvent theActionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/swagteam360/dungeonadventure/options.fxml"));
        SceneUtils.switchScene(theActionEvent, loader, getCurrentTheme());
    }

    /**
     * Event handler for the Start Game button in the application's user interface.
     * This method initializes game settings based on user input (hero name, hero type,
     * and selected difficulty), starts a new game using these settings, and switches
     * the application to the game view scene.
     *
     * @param theActionEvent the ActionEvent triggered by the user's interaction,
     *                       typically a button press to start the game.
     */
    @FXML
    private void startGameButtonEvent(final ActionEvent theActionEvent) {

        final String heroName = heroNameTextField.getText();
        final String heroType = getSelectedHeroType();
        final String difficulty = getSelectedDifficulty();

        final GameSettings gameSettings = new GameSettings(heroName, heroType, difficulty);
        GameManager.getInstance().startNewGame(gameSettings);

        final FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/swagteam360/dungeonadventure/game-view.fxml"));
        SceneUtils.switchScene(theActionEvent, loader, getCurrentTheme());

    }

    /**
     * Retrieves the type of hero selected in the user interface.
     * If no hero type is currently selected, a default value of "Warrior" is returned.
     *
     * @return the currently selected hero type as a String, or "Warrior" if none is selected.
     */
    private String getSelectedHeroType() {
        final RadioButton selectedHero = (RadioButton) HeroButtons.getSelectedToggle();
        return selectedHero != null ? selectedHero.getText() : "Warrior";
    }

    /**
     * Retrieves the selected difficulty level from the DifficultyButtons toggle group in the user interface.
     * If no difficulty is selected, a default value of "Normal" is returned.
     *
     * @return the currently selected difficulty as a String, or "Normal" if none is selected.
     */
    private String getSelectedDifficulty() {
        final RadioButton selectedDifficulty = (RadioButton) DifficultyButtons.getSelectedToggle();
        return selectedDifficulty != null ? selectedDifficulty.getText() : "Normal";
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
        final Scene scene = darkModeToggle.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(getCurrentTheme())).toExternalForm());
    }
}
