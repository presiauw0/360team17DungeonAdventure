package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

class HeroFactoryTest {

    @Test
    void testCreateWarrior() {
        Map<String, Object> fakeData = createFakeHeroData("Warrior");

        try (MockedStatic<Database> mockedDatabase = mockStatic(Database.class)) {
            Database mockDb = mock(Database.class);
            mockedDatabase.when(Database::getInstance).thenReturn(mockDb);
            when(mockDb.getHeroByName("warrior")).thenReturn(fakeData);

            Hero hero = HeroFactory.createHero("warrior");

            assertNotNull(hero);
            assertTrue(hero instanceof Warrior);
        }
    }

    @Test
    void testCreatePriestess() {
        Map<String, Object> fakeData = createFakeHeroData("Priestess");

        try (MockedStatic<Database> mockedDatabase = mockStatic(Database.class)) {
            Database mockDb = mock(Database.class);
            mockedDatabase.when(Database::getInstance).thenReturn(mockDb);
            when(mockDb.getHeroByName("priestess")).thenReturn(fakeData);

            Hero hero = HeroFactory.createHero("priestess");

            assertNotNull(hero);
            assertTrue(hero instanceof Priestess);
        }
    }

    @Test
    void testCreateThief() {
        Map<String, Object> fakeData = createFakeHeroData("Thief");

        try (MockedStatic<Database> mockedDatabase = mockStatic(Database.class)) {
            Database mockDb = mock(Database.class);
            mockedDatabase.when(Database::getInstance).thenReturn(mockDb);
            when(mockDb.getHeroByName("thief")).thenReturn(fakeData);

            Hero hero = HeroFactory.createHero("thief");

            assertNotNull(hero);
            assertTrue(hero instanceof Thief);
        }
    }

    @Test
    void testCreateUnknownHeroReturnsNull() {
        PrintStream originalErr = System.err; //silence because it throws an error message for connection to
                                              //the DB, which isnt quite necessary.
        System.setErr(new PrintStream(OutputStream.nullOutputStream()));

        try (MockedStatic<Database> mockedDatabase = mockStatic(Database.class)) {
            Database mockDb = mock(Database.class);
            mockedDatabase.when(Database::getInstance).thenReturn(mockDb);
            when(mockDb.getHeroByName("unknown")).thenReturn(null);

            Hero hero = HeroFactory.createHero("unknown");

            assertNull(hero);
        } finally {
            System.setErr(originalErr);
        }
    }


    // Helper method to create fake hero data
    private Map<String, Object> createFakeHeroData(String name) {
        Map<String, Object> data = new HashMap<>();
        data.put("Name", name);
        data.put("HealthPoints", 100);
        data.put("AttackSpeed", 5);
        data.put("DamageRangeMin", 10);
        data.put("DamageRangeMax", 20);
        data.put("HitChance", 75);
        data.put("BlockChance", 25);
        return data;
    }
}
