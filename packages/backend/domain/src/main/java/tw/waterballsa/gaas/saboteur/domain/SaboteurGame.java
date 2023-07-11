package tw.waterballsa.gaas.saboteur.domain;

import tw.waterballsa.gaas.saboteur.domain.events.DomainEvent;
import tw.waterballsa.gaas.saboteur.domain.exceptions.SaboteurGameException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.lang.String.format;
import static java.util.Objects.checkIndex;
import static java.util.Objects.requireNonNullElseGet;
import static java.util.UUID.randomUUID;
import static tw.waterballsa.gaas.saboteur.domain.commons.utils.StreamUtils.findFirst;

/**
 * Controller -> Service -> Data (聽 ORM 的話) (Repository/data access layer)
 *
 * @author johnny@waterballsa.tw
 */
public class SaboteurGame /*他才是老大 <-- Domain Driven Design*/ {
    public static final int DESTINATION_CARDS_COUNT = 3;
    private static final Random RANDOM = new Random();
    // @Id (UUID)
    private String id;
    // one-to-many relationship
    private final List<Player> players;
    private final Maze maze;

    private final List<Destination> destinations = new ArrayList<>(DESTINATION_CARDS_COUNT);

    public SaboteurGame(List<Player> players) {
        this(randomUUID().toString(), players);
    }

    public SaboteurGame(String id, List<Player> players) {
        this(id, players, new Maze());
    }

    public SaboteurGame(String id, List<Player> players, Maze maze) {
        this.id = id;
        this.players = requireNonNullElseGet(players, Collections::emptyList);

        setupDestinationCards(RANDOM.nextInt(DESTINATION_CARDS_COUNT));
        this.maze = maze;
    }

    public void startGame() {
        // check players count
        if (players.size() < DESTINATION_CARDS_COUNT || players.size() > 10) {
            throw new IllegalArgumentException("玩家人數必須介於 3 ~ 10 之間");
        }
        // TODO: initialize game
    }


    // 1. 封裝變動之處 (開出 各個 Card 類別 -> 來封裝各個行為）
    // 2. 萃取相同行為 (萃取出共同的部分到介面中）so sad
    // 3. 依賴抽象 (依賴抽象的 Card)

    public List<DomainEvent> playCard(Card.Parameters parameters) {
        parameters.player = getPlayer(parameters.playerId);
        parameters.card = parameters.player.getHandCard(parameters.handCardIndex);
        parameters.game = this;
        var events = parameters.card.execute(parameters);
        parameters.player.playCard(parameters.handCardIndex);
        return events;
    }

    public boolean isGameOver() {
        return players.stream()
                .allMatch(p -> p.getHands().isEmpty());
    }

    public Player getPlayer(String id) {
        return findFirst(players, p -> p.getId().equals(id))
                .orElseThrow(() -> new IllegalArgumentException(format("Player %s not found.", id)));
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGoldInDestinationCard(int destinationCardIndex) {
        setupDestinationCards(destinationCardIndex);
    }

    private void setupDestinationCards(int goldenDestinationCardIndex) {
        destinations.clear();
        for (int i = 0; i < DESTINATION_CARDS_COUNT; i++) {
            destinations.add(new Destination(8, 2 * i, i == goldenDestinationCardIndex));
        }
    }

    public String getId() {
        return id;
    }

    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public List<Destination> getDestinations() {
        return List.copyOf(destinations);
    }

    public Maze getMaze() {
        return maze;
    }

    public Destination getDestinationCardByIndex(int destinationCardIndex) {
        checkIndex(destinationCardIndex, destinations.size());
        return destinations.get(destinationCardIndex);
    }

    public Path getPath(int row, int col) {
        return maze.getPath(row, col)
                .orElseThrow(() -> new SaboteurGameException(format("Path not found. (row: %d, col: %d)", row, col)));
    }

    public void addPlayer(Player player) {
        players.add(player);
    }
}
