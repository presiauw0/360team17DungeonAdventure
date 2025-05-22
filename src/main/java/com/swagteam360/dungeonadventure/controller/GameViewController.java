package com.swagteam360.dungeonadventure.controller;

import com.swagteam360.dungeonadventure.model.*;
import com.swagteam360.dungeonadventure.utility.GUIUtils;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


/**
 * The GameViewController class handles the user interaction and event logic for the game view.
 * It provides functionality to respond to user interface events such as saving progress and quitting the application.
 * [ADD MORE HERE]
 * <p>
 *
 * @author Jonathan Hernandez
 * @version 1.0 (May 11, 2025)
 */
public class GameViewController {

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
    private ToggleButton myDarkModeToggle;

    /**
     * A reference to an ImageView in the game view's user interface that displays
     * the hero's image. This ImageView is dynamically updated to reflect the hero
     * type selected in the game's settings.
     * <p>
     * This variable is injected via FXML and initialized by the JavaFX framework
     * based on the associated FXML file. It is used in conjunction with methods
     * that modify the visual representation of the hero, such as setting its image
     * based on the selected hero type.
     */
    @FXML
    private ImageView myHeroImageView;

    /**
     * A Label element in the user interface used to display dialogue text from the hero.
     * This label is part of the game view and is dynamically updated to reflect
     * the hero's spoken lines during gameplay.
     * <p>
     * It is primarily managed by the "GameViewController" class and may be updated programmatically
     * during the hero's interactions or events in the game.
     * <p>
     * This field is associated with an FXML element and is injected by the JavaFX framework.
     */
    @FXML
    private Label myHeroDialogueLabel;

    /**
     * A Label element in the FXML file, used to display the name of the player's hero in the game.
     * <p>
     * This label is part of the graphical user interface controlled by GameViewController.
     * It provides a visual representation of the hero's name during gameplay.
     * <p>
     * This field is associated with the corresponding FXML element and is managed by JavaFX.
     * It may be updated dynamically based on the current game state or interactions.
     */
    @FXML
    private Label myHeroNameLabel;

    /**
     * Represents a Timeline object used for managing and animating hero dialogue sequences
     * within the game interface.
     * This variable likely governs the timing and transitions of dialogue displayed for the hero.
     */
    private Timeline myHeroDialogueTimeline;

    /**
     * Represents the button in the user interface that initiates movement
     * towards the "North" direction within the game's room navigation system.
     * <p>
     * This button is part of the room movement controls and is initialized
     * and managed by the GameViewController. Its functionality is tied to
     * the roomMovementButtons event handling method, which determines the
     * player's direction of movement within the game.
     * <p>
     * The button's visibility, text, and functionality can be updated dynamically
     * based on the game's state or conditions.
     */
    @FXML
    private Button myNorthButton;

    /**
     * Represents a button located on the south side of the user interface,
     * typically used for navigating or performing an action associated
     * with the "south" direction in the game view.
     * <p>
     * This button is controlled via FXML and is part of the {@code GameViewController} class.
     * It interacts directly with other user interface elements,
     * assisting in managing directional movement or commands in the game.
     */
    @FXML
    private Button mySouthButton;

    /**
     * Represents the button in the user interface associated with the Eastward movement in the game.
     * This button enables the player to navigate their character to the East direction within the game world.
     * <p>
     * The button is linked to the event handling logic that processes movement actions, allowing for seamless interaction.
     * It is configured and managed via FXML and integrates with the game's movement controls.
     */
    @FXML
    private Button myEastButton;

    /**
     * Represents the button in the Game View used to navigate the hero to the western room.
     * This button triggers movement logic connected to the game when interacted with.
     * <p>
     * Mapped as an FXML component, it is linked to an action event handler that manages
     * room traversal within the game's environment.
     */
    @FXML
    private Button myWestButton;

    /**
     * A Random instance used to generate random values within the GameViewController class.
     * This variable is final and ensures consistent usage of a single Random object
     * throughout the lifecycle of the GameViewController.
     */
    private final Random myRandom = new Random();

    private final List<String> myHeroDialogues = List.of(
            "Why is it so quiet down here?",
            "I hope there's treasure ahead.",
            "Did I hear something?",
            "What kind of dungeon is this anyway?",
            "Stay sharp. Stay alive.",
            "I have a bad feeling about this place."
    );

    /**
     * Handles the event triggered by clicking the "Save and Quit" button.
     * This method is intended to save the current game state and exit the application.
     * The saving logic is yet to be implemented.
     * <p>
     * This method is invoked via FXML when the associated button is clicked.
     */
    @FXML
    private void saveAndQuitButtonEvent() {
        // TODO: Add save logic here. Current state (hero, dungeon, etc.) should be saved.
        Platform.exit();
    }

    /**
     * Initializes the GameViewController instance and its associated user interface elements.
     * This method is called automatically by the JavaFX framework as part of the FXML initialization process.
     * <p>
     * The initialize method performs the following tasks:
     * 1. Ensures required UI components such as ImageView and Labels are not null by initializing them if necessary.
     * 2. Configures the dark mode toggle button using predefined utilities.
     * 3. Retrieves the hero type and name from the game settings and updates the corresponding UI elements.
     * 4. Sets the hero image in the user interface based on the retrieved hero type.
     * 5. Initiates the hero dialogue sequence to display random dynamic dialogues in the UI.
     */
    @FXML
    private void initialize() {

        if (myHeroImageView == null) {
            myHeroImageView = new ImageView();
        }

        if (myHeroDialogueLabel == null) {
            myHeroDialogueLabel = new Label();
        }

        GUIUtils.initializeDarkModeToggle(myDarkModeToggle);
        final String heroType = GameManager.getInstance().getGameSettings().getHero();
        if (heroType != null) {
            setHeroImage(heroType);
        }

        if (myHeroNameLabel == null) {
            myHeroNameLabel = new Label();
            myHeroNameLabel.setText(GameManager.getInstance().getGameSettings().getName());
        }

        myHeroNameLabel.setText(GameManager.getInstance().getGameSettings().getName());
        updateMovementButtons(GameManager.getInstance().getCurrentRoom().getAvailableDirections());
        startHeroDialogue();
    }

    /**
     * Sets the hero image in the user interface based on the specified hero type.
     * This method constructs the file path to the corresponding hero image
     * and updates the image displayed in the hero's image view.
     *
     * @param theHeroType the type of the hero whose image is to be displayed
     *                    (e.g., "warrior", "thief"). This should match the file name
     *                    (excluding the extension) located in the images' directory.
     */
    private void setHeroImage(final String theHeroType) {
        final String imagePath = "/images/" + theHeroType + ".png";
        final var imageStream = getClass().getResourceAsStream(imagePath);

        if (imageStream == null) {
            throw new IllegalStateException("Could not load hero image for type: " + theHeroType +
                    ". Ensure the image exists at path: " + imagePath);
        }

        final Image heroImage = new Image(imageStream);
        myHeroImageView.setImage(heroImage);

    }

    /**
     * Handles the event triggered by the "Help" button.
     * This method displays an informational dialog box providing instructions
     * on how to play the Dungeon Adventure game.
     * <p>
     * The displayed information includes game goals and movement controls
     * to guide the player in navigating the game.
     * <p>
     * This method is invoked via FXML when the "Help" button is clicked.
     */
    @FXML
    private void howToPlayButtonEvent() {
        GUIUtils.showHowToPlayInfo();
    }

    /**
     * Starts the hero dialogue sequence in the game view.
     * This method initializes a timeline to periodically select random dialogue sentences
     * for the hero to display, creating a dynamic and interactive experience for the user.
     * <p>
     * The dialogue text is displayed with fade-in and fade-out effects:
     * - A random sentence is selected from the list of available hero dialogues.
     * - The label displaying the dialogue is initially hidden, then it fades in over 1 second.
     * - After remaining visible for 2 seconds, it fades out over another second.
     * <p>
     * The timeline is set to repeat indefinitely, ensuring that the hero conversations continue
     * throughout the gameplay or until the timeline is stopped manually.
     */
    private void startHeroDialogue() {
        myHeroDialogueTimeline = new Timeline(new KeyFrame(
                Duration.seconds(5 + myRandom.nextInt(6)),
                event -> {
                    final String randomSentence = myHeroDialogues.get(myRandom.nextInt(myHeroDialogues.size()));
                    myHeroDialogueLabel.setText(randomSentence);
                    myHeroDialogueLabel.setOpacity(0); // Start hidden

                    // Fade in
                    final FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), myHeroDialogueLabel);
                    fadeIn.setFromValue(0);
                    fadeIn.setToValue(1);

                    // Fade out after a pause
                    final FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), myHeroDialogueLabel);
                    fadeOut.setFromValue(1);
                    fadeOut.setToValue(0);
                    fadeOut.setDelay(Duration.seconds(2)); // Label stays visible for 2 seconds

                    fadeIn.setOnFinished(e -> fadeOut.play());
                    fadeIn.play();
                }
        ));
        myHeroDialogueTimeline.setCycleCount(Animation.INDEFINITE);
        myHeroDialogueTimeline.play(); // Had help from CHATGPT for this whole method
    }

    /**
     * Handles the event triggered by clicking on the hero image.
     * This method provides a visual representation of character information
     * based on the hero type selected in the game settings.
     *
     * @param theEvent the MouseEvent instance containing details about the mouse
     *                 interaction with the hero image.
     */
    @FXML
    private void handleHeroImageClick(MouseEvent theEvent) {
        final String heroType = GameManager.getInstance().getGameSettings().getHero();
        Map<String, ImageView> heroView = Map.of(heroType, myHeroImageView);
        GUIUtils.showCharacterInfo(theEvent, heroView);
    }

    /**
     * Toggles the dark mode theme for the application's user interface.
     * This method updates the user interface appearance between dark mode
     * and light mode by interacting with the application's dark mode settings.
     * <p>
     * The dark mode toggle state is managed through the provided toggle control.
     * <p>
     * This method is invoked via FXML when the associated toggle control is
     * interacted with.
     */
    @FXML
    private void toggleDarkMode() {
        GUIUtils.toggleDarkMode(myDarkModeToggle);
    }

    /**
     * Navigates to the Inventory View of the application.
     * This method loads the "inventory-view.fxml" file and switches the scene
     * to display the Inventory View.
     *
     * @param theActionEvent the ActionEvent instance triggered by the user interaction
     *                       that initiates the navigation to the Inventory View.
     */
    @FXML
    private void goToInventoryView(final ActionEvent theActionEvent) {
        final FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/swagteam360/dungeonadventure/inventory-view.fxml"));
        GUIUtils.switchScene(theActionEvent, loader);
    }

    /**
     * Navigates back to the Game View of the application.
     * This method loads the "game-view.fxml" file and switches the scene
     * to display the Game View.
     *
     * @param theActionEvent the ActionEvent instance triggered by the user interaction
     *                       that initiates the navigation back to the Game View.
     */
    @FXML
    private void goBackToGameView(final ActionEvent theActionEvent) {
        final FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/swagteam360/dungeonadventure/game-view.fxml"));
        GUIUtils.switchScene(theActionEvent, loader);
    }

    /**
     * Handles the room movement buttons in the game interface.
     * This method updates the player's position in the game by determining the direction
     * based on the button clicked and calling the movePlayer method with new coordinates.
     * If an invalid button is clicked, an error message is displayed.
     *
     * @param theActionEvent the ActionEvent instance triggered by the user interaction
     *                       with one of the movement buttons (e.g., north, south, east, west).
     */
    @FXML
    private void roomMovementButtons(final ActionEvent theActionEvent) {

        Button clickedButton = (Button) theActionEvent.getSource();
        int newRow = GameManager.getInstance().getCurrPositionRow();
        int newCol = GameManager.getInstance().getCurrPositionCol();

        switch (clickedButton.getId()) {
            case "myNorthButton" -> newRow--;
            case "mySouthButton" -> newRow++;
            case "myEastButton" -> newCol++;
            case "myWestButton" -> newCol--;
            default -> {
                System.out.println("Invalid button clicked");
                return;
            }
        }

        GameManager.getInstance().movePlayer(newRow, newCol);
        updateMovementButtons(GameManager.getInstance().getCurrentRoom().getAvailableDirections());

    }

    /**
     * Updates the movement buttons in the user interface to reflect the availability
     * of directions the player can move within the game. If a direction is present
     * in the provided set of available directions, the corresponding button is
     * enabled; otherwise, it is disabled to indicate that movement in that
     * direction is not possible.
     *
     * @param availableDirections a set of directions that are currently available
     *                            for movement (e.g., {@code Direction.NORTH},
     *                            {@code Direction.SOUTH}, {@code Direction.EAST},
     *                            {@code Direction.WEST}).
     */
    private void updateMovementButtons(Set<Direction> availableDirections) {
        myNorthButton.setVisible(availableDirections.contains(Direction.NORTH));
        mySouthButton.setVisible(availableDirections.contains(Direction.SOUTH));
        myEastButton.setVisible(availableDirections.contains(Direction.EAST));
        myWestButton.setVisible(availableDirections.contains(Direction.WEST));
    }

}
