package com.swagteam360.dungeonadventure.model;

/**
 * The HealthPotion class represents a consumable item that restores a specified
 * number of health points (HP) to a character. This class implements the Item
 * interface, allowing it to provide a beneficial effect through its buff method.
 *
 * A HealthPotion is initialized with a definitive amount of HP it can restore,
 * which is used to generate the effect description when applied.
 *
 * @author Jonathan Hernandez
 * @version 1.0 (April 30th, 2025)
 */
public class HealthPotion implements Item {

    private static final String NAME = "Health Potion";

    /**
     * Represents the number of health points (HP) that this health potion can restore.
     * This value is immutable and is assigned during the instantiation of the HealthPotion object.
     * It is used to determine the health restoration effect when the potion is consumed.
     */
    private final int myHealAmount;

    /**
     * Constructs a new instance of the HealthPotion class.
     * @param theHealAmount The amount of HP this health potion can restore.
     */
    HealthPotion(int theHealAmount) {
        myHealAmount = theHealAmount;
    }

    /**
     * Applies the effect of the HealthPotion by returning a description of the
     * health points (HP) restored.
     *
     * @return A string indicating the amount of HP gained when the potion is consumed.
     */
    @Override
    public String buff() {
        Hero player = GameManager.getInstance().getHero();
        player.heal(myHealAmount);
        player.removeFromInventory(this);
        return "You gain " + myHealAmount + " HP!";
    }

    @Override
    public String getName() {
        return NAME;
    }

    /**
     * Retrieves the number of health points (HP) this health potion can restore.
     *
     * @return The health restoration amount of this potion as an integer.
     */
    public int getHealAmount() {
        return myHealAmount;
    }

}
