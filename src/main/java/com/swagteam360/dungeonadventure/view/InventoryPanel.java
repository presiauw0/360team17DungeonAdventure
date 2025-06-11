package com.swagteam360.dungeonadventure.view;

import com.swagteam360.dungeonadventure.model.GameManager;
import com.swagteam360.dungeonadventure.model.Item;
import com.swagteam360.dungeonadventure.model.Pillar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class InventoryPanel extends HBox implements PropertyChangeListener {
    /**
     * Hold the BUFF/USE button
     */
    private final Button myBuffButton;
    /**
     * Hold the ListView JavaFX control for general inventory items.
     */
    private final ListView<Item> myInventory;
    /**
     * Hold the ListView JavaFX control for pillars.
     */
    private final ListView<Item> myPillars;

    /**
     * Hold inventory items in s special JavaFX List. The ListView
     * will observe this list and update the interface.
     */
    private final ObservableList<Item> myObservableItems;
    /**
     * Hold pillars in a special JavaFX list.
     */
    private final ObservableList<Item> myObservablePillars;

    private final InventoryCellFactory myInventoryCellFactory;
    private final PillarCellFactory myPillarCellFactory;

    public InventoryPanel() {
        this.setMinHeight(44);
        this.setPrefHeight(44);
        //this.setMaxHeight(46);

        myBuffButton = new Button();
        myInventory = new ListView<>();
        myPillars = new ListView<>();

        // SET UP the button
        initializeBuffButton();

        myObservableItems = FXCollections.observableArrayList();
        myObservablePillars = FXCollections.observableArrayList();

        myInventoryCellFactory = new InventoryCellFactory();
        myPillarCellFactory = new PillarCellFactory();

        initializeListViewProperties();

        // ADD ListView controls to this panel
        getChildren().addAll(myBuffButton, myInventory, myPillars);

        // ADD PropertyChangeListener
        GameManager.getInstance().addPropertyChangeListener(this);
    }

    private void initializeListViewProperties() {
        // SET all lists to horizontal
        myInventory.setOrientation(Orientation.HORIZONTAL);
        myPillars.setOrientation(Orientation.HORIZONTAL);

        // Set the cell factory so that when the list is built, it is displayed
        // in a certain way as specified in the cell factory.
        myInventory.setCellFactory(myInventoryCellFactory);
        myPillars.setCellFactory(myPillarCellFactory);

        // SET the observable lists that the ListView controls should observe
        myInventory.setItems(myObservableItems);
        myPillars.setItems(myObservablePillars);
    }

    private void initializeBuffButton() {
        myBuffButton.setText("USE");
        myBuffButton.setMinWidth(44);
        myBuffButton.setMinHeight(44);
        myBuffButton.setPrefHeight(44);

        myBuffButton.disableProperty()
                .bind(myInventory.getSelectionModel().selectedItemProperty().isNull());

        // SET event handler
        EventHandler<ActionEvent> btnEventHandle = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                onBuffClick();
            }
        };
        myBuffButton.setOnAction(btnEventHandle);
    }

    private void onBuffClick() {
        final Item selected = myInventory.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println(selected.buff()); // BUFF the item
        } else {
            myBuffButton.disableProperty().setValue(true);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent theEvent) {
        if ("INVENTORY_CHANGE".equals(theEvent.getPropertyName())) {
            final List<Item> itemList = new ArrayList<>();
            // Cast check - check if the new event data is of type List (generic)
            if (theEvent.getNewValue() instanceof List) {
                final List<?> newValCast = (List<?>) theEvent.getNewValue();

                // CLEAR current lists
                myObservableItems.clear();
                myObservablePillars.clear();

                // Check every item in the list to make sure it's of type Item
                // and assign it to the appropriate Observable list
                for (final Object o : newValCast) {
                    // Pillar implements Item, so I can still cast it as such
                    if (o instanceof Pillar) {
                        myObservablePillars.add((Item)o);
                    } else if (o instanceof Item) {
                        // if it's not a pillar, but it is an item, add it to the inventory list
                        myObservableItems.add((Item)o);
                    }
                }
            }
        }
    }
}
