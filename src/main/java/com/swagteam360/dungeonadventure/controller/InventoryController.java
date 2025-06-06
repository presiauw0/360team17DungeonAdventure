package com.swagteam360.dungeonadventure.controller;

import com.swagteam360.dungeonadventure.model.Item;
import com.swagteam360.dungeonadventure.model.Pillar;
import com.swagteam360.dungeonadventure.utility.GUIUtils;
import com.swagteam360.dungeonadventure.view.InventoryCellFactory;
import java.util.List;

import com.swagteam360.dungeonadventure.view.PillarCellFactory;
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
    private Button btnBackToGameView;

    @FXML
    private ListView<Item> inventoryList; //FIXME use special ItemView class?

    @FXML
    private ListView<Item> pillarList;

    @FXML
    private Button btnBuff;

    /**
     * Hold inventory items in s special JavaFX List. The ListView
     * will observe this list and update the interface.
     */
    private final ObservableList<Item> myObservableItems = FXCollections.observableArrayList();

    /**
     * Hold pillars in a special JavaFX list.
     */
    private final ObservableList<Item> myObservablePillars = FXCollections.observableArrayList();

    /**
     * Helps generate list cells for the inventory list view.
     * The design of each inventory item is specified in the factory.
     */
    private final InventoryCellFactory myInventoryCellFactory = new InventoryCellFactory();

    private final PillarCellFactory myPillarCellFactory = new PillarCellFactory();

    @FXML
    public void initialize() {
        // disable the button if nothing is selected.
        btnBuff.disableProperty()
                .bind(inventoryList.getSelectionModel().selectedItemProperty().isNull());
        // Tell the inventory list which list to observe
        inventoryList.setItems(myObservableItems);
        // Set the cell factory so that when the list is built, it is displayed
        // in a certain way as specified in the cell factory.
        inventoryList.setCellFactory(myInventoryCellFactory);

        pillarList.setItems(myObservablePillars);
        pillarList.setCellFactory(myPillarCellFactory);
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

    @FXML
    private void onBuffClick() {
        final Item selected = inventoryList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println(selected.buff());
        } else {
            btnBuff.disableProperty().setValue(true);
        }
    }

    void setInventoryList(final List<Item> theList) {
        for (Item x : theList) {
            if (x instanceof Pillar) {      // add pillars to the pillars list
                myObservablePillars.add(x);
            } else {
                myObservableItems.add(x);   // add all other items to the items list
            }
        }
        //myObservableItems.addAll(theList);
    }
}
