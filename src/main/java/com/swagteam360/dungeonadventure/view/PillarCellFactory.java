package com.swagteam360.dungeonadventure.view;

import com.swagteam360.dungeonadventure.model.Item;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.scene.control.ListCell;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PillarCellFactory implements Callback<ListView<Item>, ListCell<Item>> {

    private static final double NUM_PILLARS_TOTAL = 4.0;
    /**
     * Resizing causes scrollbars to show up. This makes each item slightly
     * smaller to compensate for overflow.
     */
    private static final double OVERFLOW_COMPENSATION = 4.0;

    private static final String GENERIC_IMAGE = "src/main/resources/images/generic.png";


    private VBox createContainer(final String theName) {
        return createContainer(theName, GENERIC_IMAGE);
    }

    private VBox createContainer(final String theName, final String theImagePath) {
        final VBox content = new VBox();
        final Label label = new Label();
        final ImageView imageView = new ImageView();

        // find specified image
        try (final FileInputStream imageStream = new FileInputStream(theImagePath)) {
            imageView.setImage(new Image(imageStream));
        } catch (FileNotFoundException e) {
            System.out.println("Image not found: " + e);
        } catch (IOException e) {
            System.out.println("Image not loaded: " + e);
        }

        content.setAlignment(Pos.CENTER);

        //label.setText(theName);
        content.getChildren().addAll(imageView, label);

        return content;
    }

    @Override
    public ListCell<Item> call(final ListView<Item> theListView) {
        return new ListCell<>() {
            @Override
            public void updateItem(final Item theItem, final boolean empty) {
                super.updateItem(theItem, empty);


                if (empty || theItem == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setGraphic(createContainer(theItem.getName()));
                    setTooltip(new Tooltip(theItem.getName()));

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
