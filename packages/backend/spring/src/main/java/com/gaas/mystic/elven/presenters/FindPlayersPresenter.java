package com.gaas.mystic.elven.presenters;

import com.gaas.mystic.elven.domain.ElvenGame;
import com.gaas.mystic.elven.presenters.views.PlayerView;
import com.gaas.mystic.elven.usecases.FindPlayersUsecase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class FindPlayersPresenter implements FindPlayersUsecase.Presenter {

    private FindPlayersViewModel viewModel;

    @Override
    public void renderGame(ElvenGame game) {
        List<PlayerView> players = game.getPlayers().stream().map(PlayerView::toView).toList();
        viewModel = new FindPlayersViewModel(players);
    }

    public FindPlayersViewModel present() {
        return viewModel;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindPlayersViewModel {
        private List<PlayerView> players;
    }

}
