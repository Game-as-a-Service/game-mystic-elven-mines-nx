package com.gaas.mystic.elven.domain.card;

import com.gaas.mystic.elven.domain.ElvenGame;
import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.events.DomainEvent;

import java.util.List;

/**
 * 卡片
 */
public interface Card {

    List<DomainEvent> execute(Parameters parameters);

    class Parameters {
        public String playerId;
        public int handCardIndex;

        public ElvenGame game;
        public Card card;
        public Player player;

        public Parameters(String playerId, int handCardIndex) {
            this.playerId = playerId;
            this.handCardIndex = handCardIndex;
        }


    }

    CardType getType();

    String getName();

}
