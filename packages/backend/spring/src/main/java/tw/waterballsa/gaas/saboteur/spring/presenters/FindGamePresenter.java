package tw.waterballsa.gaas.saboteur.spring.presenters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.waterballsa.gaas.saboteur.app.usecases.FindGameUsecase;
import tw.waterballsa.gaas.saboteur.domain.SaboteurGame;
import tw.waterballsa.gaas.saboteur.spring.presenters.views.PlayerView;

import java.util.List;

public class FindGamePresenter implements FindGameUsecase.Presenter {

    private FindGameViewModel viewModel;

    @Override
    public void renderGame(SaboteurGame game) {
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
