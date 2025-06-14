package com.swagteam360.dungeonadventure.model;

import java.util.Objects;

/**
 * Represents the pillar logic for the game.
 *
 * @author Preston Sia (psia97)
 * @version 1.0.0
 */
public class Pillar implements Item {

    private final PillarType myPillarType;

    /**
     * Constructs a new pillar of the given pillar type.
     * @param thePillarType Enum type of pillar
     */
    Pillar(final PillarType thePillarType) {
        myPillarType = Objects.requireNonNull(thePillarType);
    }

    /**
     * Returns the type of pillar that this object is.
     * @return Enum pillar type
     */
    public PillarType getPillarType() {
        return myPillarType;
    }

    @Override
    public String getName() {
        return myPillarType.name();
    }

    @Override
    public String buff() {
        //TODO implement
        return null;
    }
}