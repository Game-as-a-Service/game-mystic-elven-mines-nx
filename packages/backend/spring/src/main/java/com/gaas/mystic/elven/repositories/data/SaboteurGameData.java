package com.gaas.mystic.elven.repositories.data;

import com.gaas.mystic.elven.SaboteurGame;
import com.gaas.mystic.elven.exceptions.SaboteurGameException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.gaas.mystic.elven.utils.StreamUtils.mapToList;
import static java.util.stream.IntStream.range;

/**
 * @author johnny@waterballsa.tw
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaboteurGameData {
    private String id;
    private List<PlayerData> players;
    private MazeData maze;
    private List<PathData> destinations = new ArrayList<>(SaboteurGame.DESTINATION_CARDS_COUNT);

    public static SaboteurGameData /*Data*/ toData(/*聚合根*/ SaboteurGame saboteurGame) {
        var maze = MazeData.toData(saboteurGame.getMaze());
        var players = mapToList(saboteurGame.getPlayers(), PlayerData::toData);
        var destinations = mapToList(saboteurGame.getDestinations(), PathData::toData);
        return new SaboteurGameData(saboteurGame.getId(), players, maze, destinations);
    }

    public SaboteurGame toDomain() {
        var players = mapToList(this.players, PlayerData::toDomain);
        SaboteurGame game = new SaboteurGame(id, players, maze.toDomain());
        game.setGoldInDestinationCard(getGoldenDestinationCardIndex());
        return game;
    }

    public int getGoldenDestinationCardIndex() {
        return range(0, destinations.size())
            .filter(i -> destinations.get(i).isGold())
            .findFirst()
            .orElseThrow(() -> new SaboteurGameException("Impossible! There must be one golden destination card!"));
    }

}
