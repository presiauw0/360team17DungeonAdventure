package com.swagteam360.dungeonadventure.model;

import java.util.Random;

/**
 * Abstract Class "Monster" defines what a "Monster" is, primarily
 * inheriting characteristics from the super class. A unique quality of "Monster"
 * is the chance to heal during battle. Other than that, "Monster" is quite
 * similar to "DungeonCharacter" and "Hero".
 *
 * @author Luke Willis
 * @version 9 May 2025
 */

public abstract class Monster extends DungeonCharacter {

    /** Field myHealChance represents the % chance for a monster to heal in combat. */
    private final double myHealChance;

    private int myMinHealPoints;

    private int myMaxHealPoints;

    /**
     * Parametered constructor defining what a "Monster" is and the attributes
     * that go along with them.
     *
     * @param theName           represents character name.
     * @param theHP             represents character health points.
     * @param theAttackSpeed    represents character attack speed.
     * @param theDamageRangeMin represents minimum damage range.
     * @param theDamageRangeMax represents maximum damage range.
     * @param theHitChance      represents character hit chance.
     * @param theHealChance     represents percent chance to heal in combat.
     * @param theMinHealPoints  represents mininum amount of health that can be gained.
     * @param theMaxHealPoints  represents maximum amount of health that can be gained
     */
    public Monster(String theName, int theHP, int theAttackSpeed, int theDamageRangeMin,
                   int theDamageRangeMax, int theHitChance, double theHealChance, int theMinHealPoints,
                   int theMaxHealPoints) {
        super(theName, theHP, theAttackSpeed, theDamageRangeMin, theDamageRangeMax, theHitChance);
        myHealChance = theHealChance;
        myMinHealPoints = theMinHealPoints;
        myMaxHealPoints = theMaxHealPoints;
    }

    /**
     * 'Heal' method represents Monsters' ability to randomly heal in combat.
     *  Uses a random object to simulate the "chance" that a monster will heal.
     */
    public int heal() {
        //establish random object
        Random random = new Random();

        //if healchance is greater than or equal to the random rolled, will heal :)
        if (random.nextDouble() < myHealChance) {
            int healRange = myMaxHealPoints - myMinHealPoints + 1;
            int healAmount = random.nextInt(healRange) + myMinHealPoints;

            int currentHP = this.getHP();
            int maxHP = this.getMaxHP();

            int actualHeal = Math.min(healAmount, maxHP - currentHP);
            //this.setHP(currentHP + actualHeal);
            super.heal(actualHeal);

            return actualHeal;
        }
        return 0;
    }

}
