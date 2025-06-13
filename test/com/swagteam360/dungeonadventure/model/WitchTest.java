package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Witch test suite.
 * This file performs unit tests against the
 * Witch class.
 *
 * @author Preston Sia (psia97)
 * @version 1.0.0, 12 June, 2025
 */
public class WitchTest {
    private final String myName = "Test Witch";
    private final int myHp = 15;
    private final int myAttackSpeed = 1;
    private final int myDamageRangeMin = 1;
    private final int myDamageRangeMax = 100;
    private final int myHitChance = 10;
    private final double myHealChance = 0.5; // is this between 0 and 1? YES
    private final int myMinHealPoints = 5;
    private final int myMaxHealPoints = 10;

    @Test
    void testConstructor() {
        final Witch witch = new Witch(
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
                        witch.getName()
                ),
                () -> assertEquals(
                        myHp,
                        witch.getHP()
                ),
                () -> assertEquals(
                        myAttackSpeed,
                        witch.getMyAttackSpeed()
                ),
                () -> assertEquals(
                        myDamageRangeMin,
                        witch.getDamageRangeMin()
                ),
                () -> assertEquals(
                        myDamageRangeMax,
                        witch.getDamageRangeMax()
                ),
                () -> assertEquals(
                        myHitChance,
                        witch.getMyHitChance()
                )
                // TODO implement checks for myHealChance, minHealth, maxHealth
        );
    }
}
