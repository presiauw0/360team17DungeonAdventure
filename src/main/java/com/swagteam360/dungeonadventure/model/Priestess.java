package com.swagteam360.dungeonadventure.model;

import java.util.Random;

/**
 *  The Priestess class is a specific type of Hero, one of the 3 player
 * characters. A Priestess has the special ability "Heal" which allows
 * the player to heal themselves for a value up to 75% of their maximum
 * health points.
 *
 * @author Luke Willis
 * @version 3 May 2025
 */
public class Priestess extends Hero {

    /** Field utilized for the heal ability of the priestess */
    private final int maximumHP;

    /**
     * Parametered constructor, calls upon the super constructor to help establish the object.
     *
     * @param theName           represents the name of the hero.
     * @param theHP             represents the health points of the hero.
     * @param theAttackSpeed    represent the attack speed of the hero.
     * @param theDamageRangeMin represents minimum damage range.
     * @param theDamageRangeMax represents maximum damage range.
     * @param theHitChance      represents the hit chance of the hero.
     * @param theBlockChance    represents the block chance of the hero.
     */
    public Priestess(String theName, int theHP, int theAttackSpeed, int theDamageRangeMin, int theDamageRangeMax, int theHitChance, int theBlockChance) {
        super(theName, theHP, theAttackSpeed, theDamageRangeMin, theDamageRangeMax, theHitChance, theBlockChance);
        maximumHP = theHP;
    }

    /**
     * "Heal" is the ability of the priestess, in which they can heal
     * their HP by a value up to 75% of their maximum health points.
     *
     *
     * @return String representing the outcome of this special move.
     */
    @Override
    public String specialMove(final Monster theMonster) {
        Random rand = new Random();

        int minHeal = 10;
        int maxHeal = (int)(0.75 * maximumHP);
        int healAmt = rand.nextInt(maxHeal - minHeal) + minHeal;

        //making sure overhealing doesn't happen.
        healAmt = (Math.min(super.getHP() + healAmt, maximumHP));
        super.heal(healAmt);
        return "You healed for " + healAmt + " points of health!";
    }
}
