package com.gaas.mystic.elven.usecases;

import com.gaas.mystic.elven.domain.ElvenGame;
import com.gaas.mystic.elven.domain.GoalCard;
import com.gaas.mystic.elven.domain.card.*;
import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.events.DomainEvent;
import com.gaas.mystic.elven.exceptions.ElvenGameException;
import com.gaas.mystic.elven.message.CardMessage;
import com.gaas.mystic.elven.message.GameNextPlayerMessage;
import com.gaas.mystic.elven.message.PlayerCardPlacedMessage;
import com.gaas.mystic.elven.outport.ElvenGameRepository;
import com.gaas.mystic.elven.socket.SocketChannel;
import com.gaas.mystic.elven.socket.SocketService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Named
@RequiredArgsConstructor
public class PlayCardUsecase {

    private final ElvenGameRepository elvenGameRepository;
    private final SocketService socketService;

    public void execute(Request request, Presenter presenter) {
        // 查
        ElvenGame game = findGame(request);
        Card handCard = game.getPlayer(request.playerId).getHandCard(request.handIndex);

        // 改
        List<DomainEvent> events = switch (CardType.valueOf(request.cardType)) {
            case MAP ->
                game.playCard(new MapCard.Parameters(request.playerId, request.handIndex, request.destinationCardIndex));
            case PATH ->
                game.playCard(new PathCard.Parameters(request.playerId, request.handIndex, request.row, request.col, request.flipped));
            case FIX ->
                game.playCard(new FixCard.Parameters(request.playerId, request.handIndex, request.targetPlayerId));
            case BROKEN ->
                game.playCard(new BrokenCard.Parameters(request.playerId, request.handIndex, request.targetPlayerId));
            case ROCKFALL ->
                game.playCard(new RockFallCard.Parameters(request.playerId, request.handIndex, request.row, request.col));
        };

        // TDD: write test  -> write just enough code to pass the test -> refactor

        // 存
        elvenGameRepository.save(game);

        // 推
        presenter.present(events);
        sendPlayerCardPlacedMessage(game, handCard, request);
        sendGameNextPlayerMessage(game);
    }

    private ElvenGame findGame(Request request) {
        String gameId = request.getGameId();
        return elvenGameRepository.findById(gameId)
            .orElseThrow(() -> new ElvenGameException(format("Game {%s} not found.", gameId)));
    }

    private void sendPlayerCardPlacedMessage(ElvenGame game, Card handCard, Request request) {
        // parameters
        String playerName = game.getPlayer(request.playerId).getName();
        String cardName = handCard.getName();
        Boolean isGoal = Optional.ofNullable(request.destinationCardIndex)
            .map(i -> game.getDestinations().get(i))
            .map(GoalCard::isGoal)
            .orElse(null);
        String targetPlayerName = Optional.ofNullable(request.targetPlayerId)
            .map(game::getPlayer)
            .map(Player::getName)
            .orElse(null);
        // message
        PlayerCardPlacedMessage message = new PlayerCardPlacedMessage(request.cardType, playerName,
            new CardMessage(request.row, request.col, cardName, isGoal, request.flipped, targetPlayerName));
        // send
        socketService.sendMessageToGamePlayers(game.getId(), SocketChannel.PLAYER_CARD_PLACED, message);
    }

    private void sendGameNextPlayerMessage(ElvenGame game) {
        GameNextPlayerMessage message = new GameNextPlayerMessage(game.getPlayers().get(0).getName());
        socketService.sendMessageToGamePlayers(game.getId(), SocketChannel.GAME_NEXT_PLAYER, message);
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


