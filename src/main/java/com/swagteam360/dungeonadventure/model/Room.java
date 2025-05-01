package com.swagteam360.dungeonadventure.model;

/**
 * The Room class represents a single room within a dungeon. A room may contain
 * various elements such as doors, items, monsters, or special structures. The class
 * provides methods to configure these attributes and retrieve a string representation
 * of the room's structure.
 *
 * @author Jonathan Hernandez
 * @version 1.0 (April 30th, 2025)
 *
 */
public class Room {

    // private Items[][] myItems;

    // All the following instance variables help format the toString method.

    /**
     * Indicates whether the left door of the room is present or accessible.
     * This variable represents the state of the left door in the current room.
     * This is represented by a "|" in the toString method.
     */
    private final boolean myDoorLeft;

    /**
     * Indicates whether the right door of the room is present or accessible.
     * This is represented by a "|" in the toString method.
     */
    private final boolean myDoorRight;

    /**
     * Indicates whether the bottom door of the room is present or accessible.
     * This is represented by a "-" in the toString method.
     */
    private final boolean myDoorBottom;

    /**
     * Indicates whether the top door of the room is present or accessible.
     * This is represented by a "-" in the toString method.
     */
    private final boolean myDoorTop;

    /**
     * Indicates whether the room contains the main entrance of the dungeon.
     * This is represented by an "i" (In) in the toString method.
     */
    private final boolean myEntrance;

    /**
     * Indicates whether the room contains the main exit of the dungeon.
     * This is represented by an "O" (Out) in the toString method.
     */
    private final boolean myExit;

    /**
     * Indicates whether the room contains multiple items.
     * This is represented by an "M" in the toString method.
     */
    private boolean myMultipleItems;

    /**
     * Indicates whether the room contains a pit.
     * This is represented by an "X" in the toString method.
     */
    private boolean myPit;

    /**
     * Indicates whether the room contains a healing potion.
     * This is represented by an "H" in the toString method.
     */
    private boolean myHealingPotion;

    /**
     * Indicates whether the room contains a vision potion.
     * This is represented by a "V" in the toString method.
     */
    private boolean myVisionPotion;

    /**
     * Indicates whether the room contains a pillar.
     * This is represented by a "P" in the toString method.
     */
    private boolean myPillar;

    /**
     * Constructs a new instance of the Room class.
     *
     * This constructor initializes a Room object with its default properties,
     * including the state of its doors, and whether it contains specific
     * attributes such as an entrance, exit, treasure, pit, or nothing. The
     * default state for all properties is typically false or uninitialized,
     * allowing further customization after object creation.
     */
    protected Room(final boolean theEntrance, final boolean theExit) {

        myEntrance = theEntrance;
        myExit = theExit;

        if (myEntrance || myExit) {
            myPillar = false; // Entrance and exit are empty rooms.
                               // These rooms must be empty

            myDoorTop = false;  // I feel like this is subject to change
            myDoorLeft = false; // via the game logic.
            myDoorRight = false;
            myDoorBottom = false;

        } else {
            myDoorTop = false;
            myDoorLeft = false;
            myDoorRight = false;
            myDoorBottom = false;

            placeItems();
            generatePit();
        }

    }

    protected void placeItems() {

        // Course description probabilities for these items.

        myHealingPotion = Math.random() < 0.10;
        myVisionPotion = Math.random() < 0.10;
        // hasPillar = Math.random() < 0.10;

        int itemCount = (myHealingPotion ? 1 : 0) +
                (myVisionPotion ? 1 : 0) +
                (myPillar ? 1 : 0);

        myMultipleItems = itemCount > 1;

    }

    protected void placeMonsters() {

    }

    /**
     * Determines if the room should contain a pit and updates the `hasPit` field appropriately.
     *
     * The method uses a random probability to decide whether the room will have a pit.
     * There is a 10% chance that the `hasPit` field will be set to true, indicating
     * the presence of a pit in the room. Otherwise, the field will remain false.
     */
    protected void generatePit() {
        myPit = Math.random() < 0.10; // Description says 10% so may adjust later for difficulty.
    }

    /**
     * Determines and returns a character symbol representing the central content
     * of the room based on its attributes.
     *
     * The symbol is chosen according to the following conditions:
     * - 'i': The room is an entrance.
     * - 'O': The room is an exit.
     * - 'P': The room contains a pillar.
     * - 'M': The room contains multiple items.
     * - 'H': The room contains a healing potion.
     * - 'V': The room contains a vision potion.
     * - 'X': The room contains a pit.
     * - ' ': The room contains none of the above.
     *
     * @return A character symbol representing the room's central content.
     */
    private char getCenterSymbol() {

        if (myEntrance) {
            return 'i';
        } else if (myExit) {
            return 'O';
        } else if (myPillar) {
            return 'P';
        } else if (myMultipleItems) {
            return 'M';
        } else if (myHealingPotion) {
            return 'H';
        } else if (myVisionPotion) {
            return 'V';
        } else if (myPit) {
            return 'X';
        } else {
            return ' ';
        }

    }

    /**
     * Constructs and returns a string representation of the room object.
     * The method visually represents the room's structure, including
     * its top, bottom, left, and right walls, along with any symbols
     * that represent the contents of the room's center.
     *
     * The representation includes:
     * - Symbols representing doors (accessible or not) on each side of the room.
     * - A center symbol derived based on the room's contents.
     *
     * The resulting string is structured to reflect the layout of the room
     * in three rows: top, middle, and bottom.
     *
     * @return A string representation of the room's structure and contents.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("*");

        if (myDoorTop) {
            sb.append("-");
        } else {
            sb.append("*");
        }
        sb.append("*\n"); // Printed the top side of the room (first row)

        if (myDoorLeft) {
            sb.append("|");
        } else {
            sb.append("*");
        }

        sb.append(getCenterSymbol()); // Get the symbol of what the room contains

        if (myDoorRight) {
            sb.append("|\n");
        } else {
            sb.append("*\n"); // Printed the left/right and middle of the room (second row)
        }
        sb.append("*");

        if (myDoorBottom) {
            sb.append("-");
        } else {
            sb.append("*");
        }
        sb.append("*"); // Printed the bottom side of the room (last row)

        return sb.toString();

    }

}
