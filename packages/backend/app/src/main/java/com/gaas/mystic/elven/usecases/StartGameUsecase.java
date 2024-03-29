package com.gaas.mystic.elven.usecases;

import com.gaas.mystic.elven.exceptions.NotFoundException;
import com.gaas.mystic.elven.outport.ElvenGameRepository;
import com.gaas.mystic.elven.socket.SocketChannel;
import com.gaas.mystic.elven.socket.SocketService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class StartGameUsecase {

    private final ElvenGameRepository elvenGameRepository;
    private final SocketService socketService;

    public void execute(String gameId) {
        // 查
        var game = elvenGameRepository.findById(gameId)
            .orElseThrow(() -> new NotFoundException("Game not found"));

        // 改
        game.startGame();

        // 存
        elvenGameRepository.save(game);

        // 推
        StartGameMessage message = new StartGameMessage(game.getPlayers().get(0).getName());
        socketService.sendMessageToGamePlayers(gameId, SocketChannel.GAME_STARTED, message);
    }
}

@Data
@AllArgsConstructor
class StartGameMessage {
    String nextPlayerName;
}
