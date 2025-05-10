package com.swagteam360.dungeonadventure.model;

/**
 * The RoomFactory class provides factory methods for creating instances of the Room class
 * with specific configurations. This factory simplifies the initialization of rooms
 * by offering predefined methods for common room types such as standard rooms,
 * entrance rooms, and exit rooms.
 *
 * @author Jonathan Hernandez
 * @version May 10, 2025
 */
public class RoomFactory {

    /**
     * Creates a new standard room instance without an entrance or exit.
     * The returned room will be initialized with its default configuration,
     * which may include randomly placed items or environment features,
     * depending on the Room class logic.
     *
     * @return A newly created standard Room instance.
     */
    public static Room createRoom() {
        return new Room(false, false);
    }

    /**
     * Creates a new Room instance configured as the entrance of the dungeon.
     * The returned room will have the entrance flag set to true, and the exit flag set to false.
     * This room is specifically intended to represent the starting point of the dungeon
     * and will not contain additional items, pits, or other attributes.
     *
     * @return A Room object configured as the entrance of the dungeon.
     */
    public static Room createEntranceRoom() {
        return new Room(true, false);
    }

    /**
     * Creates a new Room instance configured as the exit of the dungeon.
     * The returned room will have the exit flag set to true, and the entrance flag set to false.
     * This room is specifically intended to represent the endpoint of the dungeon.
     *
     * @return A Room object configured as the exit of the dungeon.
     */
    public static Room createExitRoom() {
        return new Room(false, true);
    }

}
