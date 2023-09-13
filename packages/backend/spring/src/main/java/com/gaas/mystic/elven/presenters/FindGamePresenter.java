package com.gaas.mystic.elven.presenters;

import com.gaas.mystic.elven.domain.ElvenGame;
import com.gaas.mystic.elven.presenters.views.PlayerView;
import com.gaas.mystic.elven.usecases.FindGameUsecase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class FindGamePresenter implements FindGameUsecase.Presenter {

    private FindGameViewModel viewModel;

    @Override
    public void renderGame(ElvenGame game) {
        List<PlayerView> players = game.getPlayers().stream().map(PlayerView::toView).toList();
        viewModel = new FindGameViewModel(players);
    }

    public FindGameViewModel present() {
        return viewModel;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindGameViewModel {
        private List<PlayerView> players;
    }

}
