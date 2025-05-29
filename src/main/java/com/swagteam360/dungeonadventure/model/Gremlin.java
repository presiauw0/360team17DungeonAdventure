package com.swagteam360.dungeonadventure.model;

import java.util.Random;

public class Gremlin extends Monster {
    /**
     * Parametered constructor defining what a "Gremlin" is and the attributes
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
    public Gremlin(String theName, int theHP, int theAttackSpeed,
                 int theDamageRangeMin, int theDamageRangeMax, int theHitChance,
                 double theHealChance, int theMinHealPoints, int theMaxHealPoints) {
        super(theName, theHP, theAttackSpeed, theDamageRangeMin,
                theDamageRangeMax, theHitChance, theHealChance, theMinHealPoints,
                theMaxHealPoints);
    }



    /**
     * The Gremlins unique ability "Poison" deals minimal Damage Over Time
     * to the opposing character. This method is meant to be called
     * multiple times, as defined in combat instantiation.
     * @return int representing damage done per cycle.
     */
    private int poison() {
        return 5;
    }
}
