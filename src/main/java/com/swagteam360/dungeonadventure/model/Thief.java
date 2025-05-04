package com.swagteam360.dungeonadventure.model;

/**
 *  The Thief class is a specific type of Hero, one of the 3 player
 * characters. A Thief has the special ability "Poison Knife" which allows
 * the player to poison the enemy for 5-15 seconds, dealing 5 damage/second.
 *
 * @author Luke Willis
 * @version 3 May 2025
 */
public class Thief extends Hero{
    /**
     * Parametered constructor, calls upon the super constructor to help establish the object.
     *
     * @param theName           represents the name of the hero.
     * @param theAttackDamage   represents damage done per attack.
     * @param theHP             represents the health points of the hero.
     * @param theAttackSpeed    represent the attack speed of the hero.
     * @param theDamageRangeMin represents minimum damage range.
     * @param theDamageRangeMax represents maximum damage range.
     * @param theHitChance      represents the hit chance of the hero.
     * @param theCharacterType  represents the enumerated type of the hero.
     * @param theBlockChance    represents the block chance of the hero.
     */
    public Thief(String theName, int theAttackDamage, int theHP, int theAttackSpeed, int theDamageRangeMin, int theDamageRangeMax, int theHitChance, characterType theCharacterType, int theBlockChance) {
        super(theName, theAttackDamage, theHP, theAttackSpeed, theDamageRangeMin, theDamageRangeMax, theHitChance, theCharacterType, theBlockChance);
    }

    /**
     * The special ability of the Thief is poison() in which one of his attacks
     * will poison the enemy. This ability does 5dmg/second and will last
     * anywhere from 5 to 15 seconds.
     */
    private void poison() {
        //this method needs to wait until we can implement a time system
        // as this would to Damage over time (i.e. 5dmg/second)
    }
}
