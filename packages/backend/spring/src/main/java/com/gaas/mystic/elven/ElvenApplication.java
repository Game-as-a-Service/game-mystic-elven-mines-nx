package com.gaas.mystic.elven;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ElvenApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ElvenApplication.class, args);
        context.getBean(SocketIOServer.class).start();
    }

}
