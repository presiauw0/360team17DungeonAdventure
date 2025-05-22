package com.swagteam360.dungeonadventure.model;

/**
 * The MonsterFactory class is responsible for creating instances of the
 * Monster type. Based on the provided monster name, this factory determines
 * which subclass of Monster to instantiate and returns the corresponding object.
 *
 * @author Jonathan Hernandez
 * @version 1.0 22 May 2025
 */
public class MonsterFactory {

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
    public Monster createMonster(String theMonsterName) {

        Monster theMonster = null;

        // TheAttackDamage is a loose average of min/max damage.
        // We might just need to remove it altogether tbh.
        // We also need to get data through SQL queries. - Jonathan

        if (theMonsterName.equalsIgnoreCase("skeleton")) {
            theMonster = new Skeleton("Skeleton", 40, 100, 3,
                    15, 30, 80, 40);
        } else if (theMonsterName.equalsIgnoreCase("gremlin")) {
            theMonster = new Gremlin("Gremlin", 23, 70, 5,
                    15, 30, 80, 40);
        } else if (theMonsterName.equalsIgnoreCase("ogre")) {
            theMonster = new Ogre("Ogre", 45, 200, 2,
                    30, 60, 60, 10);
        }

        return theMonster;

    }

}
