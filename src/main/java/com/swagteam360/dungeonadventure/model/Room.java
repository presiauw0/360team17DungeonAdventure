package com.swagteam360.dungeonadventure.model;

import java.util.*;

/**
 * The Room class represents a single room within a dungeon. A room may contain
 * various elements such as doors, items, monsters, or special structures. The class
 * provides methods to configure these attributes and retrieve a string representation
 * of the room's structure.
 *
 * @author Jonathan Hernandez, Preston Sia (psia97)
 * @version 1.2 10 May 2025
 *
 */
public class Room implements Cell, IRoom {

    /**
     * Contains the items in the room, such
     * as potions.
     */
    private final List<Item> myItems;

    private Pillar myPillar;
    private boolean myPit = false; //FIXME

    /**
     * Specify whether the room is an entrance,
     * exit, or none.
     */
    private final String myEntranceExit;

    // Status of the doors/walls
    /**
     * Indicates whether the left wall of the room is present.
     * This variable represents the state of the left wall in the current room.
     * This is represented by a "|" in the toString method.
     */
    private boolean myWallLeft;

    /**
     * Indicates whether the right wall of the room is present.
     * This is represented by a "|" in the toString method.
     */
    private boolean myWallRight;

    /**
     * Indicates whether the bottom wall of the room is present.
     * This is represented by a "-" in the toString method.
     */
    private boolean myWallBottom;

    /**
     * Indicates whether the top wall of the room is present or accessible.
     * This is represented by a "-" in the toString method.
     */
    private boolean myWallTop;

    /**
     * Location of the room in the maze - Row coordinate
     */
    private final int myRow;

    /**
     * Location of the room in the maze - Column coordinate
     */
    private final int myCol;

    /**
     * Used by the maze algorithm to determine if
     * a room has been visited during creation
     */
    private boolean myTraversalFlag;

    /**
     * Store information on whether the player
     * has visited this room.
     */
    private boolean myVisited;

    /**
     * Store the room's monster
     */
    private Monster myMonster;


    /**
     * Constructs a new instance of the Room class with defaults.
     * A room contains four walls and no items by default.
     * @param theEntranceExitType Sets entrance or exit type
     * @param theRow Row coordinate for the room.
     * @param theCol Column coordinate for the room.
     */
    public Room(final String theEntranceExitType,
                final int theRow, final int theCol) {
        this(theEntranceExitType, theRow, theCol,
                true, true, true, true);
    }

    /**

     * Constructs a room with specific parameters that
     * control its entrance/exit type, coordinates,
     * and status of doors (aka walls).
     * @param theEntranceExitType Sets entrance or exit type
     * @param theRow Row coordinate for the room.
     * @param theCol Column coordinate for the room.
     * @param theLeftDoor Status of left door/wall.
     * @param theRightDoor Status of right door/wall.
     * @param theTopDoor Status of upper door/wall.
     * @param theBottomDoor Status of lower door/wall.
     */
    public Room(final String theEntranceExitType,
                final int theRow, final int theCol,
                final boolean theLeftDoor, final boolean theRightDoor,
                final boolean theTopDoor, final boolean theBottomDoor) {

        super(); // explicit call to superclass

        if (theRow < 0 || theCol < 0) {
            throw new IllegalArgumentException("Row and column cannot be negative.");
        }

        myItems = new ArrayList<>();

        // set entrance or exit type
        switch (theEntranceExitType) {
            case IRoom.PROPERTY_NORMAL -> myEntranceExit = IRoom.PROPERTY_NORMAL;
            case IRoom.PROPERTY_ENTRANCE -> myEntranceExit = IRoom.PROPERTY_ENTRANCE;
            case IRoom.PROPERTY_EXIT -> myEntranceExit = IRoom.PROPERTY_EXIT;
            case null, default -> throw new IllegalArgumentException("Invalid entrance-exit type.");
        }


        // Set row, column, and default traversal flag value
        myRow = theRow;
        myCol = theCol;

        myTraversalFlag = false;
        myVisited = false;

        myWallLeft = theLeftDoor;
        myWallRight = theRightDoor;
        myWallTop = theTopDoor;
        myWallBottom = theBottomDoor;

        // generate items and pits
        if (!isEntranceOrExit()) {
            generateItems();
            generatePits();

        }

    }

    // Cell implementation
    @Override
    public boolean hasLeftWall() {
        return myWallLeft;
    }

    @Override
    public boolean hasRightWall() {
        return myWallRight;
    }

    @Override
    public boolean hasTopWall() {
        return myWallTop;
    }

    @Override
    public boolean hasBottomWall() {
        return myWallBottom;
    }

    @Override
    public void setLeftWall(final boolean theStatus) {
        myWallLeft = theStatus;
    }

    @Override
    public void setRightWall(final boolean theStatus) {
        myWallRight = theStatus;
    }

    @Override
    public void setTopWall(final boolean theStatus) {
        myWallTop = theStatus;
    }

    @Override
    public void setBottomWall(final boolean theStatus) {
        myWallBottom = theStatus;
    }

    @Override
    public int getRow() {
        return myRow;
    }

    @Override
    public int getCol() {
        return myCol;
    }

    @Override
    public boolean traversalVisitFlag() {
        return myTraversalFlag;
    }

    @Override
    public void markTraversalVisit() {
        myTraversalFlag = true;
    }


    /**
     * Randomly generated potions and other items for a room.
     */
    private void generateItems() {
        final boolean genHealingPotion = Math.random() < Item.GENERATION_PROB;
        final boolean genVisionPotion = Math.random() < Item.GENERATION_PROB;

        if (genHealingPotion) {
            myItems.add(new HealthPotion((int)(Math.random()*5)+1));
        }
        if (genVisionPotion) {
            myItems.add(new VisionPotion());
        }
    }

    /**
     * Determines if the room should contain a pit.
     * <p>
     * The method uses a random probability to decide whether the room will have a pit.
     * There is a 10% chance that the `hasPit` field will be set to true, indicating
     * the presence of a pit in the room. Otherwise, the field will remain false.
     */
    private void generatePits() {
        //myPit = Math.random() < 0.10; // Description says 10% so may adjust later for difficulty.
        // TODO implement
        final boolean genPit = Math.random() < Item.GENERATION_PROB;

        if (genPit) {
            myPit = true; //FIXME
        }
    }

    @Override
    public void addMonster() {

        // Three random choices. We need to randomly call this method else where since this method guarantees the
        // creation of a monster.

        double choice = Math.random();
        if (choice < 0.25) {
            myMonster = MonsterFactory.createMonster("Ogre");
        } else if (choice < 0.50) {
            myMonster = MonsterFactory.createMonster("Gremlin");
        } else {
            myMonster = MonsterFactory.createMonster("Witch");
        }
    }

    @Override
    public void setPillar(final Pillar thePillar) {
        if (isEntranceOrExit()) {
            throw new IllegalStateException(
                    "The pillar cannot be set on an entrance or exit room.");
        } else {
            myPillar = Objects.requireNonNull(thePillar);
        }
    }

    @Override
    public void setVisited(final boolean theVisisted) {
        myVisited = theVisisted;
    }

    @Override
    public boolean hasMonster() {
        return myMonster != null;
    }

    @Override
    public Monster getMonster() {
        return myMonster;
    }

    @Override
    public boolean hasItems() {
        return !myItems.isEmpty();
    }

    @Override
    public List<Item> getAllItems() {
        // TODO should we allow any method to retrieve the items
        //   WITHOUT clearing them? This might allow a poorly written
        //   player class to collect items over and over again
        //   if they use this method instead of collectAllItems
        return myItems;
    }

    @Override
    public List<Item> collectAllItems() {
        //TODO implement checks to ensure that items aren't collected during certain conditions
        final List<Item> roomItems = new ArrayList<>(myItems); // create a copy of the list
        myItems.clear(); // clear the list for the room so that items cannot be collected again
        return roomItems; // return the list of items to the player
    }

    @Override
    public Set<Direction> getAvailableDirections() {
        Set<Direction> directions = new HashSet<>();

        if (!myWallTop) { directions.add(Direction.NORTH);}
        if (!myWallBottom) { directions.add(Direction.SOUTH);}
        if (!myWallLeft) { directions.add(Direction.WEST);}
        if (!myWallRight) { directions.add(Direction.EAST);}

        return directions;
    }

    @Override
    public void removeMonster() {
        myMonster = null;
    }

    @Override
    public boolean isExit() {
        return IRoom.PROPERTY_EXIT.equals(myEntranceExit);
    }

    @Override
    public boolean isEntrance() {
        return IRoom.PROPERTY_ENTRANCE.equals(myEntranceExit);
    }

    @Override
    public boolean isVisited() {
        return myVisited;
    }

    // Package helpers

    /**
     * Indicate whether this room is an entrance or exit.
     * @return True if the room is an entrance or exit, false otherwise
     */
    boolean isEntranceOrExit() {
        // using package-level visibility
        return !IRoom.PROPERTY_NORMAL.equals(myEntranceExit);
    }

    /**
     * Reports whether the room has a pillar or not
     * @return Boolean value indicating whether a pillar is present
     */
    public boolean hasPillar() {
        return myPillar != null;
    }

    /**
     * Determines whether the room contains a pit.
     *
     * @return True if the room has a pit, false otherwise.
     */
    public boolean hasPit() {
        return myPit;
    }

    // Private helpers

    /**
     * Clear all items and pillars.
     */
    private void clearRoom() {
        myItems.clear(); // Clear out items
        // clear pillar code here
    }

    /**
     * Determines and returns a character symbol representing the central content
     * of the room based on its attributes.
     * <p>
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
    char getCenterSymbol() { // using package level visibility

        if (IRoom.PROPERTY_ENTRANCE.equals(myEntranceExit)) {
            return 'i';
        } else if (IRoom.PROPERTY_EXIT.equals(myEntranceExit)) {
            return 'O';
        } else if (myPillar != null) {
            return 'P';
        } else {
            char returnChar = ' ';

            if (myPit) {
                returnChar = 'X'; //FIXME when the pit is implemented
            }

            for (Item x : myItems) {
                if (returnChar == ' ') {
                    if (x instanceof HealthPotion) {
                        returnChar = 'H';
                    } else if (x instanceof VisionPotion) {
                        returnChar = 'V';
                    }
                } else {
                    returnChar = 'M'; // multiple items
                }
            }

            return returnChar;
        }
    }

    /**
     * Constructs and returns a string representation of the room object.
     * The method visually represents the room's structure, including
     * its top, bottom, left, and right walls, along with any symbols
     * that represent the contents of the room's center.
     * <p>
     * The representation includes:
     * - Symbols representing doors (accessible or not) on each side of the room.
     * - A center symbol derived based on the room's contents.
     * <p>
     * The resulting string is structured to reflect the layout of the room
     * in three rows: top, middle, and bottom.
     *
     * @return A string representation of the room's structure and contents.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("*");

        if (!myWallTop) {
            sb.append("-");
        } else {
            sb.append("*");
        }
        sb.append("*\n"); // Printed the top side of the room (first row)

        if (!myWallLeft) {
            sb.append("|");
        } else {
            sb.append("*");
        }

        sb.append(getCenterSymbol()); // Get the symbol of what the room contains

        if (!myWallRight) {
            sb.append("|\n");
        } else {
            sb.append("*\n"); // Printed the left/right and middle of the room (second row)
        }
        sb.append("*");

        if (!myWallBottom) {
            sb.append("-");
        } else {
            sb.append("*");
        }
        sb.append("*"); // Printed the bottom side of the room (last row)

        return sb.toString();

    }

}