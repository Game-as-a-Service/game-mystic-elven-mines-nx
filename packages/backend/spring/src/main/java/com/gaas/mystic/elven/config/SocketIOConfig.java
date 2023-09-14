package com.gaas.mystic.elven.config;


import com.corundumstudio.socketio.SocketIOServer;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

@ConfigurationProperties(prefix = "socket")
@Configuration
@CrossOrigin
@Data
public class SocketIOConfig {

    private String host;
    private int port;

    @Bean
    public SocketIOServer socketIOServer() {
        var config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);
        return new SocketIOServer(config);
    }

}
