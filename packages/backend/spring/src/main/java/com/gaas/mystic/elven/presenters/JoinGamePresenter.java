package com.gaas.mystic.elven.presenters;

import com.gaas.mystic.elven.Player;
import com.gaas.mystic.elven.SaboteurGame;
import com.gaas.mystic.elven.presenters.views.PlayerView;
import com.gaas.mystic.elven.usecases.JoinGameUsecase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class JoinGamePresenter implements JoinGameUsecase.Presenter {

    private SaboteurGame game;
    private Player player;

    @Override
    public void renderGame(SaboteurGame game) {
        this.game = game;
    }

    @Override
    public void renderPlayer(Player player) {
        this.player = player;
    }

    public JoinGameViewModel present() {
        List<PlayerView> players = game.getPlayers().stream().map(PlayerView::toView).toList();
        return new JoinGameViewModel(players, player.getId());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinGameViewModel {
        private List<PlayerView> players;
        private String playerId;
    }
}
