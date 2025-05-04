package com.swagteam360.dungeonadventure.model;

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
     * @param theCharacterType represents the enumerated type of the hero.
     * @param theBlockChance represents the block chance of the hero.
     */
    public Hero(final String theName, final int theAttackDamage, final int theHP,
                            final int theAttackSpeed, final int theDamageRangeMin, final int theDamageRangeMax,
                            final int theHitChance, final characterType theCharacterType,
                            final int theBlockChance) {
        super(theName, theAttackDamage, theHP, theAttackSpeed, theDamageRangeMin, theDamageRangeMax,
                theHitChance, theCharacterType);
        this.myBlockChance = theBlockChance;
    }

    //incorporate a block() method? I'm not sure how that would work.
}
