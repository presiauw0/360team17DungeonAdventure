package com.swagteam360.dungeonadventure.controller;

import com.swagteam360.dungeonadventure.model.*;
import com.swagteam360.dungeonadventure.utility.GUIUtils;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;


/**
 * The GameViewController class handles the user interaction and event logic for the game view.
 * It provides functionality to respond to user interface events. Events include moving between rooms, attacking,
 * checking inventory, toggling light/dark mode, and displaying informational dialogs.
 *
 * @author Jonathan Hernandez, Preston Sia
 * @version 1.1 (June 4, 2025)
 */
public class GameViewController implements PropertyChangeListener {

    /* **** THE FOLLOWING FIELDS HOLD REFERENCES TO FXML ELEMENTS **** */

    /**
     * A reference to the root pane of the scene.
     */
    @FXML
    private BorderPane myRootPane;

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
     * A label element in the FXML file, used to mainly display events that occur during battle.
     */
    @FXML
    private Label myBattleStatusLabel;

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
     * The button is linked to the event handling logic that processes movement actions,
     * allowing for seamless interaction. It is configured and managed via FXML and integrates
     * with the game's movement controls.
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
     * Represents the button in the user interface used for attacking.
     * It is initially hidden until the player encounters a monster.
     */
    @FXML
    private Button myAttackButton;

    /**
     * Represents the button in the user interface used for performing a special move which varies among the Heroes.
     * It is initially hidden until the player encounters a monster.
     */
    @FXML
    private Button mySpecialMoveButton;

    /**
     * Represents the button that allows quick healing in battles without needed to switch to InventoryView.
     * This button is enabled when the Hero has potions and is in battle. Otherwise, it is hidden.
     */
    @FXML
    private Button myUseHealthPotionButton;

    /**
     * Represents the Hero's health bar throughout the game. It is updated when damage is inflicted (by monsters or
     * pits) or when the Hero heals themselves.
     */
    @FXML
    private ProgressBar myHealthBar;

    /**
     * Represents the button that allows the player to view their inventory. This button is disabled during battle
     * as only health potions from the inventory are to be used (by myUseHealthPotion button).
     */
    @FXML
    private Button myInventoryButton;

    /**
     * Represents the monster's health bar in battle.
     */
    @FXML
    private ProgressBar myMonsterHealthBar;

    /**
     * Represents the monster's name to be shown in battle.
     */
    @FXML
    private Label myMonsterNameLabel;


    /* **** THE FOLLOWING FIELDS ARE GENERAL INSTANCE FIELDS FOR THE CONTROLLER **** */

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

    /**
     * A list of dialogues that the hero may say throughout the DungeonAdventure game.
     */
    private final List<String> myHeroDialogues = List.of(
            "Why is it so quiet down here?",
            "I hope there's treasure ahead.",
            "Did I hear something?",
            "What kind of dungeon is this anyway?",
            "Stay sharp. Stay alive.",
            "I have a bad feeling about this place."
    );

    /**
     * Represents a reference to BattleSystem. The player (through the GUI) will call some methods in this class
     * through two main controls; either the attack button or the special move button.
     */
    private BattleSystem myCurrentBattle;

    /**
     * Maintain a list of the player's current inventory items.
     * This list will be updated as property changes are fired for
     * inventory changes. By default, this field references
     * an empty array list. When the inventory is opened,
     * the reference held here will be sent to the appropriate
     * method in the InventoryController class.
     */
    private List<Item> myInventoryItems = new ArrayList<>();


    /* *** FXML HELPER METHODS *** */

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
        // *** GET THE SINGLETON INSTANCE OF gameManager ***
        GameManager gameManager = GameManager.getInstance();

        // *** PERFORM NULL CHECKS ***
        if (myHeroImageView == null) {
            myHeroImageView = new ImageView();
        }

        if (myHeroDialogueLabel == null) {
            myHeroDialogueLabel = new Label();
        }

        // *** OBSERVER REGISTRATION ***
        gameManager.addPropertyChangeListener(this);

        GUIUtils.initializeDarkModeToggle(myDarkModeToggle); // Initialize dark mode toggle button
        final String heroType = gameManager.getGameSettings().getHero();
        if (heroType != null) {
            setHeroImage(heroType); // Set the bottom-right image based on the hero type
        }

        if (myHeroNameLabel == null) {
            myHeroNameLabel = new Label();
            myHeroNameLabel.setText(gameManager.getGameSettings().getName()); // Set the name label
        }

        if (myMonsterNameLabel != null) {
            myMonsterNameLabel.setVisible(false);
        }
        if (myMonsterHealthBar != null) {
            myMonsterHealthBar.setVisible(false);
        }

        // *** SET the name label, UPDATE movement buttons, HIDE battle controls, SET health bar, and START hero dialogue ***
        myHeroNameLabel.setText(gameManager.getGameSettings().getName());
        updateMovementButtons(gameManager.getCurrentRoom().getAvailableDirections());
        showBattleControls(false);
        updateHealthBar(gameManager.getHero());
        startHeroDialogue();
    }

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
        final Map<String, ImageView> heroView = Map.of(heroType, myHeroImageView);
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

        InventoryController ic = loader.getController();
        ic.setInventoryList(myInventoryItems);

        unloadObserver(); // UNLOAD PCL FROM LIST BECAUSE THE CURRENT INSTANCE WILL BE KILLED OFF UPON SCENE SWITCH
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

        if (theActionEvent == null || theActionEvent.getSource() == null) {
            System.out.println("Invalid action event or source");
            return;
        }

        final Object source = theActionEvent.getSource();
        if (!(source instanceof Button clickedButton)) {
            System.out.println("Event source is not a button");
            return;
        }

        Direction targetDirection;
        if (clickedButton.getId() == null) {
            System.out.println("Button ID is null");
            return;
        }

        switch (clickedButton.getId()) {
            case "myNorthButton" -> targetDirection = Direction.NORTH;
            case "mySouthButton" -> targetDirection = Direction.SOUTH;
            case "myEastButton" -> targetDirection = Direction.EAST;
            case "myWestButton" -> targetDirection = Direction.WEST;
            default -> {
                System.out.println("Invalid button clicked");
                return;
            }
        }

        // TELL the player to move. If it's illegal, handle the exception and print a notice.
        try {
            GameManager.getInstance().movePlayer(targetDirection);
            updateMovementButtons(GameManager.getInstance().getCurrentRoom().getAvailableDirections());
        } catch(IllegalArgumentException e) {
            System.out.println("Illegal Move!");
        }

    }


    /* *** PRIVATE CONTROLLER HELPER METHODS *** */

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
    private void updateMovementButtons(final Set<Direction> availableDirections) {

        if (myCurrentBattle != null) {
            hideMovementButtons();
        } else {
            if (myNorthButton != null) {
                myNorthButton.setVisible(availableDirections.contains(Direction.NORTH));
            }
            if (mySouthButton != null) {
                mySouthButton.setVisible(availableDirections.contains(Direction.SOUTH));
            }
            if (myEastButton != null) {
                myEastButton.setVisible(availableDirections.contains(Direction.EAST));
            }
            if (myWestButton != null) {
                myWestButton.setVisible(availableDirections.contains(Direction.WEST));
            }
        }
    }

    /**
     * Updates the visibility of battle-related controls and movement buttons.
     * When battle controls are shown, movement buttons are hidden and vice versa.
     * Additionally, the visibility of the health potion button depends on the
     * player's inventory.
     *
     * @param theShow a boolean indicating whether to display the battle controls.
     *                If {@code true}, the battle controls (attack and special move buttons)
     *                will be made visible while movement controls will be hidden.
     *                If {@code false}, movement controls will be enabled, and
     *                battle controls will be hidden.
     */
    private void showBattleControls(final boolean theShow) {

        if (myAttackButton != null) {
            myAttackButton.setVisible(theShow);
        }

        if (mySpecialMoveButton != null) {
            mySpecialMoveButton.setVisible(theShow); // Show buttons for attacking when we encounter a monster
        }

        boolean hasHealthPotions = false;
        if (GameManager.getInstance().getHero() != null
                && GameManager.getInstance().getHero().getInventory() != null) {
            hasHealthPotions = GameManager.getInstance().getHero().getInventory()
                    .stream().anyMatch(item -> item instanceof HealthPotion);
        }

        if (myUseHealthPotionButton != null) {
            myUseHealthPotionButton.setVisible(theShow && hasHealthPotions);
        }

        if (!theShow) {
            if (GameManager.getInstance().getCurrentRoom() != null) {
                updateMovementButtons(GameManager.getInstance().getCurrentRoom().getAvailableDirections());
                myInventoryButton.setVisible(true);
            }
        }

    }

    /**
     * Shows the Monster's name and health when a battle occurs. Null checks are performed in case this method
     * is accidentally called.
     *
     * @param theMonster The monster whose info is displayed in the GUI.
     */
    private void showMonsterNameAndHealthBar(final Monster theMonster) {

        if (theMonster == null) {
            myMonsterNameLabel.setVisible(false);
            myMonsterHealthBar.setVisible(false);
            return;
        }

        if (myMonsterNameLabel != null) {
            myMonsterNameLabel.setText(theMonster.getName());
        }
        if (myMonsterHealthBar != null) {
            updateHealthBar(theMonster);
        }
    }

    /**
     * Hides the movement buttons if needed. This occurs when a monster is encountered.
     */
    private void hideMovementButtons() {
        if (myNorthButton != null) {
            myNorthButton.setVisible(false);
        }
        if (mySouthButton != null) {
            mySouthButton.setVisible(false);
        }
        if (myEastButton != null) {
            myEastButton.setVisible(false);
        }
        if (myWestButton != null) {
            myWestButton.setVisible(false);
        }
        if (myInventoryButton != null) {
            myInventoryButton.setVisible(false); // Had to disable inventory here, otherwise player can skip battles
                                                 // accidentally.
        }
    }

    /**
     * Updates a label that represents events that occur in battle. It is also used to notify if a player takes
     * damage from a pit or has reached the exit door (without all four pillars).
     * @param theMessage The message to be displayed for events, primarily for battle.
     */
    private void updateBattleStatus(final String theMessage) {

        if (myBattleStatusLabel != null) {
            myBattleStatusLabel.setText(theMessage);
        }
    }

    /**
     * General method that updates the health bar of a dungeon character (either a Hero or Monster).
     *
     * @param theCharacter The DungeonCharacter in which it's health progress bar needs to be updated.
     */
    private void updateHealthBar(final DungeonCharacter theCharacter) {

        ProgressBar pb = new ProgressBar();

        if (theCharacter instanceof Hero) {
            pb = myHealthBar;
        } else if (theCharacter instanceof Monster) {
            pb = myMonsterHealthBar;
        }

        if (theCharacter == null || pb == null) {
            return;
        }

        int currentHP = theCharacter.getHP();
        int maxHP = theCharacter.getMaxHP();

        if (maxHP == 0) {
            pb.setProgress(0);
            return;
        }

        double percentage = (double) currentHP / maxHP;
        pb.setProgress(percentage);
    }

    /**
     * Handles the event where the player attacks the monster. A call is made in BattleSystem through myCurrentBattle
     * to process and receive the result from the attack.
     */
    @FXML
    private void handleAttackButton() {

        if (myCurrentBattle != null && myCurrentBattle.isPlayerTurn()) {
            final String result = myCurrentBattle.processPlayerAttacks(); // Get the result from the player's attack
            continueBattleAfterPlayerMove(result);
        }

    }

    /**
     * Handles the event where the player decides to use their special move in battle. A call is made in BattleSystem
     * through myCurrentBattle to process and receive the result from this action.
     */
    @FXML
    private void handleSpecialMoveButton() {

        if (myCurrentBattle != null && myCurrentBattle.isPlayerTurn()) {
            String result = myCurrentBattle.processPlayerSpecialMove();
            continueBattleAfterPlayerMove(result);
        }

    }

    /**
     * Helper method that updates the status label given the result of the user's attack/special move. A timeline is
     * then started for a minor delay for the monster's upcoming attack and possible heal.
     *
     * @param theResult The result given by processing the player's attack, which updates the battle status.
     */
    private void continueBattleAfterPlayerMove(final String theResult) {
        updateBattleStatus(theResult);

        final boolean battleOverAfterPlayer = myCurrentBattle.isBattleOver();

        if (!battleOverAfterPlayer) {
            final Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), event -> {
                        if (myCurrentBattle != null && !myCurrentBattle.isBattleOver()) {
                            final String monsterResult = myCurrentBattle.processMonsterAttacks();
                            updateBattleStatus(monsterResult); // Show monster attack results
                            updateHealthBar(GameManager.getInstance().getHero()); // Update Hero health
                        }
                    }),
                    new KeyFrame(Duration.seconds(1.5), event -> {
                        if (myCurrentBattle != null && !myCurrentBattle.isBattleOver()) {
                            final String heal = myCurrentBattle.processMonsterHeal();
                            updateBattleStatus(heal); // After a delay, show if the monster healed
                            updateHealthBar(GameManager.getInstance().getCurrentRoom().getMonster()); // Update Monster
                                                                                                      // health

                            if (myCurrentBattle.isBattleOver()) {
                                onBattleEnd(GameManager.getInstance().getCurrentRoom(), myCurrentBattle.didHeroWin());
                            }
                        }
                    })
            );
            timeline.play();
        } else {
            onBattleEnd(GameManager.getInstance().getCurrentRoom(), myCurrentBattle.didHeroWin());
        }
    }

    /**
     * Handles the event in which the player chooses to heal themselves with a health potion.
     */
    @FXML
    private void handleHealthPotionButton() {

        List<Item> inventory = GameManager.getInstance().getHero().getInventory();
        Optional<Item> healthPotion = inventory.stream()
                .filter(item -> item instanceof HealthPotion).findFirst();

        if (healthPotion.isPresent()) {
            HealthPotion hp = (HealthPotion) healthPotion.get();
            GameManager.getInstance().getHero().heal(hp.getHealAmount());
            inventory.remove(hp);
            updateHealthBar(GameManager.getInstance().getHero()); // Update the GUI
            updateBattleStatus("You used a health potion! Gained " + hp.getHealAmount() + " health.");
        }

        showBattleControls(true); // Inventory may not have anymore health potions, so update it here.

    }

    /* *** BATTLE-RELATED CODE *** */

    /**
     * Handles the property change event that occurs when a monster is present in the room. myCurrentBattle is
     * instantiated given the Hero and Monster, battle controls are enabled, monster info is visible, movement buttons
     * are disabled, and the battle status label is updated.
     *
     * @param theHero The Hero involved in the battle.
     * @param theMonster The monster involved in the battle.
     */
    private void onBattleStart(final Hero theHero, final Monster theMonster) {

        myCurrentBattle = new BattleSystem(theHero, theMonster);
        showBattleControls(true);
        showMonsterNameAndHealthBar(theMonster);
        hideMovementButtons();
        updateBattleStatus("A fight has begun with a " + theMonster.getName());

    }

    /**
     * Handles the event where a battle ends. Checks whether the Hero was victorious, which updates events accordingly
     * (battle status label is updated, battle controls are disabled, monster info is set invisible, and movement
     * buttons are enabled again). Otherwise, if the Hero was defeated, it delegates to another method that handles
     * game-over events.
     *
     * @param theRoom The current room to be examined, which updates the enabled movement buttons.
     * @param theHeroWon Boolean that determines the outcomes of battle.
     */
    private void onBattleEnd(final Room theRoom, final boolean theHeroWon) {

        if (theHeroWon) {
            // If the Hero won, hide battle controls and update movement buttons
            updateBattleStatus("You won!");
            showBattleControls(false);
            showMonsterNameAndHealthBar(null);
            myCurrentBattle = null;
            updateMovementButtons(theRoom.getAvailableDirections());
        } else {
            updateBattleStatus("You lost!");
            showBattleControls(false);
            handleGameOver();
        }
    }

    /**
     * Handles the event if the Hero's health falls to 0. An alert is given to the user if they want to start a new game
     * or quit the application.
     */
    private void handleGameOver() {
        // ChatGPT gave the following
        Platform.runLater(() -> {
            GameManager.getInstance().removePropertyChangeListener(this);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("You lost the game!");
            alert.setContentText("Would you like to start a new game or quit?");

            ButtonType newGameButton = new ButtonType("New Game");
            ButtonType quitButton = new ButtonType("Quit");

            alert.getButtonTypes().setAll(newGameButton, quitButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == newGameButton) {
                    final FXMLLoader loader = new FXMLLoader(getClass()
                            .getResource("/com/swagteam360/dungeonadventure/game-customization.fxml"));
                    final Stage stage = (Stage) myRootPane.getScene().getWindow();
                    GUIUtils.switchScene(stage, loader);
                } else if (result.get() == quitButton) {
                    Platform.exit();
                }
            }
        });
    }

    /**
     * Handles the event where the player successfully reaches the exit room of the dungeon with all four pillars. The
     * player may choose to continue exploring or exit the dungeon, which ends the game.
     */
    private void handleGameCompletion() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Completion");
        alert.setHeaderText("You have all four Pillars!");
        alert.setContentText("Would you like to exit the dungeon or continue exploring?");

        ButtonType finishButton = new ButtonType("Finish Game");
        ButtonType continueButton = new ButtonType("Keep exploring", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(finishButton, continueButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == finishButton) {
            GameManager.getInstance().removePropertyChangeListener(this);
            final FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/com/swagteam360/dungeonadventure/game-completion.fxml"));
            final Stage stage = (Stage) myRootPane.getScene().getWindow();
            GUIUtils.switchScene(stage, loader);
        }

    }

    /**
     * Handles the event which occurs when the user reaches the exit room. If the pillar count is four, the user is
     * then prompted to end the game; otherwise the battle status label is updated to notify the player to come back
     * with all four pillars.
     * @param theHero The hero whose inventory is to be examined.
     */
    private void onExitRoomEntered(final Hero theHero) {
        if (theHero.getPillarCount() == 4) {
            handleGameCompletion();
        } else {

            if (myBattleStatusLabel != null) {
                myBattleStatusLabel.setText("You've reached the exit room! Return with all four pillars to exit.");
            }

        }
    }

    /**
     * Handles the event if the room was a pit. Damage is applied to the hero, and the battle status label is updated.
     * @param theHero The hero to be examined
     * @param theDamage The damage inflicted to the Hero from the pit.
     */
    private void onPitDamageTaken(final Hero theHero, final int theDamage) {
        updateHealthBar(theHero);
        updateBattleStatus("You've taken " + theDamage + " damage from the pit!");
    }

    /**
     * Helper method to update the inventory list by performing
     * a safe cast on the returned list of items.
     * @param theList An object representing a list of items (uncast)
     */
    private void updateInventoryList(final Object theList) {
        final List<Item> itemList = new ArrayList<>();
        // Cast check - check if theList is of type List (generic)
        if (theList instanceof List) {
            // Cast to a generic list
            final List<?> newValCast = (List<?>) theList;

            // Check every item in the list to make sure it is of type Item
            for (final Object o : newValCast) {
                if (o instanceof Item) {
                    itemList.add((Item) o);
                }
            }
        }

        myInventoryItems = itemList;
    }


    /* *** OBSERVER EVENT HANDLING *** */

    /**
     * Handles property changes that come from GameManager.
     * @param theEvent A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (theEvent.getPropertyName()) {
            case "Clear Label" -> {
                if (myBattleStatusLabel != null) {
                    myBattleStatusLabel.setText("");
                }
            }
            case "Fight" -> onBattleStart(GameManager.getInstance().getHero(), (Monster) theEvent.getNewValue());
            case "Pit" -> onPitDamageTaken(GameManager.getInstance().getHero(), (int) theEvent.getNewValue());
            case "Dead" -> handleGameOver();
            case "Exit" -> onExitRoomEntered(GameManager.getInstance().getHero());
            case "INVENTORY_CHANGE" -> updateInventoryList(theEvent.getNewValue());
        }
    }

    private void unloadObserver() {
        GameManager.getInstance().removePropertyChangeListener(this);
    }
}