package com.gaas.mystic.elven.domain;

import com.gaas.mystic.elven.domain.card.Card;
import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.domain.role.RoleCard;
import com.gaas.mystic.elven.events.DomainEvent;
import com.gaas.mystic.elven.exceptions.ElvenGameException;
import lombok.Getter;

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
    public static final int NUMBER_OF_PLAYER_HAND_CARDS = 5;
    private static final Random RANDOM = new Random();

    // @Id (UUID)
    @Getter
    private String id;

    // one-to-many relationship
    private final List<Player> players;

    @Getter
    private Player currentPlayer;

    @Getter
    private final Maze maze;

    private final List<GoalCard> goalCards = new ArrayList<>(DESTINATION_CARDS_COUNT);

    @Getter
    private final Deck deck;

    public ElvenGame(List<Player> players) {
        this(randomUUID().toString(), players);
    }

    public ElvenGame(String id, List<Player> players) {
        this(id, players, new Maze(), new Deck());
    }

    public ElvenGame(String id, List<Player> players, Maze maze) {
        this(id, players, maze, new Deck());
    }

    public ElvenGame(String id, List<Player> players, Maze maze, Deck deck) {
        this(id, players, null, maze, deck);
    }

    public ElvenGame(String id, List<Player> players, Player currentPlayer, Maze maze, Deck deck) {
        this.id = id;
        this.players = requireNonNullElseGet(players, Collections::emptyList);
        this.currentPlayer = currentPlayer == null ? this.players.get(0) : currentPlayer;
        setupDestinationCards(RANDOM.nextInt(DESTINATION_CARDS_COUNT));
        this.maze = maze;
        this.deck = deck;
    }

    public void startGame() {
        ensureNumberOfPlayersIsCorrect();
        deck.prepareDeck();
        dealRoles();
        dealCards();
    }

    private void ensureNumberOfPlayersIsCorrect() {
        // check players count
        if (players.size() < 3 || players.size() > 10) {
            throw new IllegalArgumentException("玩家人數必須介於 3 ~ 10 之間");
        }
    }

    private void dealRoles() {
        // role number
        int playerNumber = players.size();
        // elven and goblin number
        int elfNumber = 0;
        int goblinNumber = 0;
        switch (playerNumber) {
            case 3 -> {
                elfNumber = 2;
                goblinNumber = 1;
            }
            case 4 -> {
                elfNumber = 3;
                goblinNumber = 1;
            }
            case 5 -> {
                elfNumber = 3;
                goblinNumber = 2;
            }
            case 6 -> {
                elfNumber = 4;
                goblinNumber = 2;
            }
            case 7 -> {
                elfNumber = 5;
                goblinNumber = 2;
            }
            case 8 -> {
                elfNumber = 5;
                goblinNumber = 3;
            }
            case 9 -> {
                elfNumber = 6;
                goblinNumber = 3;
            }
            case 10 -> {
                elfNumber = 7;
                goblinNumber = 3;
            }
        }
        // init roles
        List<RoleCard> roleCards = new ArrayList<>(playerNumber);
        for (int i = 0; i < elfNumber; i++) {
            roleCards.add(RoleCard.ELVEN);
        }
        for (int i = 0; i < goblinNumber; i++) {
            roleCards.add(RoleCard.GOBLIN);
        }
        // shuffle
        Collections.shuffle(roleCards);
        // deal roles
        for (int i = 0; i < playerNumber; i++) {
            players.get(i).setRoleCard(roleCards.get(i));
        }
    }


    private void dealCards() {
        // deal cards
        for (Player player : players) {
            for (int i = 0; i < NUMBER_OF_PLAYER_HAND_CARDS; i++) {
                player.addHandCard(deck.draw());
            }
        }
    }


    // 1. 封裝變動之處 (開出 各個 Card 類別 -> 來封裝各個行為）
    // 2. 萃取相同行為 (萃取出共同的部分到介面中）so sad
    // 3. 依賴抽象 (依賴抽象的 Card)
    public List<DomainEvent> playCard(Card.Parameters parameters) {
        checkPlayerIsCurrentPlayer(parameters.playerId);
        parameters.player = getPlayer(parameters.playerId);
        parameters.card = parameters.player.getHandCard(parameters.handCardIndex);
        parameters.game = this;
        var events = parameters.card.execute(parameters);
        parameters.player.playCard(parameters.handCardIndex);
        turnToNextPlayer();
        return events;
    }

    private void checkPlayerIsCurrentPlayer(String playerId) {
        if (!currentPlayer.getId().equals(playerId)) {
            throw new ElvenGameException(format("Player %s is not current player.", playerId));
        }
    }

    private void turnToNextPlayer() {
        int index = players.stream().map(Player::getId).toList().indexOf(currentPlayer.getId());
        currentPlayer = players.get((index + 1) % players.size());
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
            goalCards.add(new GoalCard(2 * i - 2, 8, i == goldenDestinationCardIndex));
        }
    }

    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public List<GoalCard> getDestinations() {
        return List.copyOf(goalCards);
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
        checkGameIsNotStarted();
        checkPlayersNumber();
        checkPlayerName(player.getName());
        players.add(player);
    }

    private void checkGameIsNotStarted() {
        if (isStarted()) {
            throw new ElvenGameException("遊戲已開始，無法加入玩家");
        }
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

    public boolean isStarted() {
        return !deck.isEmpty();
    }
}
