package com.swagteam360.dungeonadventure.model;

public class Witch extends Monster {
    /**
     * Parametered constructor defining what a "Witch" is and the attributes
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
    public Witch(String theName, int theHP, int theAttackSpeed,
                 int theDamageRangeMin, int theDamageRangeMax, int theHitChance,
                 double theHealChance, int theMinHealPoints, int theMaxHealPoints) {
        super(theName, theHP, theAttackSpeed, theDamageRangeMin,
                theDamageRangeMax, theHitChance, theHealChance, theMinHealPoints,
                theMaxHealPoints);
    }

}
