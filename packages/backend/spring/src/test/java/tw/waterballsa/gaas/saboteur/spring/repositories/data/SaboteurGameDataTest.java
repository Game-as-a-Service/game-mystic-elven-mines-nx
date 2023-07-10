package tw.waterballsa.gaas.saboteur.spring.repositories.data;

import org.junit.jupiter.api.Test;
import tw.waterballsa.gaas.saboteur.domain.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;
import static tw.waterballsa.gaas.saboteur.domain.builders.Players.defaultPlayerBuilder;
import static tw.waterballsa.gaas.saboteur.spring.repositories.data.SaboteurGameData.toData;

class SaboteurGameDataTest {

    @Test
    void testToData() {
        // given
        final String ID = "GameId";
        var game = new SaboteurGame(
            ID, asList(defaultPlayerBuilder("A")
                .name("A")
                .hands(asList(
                    new Repair(ToolName.PICK),
                    new Repair(ToolName.LANTERN),
                    new MapCard()
                )).build(),
            defaultPlayerBuilder("B").name("B").hand(new Sabotage(ToolName.MINE_CART)).build(),
            defaultPlayerBuilder("C").name("C").hand(new RockFall()).build()
        ),
            new Maze(List.of(new Path(0, 0, PathCard.十字路口()),
                new Path(0, 1, PathCard.十字路口(), true),
                new Path(0, 2, PathCard.T型死路()),
                new Path(1, 1, PathCard.右彎()),
                new Path(-1, 1, PathCard.右彎(), true))));
        game.setGoldInDestinationCard(1);

        SaboteurGameData data = toData(game);

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
            assertEquals(CardData.Type.REPAIR, ACard0.getType());
            assertEquals(CardData.Type.REPAIR, ACard1.getType());
            assertEquals(CardData.Type.MAP, ACard2.getType());
            assertEquals(ToolName.PICK, ACard0.getToolName());
            assertEquals(ToolName.LANTERN, ACard1.getToolName());

            PlayerData B = players.get(1);
            assertEquals("B", B.getId());
            assertEquals("B", B.getName());
            CardData BCard0 = B.getHands().get(0);
            assertEquals(CardData.Type.SABOTEUR, BCard0.getType());
            assertEquals(ToolName.MINE_CART, BCard0.getToolName());

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
            var toolSet = Set.of(ToolName.MINE_CART, ToolName.LANTERN, ToolName.PICK);
            assertEquals(toolSet, distinctTools);
        });
    }

    @Test
    void testToDomain() {
        List<ToolData> tools = asList(new ToolData(ToolName.LANTERN, true),
            new ToolData(ToolName.PICK, true),
            new ToolData(ToolName.MINE_CART, true));
        SaboteurGameData data = new SaboteurGameData("G",
            asList(new PlayerData("A", "A", tools,
                    asList(CardData.toData(new Repair(ToolName.LANTERN)),
                        CardData.toData(new MapCard()))),
                new PlayerData("B", "B", tools,
                    asList(CardData.toData(new Repair(ToolName.MINE_CART)),
                        CardData.toData(new Sabotage(ToolName.PICK)))),
                new PlayerData("C", "C", tools,
                    asList(CardData.toData(new Repair(ToolName.PICK)),
                        CardData.toData(new RockFall())))
            ),
            new MazeData(List.of(
                new PathData(0, 0, PathCard.十字路口, false, null),
                new PathData(0, 1, PathCard.十字路口, true, null),
                new PathData(0, 2, PathCard.T型死路, false, null),
                new PathData(1, 1, PathCard.右彎, false, null),
                new PathData(-1, 1, PathCard.右彎, true, null))),
            asList(PathData.toData(new Destination(8, 0, false)),
                PathData.toData(new Destination(8, 2, true)),
                PathData.toData(new Destination(8, 4, false))));

        SaboteurGame game = data.toDomain();
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
        assertHasRepairCard(A, 0, ToolName.LANTERN);
        assertHasRepairCard(B, 0, ToolName.MINE_CART);
        assertHasRepairCard(C, 0, ToolName.PICK);
        assertHasMapCard(A, 1);
        assertHasSabotageCard(B, 1, ToolName.PICK);
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

    private void assertHasRepairCard(Player player, int handIndex, ToolName repairToolName) {
        Card card = player.getHandCard(handIndex);
        assertEquals(Repair.class, card.getClass());
        assertEquals(repairToolName, ((Repair) card).getToolName());
    }

    private void assertHasSabotageCard(Player player, int handIndex, ToolName sabotageToolName) {
        Card card = player.getHandCard(handIndex);
        assertEquals(Sabotage.class, card.getClass());
        assertEquals(sabotageToolName, ((Sabotage) card).getToolName());
    }

    private void assertHasMapCard(Player player, int handIndex) {
        Card card = player.getHandCard(handIndex);
        assertEquals(MapCard.class, card.getClass());
    }

    private void assertHasRockFallCard(Player player, int handIndex) {
        Card card = player.getHandCard(handIndex);
        assertEquals(RockFall.class, card.getClass());
    }

    private void assertThreeAvailableTools(Player player) {
        Tool[] tools = player.getTools();
        assertEquals(3, tools.length);
        assertTrue(stream(tools).allMatch(Tool::isAvailable));
    }
}
