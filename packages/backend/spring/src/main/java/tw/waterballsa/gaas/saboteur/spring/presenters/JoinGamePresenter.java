package tw.waterballsa.gaas.saboteur.spring.presenters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.waterballsa.gaas.saboteur.app.usecases.JoinGameUsecase;
import tw.waterballsa.gaas.saboteur.domain.SaboteurGame;
import tw.waterballsa.gaas.saboteur.spring.presenters.views.PlayerView;

import java.util.List;

public class JoinGamePresenter implements JoinGameUsecase.Presenter {

    private JoinGameViewModel viewModel;

    @Override
    public void renderGame(SaboteurGame game) {
        List<PlayerView> players = game.getPlayers().stream().map(PlayerView::toView).toList();
        viewModel = new JoinGameViewModel(players);
    }

    public JoinGameViewModel present() {
        return viewModel;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinGameViewModel {
        private List<PlayerView> players;
    }
}
