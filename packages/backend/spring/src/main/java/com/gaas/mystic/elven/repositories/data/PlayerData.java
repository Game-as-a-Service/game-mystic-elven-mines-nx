package com.gaas.mystic.elven.repositories.data;

import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.domain.role.RoleCard;
import com.gaas.mystic.elven.domain.tool.Tool;
import com.gaas.mystic.elven.utils.StreamUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.gaas.mystic.elven.utils.StreamUtils.mapToList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerData {
    private String id;
    private String name;
    private List<ToolData> tools;
    private List<CardData> hands;
    private RoleCard role;

    public static PlayerData toData(Player player) {
        var tools = mapToList(player.getTools(), ToolData::toData);
        var hands = mapToList(player.getHands(), CardData::toData);
        return new PlayerData(player.getId(), player.getName(), tools, hands, player.getRoleCard());
    }

    public Player toDomain() {
        var tools = StreamUtils.mapToArray(this.tools, ToolData::toDomain, Tool[]::new);
        var hands = mapToList(this.hands, CardData::toDomain);
        return new Player(id, name, hands, role, tools);
    }
}
