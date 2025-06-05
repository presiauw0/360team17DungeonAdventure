package com.swagteam360.dungeonadventure.model;

import java.util.List;
import java.util.Map;

/**
 * The HeroFactory class is responsible for creating instances of the
 * Hero type. Based on the provided Hero name, this factory determines
 * which subclass of Hero to instantiate and returns the corresponding object.
 *
 * @author Jonathan Hernandez
 * @author Luke Willis
 * @version 1.0 31 May 2025
 */
public class HeroFactory {

    private HeroFactory() {
        super();
    }



    /**
     * Creates and returns a specific Hero object based on the provided Hero name.
     * The method determines the type of Hero to instantiate and provides its
     * corresponding attributes.
     *
     * @param theHeroName the name of the Hero to be created; valid names are
     *                       "warrior", "priestess", or "thief".
     * @return a Hero object corresponding to the specified name, or null if the
     *         name does not match any predefined Hero type.
     */
    public static Hero createHero(String theHeroName) {

        Hero theHero = null;

        //utilizes singleton design pattern to only call upon one instance of Database class.
        Map<String, Object> data = Database.getInstance().getHeroByName(theHeroName);

        //checking for underflow
        if (data == null) {
            System.err.println("Hero not found: " + theHeroName);
            return theHero;
        }

        //write check for if theHeroName == name from map?
        String name = (String) data.get("Name");
        int healthPoints = (int) data.get("HealthPoints");
        int attackSpeed = (int) data.get("AttackSpeed");
        int damageRangeMin = (int) data.get("DamageRangeMin");
        int damageRangeMax = (int) data.get("DamageRangeMax");
        int hitChance = (int) data.get("HitChance");
        int blockChance = (int) data.get("BlockChance");



        //creating Hero
        if (name.equalsIgnoreCase("warrior")) {
            theHero = new Warrior("Warrior", healthPoints, attackSpeed, damageRangeMin, damageRangeMax,
                    hitChance, blockChance);
        } else if (name.equalsIgnoreCase("priestess")) {
            theHero = new Priestess("Priestess", healthPoints, attackSpeed, damageRangeMin, damageRangeMax,
                    hitChance, blockChance);
        } else if (name.equalsIgnoreCase("thief")) {
            theHero = new Thief("Thief", healthPoints, attackSpeed, damageRangeMin, damageRangeMax,
                    hitChance, blockChance);
        }

        return theHero;

    }

}
