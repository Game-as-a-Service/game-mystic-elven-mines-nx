package tw.waterballsa.gaas.saboteur.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import tw.waterballsa.gaas.saboteur.domain.events.DomainEvent;
import tw.waterballsa.gaas.saboteur.domain.exceptions.SaboteurGameException;

import java.util.Collections;
import java.util.List;

/**
 * @author johnny@waterballsa.tw
 */
@Getter
@AllArgsConstructor
public class Repair extends ActionCard {
    protected ToolName toolName;

    @Override
    public List<DomainEvent> execute(Card.Parameters parameters) {
        Parameters params = (Parameters) parameters;
        Player targetPlayer = parameters.game.getPlayer(params.getTargetPlayerId());
        Repair r = (Repair) params.card;
        Tool tool = targetPlayer.getTool(r.getToolName());
        if (tool.isAvailable()) {
            throw new SaboteurGameException("you cannot repair an available tool");
        } else {
            tool.setAvailable(true);
            return Collections.emptyList();
        }
    }

    @Value
    @EqualsAndHashCode(callSuper = true)
    public static class Parameters extends Card.Parameters {
        String targetPlayerId;

        public Parameters(String playerId, int handCardIndex, String targetPlayerId) {
            super(playerId, handCardIndex);
            this.targetPlayerId = targetPlayerId;
        }
    }
}
