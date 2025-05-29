package com.swagteam360.dungeonadventure.model;

import java.util.List;

public interface DungeonObserver {

    void update(final Room theRoom, final Hero theHero, final List<Item> theInventory);

    void onBattleStart(final Room theRoom, final Hero theHero, final Monster theMonster);

    void onBattleEnd(final Room theRoom, final Hero theHero, final boolean theHeroWon);

    void onRoomEntered(final Room theRoom, final Hero theHero);

    void onExitRoomEntered(final Room theRoom, final Hero theHero);

    void onPitDamageTaken(final Room theRoom, final Hero theHero, final int theDamage);

}
