package tw.waterballsa.gaas.saboteur.app.usecases;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import tw.waterballsa.gaas.saboteur.app.outport.SaboteurGameRepository;
import tw.waterballsa.gaas.saboteur.domain.SaboteurGame;
import tw.waterballsa.gaas.saboteur.domain.exceptions.NotFoundException;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class FindGameUsecase {

    private final SaboteurGameRepository saboteurGameRepository;

    public void execute(String gameId, Presenter presenter) {
        // 查
        var game = saboteurGameRepository.findById(gameId)
            .orElseThrow(() -> new NotFoundException("Game not found"));

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

