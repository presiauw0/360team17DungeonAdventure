package com.swagteam360.dungeonadventure.view;

import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.scene.control.ListCell;
import com.swagteam360.dungeonadventure.model.Item;

import java.io.FileInputStream;

public class InventoryCellFactory implements Callback<ListView<Item>, ListCell<Item>> {
    private static final String IMAGE_PREFIX = "src/main/resources/images/";
    private static final String GENERIC_IMAGE = "src/main/resources/images/generic.png";

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
    public ListCell<Item> call(ListView<Item> theListView) {
        return new ListCell<Item>() {
            @Override
            public void updateItem(Item theItem, boolean empty) {
                super.updateItem(theItem, empty);

                if (empty || theItem == null) {
                    setText(null);
                    setGraphic(null);
                    setTooltip(null);
                } else {
                    // get file name based on class name
                    String fileName = theItem.getClass().getSimpleName() + ".png";
                    setGraphic(createContainer(fileName));
                    setTooltip(new Tooltip(theItem.getName()));
                }
            }
        };
    }
}
