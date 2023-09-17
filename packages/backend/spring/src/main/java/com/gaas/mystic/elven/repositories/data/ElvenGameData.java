package com.gaas.mystic.elven.repositories.data;

import com.gaas.mystic.elven.domain.ElvenGame;
import com.gaas.mystic.elven.exceptions.ElvenGameException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.gaas.mystic.elven.utils.StreamUtils.mapToList;
import static java.util.stream.IntStream.range;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElvenGameData {
    private String id;
    private List<PlayerData> players;
    private MazeData maze;
    private List<PathData> destinations = new ArrayList<>(ElvenGame.DESTINATION_CARDS_COUNT);

    public static ElvenGameData /*Data*/ toData(/*聚合根*/ ElvenGame elvenGame) {
        var maze = MazeData.toData(elvenGame.getMaze());
        var players = mapToList(elvenGame.getPlayers(), PlayerData::toData);
        var destinations = mapToList(elvenGame.getDestinations(), PathData::toData);
        return new ElvenGameData(elvenGame.getId(), players, maze, destinations);
    }

    public ElvenGame toDomain() {
        var players = mapToList(this.players, PlayerData::toDomain);
        ElvenGame game = new ElvenGame(id, players, maze.toDomain());
        game.setGoldInDestinationCard(getGoldenDestinationCardIndex());
        return game;
    }

    public int getGoldenDestinationCardIndex() {
        return range(0, destinations.size())
            .filter(i -> destinations.get(i).isGold())
            .findFirst()
            .orElseThrow(() -> new ElvenGameException("Impossible! There must be one golden destination card!"));
    }

}
