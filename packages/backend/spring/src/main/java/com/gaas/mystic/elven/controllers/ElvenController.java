package com.gaas.mystic.elven.controllers;

import com.gaas.mystic.elven.presenters.CreateGamePresenter;
import com.gaas.mystic.elven.presenters.CreateGamePresenter.CreateGameViewModel;
import com.gaas.mystic.elven.presenters.FindGamePresenter;
import com.gaas.mystic.elven.presenters.FindGamePresenter.FindGameViewModel;
import com.gaas.mystic.elven.presenters.JoinGamePresenter;
import com.gaas.mystic.elven.presenters.JoinGamePresenter.JoinGameViewModel;
import com.gaas.mystic.elven.presenters.PlayCardPresenter;
import com.gaas.mystic.elven.usecases.CreateGameUsecase;
import com.gaas.mystic.elven.usecases.FindGameUsecase;
import com.gaas.mystic.elven.usecases.JoinGameUsecase;
import com.gaas.mystic.elven.usecases.PlayCardUsecase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static java.util.Objects.requireNonNullElse;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/games")
public class ElvenController {

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

        // play fix card
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
