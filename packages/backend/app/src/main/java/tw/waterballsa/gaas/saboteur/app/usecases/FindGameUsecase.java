package tw.waterballsa.gaas.saboteur.app.usecases;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import tw.waterballsa.gaas.saboteur.app.outport.SaboteurGameRepository;
import tw.waterballsa.gaas.saboteur.domain.Player;
import tw.waterballsa.gaas.saboteur.domain.SaboteurGame;

import javax.inject.Named;
import java.util.List;

import static java.util.UUID.randomUUID;
import static tw.waterballsa.gaas.saboteur.domain.builders.Players.defaultPlayerBuilder;

@Named
@RequiredArgsConstructor
public class FindGameUsecase {

    private final SaboteurGameRepository saboteurGameRepository;

    // createGame
    public void execute(String gameId, Presenter presenter) {
        // 查
        var game = saboteurGameRepository.findById(gameId).orElseThrow();

        // 推
        presenter.renderGame(game);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String gameId;
    }

    public interface Presenter {
        void renderGame(SaboteurGame game);
    }
}

