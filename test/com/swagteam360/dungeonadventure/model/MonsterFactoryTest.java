package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

class MonsterFactoryTest {

    @Test
    void testCreateWitch() {
        Map<String, Object> fakeData = createFakeMonsterData("Witch");

        try (MockedStatic<Database> mockedDatabase = mockStatic(Database.class)) {
            Database mockDb = mock(Database.class);
            mockedDatabase.when(Database::getInstance).thenReturn(mockDb);
            when(mockDb.getMonsterByName("witch")).thenReturn(fakeData);

            Monster monster = MonsterFactory.createMonster("witch");

            assertNotNull(monster);
            assertTrue(monster instanceof Witch);
        }
    }

    @Test
    void testCreateGremlin() {
        Map<String, Object> fakeData = createFakeMonsterData("Gremlin");

        try (MockedStatic<Database> mockedDatabase = mockStatic(Database.class)) {
            Database mockDb = mock(Database.class);
            mockedDatabase.when(Database::getInstance).thenReturn(mockDb);
            when(mockDb.getMonsterByName("gremlin")).thenReturn(fakeData);

            Monster monster = MonsterFactory.createMonster("gremlin");

            assertNotNull(monster);
            assertTrue(monster instanceof Gremlin);
        }
    }

    @Test
    void testCreateOgre() {
        Map<String, Object> fakeData = createFakeMonsterData("Ogre");

        try (MockedStatic<Database> mockedDatabase = mockStatic(Database.class)) {
            Database mockDb = mock(Database.class);
            mockedDatabase.when(Database::getInstance).thenReturn(mockDb);
            when(mockDb.getMonsterByName("ogre")).thenReturn(fakeData);

            Monster monster = MonsterFactory.createMonster("ogre");

            assertNotNull(monster);
            assertTrue(monster instanceof Ogre);
        }
    }

    @Test
    void testCreateUnknownMonsterReturnsNull() {
        // Silence System.err for this test
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(OutputStream.nullOutputStream()));

        try (MockedStatic<Database> mockedDatabase = mockStatic(Database.class)) {
            Database mockDb = mock(Database.class);
            mockedDatabase.when(Database::getInstance).thenReturn(mockDb);
            when(mockDb.getMonsterByName("unknown")).thenReturn(null);

            Monster monster = MonsterFactory.createMonster("unknown");

            assertNull(monster);
        } finally {
            System.setErr(originalErr);
        }
    }

    // Helper method to create fake monster data
    private Map<String, Object> createFakeMonsterData(String name) {
        Map<String, Object> data = new HashMap<>();
        data.put("Name", name);
        data.put("HealthPoints", 150);
        data.put("DamageRangeMin", 10);
        data.put("DamageRangeMax", 25);
        data.put("AttackSpeed", 4);
        data.put("HitChance", 80);
        data.put("HealChance", 0.3);
        data.put("MinHealPoints", 5);
        data.put("MaxHealPoints", 15);
        return data;
    }
}
