package com.swagteam360.dungeonadventure.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.MockedStatic;

import java.sql.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the Database class using JUnit 5 and Mockito.
 * Tests the singleton pattern and database operations with mocked connections.
 */

//@ExtendWith(MockitoExtension.class)
class DatabaseTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private Database database;

    @BeforeEach
    void setUp() {
        // Reset singleton instance before each test
        resetSingleton();

        // Create mock objects
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        database = Database.getInstance();
    }

    /**
     * Helper method to reset the singleton instance using reflection
     */
    private void resetSingleton() {
        try {
            java.lang.reflect.Field field = Database.class.getDeclaredField("mySingleton");
            field.setAccessible(true);
            field.set(null, null);
        } catch (Exception e) {
            // Handle reflection exceptions
            e.printStackTrace();
        }
    }

    @Test
    void testSingletonPattern() {
        Database instance1 = Database.getInstance();
        Database instance2 = Database.getInstance();

        assertSame(instance1, instance2, "Database should follow singleton pattern");
        assertNotNull(instance1, "Database instance should not be null");
    }

    @Test
    void testGetMonsterByName_Success() throws SQLException {
        // Arrange
        String monsterName = "Goblin";
        setupMockResultSetForMonster();

        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString()))
                    .thenReturn(mockConnection);

            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);

            // Act
            Map<String, Object> result = database.getMonsterByName(monsterName);

            // Assert
            assertNotNull(result, "Result should not be null");
            assertEquals("Goblin", result.get("Name"));
            assertEquals(50, result.get("HealthPoints"));
            assertEquals(5, result.get("DamageRangeMin"));
            assertEquals(15, result.get("DamageRangeMax"));
            assertEquals(3, result.get("AttackSpeed"));
            assertEquals(80, result.get("HitChance"));
            assertEquals(0.2, result.get("HealChance"));
            assertEquals(2, result.get("MinHealPoints"));
            assertEquals(8, result.get("MaxHealPoints"));

            // Verify interactions
            verify(mockPreparedStatement).setString(1, monsterName);
            verify(mockPreparedStatement).executeQuery();
        }
    }

    @Test
    void testGetMonsterByName_NotFound() throws SQLException {
        // Arrange
        String monsterName = "NonExistentMonster";

        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString()))
                    .thenReturn(mockConnection);

            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false); // No results found

            // Act
            Map<String, Object> result = database.getMonsterByName(monsterName);

            // Assert
            assertNull(result, "Result should be null when monster is not found");

            // Verify interactions
            verify(mockPreparedStatement).setString(1, monsterName);
        }
    }


    @Test
    void testGetHeroByName_Success() throws SQLException {
        // Arrange
        String heroName = "Warrior";
        setupMockResultSetForHero();

        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString()))
                    .thenReturn(mockConnection);

            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);

            // Act
            Map<String, Object> result = database.getHeroByName(heroName);

            // Assert
            assertNotNull(result, "Result should not be null");
            assertEquals("Warrior", result.get("Name"));
            assertEquals(100, result.get("HealthPoints"));
            assertEquals(4, result.get("AttackSpeed"));
            assertEquals(10, result.get("DamageRangeMin"));
            assertEquals(25, result.get("DamageRangeMax"));
            assertEquals(85, result.get("HitChance"));
            assertEquals(30, result.get("BlockChance"));

            // Verify interactions
            verify(mockPreparedStatement).setString(1, heroName);
            verify(mockPreparedStatement).executeQuery();
        }
    }

    @Test
    void testGetHeroByName_NotFound() throws SQLException {
        // Arrange
        String heroName = "NonExistentHero";

        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString()))
                    .thenReturn(mockConnection);

            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false); // No results found

            // Act
            Map<String, Object> result = database.getHeroByName(heroName);

            // Assert
            assertNull(result, "Result should be null when hero is not found");

            // Verify interactions
            verify(mockPreparedStatement).setString(1, heroName);
        }
    }


    /**
     * Helper method to setup mock ResultSet for monster data
     */
    private void setupMockResultSetForMonster() throws SQLException {
        when(mockResultSet.getString("Name")).thenReturn("Goblin");
        when(mockResultSet.getInt("HealthPoints")).thenReturn(50);
        when(mockResultSet.getInt("DamageRangeMin")).thenReturn(5);
        when(mockResultSet.getInt("DamageRangeMax")).thenReturn(15);
        when(mockResultSet.getInt("AttackSpeed")).thenReturn(3);
        when(mockResultSet.getInt("HitChance")).thenReturn(80);
        when(mockResultSet.getDouble("HealChance")).thenReturn(0.2);
        when(mockResultSet.getInt("MinHealPoints")).thenReturn(2);
        when(mockResultSet.getInt("MaxHealPoints")).thenReturn(8);
    }

    /**
     * Helper method to setup mock ResultSet for hero data
     */
    private void setupMockResultSetForHero() throws SQLException {
        when(mockResultSet.getString("Name")).thenReturn("Warrior");
        when(mockResultSet.getInt("HealthPoints")).thenReturn(100);
        when(mockResultSet.getInt("AttackSpeed")).thenReturn(4);
        when(mockResultSet.getInt("DamageRangeMin")).thenReturn(10);
        when(mockResultSet.getInt("DamageRangeMax")).thenReturn(25);
        when(mockResultSet.getInt("HitChance")).thenReturn(85);
        when(mockResultSet.getInt("BlockChance")).thenReturn(30);
    }
}