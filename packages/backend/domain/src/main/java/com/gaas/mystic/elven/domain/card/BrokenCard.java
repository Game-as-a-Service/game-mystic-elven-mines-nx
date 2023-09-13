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
 * 破壞卡 (破壞道具)
 */
@Getter
@AllArgsConstructor
public class BrokenCard extends ActionCard {
    private ToolName toolName;

    @Override
    public List<DomainEvent> execute(Card.Parameters parameters) {
        Parameters params = (Parameters) parameters;
        Player targetPlayer = parameters.game.getPlayer(params.getTargetPlayerId());
        BrokenCard brokenCard = (BrokenCard) params.card;
        Tool tool = targetPlayer.getTool(brokenCard.getToolName());
        if (!tool.isAvailable()) {
            throw new ElvenGameException("You cannot broke an unavailable tool");
        } else {
            tool.setAvailable(false);
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
