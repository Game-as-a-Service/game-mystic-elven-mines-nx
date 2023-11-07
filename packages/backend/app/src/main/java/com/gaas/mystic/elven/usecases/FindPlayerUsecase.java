package com.gaas.mystic.elven.usecases;

import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.exceptions.NotFoundException;
import com.gaas.mystic.elven.outport.ElvenGameRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class FindPlayerUsecase {

    private final ElvenGameRepository elvenGameRepository;

    public void execute(Request request, Presenter presenter) {
        // 查
        var player = elvenGameRepository.findById(request.gameId)
            .orElseThrow(() -> new NotFoundException("Game not found"))
            .getPlayer(request.playerId);

        // 推
        presenter.renderPlayer(player);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String gameId;
        private String playerId;
    }

    public interface Presenter {
        void renderPlayer(Player player);
    }
}

