package com.gaas.mystic.elven.domain.card;

import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.domain.tool.Tool;
import com.gaas.mystic.elven.domain.tool.ToolName;
import com.gaas.mystic.elven.events.DomainEvent;
import com.gaas.mystic.elven.exceptions.ElvenGameException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import java.util.Collections;
import java.util.List;

/**
 * 修復卡 (修復道具)
 */
@Getter
@AllArgsConstructor
public class FixCard extends ActionCard {

    public final CardType type = CardType.FIX;
    public final String name = "FixCard";
    protected ToolName toolName;

    @Override
    public List<DomainEvent> execute(Card.Parameters parameters) {
        Parameters params = (Parameters) parameters;
        Player targetPlayer = parameters.game.getPlayer(params.getTargetPlayerId());
        FixCard r = (FixCard) params.card;
        Tool tool = targetPlayer.getTool(r.getToolName());
        if (tool.isAvailable()) {
            throw new ElvenGameException("you cannot fix an available tool");
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
