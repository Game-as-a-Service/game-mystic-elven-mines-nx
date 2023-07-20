package tw.waterballsa.gaas.saboteur.spring.controllers;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.waterballsa.gaas.saboteur.app.usecases.CreateGameUsecase;
import tw.waterballsa.gaas.saboteur.app.usecases.FindGameUsecase;
import tw.waterballsa.gaas.saboteur.app.usecases.JoinGameUsecase;
import tw.waterballsa.gaas.saboteur.app.usecases.PlayCardUsecase;
import tw.waterballsa.gaas.saboteur.domain.exceptions.IllegalRequestException;
import tw.waterballsa.gaas.saboteur.spring.presenters.CreateGamePresenter;
import tw.waterballsa.gaas.saboteur.spring.presenters.CreateGamePresenter.CreateGameViewModel;
import tw.waterballsa.gaas.saboteur.spring.presenters.FindGamePresenter;
import tw.waterballsa.gaas.saboteur.spring.presenters.FindGamePresenter.FindGameViewModel;
import tw.waterballsa.gaas.saboteur.spring.presenters.JoinGamePresenter;
import tw.waterballsa.gaas.saboteur.spring.presenters.JoinGamePresenter.JoinGameViewModel;
import tw.waterballsa.gaas.saboteur.spring.presenters.PlayCardPresenter;

import javax.validation.constraints.NotBlank;
import java.util.stream.Stream;

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

    @PostMapping
    public CreateGameViewModel createGame(@RequestBody CreateGameRequest request) {
        var presenter = new CreateGamePresenter();
        createGameUsecase.execute(request.toRequest(), presenter);
        return presenter.present();
    }

    @GetMapping("/{gameId}")
    public FindGameViewModel findGame(@PathVariable String gameId) {
        var presenter = new FindGamePresenter();
        findGameUsecase.execute(gameId, presenter);
        return presenter.present();
    }

    @PostMapping("/{gameId}")
    public JoinGameViewModel joinGame(@PathVariable String gameId,
                                      @RequestBody JoinGameRequest request) {
        var presenter = new JoinGamePresenter();
        joinGameUsecase.execute(gameId, request.toRequest(), presenter);
        return presenter.present();
    }

    @PostMapping("/{gameId}:playCard")
    public ResponseEntity<?> playCard(@PathVariable String gameId,
                                      @RequestBody PlayCardRequest request) {
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

        @NotBlank
        private String host;

        // toRequest
        public CreateGameUsecase.Request toRequest() {
            return Stream.of(host)
                .filter(StringUtils::isNotBlank).findFirst()
                .map(CreateGameUsecase.Request::new)
                .orElseThrow(() -> new IllegalRequestException("Host name is required"));
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinGameRequest {

        @NotBlank
        private String name;

        // toRequest
        public JoinGameUsecase.Request toRequest() {
            return Stream.of(name)
                .filter(StringUtils::isNotBlank).findFirst()
                .map(JoinGameUsecase.Request::new)
                .orElseThrow(() -> new IllegalRequestException("Host name is required"));
        }
    }

}
