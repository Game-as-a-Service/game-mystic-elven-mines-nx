package com.gaas.mystic.elven.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.gaas.mystic.elven.domain.ElvenGame;
import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.exceptions.NotFoundException;
import com.gaas.mystic.elven.outport.ElvenGameRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.emptyList;

@Slf4j
@Component
public class SocketIOService implements SocketService {

    // Map<sessionId, client>
    private final Map<String, SocketIOClient> sessionIdToPlayer = new ConcurrentHashMap<>();

    // Map<gameId, clients>
    private final Map<String, List<SocketIOClient>> gameIdToPlayers = new ConcurrentHashMap<>();

    private final ElvenGameRepository elvenGameRepository;

    @Autowired
    public SocketIOService(SocketIOServer server, ElvenGameRepository elvenGameRepository) {
        this.elvenGameRepository = elvenGameRepository;
        SocketIONamespace namespace = server.addNamespace("/websocket");
        namespace.addConnectListener(onConnected());
        namespace.addDisconnectListener(onDisconnected());
    }

    private ConnectListener onConnected() {
        return client -> {
            String session = client.getSessionId().toString();
            Optional<String> gameIdOpt = getParam(client, "gameId");
            Optional<String> playerIdOpt = getParam(client, "playerId");
            if (gameIdOpt.isEmpty() || playerIdOpt.isEmpty()) {
                log.warn("connect fail. sessionId: {}.", session);
                client.disconnect();
                return;
            }
            String gameId = gameIdOpt.get();
            String playerId = playerIdOpt.get();
            log.info("connect sessionId: {}. gameId: {}. playerId: {}.", session, gameId, playerId);
            // get FindPlayersViewModel
            PlayerJoinLeftViewModel message = getViewModel(gameId, playerId);
            // send PLAYER_JOINED event to other clients
            sendMessageToGamePlayers(gameId, SocketChannel.PLAYER_JOINED, message);
            // save client Map
            sessionIdToPlayer.put(session, client);
            gameIdToPlayers.computeIfAbsent(gameId, k -> new ArrayList<>()).add(client);
        };
    }

    private PlayerJoinLeftViewModel getViewModel(String gameId, String playerId) {
        ElvenGame game = elvenGameRepository.findById(gameId)
            .orElseThrow(() -> new NotFoundException("Game not found"));
        String playerName = game.getPlayer(playerId).getName();
        List<String> players = game.getPlayers().stream().map(Player::getName).toList();
        return new PlayerJoinLeftViewModel(playerName, players);
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            String sessionId = client.getSessionId().toString();
            SocketIOClient clientData = sessionIdToPlayer.get(sessionId);
            String gameId = getParam(clientData, "gameId")
                .orElseThrow(() -> new NotFoundException("gameId is required."));
            String playerId = getParam(clientData, "playerId")
                .orElseThrow(() -> new NotFoundException("playerId is required."));
            log.info("disconnect sessionId: {}. gameId: {}. playerId: {}.", sessionId, gameId, playerId);
            client.disconnect();
            // remove client Map
            sessionIdToPlayer.remove(sessionId);
            List<SocketIOClient> players = gameIdToPlayers.get(gameId);
            players.remove(client);
            if (players.isEmpty()) {
                gameIdToPlayers.remove(gameId);
            }
            // TODO remove player use case ?
            PlayerJoinLeftViewModel message = removeAndViewModel(gameId, playerId);
            // send PLAYER_LEFT event to other clients
            sendMessageToGamePlayers(gameId, SocketChannel.PLAYER_LEFT, message);
        };
    }

    private PlayerJoinLeftViewModel removeAndViewModel(String gameId, String playerId) {
        // get name
        ElvenGame game = elvenGameRepository.findById(gameId)
            .orElseThrow(() -> new NotFoundException("Game not found"));
        Player player = game.getPlayer(playerId);
        String playerName = player.getName();
        // remove player
        game.removePlayer(player);
        // save
        elvenGameRepository.save(game);
        // get FindPlayersViewModel
        List<String> players = game.getPlayers().stream().map(Player::getName).toList();
        return new PlayerJoinLeftViewModel(playerName, players);
    }

    private Optional<String> getParam(SocketIOClient client, String param) {
        return client.getHandshakeData()
            .getUrlParams()
            .getOrDefault(param, emptyList())
            .stream()
            .findFirst();
    }

    private Optional<String> getGameId(SocketIOClient client) {
        return client.getHandshakeData()
            .getUrlParams()
            .getOrDefault("gameId", emptyList())
            .stream()
            .findFirst();
    }

    @Override
    public void sendMessageToGamePlayers(String gameId, SocketChannel channel, Object message) {
        gameIdToPlayers.getOrDefault(gameId, emptyList())
            .forEach(c -> c.sendEvent(channel.name(), message));
    }

    // get socket clients
    public SocketIOClient getSocketIOClient(String gameId) {
        return gameIdToPlayers.get(gameId).get(0);
    }

}

@Data
class PlayerJoinLeftViewModel {

    private final String playerName;
    private final List<String> players;

    public PlayerJoinLeftViewModel(String playerName, List<String> players) {
        this.playerName = playerName;
        this.players = players;
    }
}
