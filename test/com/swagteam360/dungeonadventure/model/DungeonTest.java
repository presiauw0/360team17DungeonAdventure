package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class DungeonTest {

    @Mock
    private DungeonMaze mockDungeonMaze;

    @Mock
    private Room mockRoom;

    private Dungeon dungeon;

    private static final int ROW_SIZE = 5;
    private static final int COL_SIZE = 5;

    @BeforeEach
    void setUp() throws Exception {
        // Create normal Dungeon instance
        dungeon = new Dungeon(ROW_SIZE, COL_SIZE);

        // Inject mockDungeonMaze using reflection
        Field field = Dungeon.class.getDeclaredField("myDungeonMaze");
        field.setAccessible(true);
        field.set(dungeon, mockDungeonMaze);
    }

    @Test
    void testGetRoomReturnsCorrectRoom() {
        when(mockDungeonMaze.getCell(2, 3)).thenReturn(mockRoom);

        Room result = dungeon.getRoom(2, 3);
        assertSame(mockRoom, result);

        verify(mockDungeonMaze).getCell(2, 3);
    }

    @Test
    void testGetRoomThrowsClassCastException() {
        Cell nonRoomCell = mock(Cell.class);
        when(mockDungeonMaze.getCell(1, 1)).thenReturn(nonRoomCell);

        assertThrows(ClassCastException.class, () -> dungeon.getRoom(1, 1));
    }

    @Test
    void testGetAdjacentRoomsWithValidCoordinates() {
        // Mock all rooms in a 3x3 grid around (2,2)
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                when(mockDungeonMaze.getCell(i, j)).thenReturn(mockRoom);
            }
        }


        Room[][] adjacentRooms = dungeon.getAdjacentRooms(2, 2);

        for (Room[] row : adjacentRooms) {
            for (Room room : row) {
                assertSame(mockRoom, room);
            }
        }
    }

    @Test
    void testGetAdjacentRoomsThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> dungeon.getAdjacentRooms(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> dungeon.getAdjacentRooms(0, -1));
    }

    @Test
    void testGetAdjacentRoomViewModels() {
        IRoom.RoomViewModel mockViewModel = mock(IRoom.RoomViewModel.class);

        when(mockRoom.getRoomViewModel()).thenReturn(mockViewModel);

        // Mock all rooms in a 3x3 grid around (1,1)
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                when(mockDungeonMaze.getCell(i, j)).thenReturn(mockRoom);
            }
        }

        IRoom.RoomViewModel[][] viewModels = dungeon.getAdjacentRoomViewModels(1, 1);

        for (IRoom.RoomViewModel[] row : viewModels) {
            for (IRoom.RoomViewModel vm : row) {
                assertSame(mockViewModel, vm);
            }
        }
    }

    @Test
    void testGetAllRoomViewModels() {
        IRoom.RoomViewModel mockViewModel = mock(IRoom.RoomViewModel.class);
        when(mockRoom.getRoomViewModel()).thenReturn(mockViewModel);

        // Mock all rooms
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE; j++) {
                when(mockDungeonMaze.getCell(i, j)).thenReturn(mockRoom);
            }
        }

        IRoom.RoomViewModel[][] viewModels = dungeon.getAllRoomViewModels();

        assertEquals(ROW_SIZE, viewModels.length);
        assertEquals(COL_SIZE, viewModels[0].length);

        for (IRoom.RoomViewModel[] row : viewModels) {
            for (IRoom.RoomViewModel vm : row) {
                assertSame(mockViewModel, vm);
            }
        }
    }

    @Test
    void testToStringDoesNotThrow() {
        // Mock basic room behavior
        when(mockDungeonMaze.getCell(anyInt(), anyInt())).thenReturn(mockRoom);
        when(mockRoom.hasTopWall()).thenReturn(false);
        when(mockRoom.hasLeftWall()).thenReturn(false);
        when(mockRoom.hasRightWall()).thenReturn(false);
        when(mockRoom.hasBottomWall()).thenReturn(false);
        when(mockRoom.getCenterSymbol()).thenReturn('.');

        assertNotNull(dungeon.toString());
    }

    @Test
    void testToDetailedStringDoesNotThrow() {
        when(mockDungeonMaze.getCell(anyInt(), anyInt())).thenReturn(mockRoom);
        when(mockRoom.toString()).thenReturn("##\n#.\n##");

        assertNotNull(dungeon.toDetailedString());
    }

    @Test
    void testGetEntranceRowAndCol() {
        assertEquals(0, dungeon.getEntranceRow());
        assertTrue(dungeon.getEntranceCol() >= 0 && dungeon.getEntranceCol() < COL_SIZE);
    }

    @Test
    void testGetRowAndColSize() {
        assertEquals(ROW_SIZE, dungeon.getRowSize());
        assertEquals(COL_SIZE, dungeon.getColSize());
    }

    @Test
    void testConstructorThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Dungeon(-1, 5));
        assertThrows(IllegalArgumentException.class, () -> new Dungeon(5, -1));
    }
}
