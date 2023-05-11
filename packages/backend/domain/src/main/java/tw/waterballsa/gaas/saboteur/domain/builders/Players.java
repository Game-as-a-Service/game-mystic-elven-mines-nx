package tw.waterballsa.gaas.saboteur.domain.builders;

import lombok.experimental.UtilityClass;
import tw.waterballsa.gaas.saboteur.domain.Player;
import tw.waterballsa.gaas.saboteur.domain.Tool;
import tw.waterballsa.gaas.saboteur.domain.ToolName;

import java.util.ArrayList;

/**
 * @author johnny@waterballsa.tw
 */
@UtilityClass
public class Players {

    public static Player.PlayerBuilder defaultPlayerBuilder(String id) {
        return Player.builder()
                .id(id)
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
