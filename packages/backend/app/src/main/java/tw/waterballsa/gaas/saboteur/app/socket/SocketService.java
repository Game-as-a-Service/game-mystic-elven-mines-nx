package tw.waterballsa.gaas.saboteur.app.socket;



public interface SocketService {

    void sendMessageByGameId(String gameId, SocketChannel channel, String message);
}
