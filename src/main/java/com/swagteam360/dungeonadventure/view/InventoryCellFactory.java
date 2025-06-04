package com.swagteam360.dungeonadventure.view;

import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.scene.control.ListCell;
import com.swagteam360.dungeonadventure.model.Item;

public class InventoryCellFactory implements Callback<ListView<Item>, ListCell<Item>> {
    @Override
    public ListCell<Item> call(ListView<Item> theListView) {
        return new ListCell<Item>() {
            @Override
            public void updateItem(Item theItem, boolean empty) {
                super.updateItem(theItem, empty);

                if (empty) {
                    setText(null);
                } else {
                    setText(theItem.getName());
                }
            }
        };
    }
}
