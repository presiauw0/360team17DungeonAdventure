package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Priestess and Hero superclass test suite.
 * This file performs unit tests against
 * the Priestess, Hero and DungeonCharacter class.
 *
 * @author Preston Sia (psia97)
 * @version 1.0.0, 6 June 2025
 */
public class PriestessTest {

    // FIXME ALL OF THE METHODS IN DungeonCharacter SHOULD PROBABLY BE PROTECTED
    // FIXME LUKE, JONATHAN WHY ARE THERE 2 MAXIMUM HEALTH POINT FIELDS??? (Priestess line 32, DungeonCharacter line 25)

    private final String myName = "Test Priestess";
    private final int myHp = 15;
    private final int myAttackSpeed = 1;
    private final int myDamageMin = 1;
    private final int myDamageMax = 100;
    private final int myHitChance = 10;
    private final int myBlockChance = 10;

    private Priestess myPriestess;

    @BeforeEach
    void setup() {
        myPriestess = new Priestess(myName, myHp, myAttackSpeed, myDamageMin, myDamageMax, myHitChance, myBlockChance);
    }

    /*  CONSTRUCTOR TESTS  */

    @Test
    void testConstructor() {

        final Priestess priestess = new Priestess(myName, myHp, myAttackSpeed, myDamageMin, myDamageMax, myHitChance, myBlockChance);

        assertAll(
                () -> assertEquals(
                        myName,
                        priestess.getName(),
                        "Name is incorrect"
                ),
                () -> assertEquals(
                        myHp,
                        priestess.getHP(),
                        "Health points is incorrect"
                ),
                () -> assertEquals(
                        myAttackSpeed,
                        priestess.getMyAttackSpeed(),
                        "attack speed is incorrect"
                ),
                () -> assertEquals(
                        myDamageMin,
                        priestess.getDamageRangeMin(),
                        "Minimum damage is incorrect"
                ),
                () -> assertEquals(
                        myDamageMax,
                        priestess.getDamageRangeMax(),
                        "Maximum damage is incorrect"
                ),
                () -> assertEquals(
                        myHitChance,
                        priestess.getMyHitChance(),
                        "Hit chance parameter is incorrect"
                )
                // TODO BLOCKCHANCE TEST
        );
    }

    @Test
    void testConstructorNullName() {
        assertThrows(
                NullPointerException.class,
                () -> new Priestess(null, myHp, myAttackSpeed, myDamageMin, myDamageMax, myHitChance, myBlockChance),
                "Failed to protect against null name data"
        );
    }

    @Test
    void testConstructorNegativeHp() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Priestess(myName, -1, myAttackSpeed, myDamageMin, myDamageMax, myHitChance, myBlockChance),
                "Failed to protect against negative health points"
        );
    }

    @Test
    void testConstructorNegativeAttackSpeed() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Priestess(myName, myHp, -1, myDamageMin, myDamageMax, myHitChance, myBlockChance),
                "Failed to protect against negative attack speed"
        );
    }

    @Test
    void testConstructorNegativeDamageMin() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Priestess(myName, myHp, myAttackSpeed, -1, myDamageMax, myHitChance, myBlockChance),
                "Failed to protect against negative minimum damage"
        );
    }

    @Test
    void testConstructorNegativeDamageMax() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Priestess(myName, myHp, myAttackSpeed, myDamageMin, -1, myHitChance, myBlockChance),
                "Failed to protect against negative maximum damage"
        );
    }

    @Test
    void testConstructorNegativeHitChance() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Priestess(myName, myHp, myAttackSpeed, myDamageMin, myDamageMax, -1, myBlockChance),
                "Failed to protect against negative hit chance"
        );
    }

    @Test
    void testConstructorNegativeBlockChance() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Priestess(myName, myHp, myAttackSpeed, myDamageMin, myDamageMax, myHitChance, myBlockChance),
                "Failed to protect against negative block chance"
        );
    }

    @Test
    void testConstructorMinGreaterThanMaxAttack() {
        final int testDamageMin = 5;
        final int testDamageMax = 4;
        assertThrows(
                IllegalArgumentException.class,
                () -> new Priestess(myName, myHp, myAttackSpeed, testDamageMin, testDamageMax, myHitChance, myBlockChance),
                "Failed to protect against paradoxical minimum and maximum damage values. " +
                        "The minimum cannot be greater than the maximum."
        );
    }

    /*  DungeonCharacter TEST SUITE  */
    /*
    @Test
    void testAttackBadMinAndMax() {
        assertThrows(
                IllegalArgumentException.class,
                () -> myPriestess.attack(5, 4, 3),
                "Failed to protect against paradoxical minimum and maximum damage values. " +
                        "The minimum cannot be greater than the maximum."
        );
    }*/
    // FIXME LUKE YOU NEED TO DOCUMENT THAT BAD MINS AND MAXES ARE ALREADY HANDLED!
    // FIXME WHAT IS THE RANGE OF theHitChance? THIS SHOULD BE SPECIFIED.
    // FIXME SPECIFY THAT THE VALUE 0 IS RETURNED IF THERE IS NO HIT (hit roll less than hit chance)


    @Test
    void testAttackNegativeHitChance() {
        assertThrows(
                IllegalArgumentException.class,
                () -> myPriestess.attack(4, 5, -1),
                "Failed to protect against negative hit chance values"
        );
    }

    @Test
    void testAttackGuaranteedHit() {
        int testMin = 2;
        int testMax = 5;
        int hitChance = 0;

        int testCondition = myPriestess.attack(testMin, testMax, hitChance);

        assertTrue(
                testCondition >= testMin && testCondition <= testMax,
                "An illegal integer value was returned: " + testCondition
        );
    }

    @Test
    void testAttackGuaranteedMiss() {
        int testMin = 2;
        int testMax = 5;
        int hitChance = 101; // I read your code to figure this out

        int testCondition = myPriestess.attack(testMin, testMax, hitChance);

        assertEquals(
                0,
                testCondition,
                "Failed to return a proper value when no hits are made"
        );
    }

    // FIXME It appears it sometimes deals 0 damage

    /**
     * Calls the method 100 times to see if
     * the random attack value is sometimes less
     * than theDamageRangeMax.
     */
    @Test
    void testAttackBelowMaxBound() {
        int testMin = 2;
        int testMax = 30;
        int hitChance = 0;

        boolean foundNumLess = false;

        for (int i = 0; i < 100; i++) {
            int testCondition =  myPriestess.attack(testMin, testMax, hitChance);
            if (testCondition < testMax) {
                foundNumLess = true;
                break;
            }
        }

        assertTrue(foundNumLess, "Did not generate a number less than the max");
    }

    /**
     * Calls the method 100 times to see if
     * the random attack value is sometimes greater
     * than theDamageRangeMin.
     */
    @Test
    void testAttackAboveMinBound() {
        int testMin = 2;
        int testMax = 30;
        int hitChance = 0;

        boolean foundNumGreater = false;
        List<Integer> nums = new ArrayList<>(100);

        for (int i = 0; i < 100; i++) {
            int testCondition =  myPriestess.attack(testMin, testMax, hitChance);
            nums.add(testCondition);
            if (testCondition > testMin) {
                foundNumGreater = true;
                break;
            }
        }

        assertTrue(foundNumGreater,
                "Did not generate a number greater than the min. " +
                        "\nNums found: " + nums + "\nMin number: " + testMin);
    }

    @Test
    void testTakeDamage() {
        int damage = 2;
        myPriestess.takeDamage(damage);

        int correctHp = myHp - damage;

        assertEquals(
                correctHp,
                myPriestess.getHP(),
                "Incorrect health point calculation"
        );
    }

    /**
     * Test what happens when the amount of damage inflicted
     * will cause the health points to go negative if not
     * dealt with correctly. Ideally the health points should
     * be set to zero.
     */
    @Test
    void testTakeDamageExceedsHealth() {
        int damage = 1000;
        myPriestess.takeDamage(damage);

        assertEquals(
                0,
                myPriestess.getHP(),
                "Health points incorrect after taking excessive damage."
                + " Should be 0. Actual: " + myPriestess.getHP()
        );
    }

    @Test
    void testTakeNegativeDamage() {
        int damage = -5;

        assertThrows(
                IllegalArgumentException.class,
                () -> myPriestess.takeDamage(damage),
                "Fail to protect against negative damage values"
        );
    }

    @Test
    void testHeal() {
        // take some damage
        int damage = 6;
        myPriestess.takeDamage(damage);

        int heal = 4;
        int correctHP = myHp - damage + heal;
        myPriestess.heal(heal);
        assertEquals(
                correctHP,
                myPriestess.getHP(),
                "Incorrect health calculation"
        );
    }

    @Test
    void testHealNegative() {
        int heal = -5;
        assertThrows(
                IllegalArgumentException.class,
                () -> myPriestess.heal(heal),
                "Fail to protect against negative heal points"
        );
    }

    /**
     * Test if giving an excessive amount of healing points exceeds the maximum.
     */
    @Test
    void testHealExceedsMax() {
        int heal = 10;
        myPriestess.heal(heal);
        assertEquals(
                myHp,
                myPriestess.getHP(),
                "Fail to limit health points"
        );
    }

    @Test
    void testGetName() {
        assertEquals(
                myName,
                myPriestess.getName(),
                "Incorrect name returned"
        );
    }

    @Test
    void testGetHp() {
        assertEquals(
                myHp,
                myPriestess.getHP(),
                "Incorrect health points"
        );
    }

    @Test
    void testGetMaxHp() {
        assertEquals(
                myHp,
                myPriestess.getMaxHP(),
                "Incorrect maximum health points"
        );
    }
    // TODO DOCUMENT HOW maxHP gets set. Initial HP set on construction is the maximum.

    /*
    @Test
    void testSetHP() {

    }*/
    // FIXME PLEASE SET THE setHP METHOD TO PACKAGE OR PROTECTED VISIBILITY
    //  (better yet, use the heal and takeDamage methods)

    @Test
    void testGetDamageRangeMin() {
        assertEquals(
                myPriestess.getDamageRangeMin(),
                myDamageMin,
                "Incorrect minimum damage"
        );
    }

    @Test
    void testGetDamageRangeMax() {
        assertEquals(
                myPriestess.getDamageRangeMax(),
                myDamageMax,
                "Incorrect maximum damage"
        );
    }

    // FIXME REMOVE setDamageRangeMax

    @Test
    void testGetMyHitChance() {
        assertEquals(
                myHitChance,
                myPriestess.getMyHitChance(),
                "Incorrect hit chance"
        );
    }

    @Test
    void testToString() {
        final String correctString = myName + "\n" +
                "----------\n" +
                "HP: " + myHp + "\n" +
                "Attack Speed: " + myAttackSpeed + "\n" +
                "Hit Chance: " + myHitChance + "\n";

        assertEquals(
                myPriestess.toString(),
                correctString,
                "Incorrect toString format"
        );
    }

    /*  HERO CLASS TESTS  */
    @Test
    void testHeroConstructor() {
        final Priestess priestess = new Priestess(myName, myHp, myAttackSpeed, myDamageMin, myDamageMax, myHitChance, myBlockChance);

        assertAll(
                () -> assertEquals(
                        priestess.getInventory().size(),
                        0,
                        "Something is wrong with the initial inventory"
                )
                /*TODO getter method for myBlockChance and corresponding test*/
        );
    }

    @Test
    void testGetInitialInventory() {
        assertEquals(
                myPriestess.getInventory().size(),
                0,
                "Inventory should be empty on construction"
        );
    }

    @Test
    void testAddToInventory() {
        final List<Item> testItems = new ArrayList<>();
        testItems.add(new VisionPotion());
        testItems.add(new HealthPotion(5));
        testItems.add(new HealthPotion(2));

        myPriestess.addToInventory(testItems);

        // LOOP through all items to make sure the same items are being referenced
        boolean multipleItemsNotFound = false;
        final List<Item> currentInventory = myPriestess.getInventory();
        for (Item x : testItems) {
            boolean specificItemNotFound = true; // first, set flag to true
            for (Item y : currentInventory) {
                if (x == y) {
                    specificItemNotFound = false; // if the item is found, flag it as such that it was found
                    break;
                }
            }

            if (specificItemNotFound) {
                multipleItemsNotFound = true;
                break;
            }
        }

        // RUN assertions
        assertEquals(
                myPriestess.getInventory().size(),
                testItems.size(),
                "Size of inventory doesn't match"
        );
        assertFalse(multipleItemsNotFound, "Items in the inventory don't match the original list");
    }

    @Test
    void testAddToInventoryNull() {
        assertThrows(
                NullPointerException.class,
                () -> myPriestess.addToInventory(null),
                "Failed to handle null argument"
        );
    }

    @Test
    void testGetPillarCount() {
        final List<Item> testItems = new ArrayList<>();
        testItems.add(new Pillar(PillarType.GOLD));
        testItems.add(new Pillar(PillarType.PLATINUM));
        testItems.add(new VisionPotion());
        testItems.add(new HealthPotion(2));

        myPriestess.addToInventory(testItems);

        assertEquals(
                myPriestess.getPillarCount(),
                2,
                "Wrong pillar count."
        );
    }

    @Test
    void testBlockNoChance() {
        int blockChance = 0;
        Priestess priestess = new Priestess(myName, myHp, myAttackSpeed, myDamageMin, myDamageMax, myHitChance, blockChance);
        assertFalse(
                priestess.block(),
                "Should not have been able to block"
        );
    }

    @Test
    void testBlockAlways() {
        int blockChance = 100;
        Priestess priestess = new Priestess(myName, myHp, myAttackSpeed, myDamageMin, myDamageMax, myHitChance, blockChance);
        assertTrue(
                priestess.block(),
                "Should have blocked"
        );
    }
    // TODO THE BLOCK CHANCE BOUNDS NEED TO BE DOCUMENTED

    /* PRIESTESS TEST SUITE */

    // FIXME This test is flaky
    @Test
    void testSpecialMove() {
        int damage = myHp - 1;
        myPriestess.takeDamage(damage);
        int maxHeal = (int)(0.75 * myHp); // Can heal up to 75% of max health points
        int hpAfterDamage = myPriestess.getHP();
        // use special move
        myPriestess.specialMove(null);

        assertTrue(
                myPriestess.getHP() - hpAfterDamage <= maxHeal,
                "Incorrect healing value range.\n" +
                        "75% value: " + maxHeal + "\nActual: " +
                        (myPriestess.getHP() - hpAfterDamage)
        );
    }
}
