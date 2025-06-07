package com.swagteam360.dungeonadventure.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Database class handles everything related to Database connection and
 * accessing. Utilizes methods to retrieve data to be used in other classes
 * for character use.
 *
 * @author Luke Willis
 * @version 28 May 2025
 */


public class Database {

    /** String which represents the path to the Database */
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/Database/360Game.db";


    /**
     * Establishing this class as a singleton
     */
    private static Database mySingleton = new Database();


    /**
     * Private constructor tests the connection to the database.
     */
    private Database() {
        try(Connection conn = DriverManager.getConnection(DB_URL)) {
            //Statement stmt = conn.createStatement();
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.err.println("Could not connect to database" + e.getMessage());
        }
    }

    /**
     * Ensuring a singleton design pattern, in which there is only one object
     * that accesses the database.
     * @return Database object which is used to access the SQLite DB.
     */
    public static Database getInstance() {
        if (mySingleton == null) {
            mySingleton = new Database();
        }
        return mySingleton;
    }


    /**
     * getMonsterByName method takes in a String as an argument and queries
     * the SQLite databse for all the stats of the monster that was requested.
     * Returns a map which has all the information required from the SQLite Database.
     * @param name represents queried monster name.
     * @return Map<String, Object> which holds all requested monster data.
     */
    protected Map<String, Object> getMonsterByName(String name) {
        String query = "SELECT * FROM MonsterStats WHERE Name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("Name", rs.getString("Name"));
                    data.put("HealthPoints", rs.getInt("HealthPoints"));
                    data.put("DamageRangeMin", rs.getInt("DamageRangeMin"));
                    data.put("DamageRangeMax", rs.getInt("DamageRangeMax"));
                    data.put("AttackSpeed", rs.getInt("AttackSpeed"));
                    data.put("HitChance", rs.getInt("HitChance"));
                    data.put("HealChance", rs.getDouble("HealChance"));
                    data.put("MinHealPoints", rs.getInt("MinHealPoints"));
                    data.put("MaxHealPoints", rs.getInt("MaxHealPoints"));

                    return data;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting monsta: " + name + e.getMessage());
        }

        //if monster isnt found
        return null;
    }

    /**
     * getMonsterByName method takes in a String as an argument and queries
     * the SQLite databse for all the stats of the hero that was requested.
     * Returns a map which has all the information required from the SQLite Database.
     * @param name represents queried hero name.
     * @return Map<String, Object> which holds all requested monster data.
     */
    protected Map<String, Object> getHeroByName(String name) {
        String query = "SELECT * FROM HeroStats WHERE Name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("Name", rs.getString("Name"));
                    data.put("HealthPoints", rs.getInt("HealthPoints"));
                    data.put("AttackSpeed", rs.getInt("AttackSpeed"));
                    data.put("DamageRangeMin", rs.getInt("DamageRangeMin"));
                    data.put("DamageRangeMax", rs.getInt("DamageRangeMax"));
                    data.put("HitChance", rs.getInt("HitChance"));
                    data.put("BlockChance", rs.getInt("BlockChance"));

                    return data;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting hero: " + name + e.getMessage());
        }

        //if hero isnt found
        return null;
    }
}
