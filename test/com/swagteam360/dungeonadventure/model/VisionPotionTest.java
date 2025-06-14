package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Vision potion test suite.
 *
 * @author Preston Sia (psia97)
 * @version 1.0.0, 13 June 2025
 */
public class VisionPotionTest {
    @Test
    void testGetName() {
        VisionPotion visionPotion = new VisionPotion();
        assertEquals(
                "Vision Potion",
                visionPotion.getName(),
                "Incorrect name"
        );
    }

    @Test
    void testBuff() {
        GameSettings testSettings = new GameSettings("Test name", "priestess", "easy");

        GameManager.getInstance().startNewGame(testSettings);

        // check for property changes
        List<PropertyChangeEvent> events = new ArrayList<>();
        GameManager.getInstance().addPropertyChangeListener(events::add);

        VisionPotion visionPotion = new VisionPotion();
        List<Item> testInventory = new ArrayList<>();
        testInventory.add(visionPotion);
        GameManager.getInstance().getHero().addToInventory(testInventory);

        visionPotion.buff();

        // test if property change was fired and the item was removed from inventory
        PropertyChangeEvent evt = null;
        for (PropertyChangeEvent e : events) {
            if (e.getPropertyName().equals("VISION_POWERS")) {
                evt = e;
            }
        }

        // check not null
        assertNotNull(evt);
        // check name
        assertEquals("VISION_POWERS", evt.getPropertyName());
        // check new value
        assertTrue((Boolean) evt.getNewValue());

        assertFalse(
                GameManager.getInstance().getHero().getInventory().contains(visionPotion)
        );
    }
}
