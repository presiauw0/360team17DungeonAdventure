package com.swagteam360.dungeonadventure.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Database class handles everything related to Database connection and
 * accessing. Utilizes methods to retrieve data to be used in other classes
 * for (mainly) monster use.
 *
 * @author Luke Willis
 * @version 28 May 2025
 */


public class Database {

    private static final String DB_URL = "jdbc:sqlite:src/main/resources/Database/360Game.db";

    //private Connection conn;

    /**
     * Establishing this class as a singleton
     */
    private static Database mySingleton = new Database();


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


    protected Map<String, Object> getMonsterByName(String name) {
        String query = "SELECT * FROM Monster WHERE Name = ?";
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
        return null;
    }



    /*
    public static List<Map<String, Object>> getMonsters() {
        List<Map<String, Object>> monsterData = new ArrayList<>();


        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM MonsterStats")) {


            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("Name", rs.getString("Name"));
                row.put("HealthPoints", rs.getInt("HealthPoints"));
                row.put("DamageRangeMin", rs.getInt("DamageRangeMin"));
                row.put("DamageRangeMax", rs.getInt("DamageRangeMax"));
                row.put("AttackSpeed", rs.getDouble("AttackSpeed"));
                row.put("HitChance", rs.getInt("HitChance"));
                row.put("HealChance", rs.getDouble("HealChance"));
                row.put("MinHealPoints", rs.getString("MinHealPoints"));
                row.put("MaxHealPoints", rs.getString("MaxHealPoints"));
                monsterData.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Error getting monstas: " + e.getMessage());
        }
        return monsterData;
    }

     */

    /*
    //try with resources
    public static void main(String[] theArgs) {

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MonsterStats");

            while (rs.next()) {
                System.out.println(rs.getString("Name") + " " + rs.getInt("HealthPoints"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

     */
}
