package tw.waterballsa.gaas.saboteur.app.usecases;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import tw.waterballsa.gaas.saboteur.app.outport.SaboteurGameRepository;
import tw.waterballsa.gaas.saboteur.domain.Player;
import tw.waterballsa.gaas.saboteur.domain.SaboteurGame;
import tw.waterballsa.gaas.saboteur.domain.exceptions.NotFoundException;

import javax.inject.Named;

import static java.util.UUID.randomUUID;
import static tw.waterballsa.gaas.saboteur.domain.builders.Players.defaultPlayerBuilder;

@Named
@RequiredArgsConstructor
public class JoinGameUsecase {

    private final SaboteurGameRepository saboteurGameRepository;

    // createGame
    public void execute(Request request, Presenter presenter) {
        // 查
        SaboteurGame saboteurGame = saboteurGameRepository.findById(request.gameId)
            .orElseThrow(() -> new NotFoundException("Game not found"));

        // 改
        Player player = defaultPlayerBuilder(randomUUID().toString())
            .name(request.playerName)
            .build();
        saboteurGame.addPlayer(player);

        // 存
        var game = saboteurGameRepository.save(saboteurGame);

        // 推
        presenter.renderGame(game);
        presenter.renderPlayer(player);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String gameId;
        private String playerName;
    }

    public interface Presenter {
        void renderGame(SaboteurGame game);

        void renderPlayer(Player player);

    }
}

