package com.gaas.mystic.elven.presenters.views;

import com.gaas.mystic.elven.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerView {
    private String playerName;

    public static PlayerView toView(Player player) {
        return new PlayerView(player.getName());
    }
}
