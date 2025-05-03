package com.swagteam360.dungeonadventure.model;

public abstract class Hero extends DungeonCharacter {

    public Hero(String theName, int theHP, int theAttackSpeed, int theHitChance, characterType theCharacterType) {
        super(theName, theHP, theAttackSpeed, theHitChance, theCharacterType);
    }
}
