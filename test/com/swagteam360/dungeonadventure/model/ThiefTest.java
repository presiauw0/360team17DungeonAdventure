package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Thief test suite.
 * This file performs unit tests against
 * the Theif class.
 *
 * @author Preston Sia (psia97)
 * @version 1.0.0, 12 June 2025
 */
public class ThiefTest {
    private final String myName = "Test Thief";
    private final int myHp = 15;
    private final int myAttackSpeed = 1;
    private final int myDamageMin = 1;
    private final int myDamageMax = 100;
    private final int myHitChance = 10;
    private final int myBlockChance = 10;

    private Thief myThief;

    @BeforeEach
    void setup() {
        myThief = new Thief(myName, myHp, myAttackSpeed, myDamageMin, myDamageMax, myHitChance, myBlockChance);
    }

    /*  CONSTRUCTOR TESTS  */
    @Test
    void testConstructor() {

        final Thief thief = new Thief(myName, myHp, myAttackSpeed, myDamageMin, myDamageMax, myHitChance, myBlockChance);

        assertAll(
                () -> assertEquals(
                        myName,
                        thief.getName(),
                        "Name is incorrect"
                ),
                () -> assertEquals(
                        myHp,
                        thief.getHP(),
                        "Health points is incorrect"
                ),
                () -> assertEquals(
                        myAttackSpeed,
                        thief.getMyAttackSpeed(),
                        "attack speed is incorrect"
                ),
                () -> assertEquals(
                        myDamageMin,
                        thief.getDamageRangeMin(),
                        "Minimum damage is incorrect"
                ),
                () -> assertEquals(
                        myDamageMax,
                        thief.getDamageRangeMax(),
                        "Maximum damage is incorrect"
                ),
                () -> assertEquals(
                        myHitChance,
                        thief.getMyHitChance(),
                        "Hit chance parameter is incorrect"
                )
                // TODO BLOCKCHANCE TEST
        );
    }

    // FIXME Jonathan, magic numbers are not good. There should be variables for these
    //  numbers, and the parameters should be documented. I'm not testing this though
    //  because it is not documented.

    // FIXME - too complicated for me right now
    /**
     *
     */
    @Test
    void testSpecialMoveSuccessful() {
        // custom warrior with a fixed damage amount
        Thief testThief = new Thief(myName, myHp, myAttackSpeed, 10,
                10, myHitChance, myBlockChance);

        Monster monster = new Witch("Test", myHp, myAttackSpeed, myDamageMin, myDamageMax, myHitChance,
                0.5, 1, 15);

        String result;

        do {
            result = testThief.specialMove(monster);
        } while ("You've been caught! Attack missed!".equals(result)
                || "You tried a surprise attack but missed!".equals(result));

        assertTrue(
                result.equals("You performed a normal attack for " + 10 + " damage!"),
                "What was returned: " + result
        );
    }
}
