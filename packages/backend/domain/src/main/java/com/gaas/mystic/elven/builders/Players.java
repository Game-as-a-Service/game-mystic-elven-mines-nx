package com.gaas.mystic.elven.builders;

import com.gaas.mystic.elven.Player;
import com.gaas.mystic.elven.Tool;
import com.gaas.mystic.elven.ToolName;
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
                        new Tool(ToolName.MINE_CART, true),
                        new Tool(ToolName.LANTERN, true),
                        new Tool(ToolName.PICK, true)
                })
                .hands(new ArrayList<>());
    }

    public static Player  defaultPlayer(String id) {
        return defaultPlayerBuilder(id).build();
    }


}
