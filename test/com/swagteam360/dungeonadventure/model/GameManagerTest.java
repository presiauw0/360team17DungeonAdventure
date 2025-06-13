package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class GameManagerTest {

    @Test
    void testStartNewGame() {

        final GameManager gm = GameManager.getInstance();
        final GameSettings gs = mock(GameSettings.class);
        when(gs.getHero()).thenReturn("warrior");
        when(gs.getDifficulty()).thenReturn("easy");

        gm.startNewGame(gs);

        assertNotNull(gm.getHero());
        assertNotNull(gm.getDungeon());
        assertNotNull(gm.getCurrentRoom());
        assertTrue(gm.getCurrentRoom().isVisited());

    }

    @Test
    void testMovePlayer() {

        // This is kind of a tricky method to test.
        // movePlayer() does not indicate that the actual player has moved.
        // The GUI prevents illegal movement (buttons are hidden), but if
        // someone else were testing this method (black-box testing), they
        // would have some trouble.

        // Yes, I know I wrote the method... - Jonathan

        final GameManager gm = GameManager.getInstance();
        final GameSettings gs = mock(GameSettings.class);
        when(gs.getHero()).thenReturn("priestess");
        when(gs.getDifficulty()).thenReturn("normal");

        final Direction[] dirs = Direction.values();

        for (Direction dir : dirs) {
            gm.startNewGame(gs);

            final Room originalRoom = gm.getCurrentRoom();
            final int originalRow = originalRoom.getRow();
            final int originalCol = originalRoom.getCol();

            gm.movePlayer(dir);
            final Room newRoom = gm.getCurrentRoom();

            if (originalRoom != newRoom) {
                // Movement occurred
                switch (dir) {
                    case NORTH, SOUTH ->
                            assertNotEquals(originalRow,
                                    newRoom.getRow(),
                                    "Row should change when moving " + dir);
                    case EAST, WEST ->
                            assertNotEquals(originalCol,
                                    newRoom.getCol(),
                                    "Col should change when moving " + dir);
                }

                assertTrue(newRoom.isVisited(), "New room should be marked as visited");
            } else {
                // No movement — likely edge of dungeon
                switch (dir) {
                    case NORTH ->
                            assertEquals(0,
                                    originalRow,
                                    "Should only fail to move NORTH if at top edge");
                    case SOUTH ->
                            assertEquals(gm.getDungeon().getRowSize() - 1,
                                    originalRow,
                                    "Should only fail to move SOUTH if at bottom edge");
                    case WEST ->
                            assertEquals(0,
                                    originalCol,
                                    "Should only fail to move WEST if at left edge");
                    case EAST ->
                            assertEquals(gm.getDungeon().getColSize() - 1,
                                    originalCol,
                                    "Should only fail to move EAST if at right edge");
                }

                assertEquals(originalRoom, newRoom, "Player should remain in same room if move was invalid");
            }

        }
    }

    @Test
    void testSaveGame() throws IOException {

        final GameManager gm = GameManager.getInstance();
        final GameSettings gs = mock(GameSettings.class);
        when(gs.getHero()).thenReturn("thief");
        when(gs.getDifficulty()).thenReturn("hard");

        gm.startNewGame(gs);

        final File temp = File.createTempFile("dungeon_save_test", ".txt");
        temp.deleteOnExit();

        gm.saveGame(temp);

        assertTrue(temp.exists(), "Save file should exist");
        assertTrue(temp.isFile(), "Save file should be a file");
        assertTrue(temp.canRead(), "Save file should be readable");
        assertTrue(temp.canWrite(), "Save file should be writeable");
        assertTrue(temp.length() > 0, "Save file should not be empty");

    }

    @Test
    void testSaveGameIOExceptionHandled() {
        final GameManager gm = GameManager.getInstance();
        final GameSettings gs = mock(GameSettings.class);
        when(gs.getHero()).thenReturn("thief");
        when(gs.getDifficulty()).thenReturn("hard");
        gm.startNewGame(gs);

        // Try saving to a directory
        final File directory = new File(System.getProperty("java.io.tmpdir"));
        assertDoesNotThrow(() -> gm.saveGame(directory),
                "Method should handle IOException without throwing");

    }

    @Test
    void testLoadGame() throws IOException {

        final GameManager gm = GameManager.getInstance();
        // Can't use mock GameSettings to test serialization
        final GameSettings gs = new GameSettings("Test Name", "thief", "hard");

        gm.startNewGame(gs);

        final File temp = File.createTempFile("dungeon_save_test", ".txt");
        temp.deleteOnExit();
        gm.saveGame(temp);

        // Keep track of state when game was saved
        final Room originalRoom = gm.getCurrentRoom();
        final int originalRow = originalRoom.getRow();
        final int originalCol = originalRoom.getCol();
        final IRoom.RoomViewModel originalViewModel = gm.getCurrentRoomViewModel();

        try {
            gm.movePlayer(Direction.NORTH);
            gm.movePlayer(Direction.WEST);
            gm.movePlayer(Direction.SOUTH);
            gm.movePlayer(Direction.EAST); // Move around a bit to change state
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Get location
        final Room movedRoom = gm.getCurrentRoom();
        final boolean hasMoved = movedRoom.getRow() != originalRow || movedRoom.getCol() != originalCol;
        assertTrue(hasMoved || movedRoom == originalRoom,
                "Player should have moved or stayed");

        gm.loadGame(temp);

        // Test if loaded room and other attributes are restored
        final Room loadedRoom = gm.getCurrentRoom();
        final IRoom.RoomViewModel loadedViewModel = gm.getCurrentRoomViewModel();

        // We cannot compare Rooms (does not override equals()) so we compare other attributes
        assertEquals(originalRow, loadedRoom.getRow(), "Row should be the same");
        assertEquals(originalCol, loadedRoom.getCol(), "Col should be the same");
        assertEquals(originalRoom.isVisited(), loadedRoom.isVisited(), "Rooms should both be visited");
        assertEquals("thief", gm.getGameSettings().getHero(), "Hero should be the same");
        assertEquals("hard", gm.getGameSettings().getDifficulty(), "Difficulty should be the same");
        assertEquals("Test Name", gm.getGameSettings().getName(), "Name should be the same");
        assertTrue(gm.getHero().getInventory().isEmpty(), "Inventory should be empty");
        assertEquals(originalViewModel, loadedViewModel, "ViewModel should be the same");

    }

    @Test
    void testLoadGameIOExceptionHandled() {
        final GameManager gm = GameManager.getInstance();
        final File file = new File("non-existent-file.txt");
        assertDoesNotThrow(() -> gm.loadGame(file),
        "Method should handle IOException without throwing");
    }

    // TODO: Figure out how to cover the ClassNotFoundException catch block

    @Test
    void testEnableSuperVision() {

        final GameManager gm = GameManager.getInstance();
        final boolean[] eventFired = {false};
        gm.startNewGame(new GameSettings("Test Name", "warrior", "easy"));

        PropertyChangeListener listener = (theEvent -> {
            if (theEvent.getPropertyName().equals("VISION_POWERS")) {
                eventFired[0] = true;
            }
        });

        gm.addPropertyChangeListener(listener);

        gm.enableSuperVision();
        assertTrue(eventFired[0], "Property change for VISION_POWERS should have fired");
        // There are no getters for mySuperVision (boolean) and mySuperVisionCounter
        gm.removePropertyChangeListener(listener);
    }

    @Test
    void testInvalidHeroType() {
        final GameManager gm = GameManager.getInstance();
        final GameSettings gs = new GameSettings("Test Name", "invalid", "easy");

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> gm.startNewGame(gs));

        assertEquals("Unknown/Invalid Hero type.", exception.getMessage());

    }

    @Test
    void testInvalidDungeonType() {
        final GameManager gm = GameManager.getInstance();
        final GameSettings gs = new GameSettings("Test Name", "warrior", "invalid");
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> gm.startNewGame(gs));
        assertEquals("Unknown/Invalid Difficulty level.", exception.getMessage());
    }

    @Test
    void testSendHeroHealthUpdate() {

        final GameManager gm = GameManager.getInstance();
        gm.startNewGame(new GameSettings("Test Name", "warrior", "easy"));

        final int expectedHP = gm.getHero().getHP();
        final boolean[] eventFired = {false};

        PropertyChangeListener listener = (theEvent -> {
            if (theEvent.getPropertyName().equals("HERO_HEALTH_CHANGE")) {
                assertNull(theEvent.getOldValue(), "Old value should be null");
                assertEquals(expectedHP, theEvent.getNewValue(), "New value should be correct");
                eventFired[0] = true;
            }

        });

        gm.addPropertyChangeListener(listener);

        gm.sendHeroHealthUpdate();
        assertTrue(eventFired[0], "HERO_HEALTH_CHANGE event should have fired");
        gm.removePropertyChangeListener(listener);
    }

}
