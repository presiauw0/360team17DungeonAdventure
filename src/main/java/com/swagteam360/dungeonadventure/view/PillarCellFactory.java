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
import java.util.Locale;

public class PillarCellFactory implements Callback<ListView<Item>, ListCell<Item>> {
    /**
     * Total number of pillars present in the game.
     * Used to calculate the size of the items.
     */
    private static final double NUM_PILLARS_TOTAL = 4.0;
    /**
     * Resizing causes scrollbars to show up. This makes each item slightly
     * smaller to compensate for overflow.
     */
    private static final double OVERFLOW_COMPENSATION = 4.0;

    /**
     * File path of a generic image to use
     * as a fallback.
     */
    private static final String GENERIC_IMAGE = "src/main/resources/images/generic.png";
    /**
     * Source file path for the image source folder.
     */
    private static final String IMAGE_PREFIX = "src/main/resources/images/";

    /**
     * Creates a VBox container for the content of each list item.
     * @param theImageName File name of the image (without the path)
     * @return A VBox container
     */
    private VBox createContainer(final String theImageName) {
        final VBox content = new VBox();
        final ImageView imageView = new ImageView();

        // find specified image
        try (final FileInputStream imageStream = new FileInputStream(IMAGE_PREFIX + theImageName)) {
            imageView.setImage(new Image(imageStream));
        } catch (Exception e) {
            // try to load generic image
            try (final FileInputStream imageStream = new FileInputStream(GENERIC_IMAGE)) {
                imageView.setImage(new Image(imageStream));
            } catch (Exception e2) {
                System.out.println("Image not loaded: " + e2);
            }

            System.out.println("Image not loaded: " + e);
        }

        content.setAlignment(Pos.CENTER);

        content.getChildren().addAll(imageView);

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
                    setGraphic(null);
                    setTooltip(null);
                } else {
                    // get image file name based on enum name
                    final String fileName = theItem.getName().toLowerCase(Locale.ROOT)
                            + "_pillar.png";
                    setGraphic(createContainer(fileName));
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
