package com.swagteam360.dungeonadventure.model;

/**
 * Concrete Ogre class extends abstract Monster class, defining
 * what an Ogre is under the inheritance hierachy. Ogre has a special ability
 * which is similar to Warriors' "Bash," dealing extra damage with a lower hit chance.
 *
 * @author Luke Willis
 * @version 9 May 2025
 */
public class Ogre extends Monster {
    /**
     * Parametered constructor defining what a "Ogre" is and the attributes
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
    public Ogre(final String theName, final int theHP, final int theAttackSpeed,
                 final int theDamageRangeMin, final int theDamageRangeMax, final int theHitChance,
                 final double theHealChance, final int theMinHealPoints, final int theMaxHealPoints) {
        super(theName, theHP, theAttackSpeed, theDamageRangeMin,
                theDamageRangeMax, theHitChance, theHealChance, theMinHealPoints,
                theMaxHealPoints);
    }
    /**
     * Similar to Warriors' "Bash" ability, the Ogres' special ability is a slam
     * attack that deals +40 damage to an opponent in battle. This ability has a lower
     * chance to hit than normal Ogre attacks.
     * @return int representing amount of damage done.
     */
    private int groundSlam() {
        return attack(getDamageRangeMin(), getDamageRangeMax(), 20) + 40;
    }
}
