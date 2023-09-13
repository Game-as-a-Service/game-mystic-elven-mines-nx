package com.gaas.mystic.elven.builders;

import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.domain.tool.Tool;
import com.gaas.mystic.elven.domain.tool.ToolName;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;

/**
 * @author johnny@waterballsa.tw
 */
@UtilityClass
public class Players {

    public static Player.PlayerBuilder defaultPlayerBuilder(String id) {
        return Player.builder()
                .id(id)
                .name(id)
                .tools(new Tool[]{
                        new Tool(ToolName.FLYING_BOOTS, true),
                        new Tool(ToolName.HARP_OF_HARMONY, true),
                        new Tool(ToolName.STARLIGHT_WAND, true)
                })
                .hands(new ArrayList<>());
    }

    public static Player  defaultPlayer(String id) {
        return defaultPlayerBuilder(id).build();
    }


}
