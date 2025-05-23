package com.swagteam360.dungeonadventure.model;

public class Witch extends Monster {
    /**
     * Parametered constructor defining what a "Witch" is and the attributes
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
    public Witch(String theName, int theAttackDamage, int theHP, int theAttackSpeed, int theDamageRangeMin, int theDamageRangeMax, int theHitChance, int theHealChance) {
        super(theName, theAttackDamage, theHP, theAttackSpeed, theDamageRangeMin, theDamageRangeMax, theHitChance, theHealChance);
    }

}
