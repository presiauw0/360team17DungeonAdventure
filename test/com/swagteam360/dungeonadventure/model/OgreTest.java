package com.swagteam360.dungeonadventure.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Ogre test suite.
 * This file performs unit tests against the Ogre.
 *
 * @author Preston Sia (psia97)
 * @version 1.0.0, 12 June 2025
 */
public class OgreTest {
    private final String myName = "Test Ogre";
    private final int myHp = 15;
    private final int myAttackSpeed = 1;
    private final int myDamageRangeMin = 1;
    private final int myDamageRangeMax = 100;
    private final int myHitChance = 10;
    private final double myHealChance = 0.5; // is this between 0 and 1? YES
    private final int myMinHealPoints = 5;
    private final int myMaxHealPoints = 10;

    /* CONSTRUCTOR TESTS */

    @Test
    void testConstructor() {
        final Ogre ogre = new Ogre(
                myName,
                myHp,
                myAttackSpeed,
                myDamageRangeMin,
                myDamageRangeMax,
                myHitChance,
                myHealChance,
                myMinHealPoints,
                myMaxHealPoints
        );

        assertAll(
                () -> assertEquals(
                        myName,
                        ogre.getName()
                ),
                () -> assertEquals(
                        myHp,
                        ogre.getHP()
                ),
                () -> assertEquals(
                        myAttackSpeed,
                        ogre.getMyAttackSpeed()
                ),
                () -> assertEquals(
                        myDamageRangeMin,
                        ogre.getDamageRangeMin()
                ),
                () -> assertEquals(
                        myDamageRangeMax,
                        ogre.getDamageRangeMax()
                ),
                () -> assertEquals(
                        myHitChance,
                        ogre.getMyHitChance()
                )
                // TODO implement checks for myHealChance, minHealth, maxHealth
        );
    }
}
