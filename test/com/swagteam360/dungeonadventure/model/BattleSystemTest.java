package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class BattleSystemTest {

    private Hero myHero;

    private Monster myMonster;

    private BattleSystem myBattleSystem;

    @BeforeEach
    void setup() {
        myHero = mock(Hero.class);
        myMonster = mock(Monster.class);

        when(myHero.getMyAttackSpeed()).thenReturn(10);
        when(myMonster.getMyAttackSpeed()).thenReturn(5);
        when(myHero.getDamageRangeMin()).thenReturn(10);
        when(myHero.getDamageRangeMax()).thenReturn(15);
        when(myMonster.getDamageRangeMin()).thenReturn(5);
        when(myMonster.getDamageRangeMax()).thenReturn(10);
        when(myHero.getMyHitChance()).thenReturn(90);
        when(myMonster.getMyHitChance()).thenReturn(10);

        when(myHero.getName()).thenReturn("Hero");
        when(myMonster.getName()).thenReturn("Monster");

        when(myHero.block()).thenReturn(false); // No blocking

        when(myHero.getHP()).thenReturn(100);
        when(myMonster.getHP()).thenReturn(100);

        myBattleSystem = new BattleSystem(myHero, myMonster);

    }

    @Test
    void testProcessPlayerAttacks() {

        when(myHero.attack(anyInt(), anyInt(), anyInt())).thenReturn(7);
        final String result = myBattleSystem.processPlayerAttacks();

        assertTrue(result.contains("attack"));
        verify(myMonster, atLeastOnce()).takeDamage(7);

        assertFalse(myBattleSystem.isPlayerTurn());

    }

    @Test
    void testProcessPlayerAttacksAllMisses() {

        when(myHero.getMyAttackSpeed()).thenReturn(10);
        when(myMonster.getMyAttackSpeed()).thenReturn(10);
        when(myHero.attack(anyInt(), anyInt(), anyInt())).thenReturn(0);

        final String result = myBattleSystem.processPlayerAttacks();
        assertTrue(result.contains("missed"));
        verify(myMonster, never()).takeDamage(anyInt());
    }

    @Test
    void testProcessMonsterAttacks() {

        when(myMonster.attack(anyInt(), anyInt(), anyInt())).thenReturn(7);
        when(myHero.getHP()).thenReturn(100);

        final String result = myBattleSystem.processMonsterAttacks();
        assertTrue(result.contains("attack"));
        verify(myHero, atLeastOnce()).takeDamage(7);

        assertTrue(myBattleSystem.isPlayerTurn());

    }

    @Test
    void testProcessMonsterAttacksAllBlocks() {

        when(myMonster.getMyAttackSpeed()).thenReturn(10);
        when(myHero.getMyAttackSpeed()).thenReturn(10); // 1 attack
        when(myMonster.attack(anyInt(), anyInt(), anyInt())).thenReturn(10);

        when(myHero.block()).thenReturn(true); // Hero blocks all attacks

        final String res1 = myBattleSystem.processMonsterAttacks();
        assertTrue(res1.contains("blocked"), "Expected attack to be blocked");
        verify(myMonster, never()).takeDamage(anyInt());

        when(myMonster.getMyAttackSpeed()).thenReturn(10);
        when(myHero.getMyAttackSpeed()).thenReturn(5); // 2 attacks
        when(myMonster.attack(anyInt(), anyInt(), anyInt())).thenReturn(10);

        when(myHero.block()).thenReturn(true); // Hero blocks all attacks

        final String res2 = myBattleSystem.processMonsterAttacks();
        assertTrue(res2.contains("blocked"), "Expected all attacks to be blocked");
        verify(myMonster, never()).takeDamage(anyInt());

    }

    @Test
    void testProcessMonsterAttacksMixedHitsBlocks() {

        when(myMonster.getMyAttackSpeed()).thenReturn(10);
        when(myHero.getMyAttackSpeed()).thenReturn(5);

        // 3 attacks
        when(myMonster.attack(anyInt(), anyInt(), anyInt()))
                .thenReturn(10) // hit
                .thenReturn(5); // block

        when(myHero.block()).thenReturn(false).thenReturn(true);
        final String result = myBattleSystem.processMonsterAttacks();
        System.out.println(result);

        assertTrue(result.contains("attempted 2 attacks"), "Expected 2 attacks");
        assertTrue(result.contains("1 was blocked"), "Expected 1 block");
        assertTrue(result.contains("1 landed"), "Expected 1 successful attack");

        verify(myHero, times(1)).takeDamage(10);

    }

    @Test
    void testProcessPlayerSpecialMove() {

        // Custom String result
        when(myHero.specialMove(myMonster)).thenReturn("Special Move Executed");
        final String result = myBattleSystem.processPlayerSpecialMove();

        // I cannot compare exact text since subclasses return different Strings.
        // We can check for null and empty, though.
        assertNotNull(result, "Special move result should not be null");
        assertFalse(result.trim().isEmpty(), "Special move result should not be empty");
        assertFalse(myBattleSystem.isPlayerTurn(), "It should no longer be the player's turn");

    }

    @Test
    void testProcessPlayerSpecialMoveNotYourTurn() {

        when(myHero.specialMove(myMonster)).thenReturn("Special Move Executed");
        myBattleSystem.processPlayerSpecialMove();

        final String result = myBattleSystem.processPlayerSpecialMove();

        assertEquals("It is not your turn yet!", result);
        verify(myHero, times(1)).specialMove(myMonster);
        assertFalse(myBattleSystem.isPlayerTurn());

    }

    @Test
    void testProcessMonsterHeal() {

        when(myMonster.getHP()).thenReturn(50);
        when(myMonster.heal()).thenReturn(20);
        when(myMonster.getName()).thenReturn("Ogre");

        final String result = myBattleSystem.processMonsterHeal();
        assertEquals("The Ogre healed itself for 20 HP!", result);

    }

    @Test
    void testProcessMonsterHealNoHP() {
        when(myMonster.getHP()).thenReturn(0);

        final String result = myBattleSystem.processMonsterHeal();
        assertEquals("", result);
    }

    @Test
    void testProcessMonsterHealFail() {
        when(myMonster.getHP()).thenReturn(100);
        when(myMonster.heal()).thenReturn(0);

        final String result = myBattleSystem.processMonsterHeal();
        assertEquals("", result);

    }

    @Test
    void testIsBattleOver() {

        // If both Hero and Monster are alive

        when(myHero.getHP()).thenReturn(10);
        when(myMonster.getHP()).thenReturn(10);
        assertFalse(myBattleSystem.isBattleOver());

        // When Hero is dead
        when(myHero.getHP()).thenReturn(0);
        assertTrue(myBattleSystem.isBattleOver());

        // When Monster is dead
        when(myHero.getHP()).thenReturn(10); // Revive hero
        when(myMonster.getHP()).thenReturn(0);
        assertTrue(myBattleSystem.isBattleOver());

    }

    @Test
    void testDidHeroWin() {

        // If both Hero and Monster are alive
        when(myHero.getHP()).thenReturn(10);
        when(myMonster.getHP()).thenReturn(10);
        assertFalse(myBattleSystem.didHeroWin());

        // When Hero is dead
        when(myHero.getHP()).thenReturn(0);
        assertFalse(myBattleSystem.didHeroWin());

        // When Monster is dead
        when(myHero.getHP()).thenReturn(10);
        when(myMonster.getHP()).thenReturn(0);
        assertTrue(myBattleSystem.didHeroWin());

    }


}
