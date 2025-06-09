package com.swagteam360.dungeonadventure.model;

/**
 * The BattleSystem class represents a combat system that facilitates a battle
 * between a Hero and a Monster. It manages the sequence of turns, calculates
 * attacks, handles health updates, and determines the outcome of the battle.
 *
 * @author Jonathan Hernandez
 * @version 1.1 (4 June 2025)
 */
public final class BattleSystem {

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
     * Represents the Hero's turn if true.
     */
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

    /**
     * Private helper method that handles attacks from Heroes and Monsters. Multiple attacks may occur when comparing
     * attack speed from the attacker and defender. If a Hero is defending, they have a chance to block the monster's
     * attack.
     *
     * @param theAttacker The attacker who may inflict damage upon the defender.
     * @param theDefender The defender who may receive damage from the attacker.
     * @param theHeroTurn A check to alter the Hero's turn after this method is finished.
     * @return The results from this interaction, including multiple attacks, blocks, attempts, and damage dealt.
     */
    private String processAttacks(final DungeonCharacter theAttacker, final DungeonCharacter theDefender,
    final boolean theHeroTurn) {

        final int attackSpeed = theAttacker.getMyAttackSpeed();
        final int defendSpeed = theDefender.getMyAttackSpeed();

        int numAttacks = Math.max(1, attackSpeed / defendSpeed); // There will be at least one attack
                                                                 // If attack speed is greater, there might be multiple

        int totalDamage = 0;
        int hits = 0;
        int blocks = 0;
        int attempts = 0;

        for (int i = 0; i < numAttacks && !isBattleOver(); i++) {
            int damage = theAttacker.attack(theAttacker.getDamageRangeMin(),
                    theAttacker.getDamageRangeMax(),
                    theAttacker.getMyHitChance());
            attempts++;

            if (damage > 0) {

                boolean blocked = false;

                if (theDefender instanceof Hero) {
                    blocked = ((Hero) theDefender).block();
                }

                if (blocked) {
                    blocks++;
                    continue;
                }

                theDefender.takeDamage(damage);
                totalDamage += damage;
                hits++;
            }
        }

        myTurn = !theHeroTurn;

        String attackerName = theHeroTurn ? "You" : "The " + theAttacker.getName();

        if (hits == 0 && blocks == 0) {
            return attackerName + " attempted " + attempts + (attempts == 1
                    ? " attack, but it missed!"
                    : " attacks, but they all missed!");
        } else if (hits == 0 && blocks > 0) {
            return attackerName + " attempted " + attempts + (attempts == 1
                    ? " attack, but it was blocked!"
                    : " attacks, but they were all blocked!");
        } else if (hits == attempts) {
            return attackerName + " landed " + hits + (hits == 1
                    ? " attack"
                    : " attacks") + " for a total of " + totalDamage + " damage!";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(attackerName)
                    .append(" attempted ")
                    .append(attempts)
                    .append(attempts == 1 ? " attack" : " attacks")
                    .append(", ");

            if (blocks > 0) {
                sb.append(blocks)
                        .append(blocks == 1 ? " was blocked, " : " were blocked, ");
            }

            sb.append(hits)
                    .append(" landed")
                    .append(" for a total of ")
                    .append(totalDamage)
                    .append(" damage!");

            return sb.toString();
        }

    }

    /**
     * Processes the player's attacks. Delegates to a private helper method that returns the results from the attack.
     *
     * @return The outcome of the player's attack.
     */
    public String processPlayerAttacks() {
        return processAttacks(myHero, myMonster, true);
    }

    /**
     * Processes the monster's attack. Delegates to a private helper method that returns the results from the attack.
     *
     * @return The outcome of the monster's attack.
     */
    public String processMonsterAttacks() {
        return processAttacks(myMonster, myHero, false);
    }

    /**
     * Processes the player's special move.
     *
     * @return The outcome of the Hero's special move.
     */
    public String processPlayerSpecialMove() {
        if (!myTurn) {
            return "It is not your turn yet!";
        }

        String result = myHero.specialMove(myMonster);
        myTurn = false;
        return result;

    }

    /**
     * Processes the event in which the Monster heals itself. This method is called from the controller, so a check
     * is made if the health is 0, in which the monster may not revive itself. Furthermore, There is a chance that the
     * healing may fail, in which this method just returns an empty string. Otherwise, the healing results are given.
     *
     * @return The results from the Monster's healing.
     */
    public String processMonsterHeal() {
        if (myMonster.getHP() <= 0) {
            return "";
        }

        int healAmount = myMonster.heal();
        if (healAmount > 0) {
            return "The " + myMonster.getName() + " healed itself for " + healAmount + " HP!";
        }

        return "";

    }

    /**
     * Checks whether the battle is over or ongoing.
     *
     * @return A boolean that represents the status of the battle.
     */
    public boolean isBattleOver() {
        return myHero.getHP() <= 0 || myMonster.getHP() <= 0;
    }

    /**
     * Checks whether the Hero won the battle against a monster.
     *
     * @return A boolean that represents the outcome of the battle.
     */
    public boolean didHeroWin() {
        return myHero.getHP() > 0 && myMonster.getHP() <= 0;
    }

    /**
     * Checks if it is the Hero's turn to attack in battle.
     *
     * @return A boolean that represents the Hero's turn.
     */
    public boolean isPlayerTurn() {
        return myTurn;
    }

}
