package com.swagteam360.dungeonadventure.model;

import java.util.List;

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
    void setPillar(final Pillar thePillar);

    /**
     * Adds a monster to a room.
     */
    void addMonster();

    /**
     * Determines whether the room contains a monster.
     * @return True if the room has a monster, false otherwise.
     */
    boolean hasMonster();

    /**
     * Retrieves the monster present in the room, if any.
     * The method checks whether the room contains a monster
     * and returns it. If no monster is present, the method
     * returns null.
     *
     * @return The Monster object if the room contains a monster,
     *         or null if no monster is present.
     */
    Monster getMonster();

    /**
     * Determines whether the room contains any items.
     *
     * @return True if the room has one or more items, false otherwise.
     */
    boolean hasItems();

    /**
     * Retrieves the list of items present in the room.
     * The returned list contains all the items currently
     * available in the room.
     *
     * @return A list of {@link Item} objects representing
     *         the items present in the room.
     */
    List<Item> getAllItems();

    /**
     * Retrieves the list of items in the room
     * and subsequently clears them from the room.
     * This can be used when the player comes to collect
     * the items.
     * @return A list of all items in the room
     */
    List<Item> collectAllItems();
}
