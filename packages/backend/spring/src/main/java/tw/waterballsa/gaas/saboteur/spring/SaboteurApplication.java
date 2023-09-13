package tw.waterballsa.gaas.saboteur.spring;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "tw.waterballsa.gaas.saboteur")
public class SaboteurApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SaboteurApplication.class, args);
        context.getBean(SocketIOServer.class).start();
    }

}
