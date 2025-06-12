package com.swagteam360.dungeonadventure.utility;

import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * The GUIUtils class provides utility methods for managing JavaFX scenes
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
public final class GUIUtils {

    /**
     * Represents the fixed width of the scene in pixels.
     * Used for specifying the width when creating a new {@link Scene}.
     * This value is constant and cannot be modified.
     */
    private static final double WIDTH = 640;

    /**
     * Represents the fixed height of the scene in pixels.
     * Used for specifying the height when creating a new {@link Scene}.
     * This value is constant and cannot be modified.
     */
    private static final double HEIGHT = 480;

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
    private static boolean myDarkMode = false;

    /**
     * Private constructor to prevent instantiation of the utility class.
     * The GUIUtils class is designed to provide static utility methods for managing
     * and customizing graphical user interface elements in a JavaFX application.
     */
    private GUIUtils() {
        super();
    }

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
                    (GUIUtils.class.getResource(getCurrentTheme())).toExternalForm());

            final Stage stage = (Stage) ((javafx.scene.Node) theEvent.getSource())
                    .getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void switchScene(final Stage theStage, final FXMLLoader theLoader) {
        try {
            final Parent root = theLoader.load();
            final Scene scene = new Scene(root, WIDTH, HEIGHT);
            scene.getStylesheets().add(Objects.requireNonNull
                    (GUIUtils.class.getResource(getCurrentTheme())).toExternalForm());
            theStage.setScene(scene);
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
        return myDarkMode ? DARK_THEME : LIGHT_THEME;
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
        theScene.getStylesheets().add(Objects.requireNonNull(GUIUtils.class.getResource(getCurrentTheme()))
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
            theToggleButton.setSelected(myDarkMode);
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
        myDarkMode = theToggleButton.isSelected();
        applyTheme(theToggleButton.getScene());
    }

    /**
     * Displays a pop-up dialog with detailed information about a character
     * when their respective {@code ImageView} is clicked.
     * The dialog contains the character's stats and special skills.
     *
     * @param theEvent the {@code MouseEvent} triggered by the user's click on a character's {@code ImageView}
     * @param theHeroViews a map where the keys are character names (e.g., "Thief", "Warrior", "Priestess")
     *                     and the values are their associated {@code ImageView} elements
     */
    public static void showCharacterInfo(final MouseEvent theEvent,
                                         Map<String, ImageView> theHeroViews) {
        final Object source = theEvent.getSource();
        String message = "";

        // The following stats are given by the course project description.
        // Unsure if these are supposed to be hard-coded in or if we need to use SQLite for these entries.
        // Nonetheless, these stats may be subject to change.

        for (Map.Entry<String, ImageView> entry : theHeroViews.entrySet()) {
            if (source == entry.getValue()) {
                message = switch (entry.getKey()) {
                    case "Thief" -> """
                        🗡️ Thief
                        
                        Special Skill: Surprise Attack
                        ➤ 40% extra attack
                        ➤ 40% normal attack
                        ➤ 20% no attack (caught)
                        
                        Stats:
                        • Hit Points: 75
                        • Attack Speed: 6
                        • Chance to Hit: 80%
                        • Damage Range: 20–40
                        • Chance to Block: 40%
                        """;
                    case "Warrior" -> """
                        🛡️ Warrior
    
                        Special Skill: Crushing Blow (40% chance)
                        ➤ Deals 75–175 damage on success
    
                        Stats:
                        • Hit Points: 125
                        • Attack Speed: 4
                        • Chance to Hit: 80%
                        • Damage Range: 35–60
                        • Chance to Block: 20%
                        """;
                    case "Priestess" -> """
                        ✨ Priestess
    
                        Special Skill: Heal (restores HP over a range)
    
                        Stats:
                        • Hit Points: 75
                        • Attack Speed: 5
                        • Chance to Hit: 70%
                        • Damage Range: 25–45
                        • Chance to Block: 30%
                        """;
                    default -> "";
                };
                break;
            }

        }

        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Character Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an informational pop-up dialog box that provides instructions on how to play
     * the game "Dungeon Adventure." The dialog contains details about the game's objectives,
     * controls, and general guidance for players.
     * <p>
     * This method is typically invoked when the user selects the "How to Play" option
     * from the user interface, offering easy access to game instructions.
     * <p>
     * The pop-up dialog uses JavaFX's Alert of type INFORMATION to present the content
     * with a title, header text, and instructions formatted as a multi-line string.
     */
    public static void showHowToPlayInfo() {
        // TODO: Elaborate more here.

        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How to play");
        alert.setHeaderText("Dungeon Adventure - Instructions");
        alert.setContentText("""
                Objectives: Explore the dungeon, collect treasure, and defeat the boss.
                
                Movement: Use WASD or arrow keys to move the hero.
                
                Good luck.
                """);
        alert.showAndWait();
    }

}
