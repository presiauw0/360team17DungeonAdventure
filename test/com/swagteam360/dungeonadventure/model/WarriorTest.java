package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WarriorTest {
    private final String myName = "Test Warrior";
    private final int myHp = 15;
    private final int myAttackSpeed = 1;
    private final int myDamageMin = 1;
    private final int myDamageMax = 100;
    private final int myHitChance = 10;
    private final int myBlockChance = 10;

    private Warrior myWarrior;

    @BeforeEach
    void setup() {
        myWarrior = new Warrior(myName, myHp, myAttackSpeed, myDamageMin,
                                myDamageMax, myHitChance, myBlockChance);
    }

    /* CONSTRUCTOR TESTS */
    /*  CONSTRUCTOR TESTS  */

    @Test
    void testConstructor() {

        final Warrior warrior = new Warrior(myName, myHp, myAttackSpeed, myDamageMin, myDamageMax, myHitChance, myBlockChance);

        assertAll(
                () -> assertEquals(
                        myName,
                        warrior.getName(),
                        "Name is incorrect"
                ),
                () -> assertEquals(
                        myHp,
                        warrior.getHP(),
                        "Health points is incorrect"
                ),
                () -> assertEquals(
                        myAttackSpeed,
                        warrior.getMyAttackSpeed(),
                        "attack speed is incorrect"
                ),
                () -> assertEquals(
                        myDamageMin,
                        warrior.getDamageRangeMin(),
                        "Minimum damage is incorrect"
                ),
                () -> assertEquals(
                        myDamageMax,
                        warrior.getDamageRangeMax(),
                        "Maximum damage is incorrect"
                ),
                () -> assertEquals(
                        myHitChance,
                        warrior.getMyHitChance(),
                        "Hit chance parameter is incorrect"
                )
                // TODO BLOCKCHANCE TEST
        );
    }

    @Test
    void testConstructorNullName() {
        assertThrows(
                NullPointerException.class,
                () -> new Warrior(null, myHp, myAttackSpeed, myDamageMin, myDamageMax, myHitChance, myBlockChance),
                "Failed to protect against null name data"
        );
    }

    @Test
    void testConstructorNegativeHp() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Warrior(myName, -1, myAttackSpeed, myDamageMin, myDamageMax, myHitChance, myBlockChance),
                "Failed to protect against negative health points"
        );
    }

    @Test
    void testConstructorNegativeAttackSpeed() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Warrior(myName, myHp, -1, myDamageMin, myDamageMax, myHitChance, myBlockChance),
                "Failed to protect against negative attack speed"
        );
    }

    @Test
    void testConstructorNegativeDamageMin() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Warrior(myName, myHp, myAttackSpeed, -1, myDamageMax, myHitChance, myBlockChance),
                "Failed to protect against negative minimum damage"
        );
    }

    @Test
    void testConstructorNegativeDamageMax() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Warrior(myName, myHp, myAttackSpeed, myDamageMin, -1, myHitChance, myBlockChance),
                "Failed to protect against negative maximum damage"
        );
    }

    @Test
    void testConstructorNegativeHitChance() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Warrior(myName, myHp, myAttackSpeed, myDamageMin, myDamageMax, -1, myBlockChance),
                "Failed to protect against negative hit chance"
        );
    }

    @Test
    void testConstructorNegativeBlockChance() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Warrior(myName, myHp, myAttackSpeed, myDamageMin, myDamageMax, myHitChance, myBlockChance),
                "Failed to protect against negative block chance"
        );
    }

    @Test
    void testConstructorMinGreaterThanMaxAttack() {
        final int testDamageMin = 5;
        final int testDamageMax = 4;
        assertThrows(
                IllegalArgumentException.class,
                () -> new Warrior(myName, myHp, myAttackSpeed, testDamageMin, testDamageMax, myHitChance, myBlockChance),
                "Failed to protect against paradoxical minimum and maximum damage values. " +
                        "The minimum cannot be greater than the maximum."
        );
    }

    /* WARRIOR TEST SUITE - SPECIAL MOVE */

    /**
     * Tests if the special move is successful 30% of the time
     * with a 10% tolerance.
     */
    @Test
    void testSpecialMoveChance() {
        Monster monster = new Witch("Test", myHp, myAttackSpeed, myDamageMin, myDamageMax, myHitChance,
                0.5, 1, 15);

        int bashFail = 0;
        int bashSuccess = 0;

        String result;

        for (int i = 0; i < 100; i++) {
            result = myWarrior.specialMove(monster);
            if ("Bash failed! You missed!".equals(result)) {
                bashFail++;
            } else {
                bashSuccess++;
            }
        }

        assertTrue(
                bashSuccess < 40,
                "Bash successes are not within 10% of the 30% probability"
        );
        assertTrue(
                bashFail > 60,
                "Bash failures are not within 10% of the 70% probability"
        );
    }

    // FIXME Sometimes the special move indicates that it was successful
    //  but deals 0 damage
    @Test
    void testSpecialMoveValue() {
        // custom warrior with a fixed damage amount
        Warrior testWarrior = new Warrior(myName, myHp, myAttackSpeed, 10,
                10, myHitChance, myBlockChance);

        Monster monster = new Witch("Test", myHp, myAttackSpeed, myDamageMin, myDamageMax, myHitChance,
                0.5, 1, 15);


        String result;

        do {
            result = testWarrior.specialMove(monster);
        } while("Bash failed! You missed!".equals(result));

        assertEquals(
                "Bash successful! You dealt " + 20 + " damage.",
                result,
                "Incorrect bash calculation"
        );
    }
}
