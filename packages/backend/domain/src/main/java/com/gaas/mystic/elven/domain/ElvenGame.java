package com.gaas.mystic.elven.domain;

import com.gaas.mystic.elven.domain.card.Card;
import com.gaas.mystic.elven.domain.card.PathCard;
import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.events.DomainEvent;
import com.gaas.mystic.elven.exceptions.ElvenGameException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.gaas.mystic.elven.utils.StreamUtils.findFirst;
import static java.lang.String.format;
import static java.util.Objects.checkIndex;
import static java.util.Objects.requireNonNullElseGet;
import static java.util.UUID.randomUUID;

/**
 * Elven Game
 * Controller -> Service -> Data (聽 ORM 的話) (Repository/data access layer)
 * ElvenGame 他才是老大 <-- Domain Driven Design
 */
public class ElvenGame {
    public static final int DESTINATION_CARDS_COUNT = 3;
    private static final Random RANDOM = new Random();
    // @Id (UUID)
    private String id;
    // one-to-many relationship
    private final List<Player> players;
    private final Maze maze;

    private final List<GoalCard> goalCards = new ArrayList<>(DESTINATION_CARDS_COUNT);

    private final List<Card> deck = new ArrayList<>();

    public ElvenGame(List<Player> players) {
        this(randomUUID().toString(), players);
    }

    public ElvenGame(String id, List<Player> players) {
        this(id, players, new Maze());
    }

    public ElvenGame(String id, List<Player> players, Maze maze) {
        this.id = id;
        this.players = requireNonNullElseGet(players, Collections::emptyList);

        setupDestinationCards(RANDOM.nextInt(DESTINATION_CARDS_COUNT));
        this.maze = maze;
    }

    public void startGame() {
        checkStartGameCondition();
        initializeDeck();
        dealCards();
    }

    private void checkStartGameCondition() {
        // check players count
        if (players.size() < 3 || players.size() > 10) {
            throw new IllegalArgumentException("玩家人數必須介於 3 ~ 10 之間");
        }
    }

    private void initializeDeck() {
        // clear deck
        deck.clear();
        // add cards
        for (int i = 0; i < 5; i++) {
            deck.add(PathCard.cross());
            deck.add(PathCard.rightCurve());
            deck.add(PathCard.horizontalT());
            deck.add(PathCard.straightT());
        }
        for (int i = 0; i < 4; i++) {
            deck.add(PathCard.leftCurve());
            deck.add(PathCard.horizontal());
        }
        for (int i = 0; i < 3; i++) {
            deck.add(PathCard.straight());
        }
        deck.add(PathCard.deadEndCross());
        deck.add(PathCard.deadEndLeftCurve());
        deck.add(PathCard.deadEndRightCurve());
        deck.add(PathCard.deadEndHorizontal1());
        deck.add(PathCard.deadEndHorizontal2());
        deck.add(PathCard.deadEndStraight1());
        deck.add(PathCard.deadEndStraight2());
        deck.add(PathCard.deadEndHorizontalT());
        deck.add(PathCard.deadEndStraightT());
        // shuffle
        Collections.shuffle(deck);
    }

    private void dealCards() {
        // card number
        int cardNumber = 5;
        // deal cards
        for (Player player : players) {
            for (int i = 0; i < cardNumber; i++) {
                player.addHandCard(deck.remove(0));
            }
        }
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
        goalCards.clear();
        for (int i = 0; i < DESTINATION_CARDS_COUNT; i++) {
            goalCards.add(new GoalCard(8, 2 * i, i == goldenDestinationCardIndex));
        }
    }

    public String getId() {
        return id;
    }

    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public List<GoalCard> getDestinations() {
        return List.copyOf(goalCards);
    }

    public Maze getMaze() {
        return maze;
    }

    public GoalCard getDestinationCardByIndex(int destinationCardIndex) {
        checkIndex(destinationCardIndex, goalCards.size());
        return goalCards.get(destinationCardIndex);
    }

    public Path getPath(int row, int col) {
        return maze.getPath(row, col)
            .orElseThrow(() -> new ElvenGameException(format("Path not found. (row: %d, col: %d)", row, col)));
    }

    public void addPlayer(Player player) {
        checkPlayersNumber();
        checkPlayerName(player.getName());
        players.add(player);
    }

    private void checkPlayersNumber() {
        if (players.size() >= 10) {
            throw new ElvenGameException("玩家人數已達上限 10 人");
        }
    }

    private void checkPlayerName(String playerName) {
        players.stream().filter(p -> p.getName().equals(playerName))
            .findAny()
            .ifPresent(p -> {
                throw new ElvenGameException(format("玩家名稱 %s 已存在", playerName));
            });
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }
}
