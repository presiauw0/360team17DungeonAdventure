package com.swagteam360.dungeonadventure.model;

/**
 * Abtract Class "Monster" defines what a "Monster" is, primarily
 * inheriting characteristics from the super class. A unique quality of "Monster"
 * is the chance to heal during battle. Other than that, "Monster" is quite
 * similar to "DungeonCharacter" and "Hero".
 *
 * @author Luke Willis
 * @version 9 May 2025
 */

public abstract class Monster extends DungeonCharacter {

    /** Field myHealChance represents the % chance for a monster to heal in combat. */
    private int myHealChance;

    /**
     * Parametered constructor defining what a "Monster" is and the attributes
     * that go along with them.
     *
     * @param theName           represents character name.
     * @param theAttackDamage   represents damage done per attack.
     * @param theHP             represents character health points.
     * @param theAttackSpeed    represents character attack speed.
     * @param theDamageRangeMin represents minimum damage range.
     * @param theDamageRangeMax represents maximum damage range.
     * @param theHitChance      represents character hit chance.
     * @param theHealChance     represents percent chance to heal in combat.
     */
    public Monster(String theName, int theAttackDamage, int theHP, int theAttackSpeed, int theDamageRangeMin,
                   int theDamageRangeMax, int theHitChance, int theHealChance) {
        super(theName, theAttackDamage, theHP, theAttackSpeed, theDamageRangeMin, theDamageRangeMax, theHitChance);
        myHealChance = theHealChance;
    }
}
