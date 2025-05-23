package com.swagteam360.dungeonadventure.model;

import java.util.Objects;

public class Pillar {

    private final PillarType myPillarType;

    /**
     * Constructs a new pillar of the given pillar type.
     * @param thePillarType Enum type of pillar
     */
    public Pillar(final PillarType thePillarType) {
        myPillarType = Objects.requireNonNull(thePillarType);
    }

    /**
     * Returns the type of pillar that this object is.
     * @return Enum pillar type
     */
    public PillarType getPillarType() {
        return myPillarType;
    }

    public void buff() {
        //TODO implement
    }
}