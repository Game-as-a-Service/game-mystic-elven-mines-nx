package tw.waterballsa.gaas.saboteur.spring.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@Slf4j
@Component
@CrossOrigin
public class SocketIOConfig {

    @Value("${socket.host}")
    private String socketHost;
    @Value("${socket.port}")
    private int socketPort;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(socketHost);
        config.setPort(socketPort);
        SocketIOServer server = new SocketIOServer(config);
        server.start();
        return server;
    }

}
