package com.gaas.mystic.elven.domain.card;

import com.gaas.mystic.elven.domain.GoalCard;
import com.gaas.mystic.elven.events.DestinationCardRevealedEvent;
import com.gaas.mystic.elven.events.DomainEvent;
import lombok.Getter;

import java.util.List;

import static java.util.Collections.singletonList;

/**
 * 地圖卡 (可以查看任意一個終點卡)
 */
@Getter
public class MapCard extends ActionCard {

    private final CardType type = CardType.MAP;
    private final String name = MapCard.class.getSimpleName();

    @Override
    public List<DomainEvent> execute(Card.Parameters parameters) {
        Parameters params = (Parameters) parameters;
        GoalCard card = parameters.game.getDestinationCardByIndex(params.destinationCardIndex);
        return singletonList(new DestinationCardRevealedEvent(params.destinationCardIndex, card.isGoal()));
    }

    public static class Parameters extends Card.Parameters {
        int destinationCardIndex;

        public Parameters(String playerId, int handCardIndex, int destinationCardIndex) {
            super(playerId, handCardIndex);
            this.destinationCardIndex = destinationCardIndex;
        }
    }

}
