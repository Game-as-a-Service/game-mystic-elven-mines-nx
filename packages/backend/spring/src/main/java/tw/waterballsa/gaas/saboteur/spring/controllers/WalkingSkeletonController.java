package tw.waterballsa.gaas.saboteur.spring.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.waterballsa.gaas.saboteur.domain.Player;
import tw.waterballsa.gaas.saboteur.domain.SaboteurGame;
import tw.waterballsa.gaas.saboteur.spring.repositories.SpringSaboteurGameRepository;

import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
import static tw.waterballsa.gaas.saboteur.domain.builders.Players.defaultPlayerBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class WalkingSkeletonController {

    private final SpringSaboteurGameRepository repository;

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("Hello, world!");
    }

    @GetMapping("/createGame/{gameId}")
    public ResponseEntity<?> createGame(@PathVariable String gameId) {
        // player
        Player playerA = defaultPlayerBuilder("A").build();
        Player playerB = defaultPlayerBuilder("B").build();
        Player playerC = defaultPlayerBuilder("C").build();
        // create game
        var game = new SaboteurGame(gameId, List.of(playerA, playerB, playerC));
        // save game
        repository.save(game);

        return ResponseEntity.ok("Game created.");
    }

    @ResponseBody
    @GetMapping("/findGame/{gameId}")
    public ResponseEntity<?> findGame(@PathVariable String gameId) {
        return repository.findById(gameId)
            .map(ResponseEntity::ok)
            .orElseGet(notFound()::build);
    }

}
