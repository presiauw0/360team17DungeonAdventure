package com.swagteam360.dungeonadventure.model;

/**
 * The Item interface represents a general entity within the dungeon
 * that provides a beneficial effect or enhancement. Items implementing
 * this interface are expected to provide specific functionality or behavior
 * through the buff method.
 *
 * @author Jonathan Hernandez
 * @version 1.0 (April 30th, 2025)
 */
public interface Item {

    /**
     * Percent probability that an item is generated.
     */
    double GENERATION_PROB = 0.10;

    /**
     * Applies the item's effect or enhancement to the Hero.
     *
     * @return A string representation of the item's effect or enhancement.
     */
    String buff();

    /**
     * Get the name of the potion.
     * @return String name of the potion
     */
    String getName();

}
