package com.gaas.mystic.elven.controllers;

import com.gaas.mystic.elven.Player;
import com.gaas.mystic.elven.SaboteurGame;
import com.gaas.mystic.elven.builders.Players;
import com.gaas.mystic.elven.repositories.SpringSaboteurGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class WalkingSkeletonController {

    private final SpringSaboteurGameRepository repository;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/createGame/{gameId}")
    public String createGame(@PathVariable String gameId) {
        // player
        Player playerA = Players.defaultPlayerBuilder("A").build();
        Player playerB = Players.defaultPlayerBuilder("B").build();
        Player playerC = Players.defaultPlayerBuilder("C").build();
        // create game
        var game = new SaboteurGame(gameId, List.of(playerA, playerB, playerC));
        // save game
        repository.save(game);

        return "success";
    }

    @ResponseBody
    @GetMapping("/findGame/{gameId}")
    public ResponseEntity<?> findGame(@PathVariable String gameId) {
        return repository.findById(gameId)
            .map(ResponseEntity::ok)
            .orElseGet(notFound()::build);
    }

}
