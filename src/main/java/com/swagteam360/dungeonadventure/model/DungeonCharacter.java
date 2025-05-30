package com.swagteam360.dungeonadventure.model;

import java.util.Random;
import java.io.Serializable;

/**
 * The DungeonCharacter Abstract Class represents a character, whether
 * it be a player character or a non-player character, and defines basic
 * attributes that are common throughout all characters in the DungeonAdventure
 * game.
 *
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
        myName = theName;
        myHP = theHP;
        myMaxHP = theHP;
        myAttackSpeed = theAttackSpeed;
        myDamageRangeMin = theDamageRangeMin;
        myDamageRangeMax = theDamageRangeMax;
        myHitChance = theHitChance;
    }


    /**
     * Attack method, uses a random number between the minimum and the maximum
     * to damage the opponent.
     * @param theDamageRangeMin represents min amount of damage that can be inflicted.
     * @param theDamageRangeMax represents max amount of damage that can be inflicted.
     * @param theHitChance represents chance to miss a hit
     * @return int representing amount of damage dealt.
     */
    public int attack(final int theDamageRangeMin, final int theDamageRangeMax,
                      final int theHitChance) {

        int returned;
        Random random = new Random();

        // check if attack hits based on hitchance
        int hitRoll = random.nextInt(100) + 1; //roll 1-100

        if (hitRoll <= theHitChance) {

            //attack hits
            int dmg = 0;
            if (theDamageRangeMax > theDamageRangeMin) {
                dmg = random.nextInt(theDamageRangeMax - theDamageRangeMin + 1) + theDamageRangeMin; //ensures always above the min
            } else {
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
        this.myHP = Math.max(0,  this.myHP - theDamage);
    }

    /**
     * Method to heal the health points of a dungeon character.
     * @param theAdded represents the health added to the character
     */
    public void heal(int theAdded) {
        this.myHP += theAdded;
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
     * Setter method to change characters health points.
     * @param theHP represents new value of health points.
     */
    public void setHP(final int theHP) {
        myHP = theHP;
    }

    /**
     * Getter method fetches damage range minimum.
     * @return int damage range min.
     */
    public int getDamageRangeMin() {return myDamageRangeMin;}

    /**
     * Setter to set damage range min.
     * @param theDamageRangeMin represents new value of damage range min.
     */
    public void setDamageRangeMin(final int theDamageRangeMin) {
        myDamageRangeMin = theDamageRangeMin;
    }

    /**
     * Getter to fetch damage range max.
     * @return int representing damage range max.
     */
    public int getDamageRangeMax() {return myDamageRangeMax;}

    /**
     * Setter to set damage range maxmimum.
     * @param theDamageRangeMax represents new value of damage range maximum.
     */
    public void setDamageRangeMax(final int theDamageRangeMax) {
        myDamageRangeMax = theDamageRangeMax;
    }


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
