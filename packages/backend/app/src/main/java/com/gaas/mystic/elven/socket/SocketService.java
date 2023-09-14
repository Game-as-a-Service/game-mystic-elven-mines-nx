package com.gaas.mystic.elven.socket;


public interface SocketService {

    void sendMessageToGamePlayers(String gameId, SocketChannel channel, Object message);
}
