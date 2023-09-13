package com.gaas.mystic.elven.repositories.data;

import com.gaas.mystic.elven.domain.*;
import com.gaas.mystic.elven.domain.card.*;
import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.domain.tool.Tool;
import com.gaas.mystic.elven.domain.tool.ToolName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.gaas.mystic.elven.builders.Players.defaultPlayerBuilder;
import static com.gaas.mystic.elven.repositories.data.ElvenGameData.toData;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;

class ElvenGameDataTest {

    @Test
    void testToData() {
        // given
        final String ID = "GameId";
        var game = new ElvenGame(
            ID, asList(defaultPlayerBuilder("A")
                .name("A")
                .hands(asList(
                    new FixCard(ToolName.STARLIGHT_WAND),
                    new FixCard(ToolName.HARP_OF_HARMONY),
                    new MapCard()
                )).build(),
            defaultPlayerBuilder("B").name("B").hand(new BrokenCard(ToolName.FLYING_BOOTS)).build(),
            defaultPlayerBuilder("C").name("C").hand(new RockFallCard()).build()
        ),
            new Maze(List.of(new Path(0, 0, PathCard.十字路口()),
                new Path(0, 1, PathCard.十字路口(), true),
                new Path(0, 2, PathCard.T型死路()),
                new Path(1, 1, PathCard.右彎()),
                new Path(-1, 1, PathCard.右彎(), true))));
        game.setGoldInDestinationCard(1);

        ElvenGameData data = toData(game);

        // assert
        assertEquals(game.getId(), data.getId());
        List<PlayerData> players = data.getPlayers();
        assertEquals(3, players.size());


        assertAll("players hand cards assertion", () -> {
            PlayerData A = players.get(0);
            assertEquals("A", A.getId());
            assertEquals("A", A.getName());
            CardData ACard0 = A.getHands().get(0);
            CardData ACard1 = A.getHands().get(1);
            CardData ACard2 = A.getHands().get(2);
            assertEquals(3, A.getHands().size());
            assertEquals(CardData.Type.FIX, ACard0.getType());
            assertEquals(CardData.Type.FIX, ACard1.getType());
            assertEquals(CardData.Type.MAP, ACard2.getType());
            assertEquals(ToolName.STARLIGHT_WAND, ACard0.getToolName());
            assertEquals(ToolName.HARP_OF_HARMONY, ACard1.getToolName());

            PlayerData B = players.get(1);
            assertEquals("B", B.getId());
            assertEquals("B", B.getName());
            CardData BCard0 = B.getHands().get(0);
            assertEquals(CardData.Type.BROKEN, BCard0.getType());
            assertEquals(ToolName.FLYING_BOOTS, BCard0.getToolName());

            PlayerData C = players.get(2);
            assertEquals("C", C.getId());
            assertEquals("C", C.getName());
            CardData CCard0 = C.getHands().get(0);
            assertEquals(CardData.Type.ROCK_FALL, CCard0.getType());
        });

        players.forEach(this::assertThreeAvailableTools);

        assertAll("one destination card must be golden.", () -> {
            List<PathData> destinationCards = data.getDestinations();
            assertFalse(destinationCards.get(0).isGold());
            assertTrue(destinationCards.get(1).isGold());
            assertFalse(destinationCards.get(2).isGold());
        });

        MazeData maze = data.getMaze();
        Set<PathData> actualPaths = new HashSet<>(maze.getPaths());
        var expectedPaths = Set.of(
            new PathData(0, 0, PathCard.十字路口, false, null),
            new PathData(0, 1, PathCard.十字路口, true, null),
            new PathData(0, 2, PathCard.T型死路, false, null),
            new PathData(1, 1, PathCard.右彎, false, null),
            new PathData(-1, 1, PathCard.右彎, true, null));
        assertEquals(expectedPaths, actualPaths);
    }

    private void assertThreeAvailableTools(PlayerData player) {
        assertAll("player should have three available tools", () -> {
            var distinctTools = player.getTools().stream().map(ToolData::getToolName).collect(toSet());
            assertEquals(3, distinctTools.size());
            var toolSet = Set.of(ToolName.FLYING_BOOTS, ToolName.HARP_OF_HARMONY, ToolName.STARLIGHT_WAND);
            assertEquals(toolSet, distinctTools);
        });
    }

    @Test
    void testToDomain() {
        List<ToolData> tools = asList(new ToolData(ToolName.HARP_OF_HARMONY, true),
            new ToolData(ToolName.STARLIGHT_WAND, true),
            new ToolData(ToolName.FLYING_BOOTS, true));
        ElvenGameData data = new ElvenGameData("G",
            asList(new PlayerData("A", "A", tools,
                    asList(CardData.toData(new FixCard(ToolName.HARP_OF_HARMONY)),
                        CardData.toData(new MapCard()))),
                new PlayerData("B", "B", tools,
                    asList(CardData.toData(new FixCard(ToolName.FLYING_BOOTS)),
                        CardData.toData(new BrokenCard(ToolName.STARLIGHT_WAND)))),
                new PlayerData("C", "C", tools,
                    asList(CardData.toData(new FixCard(ToolName.STARLIGHT_WAND)),
                        CardData.toData(new RockFallCard())))
            ),
            new MazeData(List.of(
                new PathData(0, 0, PathCard.十字路口, false, null),
                new PathData(0, 1, PathCard.十字路口, true, null),
                new PathData(0, 2, PathCard.T型死路, false, null),
                new PathData(1, 1, PathCard.右彎, false, null),
                new PathData(-1, 1, PathCard.右彎, true, null))),
            asList(PathData.toData(new GoalCard(8, 0, false)),
                PathData.toData(new GoalCard(8, 2, true)),
                PathData.toData(new GoalCard(8, 4, false))));

        ElvenGame game = data.toDomain();
        List<Player> players = game.getPlayers();
        Player A = players.get(0);
        Player B = players.get(1);
        Player C = players.get(2);

        var destinationCards = game.getDestinations();
        assertEquals(3, players.size());
        assertFalse(destinationCards.get(0).isGold());
        assertTrue(destinationCards.get(1).isGold());
        assertFalse(destinationCards.get(2).isGold());

        players.forEach(this::assertThreeAvailableTools);
        assertEquals("A", A.getName());
        assertEquals("B", B.getName());
        assertEquals("C", C.getName());
        assertHasFixCard(A, 0, ToolName.HARP_OF_HARMONY);
        assertHasFixCard(B, 0, ToolName.FLYING_BOOTS);
        assertHasFixCard(C, 0, ToolName.STARLIGHT_WAND);
        assertHasMapCard(A, 1);
        assertHasBrokenCard(B, 1, ToolName.STARLIGHT_WAND);
        assertHasRockFallCard(C, 1);

        Maze maze = game.getMaze();
        Set<Path> actualPaths = new HashSet<>(maze.getPaths());
        var expectedPaths = Set.of(new Path(0, 0, PathCard.十字路口()),
            new Path(0, 1, PathCard.十字路口(), true),
            new Path(0, 2, PathCard.T型死路()),
            new Path(1, 1, PathCard.右彎()),
            new Path(-1, 1, PathCard.右彎(), true));
        assertEquals(expectedPaths, actualPaths);
    }

    private void assertHasFixCard(Player player, int handIndex, ToolName fixToolName) {
        Card card = player.getHandCard(handIndex);
        assertEquals(FixCard.class, card.getClass());
        assertEquals(fixToolName, ((FixCard) card).getToolName());
    }

    private void assertHasBrokenCard(Player player, int handIndex, ToolName brokenToolName) {
        Card card = player.getHandCard(handIndex);
        assertEquals(BrokenCard.class, card.getClass());
        assertEquals(brokenToolName, ((BrokenCard) card).getToolName());
    }

    private void assertHasMapCard(Player player, int handIndex) {
        Card card = player.getHandCard(handIndex);
        assertEquals(MapCard.class, card.getClass());
    }

    private void assertHasRockFallCard(Player player, int handIndex) {
        Card card = player.getHandCard(handIndex);
        assertEquals(RockFallCard.class, card.getClass());
    }

    private void assertThreeAvailableTools(Player player) {
        Tool[] tools = player.getTools();
        assertEquals(3, tools.length);
        assertTrue(stream(tools).allMatch(Tool::isAvailable));
    }
}
