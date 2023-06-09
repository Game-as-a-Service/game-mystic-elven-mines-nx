package tw.waterballsa.gaas.saboteur.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tw.waterballsa.gaas.saboteur.domain.events.DomainEvent;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

/**
 * @author johnny@waterballsa.tw
 */
@Getter
@AllArgsConstructor
public class RockFall extends ActionCard {
    @Override
    public List<DomainEvent> execute(Card.Parameters parameters) {
        var params = (RockFall.Parameters) parameters;
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
