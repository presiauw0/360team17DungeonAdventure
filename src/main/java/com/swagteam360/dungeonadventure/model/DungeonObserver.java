package com.swagteam360.dungeonadventure.model;

import java.util.List;

public interface DungeonObserver {

    void update(Room theRoom, Hero theHero, List<Item> theInventory);
    void onBattleStart(Room theRoom, Hero theHero, Monster theMonster);

    void onBattleEnd(Room theRoom, Hero theHero, boolean theHeroWon);

}
