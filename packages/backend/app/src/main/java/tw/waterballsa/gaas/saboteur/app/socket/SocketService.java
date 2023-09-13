package tw.waterballsa.gaas.saboteur.app.socket;


public interface SocketService {

    void sendMessageToGamePlayers(String gameId, SocketChannel channel, Object message);
}
