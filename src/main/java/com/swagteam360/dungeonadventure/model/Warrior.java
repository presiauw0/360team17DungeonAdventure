package com.swagteam360.dungeonadventure.model;

/**
 * The Warrior class is a specific type of Hero, one of the 3 player
 * characters. A warrior has the special ability "Bash" which deals more damage
 * with a smaller hit chance.
 *
 * @author Luke Willis
 * @version 3 May 2025
 */
public class Warrior extends Hero {

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
    public Warrior(final String theName, final int theAttackDamage, final int theHP,
                final int theAttackSpeed, final int theDamageRangeMin, final int theDamageRangeMax,
                final int theHitChance, final int theBlockChance) {
        super(theName, theAttackDamage, theHP, theAttackSpeed, theDamageRangeMin, theDamageRangeMax,
                theHitChance, theBlockChance);
    }

    /**
     * "Bash" is the special ability of the warrior, in which this attack
     * has a 30% chance of hitting but does double the damage a normal warrior
     * attack does.
     *
     * @return int representing amount of damage dealt
     */
    private int bash() {
        //multiplier for bash damage, 30% chance to hit, multiplied by 2 for ability effect.
        /*
         * method returns the attack function, with the warriors default damage min and max,
         * and with a set 30% hit chance. Then, multiplies output by 2 to result in the doubled
         * damage.
         */
        return attack(getDamageRangeMin(), getDamageRangeMax(), 30) * 2;
    }
}
