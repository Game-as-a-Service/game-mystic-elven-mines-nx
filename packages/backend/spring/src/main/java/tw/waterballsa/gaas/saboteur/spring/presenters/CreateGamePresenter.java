package tw.waterballsa.gaas.saboteur.spring.presenters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.waterballsa.gaas.saboteur.app.usecases.CreateGameUsecase;
import tw.waterballsa.gaas.saboteur.domain.Player;
import tw.waterballsa.gaas.saboteur.domain.SaboteurGame;
import tw.waterballsa.gaas.saboteur.spring.presenters.views.PlayerView;

public class CreateGamePresenter implements CreateGameUsecase.Presenter {

    private CreateGameViewModel viewModel;

    @Override
    public void renderGame(SaboteurGame game) {
        Player host = game.getPlayers().get(0);
        viewModel = new CreateGameViewModel(game.getId(), PlayerView.toView(host));
    }

    public CreateGameViewModel present() {
        return viewModel;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateGameViewModel {
        private String gameId;
        private PlayerView player;
    }

}
