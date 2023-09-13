package com.gaas.mystic.elven.presenters;

import com.gaas.mystic.elven.domain.ElvenGame;
import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.presenters.views.PlayerView;
import com.gaas.mystic.elven.usecases.CreateGameUsecase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CreateGamePresenter implements CreateGameUsecase.Presenter {

    private CreateGameViewModel viewModel;

    @Override
    public void renderGame(ElvenGame game) {
        Player player = game.getPlayers().get(0);
        viewModel = new CreateGameViewModel(game.getId(), player.getId(), PlayerView.toView(player));
    }

    public CreateGameViewModel present() {
        return viewModel;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateGameViewModel {
        private String gameId;
        private String playerId;
        private PlayerView player;
    }

}
