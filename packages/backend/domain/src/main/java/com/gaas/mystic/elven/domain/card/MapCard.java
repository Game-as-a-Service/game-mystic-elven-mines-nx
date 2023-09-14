package com.gaas.mystic.elven.domain.card;

import com.gaas.mystic.elven.events.DestinationCardRevealedEvent;
import com.gaas.mystic.elven.events.DomainEvent;

import java.util.List;

import static java.util.Collections.singletonList;

/**
 * 地圖卡 (可以查看任意一個終點卡)
 */
public class MapCard extends ActionCard {

    @Override
    public List<DomainEvent> execute(Card.Parameters parameters) {
        Parameters params = (Parameters) parameters;
        GoalCard card = parameters.game.getDestinationCardByIndex(params.destinationCardIndex);
        return singletonList(new DestinationCardRevealedEvent(params.destinationCardIndex, card.isGold()));
    }

    public static class Parameters extends Card.Parameters {
        int destinationCardIndex;

        public Parameters(String playerId, int handCardIndex, int destinationCardIndex) {
            super(playerId, handCardIndex);
            this.destinationCardIndex = destinationCardIndex;
        }
    }

}
