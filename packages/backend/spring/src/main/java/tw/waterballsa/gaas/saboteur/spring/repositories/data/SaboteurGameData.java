package tw.waterballsa.gaas.saboteur.spring.repositories.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.waterballsa.gaas.saboteur.domain.SaboteurGame;
import tw.waterballsa.gaas.saboteur.domain.exceptions.SaboteurGameException;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.IntStream.range;
import static tw.waterballsa.gaas.saboteur.domain.SaboteurGame.DESTINATION_CARDS_COUNT;
import static tw.waterballsa.gaas.saboteur.domain.commons.utils.StreamUtils.mapToList;

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
    private List<PathData> destinations = new ArrayList<>(DESTINATION_CARDS_COUNT);

    public SaboteurGame toDomain() {
        var players = mapToList(this.players, PlayerData::toDomain);
        SaboteurGame game = new SaboteurGame(id, players, maze.toDomain());
        game.setGoldInDestinationCard(getGoldenDestinationCardIndex());
        return game;
    }

    public static SaboteurGameData /*Data*/ toData(/*聚合根*/ SaboteurGame saboteurGame) {
        var maze = MazeData.toData(saboteurGame.getMaze());
        var players = mapToList(saboteurGame.getPlayers(), PlayerData::toData);
        var destinations = mapToList(saboteurGame.getDestinations(), PathData::toData);
        return new SaboteurGameData(saboteurGame.getId(), players, maze, destinations);
    }

    public int getGoldenDestinationCardIndex() {
        return range(0, destinations.size())
                .filter(i -> destinations.get(i).isGold())
                .findFirst()
                .orElseThrow(() -> new SaboteurGameException("Impossible! There must be one golden destination card!"));
    }

}
