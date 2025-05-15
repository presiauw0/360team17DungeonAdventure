package com.swagteam360.dungeonadventure.controller;

import com.swagteam360.dungeonadventure.model.GameManager;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;


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
     * Represents a Timeline object used for managing and animating hero dialogue sequences
     * within the game interface.
     * This variable likely governs the timing and transitions of dialogue displayed for the hero.
     */
    private Timeline myHeroDialogueTimeline;

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
     * Initializes the game view by setting up the hero image based on the hero type
     * specified in the current game settings.
     * This method retrieves the selected hero type from the GameManager's game settings
     * and calls the setHeroImage method to update the displayed hero image.
     * If no hero type is specified, no action is performed.
     */
    public void initialize() {
        final String heroType = GameManager.getInstance().getGameSettings().getHero();
        if (heroType != null) {
            setHeroImage(heroType);
        }

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
    private void helpButtonEvent() {

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

    public void startHeroDialogue() {
        myHeroDialogueTimeline = new Timeline(new KeyFrame(
                Duration.seconds(5 + myRandom.nextInt(6)),
                event -> {
                    String randomSentence = myHeroDialogues.get(myRandom.nextInt(myHeroDialogues.size()));
                    myHeroDialogueLabel.setText(randomSentence);
                    myHeroDialogueLabel.setOpacity(0); // Start hidden

                    // Fade in
                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), myHeroDialogueLabel);
                    fadeIn.setFromValue(0);
                    fadeIn.setToValue(1);

                    // Fade out after a pause
                    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), myHeroDialogueLabel);
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

}
