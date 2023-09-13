package com.gaas.mystic.elven.usecases;

import com.gaas.mystic.elven.*;
import com.gaas.mystic.elven.events.DomainEvent;
import com.gaas.mystic.elven.exceptions.SaboteurGameException;
import com.gaas.mystic.elven.outport.SaboteurGameRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import java.util.List;

import static java.lang.String.format;

/**
 * @author johnny@waterballsa.tw
 */
@Named
@RequiredArgsConstructor
public class PlayCardUsecase {

    private final SaboteurGameRepository saboteurGameRepository;

    public void execute(Request request, Presenter presenter) {
        // 查
        SaboteurGame game = findGame(request);

        List<DomainEvent> events = switch (CardType.valueOf(request.cardType)) {
            case MAP ->
                    game.playCard(new MapCard.Parameters(request.playerId, request.handIndex, request.destinationCardIndex));
            case PATH ->
                    game.playCard(new PathCard.Parameters(request.playerId, request.handIndex, request.row, request.col, request.flipped));
            case REPAIR ->
                    game.playCard(new Repair.Parameters(request.playerId, request.handIndex, request.targetPlayerId));
            case SABOTAGE ->
                    game.playCard(new Sabotage.Parameters(request.playerId, request.handIndex, request.targetPlayerId));
            case ROCKFALL ->
                    game.playCard(new RockFall.Parameters(request.playerId, request.handIndex, request.row, request.col));
        };

        // TDD: write test  -> write just enough code to pass the test -> refactor

        // 存
        saboteurGameRepository.save(game);

        // 推
        presenter.present(events);
    }

    enum CardType {
        MAP,
        PATH,
        REPAIR,
        SABOTAGE,
        ROCKFALL
    }

    private SaboteurGame findGame(Request request) {
        String gameId = request.getGameId();
        return saboteurGameRepository.findById(gameId)
                .orElseThrow(() -> new SaboteurGameException(format("Game {%s} not found.", gameId)));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String gameId;
        private String playerId;
        private int handIndex;
        private String cardType;
        private String targetPlayerId;
        private Integer destinationCardIndex;
        private Integer row, col;
        private Boolean flipped;
    }

    public interface Presenter {
        void present(List<DomainEvent> events);
    }


}
