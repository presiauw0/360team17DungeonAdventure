package com.swagteam360.dungeonadventure.controller;

import com.swagteam360.dungeonadventure.model.GameManager;
import com.swagteam360.dungeonadventure.model.GameSettings;
import com.swagteam360.dungeonadventure.utility.SceneUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * The StartMenuController class manages the interactions and navigation
 * between the scenes within the application. It handles user inputs
 * and events triggered by various UI elements such as buttons, checkboxes,
 * and menu items.
 *
 * @author Jonathan Hernandez
 * @version 1.2 (May 14th, 2025)
 */
public class StartMenuController {

    /**
     * A ToggleButton in the user interface for enabling or disabling dark mode.
     * This button provides users with the option to switch between light mode
     * and dark mode in the application. The state of the button determines the
     * active mode:
     * - Selected: Dark mode is enabled.
     * - Deselected: Light mode is active.
     * <p>
     * This variable is injected via FXML and linked to the application's scene
     * graph. It may be associated with an event handler to execute the necessary
     * logic when the button's state changes.
     */
    @FXML
    private ToggleButton darkModeToggle;

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
        SceneUtils.switchScene(theActionEvent, loader);
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

        final javafx.scene.control.ButtonType result = alert.showAndWait()
                .orElse(javafx.scene.control.ButtonType.CANCEL);

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
        SceneUtils.switchScene(theActionEvent, loader);
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
        SceneUtils.switchScene(theActionEvent, loader);
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
        SceneUtils.switchScene(theActionEvent, loader);
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
        SceneUtils.switchScene(theActionEvent, loader);
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
        SceneUtils.switchScene(theActionEvent, loader);

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
     * Initializes the user interface components of the Start Menu.
     * This method is invoked automatically by the JavaFX framework after
     * the application loads the FXML file for the Start Menu.
     * <p>
     * Specifically, it configures the dark mode toggle button by interfacing
     * with the SceneUtils class to ensure the toggle reflects the current
     * application theme when the scene is loaded.
     */
    @FXML
    private void initialize() {
        SceneUtils.initializeDarkModeToggle(darkModeToggle);
    }

    /**
     * Toggles the application's theme between dark mode and light mode.
     * This method uses the SceneUtils class to change the visual appearance
     * of the application's user interface based on the state of the dark mode toggle.
     * <p>
     * This method is bound to the JavaFX UI component responsible for the dark mode toggle.
     */
    @FXML
    private void toggleDarkMode() {
        SceneUtils.toggleDarkMode(darkModeToggle);
    }
}
