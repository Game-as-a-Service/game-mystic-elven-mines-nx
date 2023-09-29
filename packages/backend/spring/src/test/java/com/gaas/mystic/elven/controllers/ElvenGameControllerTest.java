package com.gaas.mystic.elven.controllers;

import com.gaas.mystic.elven.builders.Players;
import com.gaas.mystic.elven.domain.*;
import com.gaas.mystic.elven.domain.card.*;
import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.domain.tool.Tool;
import com.gaas.mystic.elven.domain.tool.ToolName;
import com.gaas.mystic.elven.outport.ElvenGameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ElvenGameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ElvenGameRepository gameRepository;

    @Test
    public void testPlayerCreateGame() throws Exception {
        mockMvc.perform(post("/api/games")
                .contentType(APPLICATION_JSON)
                .content("""
                    {
                        "playerName": "A"
                    }"""))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.gameId").exists())
            .andExpect(jsonPath("$.playerId").exists())
            .andExpect(jsonPath("$.player.playerName").value("A"));
    }

    @Test
    public void testPlayerFindGame() throws Exception {
        Player A = Players.defaultPlayer("A");
        ElvenGame game = givenGameStarted(A);

        mockMvc.perform(get("/api/games/{gameId}/players", game.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.players").isArray())
            .andExpect(jsonPath("$.players[0].playerName").value(A.getName()));
    }

    @Test
    public void testPlayerJoinGame() throws Exception {
        Player A = Players.defaultPlayer("A");
        ElvenGame game = givenGameStarted(A);

        mockMvc.perform(post("/api/games/{gameId}", game.getId())
                .contentType(APPLICATION_JSON)
                .content("""
                    {
                        "playerName": "BB"
                    }"""))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.players").isArray())
            .andExpect(jsonPath("$.players[0].playerName").value(A.getName()))
            .andExpect(jsonPath("$.players[1].playerName").value("BB"))
            .andExpect(jsonPath("$.playerId").exists());
    }

    @Test
    public void givenPlayerJoinGame_whenSamePlayerName_thenFail() throws Exception {
        Player A = Players.defaultPlayer("A");
        ElvenGame game = givenGameStarted(A);

        mockMvc.perform(post("/api/games/{gameId}", game.getId())
                .contentType(APPLICATION_JSON)
                .content("""
                    {
                        "playerName": "A"
                    }"""))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void testPlayerFindPlayersAfterGameStarted() throws Exception {
        Player A = Players.defaultPlayer("A");
        Player B = Players.defaultPlayer("B");
        Player C = Players.defaultPlayer("C");
        ElvenGame game = givenGameStarted(A, B, C);
        game.startGame();
        gameRepository.save(game);

        mockMvc.perform(get("/api/games/{gameId}/players", game.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.players").isArray())
            .andExpect(jsonPath("$.players[0].playerName").value(A.getName()))
            .andExpect(jsonPath("$.players[0].cardNum").exists())
            .andExpect(jsonPath("$.players[0].cardNum").isNumber())
            .andExpect(jsonPath("$.players[0].cardNum").value(5))
            .andExpect(jsonPath("$.players[0].tools[0].toolName").exists())
            .andExpect(jsonPath("$.players[0].tools[0].toolName").value("FLYING_BOOTS"))
            .andExpect(jsonPath("$.players[0].tools[0].available").exists())
            .andExpect(jsonPath("$.players[0].tools[0].available").value(true))
            .andExpect(jsonPath("$.players[0].tools[1].toolName").exists())
            .andExpect(jsonPath("$.players[0].tools[1].toolName").value("HARP_OF_HARMONY"))
            .andExpect(jsonPath("$.players[0].tools[1].available").exists())
            .andExpect(jsonPath("$.players[0].tools[1].available").value(true))
            .andExpect(jsonPath("$.players[0].tools[2].toolName").exists())
            .andExpect(jsonPath("$.players[0].tools[2].toolName").value("STARLIGHT_WAND"))
            .andExpect(jsonPath("$.players[0].tools[2].available").exists())
            .andExpect(jsonPath("$.players[0].tools[2].available").value(true));
    }

    // ATDD (1) 先寫驗收測試程式 （2) ------------
    @Test
    public void 修好其中一個工具耶() throws Exception {
        Player A = new Player(
            "A", "A",
            emptyList(),
            new Tool(ToolName.FLYING_BOOTS, true),
            new Tool(ToolName.HARP_OF_HARMONY, true),
            new Tool(ToolName.STARLIGHT_WAND, false)
        );
        Player B = new Player(
            "B", "B",
            emptyList(),
            new Tool(ToolName.FLYING_BOOTS, true),
            new Tool(ToolName.HARP_OF_HARMONY, true),
            new Tool(ToolName.STARLIGHT_WAND, true)
        );
        B.addHandCard(new FixCard(ToolName.STARLIGHT_WAND));

        Player C = new Player(
            "C", "C",
            emptyList(),
            new Tool(ToolName.FLYING_BOOTS, true),
            new Tool(ToolName.HARP_OF_HARMONY, true),
            new Tool(ToolName.STARLIGHT_WAND, true));

        ElvenGame game = givenGameStarted(A, B, C);

        mockMvc.perform(post("/api/games/{gameId}:playCard", game.getId())
                .contentType(APPLICATION_JSON)
                .content("""
                    {  "cardType": "FIX",
                    "playerId": "B",
                      "handIndex": 0,
                      "targetPlayerId": "A"
                    }"""))
            .andExpect(status().isNoContent());

        var actualGame = findGameById(game.getId());
        Player actualA = actualGame.getPlayer("A");

        assertTrue(actualA.getTool(ToolName.FLYING_BOOTS).isAvailable());
        assertTrue(actualA.getTool(ToolName.HARP_OF_HARMONY).isAvailable());
        assertTrue(actualA.getTool(ToolName.STARLIGHT_WAND).isAvailable());
    }

    private ElvenGame givenGameStarted(ElvenGame game) {
        return gameRepository.save(game);
    }

    private ElvenGame givenGameStarted(Player... players) {
        return gameRepository.save(new ElvenGame(asList(players)));
    }

    private ElvenGame findGameById(String gameId) {
        // 從 repo 查出 game
        return gameRepository.findById(gameId).orElseThrow();
    }

    @Test
    public void 都好的硬要修() throws Exception {
        Player A = Players.defaultPlayer("A");
        Player B = Players.defaultPlayer("B");
        B.addHandCard(new FixCard(ToolName.STARLIGHT_WAND));

        Player C = Players.defaultPlayer("C");

        ElvenGame game = givenGameStarted(A, B, C);

        mockMvc.perform(post("/api/games/{gameId}:playCard", game.getId())
                .contentType(APPLICATION_JSON)
                .content("""
                    {   "cardType": "FIX",
                    "playerId": "B",
                      "handIndex": 0,
                      "targetPlayerId": "A"
                    }"""))
            .andExpect(status().isBadRequest());

        var actualGame = findGameById(game.getId());
        Player actualA = actualGame.getPlayer("A");

        assertTrue(actualA.getTool(ToolName.FLYING_BOOTS).isAvailable());
        assertTrue(actualA.getTool(ToolName.HARP_OF_HARMONY).isAvailable());
        assertTrue(actualA.getTool(ToolName.STARLIGHT_WAND).isAvailable());
    }

    @Test
    public void 看終點底下有無金礦喔() throws Exception {
        Player A = Players.defaultPlayerBuilder("A")
            .hand(new MapCard()).build();
        Player B = Players.defaultPlayer("B");
        Player C = Players.defaultPlayer("C");

        ElvenGame game = new ElvenGame(asList(A, B, C)); //givenGameStarted?
        game.setGoldInDestinationCard(2);
        game = givenGameStarted(game);

        //A玩家對 終點2 使用地圖卡
        mockMvc.perform(post("/api/games/{gameId}:playCard", game.getId())
                .contentType(APPLICATION_JSON)
                .content("""
                    {   "cardType": "MAP",
                    "playerId": "A",
                      "handIndex": 0,
                      "destinationCardIndex": 2
                    }"""))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.isGold").value(true));

    }

    @Test
    void 好矮人gameOver() throws Exception {
        // Given
        Player A = Players.defaultPlayer("A");
        Player B = Players.defaultPlayer("B");
        Player C = Players.defaultPlayerBuilder("C")
            .hand(new BrokenCard(ToolName.HARP_OF_HARMONY)).build();

        ElvenGame game = givenGameStarted(A, B, C);

        //When, C玩家打出最後一張手牌
        playBrokenCardSuccessfully(game, "C", 0, "A");

        // Then: assertions
        ElvenGame actualGame = gameRepository.findById(game.getId()).orElseThrow();

        // TODO: 要區分好矮人/壞矮人
        assertTrue(actualGame.isGameOver());
    }

    @Test
    void 工具如果被破壞了就不能蓋路了() throws Exception {
        // Given
        Player A = Players.defaultPlayerBuilder("A")
            .hand(PathCard.deadEndStraightT()) // <--- code with me 在搞
            .tools(new Tool[]{
                new Tool(ToolName.FLYING_BOOTS, true),
                new Tool(ToolName.HARP_OF_HARMONY, false),
                new Tool(ToolName.STARLIGHT_WAND, true)})
            .build();
        Player B = Players.defaultPlayer("B");
        Player C = Players.defaultPlayer("C");

        ElvenGame game = givenGameStarted(A, B, C);

        // When
        playPathCard(game, "A", 0, 0, 1, false)
            .andExpect(status().isBadRequest());
    }

    @Test
    void 唉呀怎麼壞了() throws Exception {
        Player A = Players.defaultPlayerBuilder("A")
            .tools(new Tool[]{
                new Tool(ToolName.FLYING_BOOTS, true),
                new Tool(ToolName.HARP_OF_HARMONY, true),
                new Tool(ToolName.STARLIGHT_WAND, true)})
            .build();
        Player B = Players.defaultPlayerBuilder("B")
            .hand(new BrokenCard(ToolName.HARP_OF_HARMONY))
            .build();

        ElvenGame game = givenGameStarted(A, B, Players.defaultPlayer("C"));

        // when
        playBrokenCardSuccessfully(game, "B", 0, "A");

        var actualGame = gameRepository.findById(game.getId()).orElseThrow();
        Player player = actualGame.getPlayer(A.getId());
        assertFalse(player.getTool(ToolName.HARP_OF_HARMONY).isAvailable());
        assertTrue(player.getTool(ToolName.FLYING_BOOTS).isAvailable());
        assertTrue(player.getTool(ToolName.STARLIGHT_WAND).isAvailable());
    }

    private void playBrokenCardSuccessfully(ElvenGame game, String playerId, int handIndex, String targetPlayerId) throws Exception {
        mockMvc.perform(post("/api/games/{gameId}:playCard", game.getId())
                .contentType(APPLICATION_JSON)
                .content(format("""
                    {   "cardType": "BROKEN",
                    "playerId": "%s",
                      "handIndex": %d,
                      "targetPlayerId": "%s"
                    }""", playerId, handIndex, targetPlayerId)))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testRockFall() throws Exception {
        Player A = Players.defaultPlayerBuilder("A")
            .hand(new RockFallCard()).build();
        Player B = Players.defaultPlayer("B");
        Player C = Players.defaultPlayer("C");

        var game = givenGameStarted(new ElvenGame("GameId", List.of(A, B, C),
            new Maze(List.of(new Path(0, 0, PathCard.cross()),
                new Path(0, 1, PathCard.cross()),
                new Path(-1, 1, PathCard.rightCurve(), true)))));

        mockMvc.perform(post("/api/games/{gameId}:playCard", game.getId())
                .contentType(APPLICATION_JSON)
                .content("""
                    {"cardType": "ROCKFALL",
                     "playerId": "A",
                     "row": 0,
                     "col": 1
                    }"""))
            .andExpect(status().is2xxSuccessful());

        var actualGame = gameRepository.findById(game.getId()).orElseThrow();
        assertNull(actualGame.getMaze().getPath(0, 1).orElse(null));
    }

    @Test
    @SuppressWarnings("NonAsciiCharacters")
    void test迷宮案例1() throws Exception {
        // Given
        Player A = Players.defaultPlayerBuilder("A")
            .hand(PathCard.cross())
            .build();
        Player B = Players.defaultPlayerBuilder("B")
            .hand(PathCard.deadEndStraightT())
            .build();
        Player C = Players.defaultPlayerBuilder("C")
            .hand(PathCard.straight())
            .hand(PathCard.rightCurve())
            .build();

        ElvenGame game = givenGameStarted(A, B, C);

        // When -- Then
        //  輪到 A 出一張十字路口，可以成功連接回起點，放置成功
        playPathCard(game, "A", 0, 0, 1, false)
            .andExpect(status().is2xxSuccessful());

        // 輪到 B 出一張T型死路，可以成功連接回起點，放置成功
        playPathCard(game, "B", 0, 0, 2, false)
            .andExpect(status().is2xxSuccessful());

        // 輪到 C 出一張一字型卡，無法成功連接回起點，放置失敗
        playPathCard(game, "C", 0, 0, 3, false)
            .andExpect(status().isBadRequest());

        //輪到 C 出另一張L型（翻轉右彎）卡，可以成功連接回起點，放置成功
        playPathCard(game, "C", 1, -1, 1, true)
            .andExpect(status().is2xxSuccessful());

        var actualGame = gameRepository.findById(game.getId()).orElseThrow();
        Maze maze = actualGame.getMaze();

        Assertions.assertEquals(PathCard.cross(), maze.getPath(0, 1)
            .map(Path::getPathCard).orElseThrow());

        Assertions.assertEquals(PathCard.deadEndStraightT(), maze.getPath(0, 2)
            .map(Path::getPathCard).orElseThrow());

        Path actual右彎 = maze.getPath(-1, 1).orElseThrow();
        assertTrue(actual右彎.isFlipped());
    }

    private ResultActions playPathCard(ElvenGame game, String playerId, int handIndex, int row, int col, boolean flipped) throws Exception {
        return mockMvc.perform(post("/api/games/{gameId}:playCard", game.getId())
            .contentType(APPLICATION_JSON)
            .content(format("""
                    {
                     "cardType": "PATH",
                    "playerId": "%s",
                    "handIndex": %d,
                    "row": %d,
                    "col": %d,
                    "flipped": %b}
                """, playerId, handIndex, row, col, flipped)));
    }

}
