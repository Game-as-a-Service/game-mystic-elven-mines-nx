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
public class CreateGameUsecase {

    private final SaboteurGameRepository saboteurGameRepository;

    // createGame
    public void execute(Request request, Presenter presenter) {
        // 建
        Player host = defaultPlayerBuilder(randomUUID().toString())
            .name(request.host)
            .build();

        // 存
        var game = saboteurGameRepository.save(new SaboteurGame(List.of(host)));

        // 推
        presenter.renderGame(game);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String host;
    }

    public interface Presenter {
        void renderGame(SaboteurGame game);
    }
}

