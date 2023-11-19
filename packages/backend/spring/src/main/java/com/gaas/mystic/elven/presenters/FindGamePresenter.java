package com.gaas.mystic.elven.presenters;

import com.gaas.mystic.elven.domain.ElvenGame;
import com.gaas.mystic.elven.presenters.views.MazeView;
import com.gaas.mystic.elven.usecases.FindGameUsecase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FindGamePresenter implements FindGameUsecase.Presenter {

    private FindGameViewModel viewModel;

    @Override
    public void renderGame(ElvenGame game) {
        int deckSize = game.getDeck().size();
        MazeView maze = MazeView.toView(game);
        viewModel = new FindGameViewModel(deckSize, maze);
    }

    public FindGameViewModel present() {
        return viewModel;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindGameViewModel {
        private Integer deckSize;
        private MazeView maze;
    }

}
