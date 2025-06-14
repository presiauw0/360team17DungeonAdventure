package com.swagteam360.dungeonadventure.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Pillar test suite
 *
 * @author Preston Sia (psia97)
 * @version 1.0.0, 13 June 2025
 */
public class PillarTest {
    @Test
    void testConstructor() {
        Pillar pillar = new Pillar(PillarType.GOLD);
        assertEquals(
                PillarType.GOLD,
                pillar.getPillarType()
        );
    }

    @Test
    void testNullConstructor() {
        assertThrows(
                NullPointerException.class,
                () -> new Pillar(null)
        );
    }

    @Test
    void testGetName() {
        PillarType type = PillarType.GOLD;
        Pillar pillar = new Pillar(type);
        assertEquals(
                type.name(),
                pillar.getName()
        );
    }

    @Test
    void buff() {
        Pillar pillar = new Pillar(PillarType.GOLD);
        assertNull(pillar.buff());
    }
}
