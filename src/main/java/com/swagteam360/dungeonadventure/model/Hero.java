package com.swagteam360.dungeonadventure.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Hero abstract class represents the possible player characters and their
 * baseline attributes. All Heroes have the same characteristics as a "DungeonCharacter"
 * but have an additional field "block chance" which gives them a random chance to block
 * an enemy attack.
 *
 * @author Luke Willis, Jonathan Hernandez
 * @version 4 June 2025
 */
public abstract class Hero extends DungeonCharacter {

    /**
     * Field myBlockChance represents a percent chance that a hero might block a
     * receiving attack.
     */
    private final int myBlockChance;

    /**
     * Instance field that represents the Hero's inventory.
     */
    private final List<Item> myInventory;

    /**
     * Parameter constructor calls upon the super constructor to help establish the object.
     *
     * @param theName represents the name of the hero.
     * @param theHP represents the health points of the hero.
     * @param theAttackSpeed represent the attack speed of the hero.
     * @param theDamageRangeMin represents minimum damage range.
     * @param theDamageRangeMax represents maximum damage range.
     * @param theHitChance represents the hit chance of the hero.
     * @param theBlockChance represents the block chance of the hero.
     */
    public Hero(final String theName, final int theHP,
                            final int theAttackSpeed, final int theDamageRangeMin, final int theDamageRangeMax,
                            final int theHitChance, final int theBlockChance) {
        super(theName, theHP, theAttackSpeed, theDamageRangeMin, theDamageRangeMax,
                theHitChance);
        myInventory = new ArrayList<>();
        this.myBlockChance = theBlockChance;
    }

    /**
     * Adds a list of items to the Hero's inventory.
     *
     * @param theItems The list of items to be added to the Hero's inventory.
     */
    public void addToInventory(final List<Item> theItems) {
        myInventory.addAll(theItems);
    }

    /**
     * Returns a copy of the Hero's inventory.
     *
     * @return An ArrayList of the Hero's inventory.
     */
    public List<Item> getInventory() {
        return new ArrayList<>(myInventory); // create a copy of the list to return
    }

    /**
     * Returns the pillar count of the Hero. Goes through the Hero's inventory and checks whether an item is an
     * instance of a pillar.
     *
     * @return The pillar count from the Hero's inventory.
     */
    public int getPillarCount() {
        return (int) myInventory.stream().filter(item -> item instanceof Pillar).count();
    }

    /**
     * Abstract method that Hero subclasses must implement. Each Hero has a special move performed against a monster.
     *
     * @param theMonster The monster in which the special move may be performed on.
     * @return A String that represents the results of the special move.
     */
    public abstract String specialMove(final Monster theMonster);

    /**
     * Hero's have a chance to block an attack. This method handles that chance and returns whether a random int number
     * is below myBlockChance.
     *
     * @return True if a block was successful, otherwise false.
     */
    public boolean block() {
        Random rand = new Random();
        return rand.nextInt(100) < myBlockChance;
    }
}
