package com.gaas.mystic.elven.domain.card;

import com.gaas.mystic.elven.domain.Maze;
import com.gaas.mystic.elven.events.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static java.util.Collections.emptyList;

/**
 * 落石卡
 */
@Getter
@AllArgsConstructor
public class RockFallCard extends ActionCard {
    @Override
    public List<DomainEvent> execute(Card.Parameters parameters) {
        var params = (RockFallCard.Parameters) parameters;
        Maze maze = parameters.game.getMaze();
        maze.removePath(params.row, params.col);
        return emptyList();
    }

    public static class Parameters extends Card.Parameters {
        private final int row;
        private final int col;

        public Parameters(String playerId, int handCardIndex, int row, int col) {
            super(playerId, handCardIndex);
            this.row = row;
            this.col = col;
        }
    }
}
