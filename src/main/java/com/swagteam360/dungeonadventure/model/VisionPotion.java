package com.swagteam360.dungeonadventure.model;

/**
 * The VisionPotion class represents a consumable item that, when used, grants
 * enhanced vision of the surrounding area to the player. This item is beneficial
 * for exploring the dungeon, allowing the player to gain insights into the
 * nearby environment or hidden elements.
 *
 * This class implements the Item interface, which requires defining specific
 * functionality through the buff method.
 *
 * @author Jonathan Hernandez
 * @version 1.0 (April 30th, 2025)
 */
public class VisionPotion implements Item {

    // This class will eventually tie into the game logic to provide
    // enhanced vision of the surrounding area. Come back later.

    /**
     * Constructs a new instance of the VisionPotion class.
     */
    protected VisionPotion() {

    }

    /**
     * Grants the user enhanced vision of the surrounding area, enabling them to
     * gain insights about nearby elements such as hidden objects or terrain details.
     *
     * @return A string indicating the effect of the vision enhancement provided by this item.
     */
    @Override
    public String buff() {
        return "You gain vision of the surrounding area!";
    }

}
