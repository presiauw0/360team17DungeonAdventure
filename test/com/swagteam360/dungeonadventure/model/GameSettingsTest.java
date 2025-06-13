package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
public class GameSettingsTest {

    private final String myName = "Test Name";

    private final String myHero = "Test Hero";

    private final String myDifficulty = "Test Difficulty";

    // Constructor Tests

    @Test
    void testConstructor() {

        final GameSettings gs = new GameSettings(myName, myHero, myDifficulty);

        assertAll(
                () -> assertEquals(
                        myName,
                        gs.getName(),
                        "Name is incorrect"
                ),
                () -> assertEquals(
                        myHero,
                        gs.getHero(),
                        "Hero is incorrect"
                ),
                () -> assertEquals(
                        myDifficulty,
                        gs.getDifficulty(),
                        "Difficulty is incorrect"
                )
        );
    }

    @Test
    void testConstructorNullArguments() {
        assertThrows(
                NullPointerException.class,
                () -> new GameSettings(null, null, null),
                "Failed to protect against null data"
        );
    }

    @Test
    void testConstructorEmptyArguments() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new GameSettings("", "", ""),
                "Failed to protect against empty data"
        );
    }

}
