package com.gaas.mystic.elven.presenters.views;

import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.domain.tool.Tool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerView {
    private String playerName;
    private Integer cardNum;
    private List<Tool> tools;

    public static PlayerView toView(Player player) {
        return PlayerView.builder()
            .playerName(player.getName())
            .cardNum(player.getHands().size())
            .tools(Arrays.asList(player.getTools()))
            .build();
    }
}
