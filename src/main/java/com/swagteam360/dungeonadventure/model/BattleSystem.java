package com.swagteam360.dungeonadventure.model;

/**
 * The BattleSystem class represents a combat system that facilitates a battle
 * between a Hero and a Monster. It manages the sequence of turns, calculates
 * attacks, handles health updates, and determines the outcome of the battle.
 *
 * @author Jonathan Hernandez
 * @version 1.0 (25 May 2025)
 */
public class BattleSystem {

    /**
     * Represents the Hero participating in the battle within the BattleSystem.
     * This field stores the instance of the Hero character controlled by the player.
     * It is initialized during the creation of the BattleSystem and remains constant throughout its lifecycle.
     */
    private final Hero myHero;

    /**
     * Represents the Monster encountering the Hero during the battle in the BattleSystem.
     * This field holds the instance of the Monster adversary that the player
     * must engage and defeat. It is initialized during the creation of the BattleSystem
     * and remains constant throughout the battle.
     */
    private final Monster myMonster;

    private boolean myTurn;

    /**
     * Constructs a BattleSystem instance, initializing a battle between a Hero and a Monster.
     * This constructor sets up the battle entities and ensures the battle state is marked as ongoing.
     *
     * @param theHero   the Hero participating in the battle.
     * @param theMonster the Monster the Hero will fight against.
     */
    public BattleSystem(final Hero theHero, final Monster theMonster) {
        myHero = theHero;
        myMonster = theMonster;
        myTurn = true; // Player will attack first
    }

    public String processPlayerAttack() {
        if (!myTurn) {
            return "It is not your turn yet!";
        }

        int damage = myHero.attack(myHero.getDamageRangeMin(),
                myHero.getDamageRangeMax(),
                myHero.getMyHitChance());

        myTurn = false;

        if (damage > 0) {
            myMonster.takeDamage(damage);
            return "You deal " + damage + " damage";
        } else {
            return "You missed!";
        }

    }

    public String processMonsterTurn() {
        if (myTurn) {
            return "";
        }

        int damage = myMonster.attack(myMonster.getDamageRangeMin(),
                myMonster.getDamageRangeMax(),
                myMonster.getMyHitChance());

        if (damage > 0) {
            myHero.takeDamage(damage);
        }

        if (!isBattleOver()) {
            myTurn = true;
        }

        if (damage > 0) {
            return "The " + myMonster.getName() + " deals " + damage + " damage";
        } else {
            return "The " + myMonster.getName() + " missed!";
        }

    }

    public boolean isBattleOver() {
        return myHero.getHP() <= 0 || myMonster.getHP() <= 0;
    }

    public boolean didHeroWin() {
        return myHero.getHP() > 0 && myMonster.getHP() <= 0;
    }

    public boolean isPlayerTurn() {
        return myTurn;
    }

}
