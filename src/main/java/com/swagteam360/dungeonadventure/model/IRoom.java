package com.swagteam360.dungeonadventure.model;

public interface IRoom {
    /**
     * Property that specifies if the room is an entrance.
     * Rooms that are entrances do not have anything else.
     */
    String PROPERTY_ENTRANCE = "ENTRANCE";
    /**
     * Property that specifies if the room is an exit.
     * Rooms that are exits do not have anything else.
     */
    String PROPERTY_EXIT = "EXIT";
    /**
     * Property that specifies if a room is
     * neither an entrance nor exit. In other
     * words, it is normal, and has no restrictions.
     */
    String PROPERTY_NORMAL = "NONE";
    /**
     * Specifies the default maximum number of items
     * that a room can contain.
     */
    int DEFAULT_MAX_ITEMS = 10;


    /**
     * Assign a pillar to a room.
     */
    void setPillar();

    /**
     * Adds a monster to a room.
     */
    void addMonster();
}
