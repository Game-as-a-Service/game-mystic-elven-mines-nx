package tw.waterballsa.gaas.saboteur.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.waterballsa.gaas.saboteur.app.usecases.CreateGameUsecase;
import tw.waterballsa.gaas.saboteur.app.usecases.FindGameUsecase;
import tw.waterballsa.gaas.saboteur.app.usecases.JoinGameUsecase;
import tw.waterballsa.gaas.saboteur.app.usecases.PlayCardUsecase;
import tw.waterballsa.gaas.saboteur.spring.presenters.CreateGamePresenter;
import tw.waterballsa.gaas.saboteur.spring.presenters.CreateGamePresenter.CreateGameViewModel;
import tw.waterballsa.gaas.saboteur.spring.presenters.FindGamePresenter;
import tw.waterballsa.gaas.saboteur.spring.presenters.FindGamePresenter.FindGameViewModel;
import tw.waterballsa.gaas.saboteur.spring.presenters.JoinGamePresenter;
import tw.waterballsa.gaas.saboteur.spring.presenters.JoinGamePresenter.JoinGameViewModel;
import tw.waterballsa.gaas.saboteur.spring.presenters.PlayCardPresenter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static java.util.Objects.requireNonNullElse;
import static org.springframework.http.ResponseEntity.noContent;

/**
 * @author johnny@waterballsa.tw
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/games")
public class SaboteurController {

    private final CreateGameUsecase createGameUsecase;
    private final FindGameUsecase findGameUsecase;
    private final JoinGameUsecase joinGameUsecase;
    private final PlayCardUsecase playCardUsecase;

    @Operation(summary = "建立遊戲")
    @PostMapping
    public CreateGameViewModel createGame(@Valid @RequestBody CreateGameRequest request) {
        var presenter = new CreateGamePresenter();
        createGameUsecase.execute(request.toRequest(), presenter);
        return presenter.present();
    }

    @Operation(summary = "查詢遊戲")
    @GetMapping("/{gameId}")
    public FindGameViewModel findGame(@PathVariable String gameId) {
        var presenter = new FindGamePresenter();
        findGameUsecase.execute(gameId, presenter);
        return presenter.present();
    }

    @Operation(summary = "加入遊戲")
    @PostMapping("/{gameId}")
    public JoinGameViewModel joinGame(@PathVariable String gameId,
                                      @Valid @RequestBody JoinGameRequest request) {
        var presenter = new JoinGamePresenter();
        joinGameUsecase.execute(request.toRequest(gameId), presenter);
        return presenter.present();
    }

    @Operation(summary = "出牌 (未完成)")
    @PostMapping("/{gameId}:playCard")
    public ResponseEntity<?> playCard(@PathVariable String gameId,
                                      @Valid @RequestBody PlayCardRequest request) {
        var presenter = new PlayCardPresenter();
        playCardUsecase.execute(request.toRequest(gameId), presenter);
        return presenter.getViewModel()
            .map(ResponseEntity::ok)
            .orElseGet(noContent()::build);
    }

    @Value
    public static class PlayCardRequest {

        @NotBlank
        String playerId;

        int handIndex;

        @NotBlank
        String cardType;

        // play repair card
        String targetPlayerId;

        // play map card
        Integer destinationCardIndex;

        // play path card
        Integer row, col;

        Boolean flipped;

        public PlayCardUsecase.Request toRequest(String gameId) {
            return new PlayCardUsecase.Request(gameId, playerId, handIndex, cardType, targetPlayerId, destinationCardIndex,
                row, col, requireNonNullElse(flipped, false));
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateGameRequest {

        @NotBlank(message = "Player name is required")
        private String playerName;

        // toRequest
        public CreateGameUsecase.Request toRequest() {
            return new CreateGameUsecase.Request(playerName);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinGameRequest {
        @NotBlank(message = "Player name is required")
        private String playerName;

        // toRequest
        public JoinGameUsecase.Request toRequest(String gameId) {
            return new JoinGameUsecase.Request(gameId, playerName);
        }
    }

}
