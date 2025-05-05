package com.swagteam360.dungeonadventure.view;

import com.swagteam360.dungeonadventure.model.Room;

/**
 * The RoomView class serves as a simplified representation of a Room object,
 * providing access to specific room details and a textual representation
 * of the room's characteristics.
 * <p>
 * This class is designed to encapsulate a Room object and expose a method
 * to retrieve the associated Room. It also overrides the toString method
 * to provide a string representation of the room's visual structure, as
 * implemented in the underlying Room class.
 *
 * @author Jonathan Hernandez
 * @version 1.0 (April 30th, 2025)
 */
public class RoomView {

    // Subject to change.... - Jonathan

    /**
     * References the specific {@link Room} instance encapsulated within the RoomView.
     * <p>
     * This variable serves as the core representation of a room in the dungeon,
     * encapsulating its attributes such as doors, items, and other special properties.
     * It is used for retrieving details about the room and generating its visual
     * string representation through overridden methods.
     * <p>
     * This field is immutable and initialized only once during the construction of
     * the enclosing RoomView class.
     */
    private final Room myRoom;

    /**
     * Constructs a RoomView object that encapsulates a given Room instance.
     *
     * @param theRoom the Room instance to be encapsulated by this RoomView.
     */
    protected RoomView(Room theRoom) {
        myRoom = theRoom;
    }

    /**
     * Retrieves the Room instance encapsulated within this RoomView.
     *
     * @return the encapsulated Room instance.
     */
    protected Room getRoom() {
        return myRoom;
    }

    /**
     * Returns a string representation of the room encapsulated by this RoomView.
     * The representation is delegated to the overridden toString method
     * of the encapsulated Room instance.
     *
     * @return a textual representation of the Room's structure and contents,
     *         as defined by the Room class's toString method.
     */
    @Override
    public String toString() {
        return myRoom.toString();
    }

}
