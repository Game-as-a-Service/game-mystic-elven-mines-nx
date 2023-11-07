package com.gaas.mystic.elven.usecases;

import com.gaas.mystic.elven.domain.ElvenGame;
import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.builders.Players;
import com.gaas.mystic.elven.exceptions.NotFoundException;
import com.gaas.mystic.elven.outport.ElvenGameRepository;
import lombok.*;

import javax.inject.Named;

import static java.util.UUID.randomUUID;

@Named
@RequiredArgsConstructor
public class JoinGameUsecase {

    private final ElvenGameRepository elvenGameRepository;

    // createGame
    public void execute(Request request, Presenter presenter) {
        // 查
        ElvenGame elvenGame = elvenGameRepository.findById(request.gameId)
            .orElseThrow(() -> new NotFoundException("Game not found"));

        // 改
        Player player = Players.defaultPlayerBuilder(randomUUID().toString())
            .name(request.playerName)
            .build();
        elvenGame.addPlayer(player);

        // 存
        var game = elvenGameRepository.save(elvenGame);

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
        void renderGame(ElvenGame game);

        void renderPlayer(Player player);

    }
}

