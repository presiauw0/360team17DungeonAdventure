package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Gremlin and Monster superclass test suite.
 * This file performs unit tests against the
 * Gremlin and Monster class.
 *
 * @author Preston Sia (psia97)
 * @version 1.0.0, 12 June, 2025
 */
public class GremlinTest {
    private final String myName = "Test Gremlin";
    private final int myHp = 15;
    private final int myAttackSpeed = 1;
    private final int myDamageRangeMin = 1;
    private final int myDamageRangeMax = 100;
    private final int myHitChance = 10;
    private final double myHealChance = 0.5; // is this between 0 and 1? YES
    private final int myMinHealPoints = 5;
    private final int myMaxHealPoints = 10;

    //private Gremlin myGremlin;

    /*
    @BeforeEach
    void setup() {
        myGremlin = new Gremlin(
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
    }*/

    /* CONSTRUCTOR TESTS */

    @Test
    void testConstructor() {
        final Gremlin gremlin = new Gremlin(
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
                        gremlin.getName()
                ),
                () -> assertEquals(
                        myHp,
                        gremlin.getHP()
                ),
                () -> assertEquals(
                        myAttackSpeed,
                        gremlin.getMyAttackSpeed()
                ),
                () -> assertEquals(
                        myDamageRangeMin,
                        gremlin.getDamageRangeMin()
                ),
                () -> assertEquals(
                        myDamageRangeMax,
                        gremlin.getDamageRangeMax()
                ),
                () -> assertEquals(
                        myHitChance,
                        gremlin.getMyHitChance()
                )
                // TODO implement checks for myHealChance, minHealth, maxHealth
        );
    }

    @Test
    void testConstructorHealChanceNegative() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Gremlin(
                        myName,
                        myHp,
                        myAttackSpeed,
                        myDamageRangeMin,
                        myDamageRangeMax,
                        myHitChance,
                        -0.5,
                        myMinHealPoints,
                        myMaxHealPoints
                ),
                "Fail to protect against negative heal chance"
        );
    }

    @Test
    void testConstructorHealChanceGreaterThanOne() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Gremlin(
                        myName,
                        myHp,
                        myAttackSpeed,
                        myDamageRangeMin,
                        myDamageRangeMax,
                        myHitChance,
                        1.01,
                        myMinHealPoints,
                        myMaxHealPoints
                ),
                "Fail to protect against heal chance of >100%"
        );
    }

    @Test
    void testConstructorNegativeMinMaxHealth() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Gremlin(
                        myName,
                        myHp,
                        myAttackSpeed,
                        myDamageRangeMin,
                        myDamageRangeMax,
                        myHitChance,
                        myHealChance,
                        -1,
                        -1
                ),
                "Fail to protect against negative min/max heal points"
        );
    }

    @Test
    void testConstructorMinGreaterThanMaxHealPts() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Gremlin(
                        myName,
                        myHp,
                        myAttackSpeed,
                        myDamageRangeMin,
                        myDamageRangeMax,
                        myHitChance,
                        myHealChance,
                        5,
                        4
                ),
                "Fail to protect against paradoxical minimum and maximum" +
                        " values for heal points. The min cannot be greater than the max."
        );
    }

    /*  Monster test suite  */
    @Test
    void testHealAlways() {
        final Gremlin gremlin = new Gremlin(
                myName,
                myHp,
                myAttackSpeed,
                myDamageRangeMin,
                myDamageRangeMax,
                myHitChance,
                1.0,
                myMinHealPoints,
                myMaxHealPoints
        );

        int damageAmount = 10;
        gremlin.takeDamage(damageAmount);

        int healthBeforeHeal = gremlin.getHP();

        int result = gremlin.heal();

        assertAll(
                () -> assertTrue(
                        result > 0,
                        "Result was not greater than 0"
                ),
                () -> assertTrue(
                        gremlin.getHP() > healthBeforeHeal,
                        "Health did not change"
                )
        );
    }

    // FIXME heal() NEEDS more documentation

    @Test
    void testHealNever() {
        final Gremlin gremlin = new Gremlin(
                myName,
                myHp,
                myAttackSpeed,
                myDamageRangeMin,
                myDamageRangeMax,
                myHitChance,
                0,
                myMinHealPoints,
                myMaxHealPoints
        );

        int damageAmount = 10;
        gremlin.takeDamage(damageAmount);

        int healthBeforeHeal = gremlin.getHP();

        int result = gremlin.heal();

        assertAll(
                () -> assertTrue(
                        result == 0,
                        "Should not have healed"
                ),
                () -> assertTrue(
                        gremlin.getHP() == healthBeforeHeal,
                        "Health should not have changed"
                )
        );
    }

    // TODO test poison? I don't think this is going to be implemented
    @Test
    void testPoision() {

    }

}
