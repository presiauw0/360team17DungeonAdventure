package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

/*
 * depends on the real "cell" class being properly implemented and tested. Mocking room
 * led to near-infinite loops and nary a successful test.
 */

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class DungeonMazeTest {

    @Mock
    private CellFactory mockCellFactory;

    private DungeonMaze dungeonMaze;

    private static final int ROW_SIZE = 3;
    private static final int COL_SIZE = 3;

    @BeforeEach
    void setUp() {
        // Use real Room objects so buildMaze() can work
        when(mockCellFactory.createCell(anyInt(), anyInt()))
                .thenAnswer(invocation -> {
                    int row = invocation.getArgument(0);
                    int col = invocation.getArgument(1);
                    return new Room(IRoom.PROPERTY_NORMAL, row, col);
                });

        // Mock entrance location
        when(mockCellFactory.getEntranceRow()).thenReturn(0);
        when(mockCellFactory.getEntranceCol()).thenReturn(0);

        // Create the DungeonMaze — safe now!
        dungeonMaze = new DungeonMaze(ROW_SIZE, COL_SIZE, mockCellFactory);
    }

    @Test
    void testConstructorThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new DungeonMaze(-1, 5, mockCellFactory));
        assertThrows(IllegalArgumentException.class, () -> new DungeonMaze(5, -1, mockCellFactory));
    }

    @Test
    void testGetCellReturnsCorrectCell() {
        Cell cell = dungeonMaze.getCell(0, 0);
        assertNotNull(cell);
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
    }

    @Test
    void testPrintMazeDoesNotThrow() {
        // Redirect System.out to capture the printMaze() output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            dungeonMaze.printMaze();

            String output = outContent.toString();
            assertNotNull(output);
            assertFalse(output.isEmpty());
        } finally {
            // Restore System.out
            System.setOut(originalOut);
        }
    }

    @Test
    void testToStringReturnsNonEmptyString() {
        String mazeString = dungeonMaze.toString();
        assertNotNull(mazeString);
        assertFalse(mazeString.isEmpty());
    }

    @Test
    void testGenerateFieldIsCalled() {
        // Verify that createCell() was called ROW_SIZE * COL_SIZE times
        verify(mockCellFactory, times(ROW_SIZE * COL_SIZE)).createCell(anyInt(), anyInt());
    }
}
