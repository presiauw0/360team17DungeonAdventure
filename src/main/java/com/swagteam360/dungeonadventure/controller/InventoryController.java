package com.swagteam360.dungeonadventure.controller;

import com.swagteam360.dungeonadventure.model.Item;
import com.swagteam360.dungeonadventure.utility.GUIUtils;
import com.swagteam360.dungeonadventure.view.InventoryCellFactory;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public ListView<Item> inventoryList; //FIXME use special ItemView class?

    @FXML
    public Button btnBuff;

    /**
     * Hold inventory items in s special JavaFX List. The ListView
     * will observe this list and update the interface.
     */
    private final ObservableList<Item> myObservableItems = FXCollections.observableArrayList();

    public InventoryCellFactory myCellFactory = new InventoryCellFactory();


    @FXML
    public void initialize() {
        // disable the button if nothing is selected.
        btnBuff.disableProperty()
                .bind(inventoryList.getSelectionModel().selectedItemProperty().isNull());
        // Tell the inventory list which list to observe
        inventoryList.setItems(myObservableItems);
        // Set the cell factory so that when the list is built, it is displayed
        // in a certain way as specified in the cell factory.
        inventoryList.setCellFactory(myCellFactory);
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

    void setInventoryList(final List<Item> theList) {
        myObservableItems.addAll(theList);
    }
}
