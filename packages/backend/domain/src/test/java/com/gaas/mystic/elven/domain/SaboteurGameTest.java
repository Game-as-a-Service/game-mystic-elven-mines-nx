package com.gaas.mystic.elven.domain;

import com.gaas.mystic.elven.*;
import com.gaas.mystic.elven.builders.Players;
import com.gaas.mystic.elven.events.DestinationCardRevealedEvent;
import com.gaas.mystic.elven.exceptions.SaboteurGameException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.gaas.mystic.elven.PathCard.*;
import static com.gaas.mystic.elven.builders.Players.defaultPlayer;
import static com.gaas.mystic.elven.builders.Players.defaultPlayerBuilder;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.junit.jupiter.api.Assertions.*;

class SaboteurGameTest {
    @Test
    void testPlayRepairCard() {
        Player A = defaultPlayerBuilder("A").tools(
                new Tool[]{
                        new Tool(ToolName.MINE_CART, true),
                        new Tool(ToolName.LANTERN, true),
                        new Tool(ToolName.PICK, false)
                }
        ).build();

        Player B = defaultPlayerBuilder("B").hand(new Repair(ToolName.PICK)).build();
        Player C = defaultPlayer("C");

        SaboteurGame game = new SaboteurGame(asList(A, B, C));

        game.playCard(new Repair.Parameters("B", 0, "A"));

        assertTrue(game.getPlayer("A").getTool(ToolName.PICK).isAvailable());
    }

    @Test
    void testPlayMapCard() {
        var game = new SaboteurGame(
                asList(defaultPlayerBuilder("A").hand(new MapCard()).build(),
                        defaultPlayer("B"), defaultPlayer("C")));
        game.setGoldInDestinationCard(2);

        var events = game.playCard(new MapCard.Parameters("A", 0, 2));

        var domainEvent = events.get(0);
        assertEquals(DestinationCardRevealedEvent.class, domainEvent.getClass());

        var e = (DestinationCardRevealedEvent) domainEvent;
        assertEquals(2, e.getDestinationIndex());
        assertTrue(e.isGold());
    }

    @Test
    void gameShouldContainAtLeast3Players() {
        assertThrows(Exception.class, () -> new SaboteurGame(
                asList(defaultPlayer("A"), defaultPlayer("B"))
        ).startGame());
    }

    @Test
    void gameShouldContainAtMost10Players() {
        assertThrows(Exception.class, () -> {
            new SaboteurGame(
                range(0, 11)
                    .mapToObj(String::valueOf)
                    .map(Players::defaultPlayer).collect(toList())
            ).startGame();
        });

    }

    @Test
    void testRepairCardShouldNotRepairAvailableTool() {
        SaboteurGame game = new SaboteurGame(
                asList(
                        defaultPlayer("A"),
                        defaultPlayerBuilder("B").hand(new Repair(ToolName.PICK)).build(),
                        defaultPlayer("C"))
        );

        assertThrows(SaboteurGameException.class, () -> game.playCard(new Repair.Parameters("B", 0, "A")));
    }

    @Test
    void gameMustHaveOneGoldenDestinationCard() {
        var game = new SaboteurGame(anyThreePlayers());

        assertEquals(1, game.getDestinations()
                .stream().filter(Destination::isGold).count(), "There must be one golden destination card.");
    }

    @Test
    void test十字路口_happyPath() {
        var game = new SaboteurGame(
                asList(defaultPlayerBuilder("A").hand(十字路口()).hand(十字路口()).build(),
                        defaultPlayerBuilder("B").hand(十字路口()).hand(十字路口()).build(),
                        defaultPlayerBuilder("C").hand(十字路口()).hand(十字路口()).build()));

        String path = """
                -0-
                000
                -0-""";
        game.playCard(new PathCard.Parameters("A", 0, 0, 1, false));
        assertTrue(game.getPath(0, 1).looksLike(path));
        game.playCard(new PathCard.Parameters("B", 0, 1, 1, false));
        assertTrue(game.getPath(1, 1).looksLike(path));
        game.playCard(new PathCard.Parameters("C", 0, -1, 1, false));
        assertTrue(game.getPath(-1, 1).looksLike(path));
        game.playCard(new PathCard.Parameters("A", 0, 0, 2, false));
        assertTrue(game.getPath(0, 2).looksLike(path));
        game.playCard(new PathCard.Parameters("B", 0, 0, 3, false));
        assertTrue(game.getPath(0, 3).looksLike(path));
        game.playCard(new PathCard.Parameters("C", 0, -2, 1, false));
        assertTrue(game.getPath(-2, 1).looksLike(path));
    }

    @Test
    void whenPutPathIntoMaze_theNewPathShouldConnectThroughPathsToTheOrigin() {
        // given
        var game = new SaboteurGame(
                asList(defaultPlayerBuilder("A").hand(十字路口()).hand(右彎()).build(),
                        defaultPlayerBuilder("B").hand(右彎()).hand(T型死路()).hand(右彎()).build(),
                        defaultPlayerBuilder("C").hand(十字路口()).build())
        );

        // when & then
        assertDoesNotThrow(() -> game.playCard(new PathCard.Parameters("A", 0, 0, 1, false)));

        assertThrows(SaboteurGameException.class, () -> game.playCard(new PathCard.Parameters("B", 0, 0, 2, true)),
                "(起點 + 十字) + L型(翻轉後的右彎) -> fail");

        assertDoesNotThrow(() -> game.playCard(new PathCard.Parameters("B", 1, 0, 2, false)),
                "(起點 + 十字) + T型死路 -> OK");

        assertThrows(SaboteurGameException.class, () -> game.playCard(new PathCard.Parameters("C", 0, 0, 3, false)),
                "(起點 + 十字 + T型死路) + 十字路口 -> Fail");

        assertDoesNotThrow(() -> game.playCard(new PathCard.Parameters("A", 0, -1, 1, true)),
                "當 A 打一張 L型(翻轉後的右彎) 在十字路口下方時，應該要成功");

        assertThrows(SaboteurGameException.class, () -> game.playCard(new PathCard.Parameters("B", 1, 1, 1, true)),
                "當 B 打一張 L型(翻轉後的右彎) 在 十字路口上方時，應該要失敗");
    }

    @Test
    void youCannotPutPathCardOntoTheExistingPath() {

    }


    private List<Player> anyThreePlayers() {
        return asList(
                defaultPlayerBuilder("A").hand(new Repair(ToolName.PICK)).build(),
                defaultPlayerBuilder("B").hand(new Repair(ToolName.PICK)).build(),
                defaultPlayerBuilder("C").hand(new Repair(ToolName.PICK)).build());
    }

    @Test
    void testSabotage() {
        Player a = defaultPlayerBuilder("A").tools(
                new Tool[]{
                        new Tool(ToolName.MINE_CART, true),
                        new Tool(ToolName.LANTERN, true),
                        new Tool(ToolName.PICK, true)
                }
        ).build();

        Player b = defaultPlayerBuilder("B").hand(new Sabotage(ToolName.LANTERN)).build();
        Player c = defaultPlayer("C");

        SaboteurGame game = new SaboteurGame(asList(a, b, c));
        game.playCard(new Sabotage.Parameters("B", 0, "A"));

        assertFalse(a.getTool(ToolName.LANTERN).isAvailable());
    }

    @Test
    void testRockFall() {
        Player a = defaultPlayerBuilder("A").hand(new RockFall()).build();
        Player b = defaultPlayer("B");
        Player c = defaultPlayer("C");

        SaboteurGame game = new SaboteurGame("GameId", asList(a, b, c),
                new Maze(List.of(
                        new Path(0, 0, PathCard.十字路口()),
                        new Path(0, 1, PathCard.十字路口()),
                        new Path(-1, 1, PathCard.右彎(), true)
                )));
        game.playCard(new RockFall.Parameters("A", 0, 0, 1));
        assertThrows(Exception.class, () -> game.getPath(0, 1));
    }

    @Test
    void playerCanPlayPathCardOnlyIfAllHisToolsAreAvailable() {
        Player a = defaultPlayerBuilder("A").hand(PathCard.T型死路())
                .tools(new Tool[]{
                        new Tool(ToolName.MINE_CART, true),
                        new Tool(ToolName.LANTERN, false),
                        new Tool(ToolName.PICK, true)})
                .build();
        Player b = defaultPlayer("B");
        Player c = defaultPlayer("C");

        SaboteurGame game = new SaboteurGame("GameId", asList(a, b, c));

        assertThrows(RuntimeException.class, () -> game.playCard(new PathCard.Parameters("A", 0, 0, 1, false)));
    }

    @Test
    void saboteurWinWhenAllPlayerHandsArePlayed() {
        Player A = defaultPlayer("A");
        Player B = defaultPlayerBuilder("B")
                .hand(new Sabotage(ToolName.LANTERN))
                .build();

        Player C = defaultPlayer("C");

        SaboteurGame game = new SaboteurGame("GameId", asList(A, B, C));
        game.playCard(new Sabotage.Parameters("B", 0, "A"));
        assertTrue(game.isGameOver());
    }

    @Test
    void whenPlayerPlaysHand_heWillLoseTheCard() {
        Player A = defaultPlayer("A");
        Player B = defaultPlayerBuilder("B")
                .hand(new Sabotage(ToolName.LANTERN))
                .build();
        Player C = defaultPlayer("C");

        SaboteurGame game = new SaboteurGame("GameId", asList(A, B, C));

        game.playCard(new Sabotage.Parameters("B", 0, "C"));

        assertTrue(game.getPlayer("B").getHands().isEmpty());
    }
}
