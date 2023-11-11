package com.gaas.mystic.elven.controllers;

import com.gaas.mystic.elven.presenters.*;
import com.gaas.mystic.elven.presenters.CreateGamePresenter.CreateGameViewModel;
import com.gaas.mystic.elven.presenters.FindPlayerPresenter.FindPlayerViewModel;
import com.gaas.mystic.elven.presenters.FindPlayersPresenter.FindPlayersViewModel;
import com.gaas.mystic.elven.presenters.JoinGamePresenter.JoinGameViewModel;
import com.gaas.mystic.elven.usecases.*;
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
    private final FindPlayersUsecase findPlayersUsecase;
    private final FindPlayerUsecase findPlayerUsecase;
    private final JoinGameUsecase joinGameUsecase;
    private final PlayCardUsecase playCardUsecase;
    private final StartGameUsecase startGameUsecase;

    @Operation(summary = "建立遊戲")
    @PostMapping
    public CreateGameViewModel createGame(@Valid @RequestBody CreateGameRequest request) {
        var presenter = new CreateGamePresenter();
        createGameUsecase.execute(request.toRequest(), presenter);
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

    @Operation(summary = "開始遊戲")
    @PostMapping("/{gameId}:start")
    public String startGame(@PathVariable String gameId) {
        startGameUsecase.execute(gameId);
        return "OK";
    }

    @Operation(summary = "查詢所有的玩家資訊")
    @GetMapping("/{gameId}/players")
    public FindPlayersViewModel findPlayers(@PathVariable String gameId) {
        var presenter = new FindPlayersPresenter();
        findPlayersUsecase.execute(gameId, presenter);
        return presenter.present();
    }

    @Operation(summary = "查詢玩家自己的資訊")
    @GetMapping("/{gameId}/player/{playerId}")
    public FindPlayerViewModel findPlayer(@PathVariable String gameId,
                                          @PathVariable String playerId) {
        var presenter = new FindPlayerPresenter();
        findPlayerUsecase.execute(new FindPlayerUsecase.Request(gameId, playerId), presenter);
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

@Data
@NoArgsConstructor
@AllArgsConstructor
class Response<T>{
    private boolean success;
    private T data;
    private String message;

    public static <T> Response<T> success(T data){
        return new Response<>(true, data, null);
    }

    public static <T> Response<T> fail(String message){
        return new Response<>(false, null, message);
    }
}
