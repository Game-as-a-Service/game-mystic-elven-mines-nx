package tw.waterballsa.gaas.saboteur.spring.presenters.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.waterballsa.gaas.saboteur.domain.Player;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerView {
    private String id;
    private String name;

    public static PlayerView toView(Player player) {
        return new PlayerView(player.getId(), player.getName());
    }
}
