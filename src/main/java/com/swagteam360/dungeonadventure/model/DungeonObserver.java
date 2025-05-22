package com.swagteam360.dungeonadventure.model;

import java.util.List;

public interface DungeonObserver {

    void update(Room theRoom, Hero theHero, List<Item> theInventory);

}
