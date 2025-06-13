package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomFactoryTest {

    private static final int ENTRANCE_ROW = 1;
    private static final int ENTRANCE_COL = 2;
    private static final int EXIT_ROW = 3;
    private static final int EXIT_COL = 4;

    private RoomFactory roomFactory;

    @BeforeEach
    void setUp() {
        roomFactory = new RoomFactory(ENTRANCE_ROW, ENTRANCE_COL, EXIT_ROW, EXIT_COL);
    }

    @Test
    void testConstructorThrowsOnNegativeCoordinates() {
        assertThrows(IllegalArgumentException.class,
                () -> new RoomFactory(-1, 0, 0, 0));
        assertThrows(IllegalArgumentException.class,
                () -> new RoomFactory(0, -1, 0, 0));
        assertThrows(IllegalArgumentException.class,
                () -> new RoomFactory(0, 0, -1, 0));
        assertThrows(IllegalArgumentException.class,
                () -> new RoomFactory(0, 0, 0, -1));
    }

    @Test
    void testGetters() {
        assertEquals(ENTRANCE_ROW, roomFactory.getEntranceRow());
        assertEquals(ENTRANCE_COL, roomFactory.getEntranceCol());
        assertEquals(EXIT_ROW, roomFactory.getExitRow());
        assertEquals(EXIT_COL, roomFactory.getExitCol());
    }

    @Test
    void testCreateEntranceRoom() {
        Cell cell = roomFactory.createCell(ENTRANCE_ROW, ENTRANCE_COL);
        assertTrue(cell instanceof Room);
        Room room = (Room) cell;
        assertTrue(room.isEntrance());
        assertFalse(room.isExit());
        assertEquals(IRoom.PROPERTY_ENTRANCE, room.getRoomViewModel().entranceExit());
    }

    @Test
    void testCreateExitRoom() {
        Cell cell = roomFactory.createCell(EXIT_ROW, EXIT_COL);
        assertTrue(cell instanceof Room);
        Room room = (Room) cell;
        assertTrue(room.isExit());
        assertFalse(room.isEntrance());
        assertEquals(IRoom.PROPERTY_EXIT, room.getRoomViewModel().entranceExit());
    }

    @Test
    void testCreateNormalRoom() {
        int normalRow = 5;
        int normalCol = 6;
        Cell cell = roomFactory.createCell(normalRow, normalCol);
        assertTrue(cell instanceof Room);
        Room room = (Room) cell;
        assertFalse(room.isEntrance());
        assertFalse(room.isExit());
        assertEquals(IRoom.PROPERTY_NORMAL, room.getRoomViewModel().entranceExit());
    }

    @Test
    void testCreateCellThrowsOnNegativeCoordinates() {
        assertThrows(IllegalArgumentException.class,
                () -> roomFactory.createCell(-1, 0));
        assertThrows(IllegalArgumentException.class,
                () -> roomFactory.createCell(0, -1));
    }
}
