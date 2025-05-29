package com.swagteam360.dungeonadventure.controller;

import com.swagteam360.dungeonadventure.utility.GUIUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * InventoryController handles the user interaction and event logic
 * for the functionality of the InventoryView.
 *
 * @author Preston Sia (psia97)
 * @version 1.00, 28 May 2025
 */
public class InventoryController {

    @FXML
    public Button btnBackToGameView;

    @FXML
    public ListView<String> inventoryList; //FIXME use special ItemView class?

    @FXML
    public void initialize() {
        // Register observer with the GameManager
        //GameManager.getInstance().addPCL(this);
        System.out.println("TEST");

        String[] test = {"item1", "item2"};
        inventoryList.getItems().addAll(test);
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
}
