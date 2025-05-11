package com.swagteam360.dungeonadventure.model;

import java.util.Random;

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

    /**
     * 'Heal' method represents Monsters' ability to randomly heal in combat.
     * Utilizes a random object to simulate the "chance" that a monster will heal.
     */
    private void heal() {
        //establish random object
        Random random = new Random();

        //roll for heal chance
        int healRoll = random.nextInt(100) + 1; //roll 1-100

        //if healchance is greater than or equal to the random rolled, will heal :)
        if (myHealChance >= healRoll) {
            this.setHP(this.getHP() + healRoll); //set new Health Points
        }
    }

}
