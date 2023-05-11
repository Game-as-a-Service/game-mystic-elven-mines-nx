package tw.waterballsa.gaas.saboteur.spring.repositories.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.waterballsa.gaas.saboteur.domain.Player;
import tw.waterballsa.gaas.saboteur.domain.Tool;

import java.util.List;

import static tw.waterballsa.gaas.saboteur.domain.commons.utils.StreamUtils.mapToArray;
import static tw.waterballsa.gaas.saboteur.domain.commons.utils.StreamUtils.mapToList;

/**
 * @author johnny@waterballsa.tw
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerData {
    private String id;
    private List<ToolData> tools;
    private List<CardData> hands;

    public Player toDomain() {
        var tools = mapToArray(this.tools, ToolData::toDomain, Tool[]::new);
        var hands = mapToList(this.hands, CardData::toDomain);
        return new Player(id, hands, tools);
    }

    public static PlayerData toData(Player player) {
        var tools = mapToList(player.getTools(), ToolData::toData);
        var hands = mapToList(player.getHands(), CardData::toData);
        return new PlayerData(player.getId(), tools, hands);
    }
}
