package com.swagteam360.dungeonadventure.model;

import java.util.Objects;
import java.util.Random;
import java.io.Serializable;

/**
 * The DungeonCharacter Abstract Class represents a character, whether
 * it be a player character or a non-player character, and defines basic
 * attributes that are common throughout all characters in the DungeonAdventure
 * game.
 * For dungeon characters, the maximum number of health points is set based
 * on the initial number of health points given to the character. In otherwords,
 * characters start out with full health, and that amount of health is their max.
 * Every character has a name, base HP, damage range, attack speed, hit chance,
 * and a "character type," an enumerated value to help identify characters.
 *
 * @author Luke Willis
 * @version 1 May 2025
 */
public abstract class DungeonCharacter implements Serializable {
    /** Field myName represents name given to a character. */
    private String myName;

    /** Field myHP represents Health Points given to a character. */
    private int myHP;

    private final int myMaxHP;

    /** Field myDamageRangeMin represents the minimum damage a character can inflict. */
    private int myDamageRangeMin;

    /** Field myDanageRangeMax represents the maximum damage a character can inflict.  */
    private int myDamageRangeMax;

    /** Field myAttackSpeed represents attack speed of a character (to determine turn order) */
    private int myAttackSpeed;

    /**
     * Field myHitChance represents a percent chance to hit or miss a target.
     * If a character has 90% hit chance, they have a 10% chance of missing their
     * hit and doing no damage to their opponent.
     */
    private final int myHitChance;


    /**
     * Parametered constructor defining what a "DungeonCharacter" is and the attributes
     * that go along with them.
     * @param theName represents character name.
     * @param theHP represents character health points.
     * @param theAttackSpeed represents character attack speed.
     * @param theDamageRangeMin represents minimum damage range.
     * @param theDamageRangeMax represents maximum damage range.
     * @param theHitChance represents character hit chance.
     */
    public DungeonCharacter(final String theName, final int theHP,
                            final int theAttackSpeed, final int theDamageRangeMin, final int theDamageRangeMax,
                            final int theHitChance) {
        myName = Objects.requireNonNull(theName);

        // test for negative value
        if (theHP < 0 || theAttackSpeed < 0 || theDamageRangeMin < 0
            || theDamageRangeMax < 0 || theHitChance < 0) {
            throw new IllegalArgumentException("Parameters cannot be negative");
        }
        // test if minimum values are greater than maximum values
        if (theDamageRangeMin > theDamageRangeMax) {
            throw new IllegalArgumentException(
                    "The minimum damage range cannot be greater than the maximum damage range"
            );
        }

        myHP = theHP;
        myMaxHP = theHP;
        myAttackSpeed = theAttackSpeed;
        myDamageRangeMin = theDamageRangeMin;
        myDamageRangeMax = theDamageRangeMax;
        myHitChance = theHitChance;
    }


    /**
     * Attack method, uses a random number between the minimum and the maximum
     * to damage the opponent. HitChance controls the percent chance
     * that a hit is actually made. If no hit is made, the method will
     * return a value of 0. If theDamageRangeMin is GREATER than theDamageRangeMax,
     * and a hit is made, the value specified in theDamageRangeMin is used (which
     * may be the maximum in this case).
     * @param theDamageRangeMin represents min amount of damage that can be inflicted.
     * @param theDamageRangeMax represents max amount of damage that can be inflicted.
     * @param theHitChance represents chance to miss a hit. This should be a number between 0 and 100,
     *                     representing a whole number percent.
     * @return int representing amount of damage dealt.
     */
    public int attack(final int theDamageRangeMin, final int theDamageRangeMax,
                      final int theHitChance) {

        // test negative hit chance
        if (theHitChance < 0){
            throw new IllegalArgumentException("Hit chance cannot be negative");
        }

        int returned;
        Random random = new Random();

        // check if attack hits based on hitchance
        int hitRoll = random.nextInt(100) + 1; //roll 1-100

        if (hitRoll <= theHitChance) {

            //attack hits
            int dmg = 0;
            if (theDamageRangeMax > theDamageRangeMin) {
                dmg = random.nextInt(theDamageRangeMax - theDamageRangeMin + 1) + theDamageRangeMin; //ensures always above the min
            } else { // if the min is greater than the max, use theDamageRangeMin value.
                dmg = theDamageRangeMin;
            }

            returned = dmg;
        } else {
            returned = 0;
        }
        return returned;
    }

    /**
     * Method to apply damage taken to a DungeonCharacter.
     * @param theDamage represents damage taken.
     */
    public void takeDamage(int theDamage) {
        // test negative parameter
        if (theDamage < 0) {
            throw new IllegalArgumentException("Damage value cannot be negative");
        }

        this.myHP = Math.max(0,  this.myHP - theDamage);
    }

    /**
     * Method to heal the health points of a dungeon character.
     * If the amount will cause the character to exceed its
     * maximum health, then the maximum health is set.
     * If passed a negative parameter, an IllegalArgumentException
     * is thrown.
     * @param theAdded represents the health added to the character
     */
    public void heal(int theAdded) {
        // test negative value
        if (theAdded < 0) {
            throw new IllegalArgumentException("Parameter cannot be negative");
        }

        //this.myHP += theAdded;
        int hpCalc = myHP + theAdded;
        myHP = Math.min(hpCalc, myMaxHP); // Do NOT exceed the maximum HP
    }
    /**
     * Getter method to fetch character name.
     * @return String of character name.
     */
    public String getName() {return myName.toString();}

    /**
     * Getter method to fetch character health points.
     * @return int character HP.
     */
    public int getHP() {return myHP;}

    public int getMaxHP() {return myMaxHP;}

    /**
     * Getter method fetches damage range minimum.
     * @return int damage range min.
     */
    public int getDamageRangeMin() {return myDamageRangeMin;}

    /**
     * Getter to fetch damage range max.
     * @return int representing damage range max.
     */
    public int getDamageRangeMax() {return myDamageRangeMax;}

    /**
     * Getter method to fetch character attack speed.
     * @return int character attack speed.
     */
    public int getMyAttackSpeed() {return myAttackSpeed;}

    /**
     * Setter method to set attack speed.
     * @param theAttackSpeed represents new attack speed.
     */
    public void setMyAttackSpeed(final int theAttackSpeed) {
        // check negative value
        if (theAttackSpeed < 0) {
            throw new IllegalArgumentException("Attack speed cannot be negative");
        }

        myAttackSpeed = theAttackSpeed;
    }
    /**
     * Getter method to fetch character hit chance.
     * @return int character hit chance.
     */
    public int getMyHitChance() {return myHitChance;}

    /**
     * Override of Objects' toString to portray a version of the character
     * object with information displayed such as Name, character type, and values
     * for HP, Attack Speed, and Hit Chance.
     * @return string representation of DungeonCharacter object.
     */
    @Override
    public String toString() {
        return  myName + "\n" +
                "----------\n" +
                "HP: " + myHP + "\n" +
                "Attack Speed: " + myAttackSpeed +"\n" +
                "Hit Chance: " + myHitChance + "\n";

    }
}
