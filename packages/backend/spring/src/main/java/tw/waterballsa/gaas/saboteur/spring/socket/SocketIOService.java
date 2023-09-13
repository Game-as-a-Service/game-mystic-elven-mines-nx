package tw.waterballsa.gaas.saboteur.spring.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.waterballsa.gaas.saboteur.app.socket.SocketChannel;
import tw.waterballsa.gaas.saboteur.app.socket.SocketService;
import tw.waterballsa.gaas.saboteur.domain.exceptions.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.emptyList;
import static tw.waterballsa.gaas.saboteur.app.socket.SocketChannel.PLAYER_JOINED;
import static tw.waterballsa.gaas.saboteur.app.socket.SocketChannel.PLAYER_LEFT;

@Slf4j
@Component
public class SocketIOService implements SocketService {

    // Map<sessionId, client>
    private final Map<String, SocketIOClient> sessionIdToPlayer = new ConcurrentHashMap<>();

    // Map<gameId, clients>
    private final Map<String, List<SocketIOClient>> gameIdToPlayers = new ConcurrentHashMap<>();

    @Autowired
    public SocketIOService(SocketIOServer server) {
        SocketIONamespace namespace = server.addNamespace("/websocket");
        namespace.addConnectListener(onConnected());
        namespace.addDisconnectListener(onDisconnected());
    }

    private ConnectListener onConnected() {
        return client -> {
            String session = client.getSessionId().toString();
            Optional<String> gameIdOpt = getParam(client, "gameId");
            Optional<String> playerIdOpt = getParam(client, "playerId");
            if(gameIdOpt.isEmpty() || playerIdOpt.isEmpty()) {
                log.warn("connect fail. sessionId: {}.", session);
                client.disconnect();
                return;
            }
            String gameId = gameIdOpt.get();
            String playerId = playerIdOpt.get();
            log.info("connect sessionId: {}. gameId: {}. playerId: {}.", session, gameId, playerId);
            // send PLAYER_JOINED event to other clients
            sendMessageToGamePlayers(gameId, PLAYER_JOINED, playerId);
            // save client Map
            sessionIdToPlayer.put(session, client);
            gameIdToPlayers.computeIfAbsent(gameId, k -> new ArrayList<>()).add(client);
        };
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
            if(players.isEmpty()) {
                gameIdToPlayers.remove(gameId);
            }
            // send PLAYER_LEFT event to other clients
            sendMessageToGamePlayers(gameId, PLAYER_LEFT, playerId);
        };
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
    public void sendMessageToGamePlayers(String gameId, SocketChannel channel, String message) {
        gameIdToPlayers.getOrDefault(gameId, emptyList())
            .forEach(c -> c.sendEvent(channel.name(), message));
    }

}

