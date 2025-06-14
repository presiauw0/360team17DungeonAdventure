package com.swagteam360.dungeonadventure.model;

import java.util.Random;

/**
 *  The Thief class is a specific type of Hero, one of the 3-player
 * characters. A Thief has the special ability Surprise attack, which
 * may guarantee two attacks, one normal attack, or no attack (caught).
 *
 * @author Luke Willis, Jonathan Hernandez
 * @version 4 June 2025
 */
public class Thief extends Hero {

    /**
     * Variable for roll chance
     */
    private static final int HUNDRED = 100;

    /**
     * Variable which represents the chance of a thief being caught.
     */
    private static final int PERCENT_CAUGHT = 20;

    /**
     * Variable that represents the chance of a thief performing a normal attack
     * instead of a "Surprise Attack". Note that this normal attack can fail as well.
     */
    private static final int PERCENT_NORMAL= 60;

    /**
     * Parameter constructor calls upon the super constructor to help establish the object.
     *
     * @param theName           represents the name of the hero.
     * @param theHP             represents the health points of the hero.
     * @param theAttackSpeed    represent the attack speed of the hero.
     * @param theDamageRangeMin represents minimum damage range.
     * @param theDamageRangeMax represents maximum damage range.
     * @param theHitChance      represents the hit chance of the hero.
     * @param theBlockChance    represents the block chance of the hero.
     */
    public Thief(String theName, int theHP, int theAttackSpeed, int theDamageRangeMin, int theDamageRangeMax, int theHitChance, int theBlockChance) {
        super(theName, theHP, theAttackSpeed, theDamageRangeMin, theDamageRangeMax, theHitChance, theBlockChance);
    }

    /**
     * Handles the specialMove of the Thief. A surprise attack guarantees two attacks, but
     * if the roll chance is smaller, the thief may perform a regular attack or miss.
     *
     * @param theMonster The monster in which the special move may be performed on.
     * @param theRandom The random number generator used to determine the roll chance.
     * @return A string representation of the outcome of this special move.
     */
    protected String specialMove(final Monster theMonster, final Random theRandom) {

        final int roll = theRandom.nextInt(HUNDRED);
        String result;

        if (roll < PERCENT_CAUGHT) {
            result = "You've been caught! Attack missed!";
        } else if (roll < PERCENT_NORMAL) {
            int damage = attack(getDamageRangeMin(), getDamageRangeMax(), HUNDRED); // Guaranteed to hit

            theMonster.takeDamage(damage);
            result = "You performed a normal attack for " + damage + " damage!";

        } else {
            int totalDamage = 0;
            StringBuilder sb = new StringBuilder("Surprise attack successful! ");
            for (int i = 1; i <= 2; i++) {
                int damage = attack(getDamageRangeMin(), getDamageRangeMax(), HUNDRED); // Guaranteed to hit
                theMonster.takeDamage(damage);
                totalDamage += damage;
                sb.append("Hit ").append(i).append(": ").append(damage).append(" damage!\n");

            }

            sb.append("Total damage: ").append(totalDamage).append("!");
            result = sb.toString();
        }

        return result;

    }

    /**
     * Thief can launch a surprise attack against a monster. A successful surprise attack will deal two attacks worth
     * of damage to the monster. An unsuccessful surprise attack may only consist of one attack or a missed attack.
     *
     * @param theMonster The monster in which the special move may be performed on.
     * @return A string representation of the outcome of this special move.
     */
    @Override
    public String specialMove(final Monster theMonster) {
        return specialMove(theMonster, new Random());
    }
}
