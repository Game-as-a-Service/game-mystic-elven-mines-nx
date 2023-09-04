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

import static tw.waterballsa.gaas.saboteur.app.socket.SocketChannel.PLAYER_JOINED;
import static tw.waterballsa.gaas.saboteur.app.socket.SocketChannel.PLAYER_LEFT;

@Slf4j
@Component
public class SocketIOService implements SocketService {

    // Map<sessionId, client>
    private final Map<String, SocketIOClient> clientBySession = new ConcurrentHashMap<>();

    // Map<gameId, clients>
    private final Map<String, List<SocketIOClient>> clientsByGame = new ConcurrentHashMap<>();

    @Autowired
    public SocketIOService(SocketIOServer server) {
        SocketIONamespace namespace = server.addNamespace("/websocket");
        namespace.addConnectListener(onConnected());
        namespace.addDisconnectListener(onDisconnected());
    }

    private ConnectListener onConnected() {
        return client -> {
            String session = client.getSessionId().toString();
            String gameId = getGameId(client).orElseThrow(() -> new NotFoundException("gameId is required."));
            String userId = getUserId(client).orElseThrow(() -> new NotFoundException("userId is required."));
            log.info("connect sessionId: {}. gameId: {}. userId: {}.", session, gameId, userId);
            // send PLAYER_JOINED event to other clients
            sendMessageByGameId(gameId, PLAYER_JOINED, userId);
            // save client Map
            clientBySession.put(session, client);
            clientsByGame.computeIfAbsent(gameId, k -> new ArrayList<>()).add(client);
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            String session = client.getSessionId().toString();
            SocketIOClient clientData = clientBySession.get(session);
            String gameId = getGameId(clientData).orElseThrow(() -> new NotFoundException("gameId is required."));
            String userId = getUserId(clientData).orElseThrow(() -> new NotFoundException("gameId is required."));
            log.info("disconnect sessionId: {}. gameId: {}. userId: {}.", session, gameId, userId);
            client.disconnect();
            // remove client Map
            clientBySession.remove(session);
            clientsByGame.get(gameId).remove(client);
            if(clientsByGame.get(gameId).isEmpty()) {
                clientsByGame.remove(gameId);
            }
            // send PLAYER_LEFT event to other clients
            sendMessageByGameId(gameId, PLAYER_LEFT, userId);
        };
    }

    private Optional<String> getUserId(SocketIOClient client) {
        Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
        return Optional.ofNullable(params.get("userId")).map(id -> id.get(0));
    }

    private Optional<String> getGameId(SocketIOClient client) {
        Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
        return Optional.ofNullable(params.get("gameId")).map(id -> id.get(0));
    }

    @Override
    public void sendMessageByGameId(String gameId, SocketChannel channel, String message) {
        clientsByGame.getOrDefault(gameId, Collections.emptyList())
            .forEach(c -> c.sendEvent(channel.name(), message));
    }

}

