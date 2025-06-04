package com.swagteam360.dungeonadventure.model;

import java.util.Random;

/**
 *  The Thief class is a specific type of Hero, one of the 3 player
 * characters. A Thief has the special ability "Poison Knife" which allows
 * the player to poison the enemy for 5-15 seconds, dealing 5 damage/second.
 *
 * @author Luke Willis, Jonathan Hernandez
 * @version 4 June 2025
 */
public class Thief extends Hero{
    /**
     * Parametered constructor, calls upon the super constructor to help establish the object.
     *
     * @param theName           represents the name of the hero.
     * @param theHP             represents the health points of the hero.
     * @param theAttackSpeed    represent the attack speed of the hero.
     * @param theDamageRangeMin represents minimum damage range.
     * @param theDamageRangeMax represents maximum damage range.
     * @param theHitChance      represents the hit chance of the hero.
     * @param theBlockChance    represents the block chance of the hero.
     */
    public Thief(String theName, int theHP, int theAttackSpeed, int theDamageRangeMin, int theDamageRangeMax, int theHitChance, int theBlockChance) {
        super(theName, theHP, theAttackSpeed, theDamageRangeMin, theDamageRangeMax, theHitChance, theBlockChance);
    }

    /**
     * Thief can launch a surprise attack against a monster. A successful surprise attack will deal two attacks worth
     * of damage to the monster. An unsuccessful surprise attack may only consist of one attack or a missed attack.
     *
     * @param theMonster The monster in which the special move may be performed on.
     * @return A string representation of the outcome of this special move.
     */
    @Override
    public String specialMove(final Monster theMonster) {

        Random rand = new Random();
        final int roll = rand.nextInt(100);
        String result;

        if (roll < 20) {
            result = "You've been caught! Attack missed!";
        } else if (roll < 60) {
            int damage = attack(getDamageRangeMin(), getDamageRangeMax(), getMyHitChance());
            if (damage > 0) {
                theMonster.takeDamage(damage);
                result = "You performed a normal attack for " + damage + " damage!";
            } else {
                result = "You tried a surprise attack but missed!";
            }
        } else {
            int totalDamage = 0;
            StringBuilder sb = new StringBuilder("Surprise attack successful!");
            for (int i = 1; i <= 2; i++) {
                int damage = attack(getDamageRangeMin(), getDamageRangeMax(), getMyHitChance());
                if (damage > 0) {
                    theMonster.takeDamage(damage);
                    totalDamage += damage;
                    sb.append("Hit ").append(i).append(": ").append(damage).append(" damage!\n");
                } else {
                    sb.append("Hit ").append(i).append(": Missed!\n");
                }
            }

            sb.append("Total damage: ").append(totalDamage).append("!");
            result = sb.toString();
        }

        return result;

    }
}
