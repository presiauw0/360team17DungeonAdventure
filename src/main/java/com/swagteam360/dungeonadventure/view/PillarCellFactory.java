package com.swagteam360.dungeonadventure.view;

import com.swagteam360.dungeonadventure.model.Item;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.scene.control.ListCell;

public class PillarCellFactory implements Callback<ListView<Item>, ListCell<Item>> {

    private static final double NUM_PILLARS_TOTAL = 4.0;
    /**
     * Resizing causes scrollbars to show up. This makes each item slightly
     * smaller to compensate for overflow.
     */
    private static final double OVERFLOW_COMPENSATION = 4.0;

    @Override
    public ListCell<Item> call(final ListView<Item> theListView) {
        return new ListCell<>() {

            @Override
            public void updateItem(final Item theItem, final boolean empty) {
                super.updateItem(theItem, empty);

                if (empty || theItem == null) {
                    setText(null);
                } else {
                    setText(theItem.getName());

                    // resize items (according to ChatGPT)
                    setPrefWidth(theListView.widthProperty().doubleValue() / (NUM_PILLARS_TOTAL) - OVERFLOW_COMPENSATION);

                    theListView.widthProperty().addListener((theObs, theOldVal, theNewVal) -> {
                        setPrefWidth(theNewVal.doubleValue() / NUM_PILLARS_TOTAL);
                    });
                }
            }

        };
    }


}
