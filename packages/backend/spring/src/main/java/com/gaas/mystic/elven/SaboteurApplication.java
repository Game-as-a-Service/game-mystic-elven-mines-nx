package com.gaas.mystic.elven;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SaboteurApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SaboteurApplication.class, args);
        context.getBean(SocketIOServer.class).start();
    }

}
