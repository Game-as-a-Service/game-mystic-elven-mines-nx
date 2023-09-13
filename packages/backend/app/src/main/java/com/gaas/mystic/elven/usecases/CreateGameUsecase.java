package com.gaas.mystic.elven.usecases;

import com.gaas.mystic.elven.domain.ElvenGame;
import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.outport.ElvenGameRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import java.util.List;

import static com.gaas.mystic.elven.builders.Players.defaultPlayerBuilder;
import static java.util.UUID.randomUUID;

@Named
@RequiredArgsConstructor
public class CreateGameUsecase {

    private final ElvenGameRepository elvenGameRepository;

    // createGame
    public void execute(Request request, Presenter presenter) {
        // 建
        Player host = defaultPlayerBuilder(randomUUID().toString())
            .name(request.host)
            .build();

        // 存
        var game = elvenGameRepository.save(new ElvenGame(List.of(host)));

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
        void renderGame(ElvenGame game);
    }
}

