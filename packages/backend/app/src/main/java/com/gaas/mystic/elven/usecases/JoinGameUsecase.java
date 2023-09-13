package com.gaas.mystic.elven.usecases;

import com.gaas.mystic.elven.Player;
import com.gaas.mystic.elven.SaboteurGame;
import com.gaas.mystic.elven.builders.Players;
import com.gaas.mystic.elven.exceptions.NotFoundException;
import com.gaas.mystic.elven.outport.SaboteurGameRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;

import static java.util.UUID.randomUUID;

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
        Player player = Players.defaultPlayerBuilder(randomUUID().toString())
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

