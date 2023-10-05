package com.gaas.mystic.elven.domain;

import com.gaas.mystic.elven.builders.Players;
import com.gaas.mystic.elven.domain.card.*;
import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.domain.tool.Tool;
import com.gaas.mystic.elven.domain.tool.ToolName;
import com.gaas.mystic.elven.events.DestinationCardRevealedEvent;
import com.gaas.mystic.elven.exceptions.ElvenGameException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.gaas.mystic.elven.domain.card.PathCard.*;
import static com.gaas.mystic.elven.builders.Players.defaultPlayer;
import static com.gaas.mystic.elven.builders.Players.defaultPlayerBuilder;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.junit.jupiter.api.Assertions.*;

class ElvenGameTest {
    @Test
    void testPlayFixCard() {
        Player A = defaultPlayerBuilder("A").tools(
                new Tool[]{
                        new Tool(ToolName.FLYING_BOOTS, true),
                        new Tool(ToolName.HARP_OF_HARMONY, true),
                        new Tool(ToolName.STARLIGHT_WAND, false)
                }
        ).build();

        Player B = defaultPlayerBuilder("B").hand(new FixCard(ToolName.STARLIGHT_WAND)).build();
        Player C = defaultPlayer("C");

        ElvenGame game = new ElvenGame(asList(A, B, C));

        game.playCard(new FixCard.Parameters("B", 0, "A"));

        assertTrue(game.getPlayer("A").getTool(ToolName.STARLIGHT_WAND).isAvailable());
    }

    @Test
    void testPlayMapCard() {
        var game = new ElvenGame(
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
        assertThrows(Exception.class, () -> new ElvenGame(
                asList(defaultPlayer("A"), defaultPlayer("B"))
        ).startGame());
    }

    @Test
    void gameShouldContainAtMost10Players() {
        assertThrows(Exception.class, () -> {
            new ElvenGame(
                range(0, 11)
                    .mapToObj(String::valueOf)
                    .map(Players::defaultPlayer).collect(toList())
            ).startGame();
        });

    }

    @Test
    void testFixCardShouldNotRepairAvailableTool() {
        ElvenGame game = new ElvenGame(
                asList(
                        defaultPlayer("A"),
                        defaultPlayerBuilder("B").hand(new FixCard(ToolName.STARLIGHT_WAND)).build(),
                        defaultPlayer("C"))
        );

        assertThrows(ElvenGameException.class, () -> game.playCard(new FixCard.Parameters("B", 0, "A")));
    }

    @Test
    void gameMustHaveOneGoldenDestinationCard() {
        var game = new ElvenGame(anyThreePlayers());

        assertEquals(1, game.getDestinations()
                .stream().filter(GoalCard::isGoal).count(), "There must be one golden destination card.");
    }

    @Test
    void test十字路口_happyPath() {
        var game = new ElvenGame(
                asList(defaultPlayerBuilder("A").hand(cross()).hand(cross()).build(),
                        defaultPlayerBuilder("B").hand(cross()).hand(cross()).build(),
                        defaultPlayerBuilder("C").hand(cross()).hand(cross()).build()));

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
        var game = new ElvenGame(
                asList(defaultPlayerBuilder("A").hand(cross()).hand(rightCurve()).build(),
                        defaultPlayerBuilder("B").hand(rightCurve()).hand(deadEndStraightT()).hand(rightCurve()).build(),
                        defaultPlayerBuilder("C").hand(cross()).build())
        );

        // when & then
        assertDoesNotThrow(() -> game.playCard(new PathCard.Parameters("A", 0, 0, 1, false)));

        assertThrows(ElvenGameException.class, () -> game.playCard(new PathCard.Parameters("B", 0, 0, 2, true)),
                "(起點 + 十字) + L型(翻轉後的右彎) -> fail");

        assertDoesNotThrow(() -> game.playCard(new PathCard.Parameters("B", 1, 0, 2, false)),
                "(起點 + 十字) + T型死路 -> OK");

        assertThrows(ElvenGameException.class, () -> game.playCard(new PathCard.Parameters("C", 0, 0, 3, false)),
                "(起點 + 十字 + T型死路) + 十字路口 -> Fail");

        assertDoesNotThrow(() -> game.playCard(new PathCard.Parameters("A", 0, -1, 1, true)),
                "當 A 打一張 L型(翻轉後的右彎) 在十字路口下方時，應該要成功");

        assertThrows(ElvenGameException.class, () -> game.playCard(new PathCard.Parameters("B", 1, 1, 1, true)),
                "當 B 打一張 L型(翻轉後的右彎) 在 十字路口上方時，應該要失敗");
    }

    @Test
    void youCannotPutPathCardOntoTheExistingPath() {

    }


    private List<Player> anyThreePlayers() {
        return asList(
                defaultPlayerBuilder("A").hand(new FixCard(ToolName.STARLIGHT_WAND)).build(),
                defaultPlayerBuilder("B").hand(new FixCard(ToolName.STARLIGHT_WAND)).build(),
                defaultPlayerBuilder("C").hand(new FixCard(ToolName.STARLIGHT_WAND)).build());
    }

    @Test
    void testBrokenCard() {
        Player a = defaultPlayerBuilder("A").tools(
                new Tool[]{
                        new Tool(ToolName.FLYING_BOOTS, true),
                        new Tool(ToolName.HARP_OF_HARMONY, true),
                        new Tool(ToolName.STARLIGHT_WAND, true)
                }
        ).build();

        Player b = defaultPlayerBuilder("B").hand(new BrokenCard(ToolName.HARP_OF_HARMONY)).build();
        Player c = defaultPlayer("C");

        ElvenGame game = new ElvenGame(asList(a, b, c));
        game.playCard(new BrokenCard.Parameters("B", 0, "A"));

        assertFalse(a.getTool(ToolName.HARP_OF_HARMONY).isAvailable());
    }

    @Test
    void testRockFall() {
        Player a = defaultPlayerBuilder("A").hand(new RockFallCard()).build();
        Player b = defaultPlayer("B");
        Player c = defaultPlayer("C");

        ElvenGame game = new ElvenGame("GameId", asList(a, b, c),
                new Maze(List.of(
                        new Path(0, 0, PathCard.cross()),
                        new Path(0, 1, PathCard.cross()),
                        new Path(-1, 1, PathCard.rightCurve(), true)
                )));
        game.playCard(new RockFallCard.Parameters("A", 0, 0, 1));
        assertThrows(Exception.class, () -> game.getPath(0, 1));
    }

    @Test
    void playerCanPlayPathCardOnlyIfAllHisToolsAreAvailable() {
        Player a = defaultPlayerBuilder("A").hand(PathCard.deadEndStraightT())
                .tools(new Tool[]{
                        new Tool(ToolName.FLYING_BOOTS, true),
                        new Tool(ToolName.HARP_OF_HARMONY, false),
                        new Tool(ToolName.STARLIGHT_WAND, true)})
                .build();
        Player b = defaultPlayer("B");
        Player c = defaultPlayer("C");

        ElvenGame game = new ElvenGame("GameId", asList(a, b, c));

        assertThrows(RuntimeException.class, () -> game.playCard(new PathCard.Parameters("A", 0, 0, 1, false)));
    }

    @Test
    void saboteurWinWhenAllPlayerHandsArePlayed() {
        Player A = defaultPlayer("A");
        Player B = defaultPlayerBuilder("B")
                .hand(new BrokenCard(ToolName.HARP_OF_HARMONY))
                .build();

        Player C = defaultPlayer("C");

        ElvenGame game = new ElvenGame("GameId", asList(A, B, C));
        game.playCard(new BrokenCard.Parameters("B", 0, "A"));
        assertTrue(game.isGameOver());
    }

    @Test
    void whenPlayerPlaysHand_heWillLoseTheCard() {
        Player A = defaultPlayer("A");
        Player B = defaultPlayerBuilder("B")
                .hand(new BrokenCard(ToolName.HARP_OF_HARMONY))
                .build();
        Player C = defaultPlayer("C");

        ElvenGame game = new ElvenGame("GameId", asList(A, B, C));

        game.playCard(new BrokenCard.Parameters("B", 0, "C"));

        assertTrue(game.getPlayer("B").getHands().isEmpty());
    }
}
