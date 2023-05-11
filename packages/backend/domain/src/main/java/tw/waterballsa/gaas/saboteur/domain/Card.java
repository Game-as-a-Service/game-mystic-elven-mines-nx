package tw.waterballsa.gaas.saboteur.domain;

import tw.waterballsa.gaas.saboteur.domain.events.DomainEvent;

import java.util.List;

/**
 * @author johnny@waterballsa.tw
 */
@FunctionalInterface
public interface Card {

    List<DomainEvent> execute(Parameters parameters);

    class Parameters {
        public String playerId;
        public int handCardIndex;

        public SaboteurGame game;
        public Card card;
        public Player player;

        public Parameters(String playerId, int handCardIndex) {
            this.playerId = playerId;
            this.handCardIndex = handCardIndex;
        }

    }

}
