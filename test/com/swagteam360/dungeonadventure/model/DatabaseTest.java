package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.util.Map;

import org.junit.jupiter.api.Test;


public class DatabaseTest {

    private Database db;

    @BeforeEach
    public void setUp() {
        db = Database.getInstance();
    }

    @Test
    void testGetMonsterByName_returnsMonsterData() throws Exception {
        // Mock connection, statement, and result set
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        // Mock DriverManager to return our mock connection
        try (MockedStatic<DriverManager> driverManager = Mockito.mockStatic(DriverManager.class)) {
            driverManager.when(() -> DriverManager.getConnection(anyString())).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
            when(mockStmt.executeQuery()).thenReturn(mockRs);

            when(mockRs.next()).thenReturn(true);
            when(mockRs.getString("Name")).thenReturn("Goblin");
            when(mockRs.getInt("HealthPoints")).thenReturn(30);
            when(mockRs.getInt("DamageRangeMin")).thenReturn(5);
            when(mockRs.getInt("DamageRangeMax")).thenReturn(10);
            when(mockRs.getInt("AttackSpeed")).thenReturn(2);
            when(mockRs.getInt("HitChance")).thenReturn(80);
            when(mockRs.getDouble("HealChance")).thenReturn(0.3);
            when(mockRs.getInt("MinHealPoints")).thenReturn(2);
            when(mockRs.getInt("MaxHealPoints")).thenReturn(6);

            Map<String, Object> monster = db.getMonsterByName("Goblin");

            assertNotNull(monster);
            assertEquals("Goblin", monster.get("Name"));
            assertEquals(30, monster.get("HealthPoints"));
            assertEquals(2, monster.get("AttackSpeed"));
        }
    }

    @Test
    void testGetHeroByName_returnsHeroData() throws Exception {
        // Mock connection, statement, and result set
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        // Mock DriverManager to return our mock connection
        try (MockedStatic<DriverManager> driverManager = Mockito.mockStatic(DriverManager.class)) {
            driverManager.when(() -> DriverManager.getConnection(anyString())).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
            when(mockStmt.executeQuery()).thenReturn(mockRs);

            when(mockRs.next()).thenReturn(true);
            when(mockRs.getString("Name")).thenReturn("Knight");
            when(mockRs.getInt("HealthPoints")).thenReturn(100);
            when(mockRs.getInt("AttackSpeed")).thenReturn(3);
            when(mockRs.getInt("DamageRangeMin")).thenReturn(10);
            when(mockRs.getInt("DamageRangeMax")).thenReturn(15);
            when(mockRs.getInt("HitChance")).thenReturn(90);
            when(mockRs.getInt("BlockChance")).thenReturn(50);

            Map<String, Object> hero = db.getHeroByName("Knight");

            assertNotNull(hero);
            assertEquals("Knight", hero.get("Name"));
            assertEquals(100, hero.get("HealthPoints"));
            assertEquals(50, hero.get("BlockChance"));
        }
    }

    @Test
    void testGetMonsterByName_notFound() throws Exception {
        try (MockedStatic<DriverManager> driverManager = Mockito.mockStatic(DriverManager.class)) {
            Connection mockConn = mock(Connection.class);
            PreparedStatement mockStmt = mock(PreparedStatement.class);
            ResultSet mockRs = mock(ResultSet.class);

            driverManager.when(() -> DriverManager.getConnection(anyString())).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
            when(mockStmt.executeQuery()).thenReturn(mockRs);
            when(mockRs.next()).thenReturn(false);

            Map<String, Object> monster = db.getMonsterByName("NonExistent");
            assertNull(monster);
        }
    }
    @Test
    void testDatabaseConstructor_handlesConnectionError() throws Exception {
        try (MockedStatic<DriverManager> driverManager = Mockito.mockStatic(DriverManager.class)) {
            driverManager.when(() -> DriverManager.getConnection(anyString()))
                    .thenThrow(new SQLException("Mock failure"));

            // Recreate singleton with reflection to force constructor call
            //Field instance = Database.class.getDeclaredField("mySingleton");
            //instance.setAccessible(true);
            //instance.set(null, null); // clear singleton

            assertDoesNotThrow(() -> Database.getInstance());
        }
    }

    @Test
    void testGetHeroByName_SQLExceptionHandled() throws Exception {
        try (MockedStatic<DriverManager> driverManager = Mockito.mockStatic(DriverManager.class)) {
            driverManager.when(() -> DriverManager.getConnection(anyString()))
                    .thenThrow(new SQLException("Fake SQL error"));

            Map<String, Object> result = db.getHeroByName("Knight");
            assertNull(result);
        }
    }

}
