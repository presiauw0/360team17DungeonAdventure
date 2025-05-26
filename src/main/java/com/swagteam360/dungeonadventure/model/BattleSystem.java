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

    /**
     * Indicates whether the current battle in the BattleSystem is over.
     * This variable is set to true when one of the combatants (Hero or Monster)
     * is defeated, or under certain conditions that signify the end of the battle.
     * It determines the continuation or termination of the battle loop.
     */
    private boolean myBattleOver;

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
        myBattleOver = false;
    }

    /**
     * Initiates and manages the battle sequence between the Hero and the Monster.
     * The battle alternates turns between the Hero and the Monster, starting with
     * the Hero's attack. In each turn, attacks are resolved based on a random hit
     * chance and damage range specific to the character attacking. The battle
     * continues until either the Hero or the Monster's health points (HP) are
     * reduced to zero or less. The outcome of the battle determines whether the
     * Hero emerges victorious or is defeated.
     *
     * @return true if the Hero wins the battle, false if the Hero loses.
     */
    public boolean startBattle() {

        boolean heroWon = false;

        while (!myBattleOver) {

            int heroDamage = myHero.attack(myHero.getDamageRangeMin(),
                                           myHero.getDamageRangeMax(),
                                           myHero.getMyHitChance());

            // Hero's turn

            if (heroDamage > 0) {
                myMonster.takeDamage(heroDamage);
                System.out.println("Hero deals " + heroDamage + " damage to Monster");
            } else {
                System.out.println("Hero's attack missed");
            }

            // Check if monster's health is 0 or less

            if (myMonster.getHP() <= 0) {
                System.out.println("Monster has been defeated!");
                myBattleOver = true;
                heroWon = true;
                break;
            }

            int monsterDamage = myMonster.attack(myMonster.getDamageRangeMin(),
                                                 myMonster.getDamageRangeMax(),
                                                 myMonster.getMyHitChance());

            if (monsterDamage > 0) {
                myHero.takeDamage(monsterDamage);
                System.out.println("Monster deals " + monsterDamage + " damage to Hero");
            } else {
                System.out.println("Monster's attack missed");
            }

            if (myHero.getHP() <= 0) {
                System.out.println("Hero has been defeated!");
                myBattleOver = true;
                break;
            }

        }

        return heroWon;

    }

}
