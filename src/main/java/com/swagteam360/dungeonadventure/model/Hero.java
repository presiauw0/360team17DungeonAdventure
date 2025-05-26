package com.swagteam360.dungeonadventure.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Hero abstract class represents the possible player characters, and their
 * baseline attributes. All Heroes have the same characteristics as a "DungeonCharacter"
 * but have an additional field "block chance" which gives them a random chance to block
 * an enemies attack.
 *
 * @author Luke Willis
 * @version 3 May 2025
 */
public abstract class Hero extends DungeonCharacter {

    /**
     * Field myBlockChance represents a percent chance that a hero might block a
     * receiving attack.
     */
    private int myBlockChance;

    private final List<Item> myInventory;

    /**
     * Parametered constructor, calls upon the super constructor to help establish the object.
     *
     * @param theName represents the name of the hero.
     * @param theAttackDamage represents damage done per attack.
     * @param theHP represents the health points of the hero.
     * @param theAttackSpeed represent the attack speed of the hero.
     * @param theDamageRangeMin represents minimum damage range.
     * @param theDamageRangeMax represents maximum damage range.
     * @param theHitChance represents the hit chance of the hero.
     * @param theBlockChance represents the block chance of the hero.
     */
    public Hero(final String theName, final int theAttackDamage, final int theHP,
                            final int theAttackSpeed, final int theDamageRangeMin, final int theDamageRangeMax,
                            final int theHitChance, final int theBlockChance) {
        super(theName, theAttackDamage, theHP, theAttackSpeed, theDamageRangeMin, theDamageRangeMax,
                theHitChance);
        myInventory = new ArrayList<>();
        this.myBlockChance = theBlockChance;
    }

    public void addToInventory(final List<Item> theItems) {
        myInventory.addAll(theItems);
    }

    public List<Item> getInventory() {
        return new ArrayList<>(myInventory); // create a copy of the list to return
    }

    //incorporate a block() method? I'm not sure how that would work.
}
