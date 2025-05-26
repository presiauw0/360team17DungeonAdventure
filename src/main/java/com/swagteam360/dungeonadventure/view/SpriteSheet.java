package com.swagteam360.dungeonadventure.view;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;


public class SpriteSheet {
    private Image mySheet;
    private int mySpriteWidth;
    private int mySpriteHeight;

    private SpriteSheet(Image theSheet, int theSpriteWidth, int theSpriteHeight) {
        mySheet = theSheet;
        mySpriteWidth = theSpriteWidth;
        mySpriteHeight = theSpriteHeight;
    }

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
