package com.gaas.mystic.elven.controllers;

import com.gaas.mystic.elven.*;
import com.gaas.mystic.elven.builders.Players;
import com.gaas.mystic.elven.outport.SaboteurGameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
class SaboteurGameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SaboteurGameRepository gameRepository;

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
        SaboteurGame game = givenGameStarted(A);

        mockMvc.perform(get("/api/games/{gameId}", game.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.players").isArray())
            .andExpect(jsonPath("$.players[0].playerName").value(A.getName()));
    }

    @Test
    public void testPlayerJoinGame() throws Exception {
        Player A = Players.defaultPlayer("A");
        SaboteurGame game = givenGameStarted(A);

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
        SaboteurGame game = givenGameStarted(A);

        mockMvc.perform(post("/api/games/{gameId}", game.getId())
                .contentType(APPLICATION_JSON)
                .content("""
                    {
                        "playerName": "A"
                    }"""))
            .andExpect(status().is4xxClientError());
    }

    // ATDD (1) 先寫驗收測試程式 （2) ------------
    @Test
    public void 修好其中一個工具耶() throws Exception {
        Player A = new Player(
            "A", "A",
            emptyList(),
            new Tool(ToolName.MINE_CART, true),
            new Tool(ToolName.LANTERN, true),
            new Tool(ToolName.PICK, false)
        );
        Player B = new Player(
            "B", "B",
            emptyList(),
            new Tool(ToolName.MINE_CART, true),
            new Tool(ToolName.LANTERN, true),
            new Tool(ToolName.PICK, true)
        );
        B.addHandCard(new Repair(ToolName.PICK));

        Player C = new Player(
            "C", "C",
            emptyList(),
            new Tool(ToolName.MINE_CART, true),
            new Tool(ToolName.LANTERN, true),
            new Tool(ToolName.PICK, true));

        SaboteurGame game = givenGameStarted(A, B, C);

        mockMvc.perform(post("/api/games/{gameId}:playCard", game.getId())
                .contentType(APPLICATION_JSON)
                .content("""
                    {  "cardType": "REPAIR",
                    "playerId": "B",
                      "handIndex": 0,
                      "targetPlayerId": "A"
                    }"""))
            .andExpect(status().isNoContent());

        var actualGame = findGameById(game.getId());
        Player actualA = actualGame.getPlayer("A");

        assertTrue(actualA.getTool(ToolName.MINE_CART).isAvailable());
        assertTrue(actualA.getTool(ToolName.LANTERN).isAvailable());
        assertTrue(actualA.getTool(ToolName.PICK).isAvailable());
    }

    private SaboteurGame givenGameStarted(SaboteurGame game) {
        return gameRepository.save(game);
    }

    private SaboteurGame givenGameStarted(Player... players) {
        return gameRepository.save(new SaboteurGame(asList(players)));
    }

    private SaboteurGame findGameById(String gameId) {
        // 從 repo 查出 game
        return gameRepository.findById(gameId).orElseThrow();
    }

    @Test
    public void 都好的硬要修() throws Exception {
        Player A = Players.defaultPlayer("A");
        Player B = Players.defaultPlayer("B");
        B.addHandCard(new Repair(ToolName.PICK));

        Player C = Players.defaultPlayer("C");

        SaboteurGame game = givenGameStarted(A, B, C);

        mockMvc.perform(post("/api/games/{gameId}:playCard", game.getId())
                .contentType(APPLICATION_JSON)
                .content("""
                    {   "cardType": "REPAIR",
                    "playerId": "B",
                      "handIndex": 0,
                      "targetPlayerId": "A"
                    }"""))
            .andExpect(status().isBadRequest());

        var actualGame = findGameById(game.getId());
        Player actualA = actualGame.getPlayer("A");

        assertTrue(actualA.getTool(ToolName.MINE_CART).isAvailable());
        assertTrue(actualA.getTool(ToolName.LANTERN).isAvailable());
        assertTrue(actualA.getTool(ToolName.PICK).isAvailable());
    }

    @Test
    public void 看終點底下有無金礦喔() throws Exception {
        Player A = Players.defaultPlayerBuilder("A")
            .hand(new MapCard()).build();
        Player B = Players.defaultPlayer("B");
        Player C = Players.defaultPlayer("C");

        SaboteurGame game = new SaboteurGame(asList(A, B, C)); //givenGameStarted?
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
            .hand(new Sabotage(ToolName.LANTERN)).build();

        SaboteurGame game = givenGameStarted(A, B, C);

        //When, C玩家打出最後一張手牌
        playSabotageCardSuccessfully(game, "C", 0, "A");

        // Then: assertions
        SaboteurGame actualGame = gameRepository.findById(game.getId()).orElseThrow();

        // TODO: 要區分好矮人/壞矮人
        assertTrue(actualGame.isGameOver());
    }

    @Test
    void 工具如果被破壞了就不能蓋路了() throws Exception {
        // Given
        Player A = Players.defaultPlayerBuilder("A")
            .hand(PathCard.T型死路()) // <--- code with me 在搞
            .tools(new Tool[]{
                new Tool(ToolName.MINE_CART, true),
                new Tool(ToolName.LANTERN, false),
                new Tool(ToolName.PICK, true)})
            .build();
        Player B = Players.defaultPlayer("B");
        Player C = Players.defaultPlayer("C");

        SaboteurGame game = givenGameStarted(A, B, C);

        // When
        playPathCard(game, "A", 0, 0, 1, false)
            .andExpect(status().isBadRequest());
    }

    @Test
    void 唉呀怎麼壞了() throws Exception {
        Player A = Players.defaultPlayerBuilder("A")
            .tools(new Tool[]{
                new Tool(ToolName.MINE_CART, true),
                new Tool(ToolName.LANTERN, true),
                new Tool(ToolName.PICK, true)})
            .build();
        Player B = Players.defaultPlayerBuilder("B")
            .hand(new Sabotage(ToolName.LANTERN))
            .build();

        SaboteurGame game = givenGameStarted(A, B, Players.defaultPlayer("C"));

        // when
        playSabotageCardSuccessfully(game, "B", 0, "A");

        var actualGame = gameRepository.findById(game.getId()).orElseThrow();
        Player player = actualGame.getPlayer(A.getId());
        assertFalse(player.getTool(ToolName.LANTERN).isAvailable());
        assertTrue(player.getTool(ToolName.MINE_CART).isAvailable());
        assertTrue(player.getTool(ToolName.PICK).isAvailable());
    }

    private void playSabotageCardSuccessfully(SaboteurGame game, String playerId, int handIndex, String targetPlayerId) throws Exception {
        mockMvc.perform(post("/api/games/{gameId}:playCard", game.getId())
                .contentType(APPLICATION_JSON)
                .content(format("""
                    {   "cardType": "SABOTAGE",
                    "playerId": "%s",
                      "handIndex": %d,
                      "targetPlayerId": "%s"
                    }""", playerId, handIndex, targetPlayerId)))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testRockFall() throws Exception {
        Player A = Players.defaultPlayerBuilder("A")
            .hand(new RockFall()).build();
        Player B = Players.defaultPlayer("B");
        Player C = Players.defaultPlayer("C");

        var game = givenGameStarted(new SaboteurGame("GameId", List.of(A, B, C),
            new Maze(List.of(new Path(0, 0, PathCard.十字路口()),
                new Path(0, 1, PathCard.十字路口()),
                new Path(-1, 1, PathCard.右彎(), true)))));

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
            .hand(PathCard.十字路口())
            .build();
        Player B = Players.defaultPlayerBuilder("B")
            .hand(PathCard.T型死路())
            .build();
        Player C = Players.defaultPlayerBuilder("C")
            .hand(PathCard.一字型())
            .hand(PathCard.右彎())
            .build();

        SaboteurGame game = givenGameStarted(A, B, C);

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

        Assertions.assertEquals(PathCard.十字路口(), maze.getPath(0, 1)
            .map(Path::getPathCard).orElseThrow());

        Assertions.assertEquals(PathCard.T型死路(), maze.getPath(0, 2)
            .map(Path::getPathCard).orElseThrow());

        Path actual右彎 = maze.getPath(-1, 1).orElseThrow();
        assertTrue(actual右彎.isFlipped());
    }

    private ResultActions playPathCard(SaboteurGame game, String playerId, int handIndex, int row, int col, boolean flipped) throws Exception {
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
