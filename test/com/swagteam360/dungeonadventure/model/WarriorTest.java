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
    @Test
    void testSpecialMove() {
        // TODO how to test?
    }
}
