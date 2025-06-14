package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Health potion test suite.
 *
 * @author Preston Sia (psia97)
 * @version 1.0.0, 13 June 2025
 */
public class HealthPotionTest {

    private final int myHealthAmount = 5;
    private final String myName = "Health Potion"; // refer to the static field in HealthPotion

    @Test
    void testConstructor() {
        final HealthPotion testPotion = new HealthPotion(myHealthAmount);

        assertEquals(
                myHealthAmount,
                testPotion.getHealAmount(),
                "Incorrect health amount"
        );
    }

    @Test
    void testConstructorNegativeHealth() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new HealthPotion(-1),
                "Fail to protect against negative health amount"
        );
    }

    @Test
    void testName() {
        final HealthPotion testPotion = new HealthPotion(myHealthAmount);
        assertEquals(
                myName,
                testPotion.getName()
        );
    }

    @Test
    void testGetHealAmount() {
        final HealthPotion testPotion = new HealthPotion(myHealthAmount);
        assertEquals(
                myHealthAmount,
                testPotion.getHealAmount(),
                "Incorrect heal amount returned"
        );
    }

    @Test
    void testBuff() {
        GameSettings testSettings = new GameSettings("Test name", "priestess", "easy");

        GameManager.getInstance().startNewGame(testSettings);

        int initialHealth = GameManager.getInstance().getHero().getHP();
        int damageToTake = 10;

        GameManager.getInstance().getHero().takeDamage(damageToTake);

        final HealthPotion testPotion = new HealthPotion(myHealthAmount);
        List<Item> testInventory = new ArrayList<>();
        testInventory.add(testPotion);
        GameManager.getInstance().getHero().addToInventory(testInventory);

        testPotion.buff();

        assertEquals(
                initialHealth - damageToTake + myHealthAmount,
                GameManager.getInstance().getHero().getHP()
        );

        assertFalse(
                GameManager.getInstance().getHero().getInventory().contains(testPotion)
        );
    }
}
