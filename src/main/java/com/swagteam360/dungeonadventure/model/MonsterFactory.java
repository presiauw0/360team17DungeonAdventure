package com.swagteam360.dungeonadventure.model;

import java.util.List;
import java.util.Map;

/**
 * The MonsterFactory class is responsible for creating instances of the
 * Monster type. Based on the provided monster name, this factory determines
 * which subclass of Monster to instantiate and returns the corresponding object.
 *
 * @author Jonathan Hernandez
 * @author Luke Willis
 * @version 2.0 25 May 2025
 */
public class MonsterFactory {

    private MonsterFactory() {
        super();
    }



    /**
     * Creates and returns a specific Monster object based on the provided monster name.
     * The method determines the type of Monster to instantiate and provides its
     * corresponding attributes.
     *
     * @param theMonsterName the name of the monster to be created; valid names are
     *                       "skeleton", "gremlin", or "ogre".
     * @return a Monster object corresponding to the specified name, or null if the
     *         name does not match any predefined monster type.
     */
    public static Monster createMonster(String theMonsterName) {

        Monster theMonster = null;

        Map<String, Object> data = Database.getInstance().getMonsterByName(theMonsterName);

        //checking for underflow
        if (data == null) {
            System.err.println("Monster not found: " + theMonsterName);
            return theMonster;
        }

        //write check for if theMonsterName == name from map
        String name = (String) data.get("Name");
        int healthPoints = (int) data.get("HealthPoints");
        int damageRangeMin = (int) data.get("DamageRangeMin");
        int damageRangeMax = (int) data.get("DamageRangeMax");
        int attackSpeed = (int) data.get("AttackSpeed");
        int hitChance = (int) data.get("HitChance");
        double healChance = (double) data.get("HealChance");
        int minHealPoints = (int) data.get("MinHealPoints");
        int maxHealPoints = (int) data.get("MaxHealPoints");



        if (name.equalsIgnoreCase("witch")) {
            theMonster = new Witch("Witch", healthPoints, attackSpeed, damageRangeMin, damageRangeMax,
                    hitChance, healChance, minHealPoints, maxHealPoints);
        } else if (name.equalsIgnoreCase("gremlin")) {
            theMonster = new Gremlin("Gremlin", healthPoints, attackSpeed, damageRangeMin, damageRangeMax,
                    hitChance, healChance, minHealPoints, maxHealPoints);
        } else if (name.equalsIgnoreCase("ogre")) {
            theMonster = new Ogre("Ogre", healthPoints, attackSpeed, damageRangeMin, damageRangeMax,
                    hitChance, healChance, minHealPoints, maxHealPoints);
        }

        return theMonster;

    }

}
