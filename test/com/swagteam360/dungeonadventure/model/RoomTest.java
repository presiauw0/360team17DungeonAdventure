package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;
import java.util.Set;

class RoomTest {

    private Room room;

    private static final int ROW = 2;
    private static final int COL = 3;

    @BeforeEach
    void setUp() {
        room = new Room(IRoom.PROPERTY_NORMAL, ROW, COL);
    }

    @Test
    void testWallGettersAndSetters() {
        assertTrue(room.hasLeftWall());
        assertTrue(room.hasRightWall());
        assertTrue(room.hasTopWall());
        assertTrue(room.hasBottomWall());

        room.setLeftWall(false);
        room.setRightWall(false);
        room.setTopWall(false);
        room.setBottomWall(false);

        assertFalse(room.hasLeftWall());
        assertFalse(room.hasRightWall());
        assertFalse(room.hasTopWall());
        assertFalse(room.hasBottomWall());
    }

    @Test
    void testCoordinates() {
        assertEquals(ROW, room.getRow());
        assertEquals(COL, room.getCol());
    }

    @Test
    void testTraversalVisitFlag() {
        assertFalse(room.traversalVisitFlag());
        room.markTraversalVisit();
        assertTrue(room.traversalVisitFlag());
    }

    @Test
    void testEntranceAndExitFlags() {
        Room entranceRoom = new Room(IRoom.PROPERTY_ENTRANCE, 0, 0);
        Room exitRoom = new Room(IRoom.PROPERTY_EXIT, 1, 1);
        Room normalRoom = new Room(IRoom.PROPERTY_NORMAL, 2, 2);

        assertTrue(entranceRoom.isEntrance());
        assertFalse(entranceRoom.isExit());
        assertTrue(entranceRoom.isEntranceOrExit());

        assertTrue(exitRoom.isExit());
        assertFalse(exitRoom.isEntrance());
        assertTrue(exitRoom.isEntranceOrExit());

        assertFalse(normalRoom.isEntrance());
        assertFalse(normalRoom.isExit());
        assertFalse(normalRoom.isEntranceOrExit());
    }

    @Test
    void testSetPillar() {
        Pillar pillar = new Pillar(PillarType.BRONZE);
        assertFalse(room.hasPillar());
        room.setPillar(pillar);
        assertTrue(room.hasPillar());

        List<Item> items = room.collectAllItems();
        assertTrue(items.contains(pillar));
        assertFalse(room.hasPillar());
    }

    @Test
    void testSetPillarThrowsOnEntranceExit() {
        Room entranceRoom = new Room(IRoom.PROPERTY_ENTRANCE, 0, 0);
        Pillar pillar = new Pillar(PillarType.GOLD);

        assertThrows(IllegalStateException.class, () -> entranceRoom.setPillar(pillar));
    }

    @Test
    void testAddRemoveMonster() {
        // Mock MonsterFactory
        Monster fakeMonster = mock(Monster.class);
        try (MockedStatic<MonsterFactory> mockedFactory = mockStatic(MonsterFactory.class)) {
            mockedFactory.when(() -> MonsterFactory.createMonster(anyString()))
                    .thenReturn(fakeMonster);

            assertFalse(room.hasMonster());
            assertNull(room.getMonster());

            room.addMonster();

            assertTrue(room.hasMonster());
            assertEquals(fakeMonster, room.getMonster());

            room.removeMonster();

            assertFalse(room.hasMonster());
            assertNull(room.getMonster());
        }
    }

    @Test
    void testVisitedFlag() {
        assertFalse(room.isVisited());
        room.setVisited(true);
        assertTrue(room.isVisited());
    }

    @Test
    void testAvailableDirectionsAllBlockedInitially() {
        Set<Direction> directions = room.getAvailableDirections();
        assertTrue(directions.isEmpty());

        room.setTopWall(false);
        room.setBottomWall(false);
        room.setLeftWall(false);
        room.setRightWall(false);

        directions = room.getAvailableDirections();
        assertTrue(directions.contains(Direction.NORTH));
        assertTrue(directions.contains(Direction.SOUTH));
        assertTrue(directions.contains(Direction.WEST));
        assertTrue(directions.contains(Direction.EAST));
    }

    @Test
    void testGetAllItemsAndCollectAllItems() {
        // Add a pillar first
        Pillar pillar = new Pillar(PillarType.SILVER);
        room.setPillar(pillar);

        List<Item> itemsBefore = room.getAllItems();
        assertNotNull(itemsBefore);

        List<Item> collectedItems = room.collectAllItems();
        assertTrue(collectedItems.contains(pillar));
        assertTrue(room.getAllItems().isEmpty());
    }

    @Test
    void testGetRoomViewModel() {
        IRoom.RoomViewModel viewModel = room.getRoomViewModel();
        assertEquals(room.hasLeftWall(), viewModel.leftWall());
        assertEquals(room.hasRightWall(), viewModel.rightWall());
        assertEquals(room.hasTopWall(), viewModel.topWall());
        assertEquals(room.hasBottomWall(), viewModel.bottomWall());
        assertEquals(room.isEntrance() ? IRoom.PROPERTY_ENTRANCE :
                room.isExit() ? IRoom.PROPERTY_EXIT :
                        IRoom.PROPERTY_NORMAL, viewModel.entranceExit());
        assertEquals(room.isVisited(), viewModel.visited());
        assertEquals(room.getRow(), viewModel.row());
        assertEquals(room.getCol(), viewModel.col());
        assertEquals(room.toString(), viewModel.roomString());
    }

    @Test
    void testToStringFormat() {
        String result = room.toString();
        assertNotNull(result);
        assertTrue(result.contains("*"));
        assertTrue(result.length() > 0);
    }

    // OPTIONAL — Test pit generation probabilistically
    @Test
    void testPitGeneration() {
        int numTrials = 1000;
        int numWithPits = 0;

        for (int i = 0; i < numTrials; i++) {
            Room testRoom = new Room(IRoom.PROPERTY_NORMAL, i, 0);
            if (testRoom.hasPit()) {
                numWithPits++;
            }
        }

        System.out.println("Rooms with pits: " + numWithPits + " out of " + numTrials);

        // Assert that at least some pits are generated
        assertTrue(numWithPits > 0, "No pits generated — check generatePits() logic!");
    }
}
