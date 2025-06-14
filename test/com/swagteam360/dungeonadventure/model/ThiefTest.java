package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Thief test suite.
 * This file performs unit tests against
 * the Thief class.
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
        myThief = spy(new Thief(myName, myHp, myAttackSpeed, myDamageMin, myDamageMax, myHitChance, myBlockChance));
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

//    @Test
//    void testSpecialMoveSuccessful() {
//        // custom warrior with a fixed damage amount
//        Thief testThief = new Thief(myName, myHp, myAttackSpeed, 10,
//                10, myHitChance, myBlockChance);
//
//        Monster monster = new Witch("Test", myHp, myAttackSpeed, myDamageMin, myDamageMax, myHitChance,
//                0.5, 1, 15);
//
//        String result;
//
//        do {
//            result = testThief.specialMove(monster);
//        } while ("You've been caught! Attack missed!".equals(result)
//                || "You tried a surprise attack but missed!".equals(result));
//
//        assertTrue(
//                result.equals("You performed a normal attack for " + 10 + " damage!"),
//                "What was returned: " + result
//        );
//    }

    @Test
    void testSpecialMoveCaught() {
        // Mock
        final Monster monster = mock(Monster.class);
        final Random mockRand = mock(Random.class);

        when(mockRand.nextInt(100)).thenReturn(5);
        final String result = myThief.specialMove(monster, mockRand);

        assertTrue(result.contains("caught"));
        verify(monster, never()).takeDamage(anyInt());

    }

    @Test
    void testSpecialMoveNormal() {

        final Monster monster = mock(Monster.class);
        final Random mockRand = mock(Random.class);

        when(mockRand.nextInt(100)).thenReturn(25);
        doReturn(10).when(myThief).attack(anyInt(), anyInt(), anyInt());
        final String result = myThief.specialMove(monster, mockRand);

        assertTrue(result.contains("normal attack"));
        verify(monster).takeDamage(10);
    }

    @Test
    void testSpecialMoveSurprise() {

        final Monster monster = mock(Monster.class);
        final Random mockRand = mock(Random.class);

        when(mockRand.nextInt(100)).thenReturn(90);
        doReturn(10, 15).when(myThief).attack(anyInt(), anyInt(), anyInt());

        final String result = myThief.specialMove(monster, mockRand);

        // Assertions to check if the response reflects the two hits
        assertTrue(result.contains("Surprise attack successful!"));
        assertTrue(result.contains("Hit 1: 10 damage!"));
        assertTrue(result.contains("Hit 2: 15 damage!"));
        assertTrue(result.contains("Total damage: 25!"));

        // Verify the monster took the correct damage twice
        verify(monster).takeDamage(10);
        verify(monster).takeDamage(15);

    }

}
