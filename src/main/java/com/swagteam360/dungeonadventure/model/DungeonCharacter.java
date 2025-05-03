package com.swagteam360.dungeonadventure.model;

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
public abstract class DungeonCharacter {
    /** Field myName represents name given to a character. */
    private String myName;

    /** Field myHP represents Health Points given to a character. */
    private int myHP;

    /** Field myDamageRangeMin represents the minimum damage range of a character. */
    private int myDamageRangeMin;

    /** Field myDanageRangeMax represents the maximum damage range of a character */
    private int myDamageRangeMax;

    /** Field myAttackSpeed represents attack speed of a character (to determine turn order) */
    private int myAttackSpeed;

    /**
     * Field myHitChance represents a percent chance to hit or miss a target.
     * If a character has 90% hit chance, they have a 10% chance of missing their
     * hit and doing no damage to their opponent.
     */
    private int myHitChance;

    /** */
    private final characterType myCharacterType;


    //constructor

    public DungeonCharacter(final String theName, final int theHP, final int theAttackSpeed,
                            final int theHitChance, final characterType theCharacterType) {
        myName = theName;
        myHP = theHP;
        myAttackSpeed = theAttackSpeed;
        myHitChance = theHitChance;
        myCharacterType = theCharacterType;
    }

    /*
    Im not to sure if this should be defined here or elsewhere as a separate class.0
     */
    protected enum characterType {
        WARRIOR,
        PRIESTESS,
        THIEF,
        OGRE,
        SKELETON,
        GOBLIN
    }
    public int attack() {

    }

    public String getName() {return myName.toString();}

    public int getHP() {return myHP;}

    public void setHP(final int theHP) {
        myHP = theHP;
    }

    public int getMyAttackSpeed() {return myAttackSpeed;}

    public int getMyHitChance() {return myHitChance;}

    @Override
    public String toString() {
        return  myName + " Type: " + myCharacterType.toString() + "\n" +
                "----------\n" +
                "HP: " + myHP + "\n" +
                "Attack Speed: " + myAttackSpeed +"\n" +
                "Hit Chance: " + myHitChance + "\n";

    }
}
