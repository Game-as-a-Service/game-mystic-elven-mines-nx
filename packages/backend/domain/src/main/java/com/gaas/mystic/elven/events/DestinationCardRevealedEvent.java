package com.gaas.mystic.elven.events;

public class DestinationCardRevealedEvent extends DomainEvent {

    public int destinationIndex;
    public boolean isGold;

    public DestinationCardRevealedEvent(int destinationIndex, boolean isGold) {
        this.destinationIndex = destinationIndex;
        this.isGold = isGold;
    }

    public int getDestinationIndex() {
        return destinationIndex;
    }

    public boolean isGold() {
        return isGold;
    }
}
