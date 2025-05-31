package com.swagteam360.dungeonadventure.view;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 * Default class "SpriteSheet" assists in implementation of
 * sprites' idle animations. Represents a sprite sheet using JavaFX's
 * Image system. This class allows sprites to be extracted from a
 * larger sprite sheet image.
 *
 * @author Luke Willis
 * @version 23 May 2025
 */
class SpriteSheet {
    private Image mySheet;
    private int mySpriteWidth;
    private int mySpriteHeight;

    /**
     * Constructs a SpriteSheet from the given image and sprite dimensions.
     * @param theSheet represents the full sprite sheet image
     * @param theSpriteWidth represents the width (in pixels) of each individual sprite
     * @param theSpriteHeight represents the height (in pixels) of each individual sprite.
     */
    private SpriteSheet(Image theSheet, int theSpriteWidth, int theSpriteHeight) {
        mySheet = theSheet;
        mySpriteWidth = theSpriteWidth;
        mySpriteHeight = theSpriteHeight;
    }

    /**
     * Retrieves a single sprite from the sprite sheet based on column and row indices.
     *
     * @param col represents the column index of the sprite (starting at 0)
     * @param row reoresents the row index of the sprite (starting at 0)
     * @return an Image representing the specified sprite
     */
    private Image getSprite(int col, int row) {
        return new WritableImage(
                mySheet.getPixelReader(),
                col * mySpriteWidth,
                row * mySpriteHeight,
                mySpriteWidth,
                mySpriteHeight
        );
    }


}
