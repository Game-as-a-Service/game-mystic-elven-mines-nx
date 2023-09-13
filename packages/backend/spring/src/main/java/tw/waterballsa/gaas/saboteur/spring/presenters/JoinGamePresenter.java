package tw.waterballsa.gaas.saboteur.spring.presenters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.waterballsa.gaas.saboteur.app.usecases.JoinGameUsecase;
import tw.waterballsa.gaas.saboteur.domain.Player;
import tw.waterballsa.gaas.saboteur.domain.SaboteurGame;
import tw.waterballsa.gaas.saboteur.spring.presenters.views.PlayerView;

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
