package tw.waterballsa.gaas.saboteur.spring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.waterballsa.gaas.saboteur.domain.Player;
import tw.waterballsa.gaas.saboteur.domain.SaboteurGame;
import tw.waterballsa.gaas.saboteur.spring.repositories.SpringSaboteurGameRepository;

import java.util.List;
import java.util.Optional;

import static tw.waterballsa.gaas.saboteur.domain.builders.Players.defaultPlayerBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class WalkingSkeletonController {

    private SpringSaboteurGameRepository repository;

    @Autowired
    public WalkingSkeletonController(SpringSaboteurGameRepository repository) {
        this.repository = repository;
    }

    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @ResponseBody
    @GetMapping("/createGame/{gameId}")
    public String createGame(@PathVariable String gameId) {
        // player
        Player playerA = defaultPlayerBuilder("A").build();
        Player playerB = defaultPlayerBuilder("B").build();
        Player playerC = defaultPlayerBuilder("C").build();
        // create game
        var game = new SaboteurGame(gameId, List.of(playerA, playerB, playerC));
        // save game
        repository.save(game);

        return "success";
    }

    @ResponseBody
    @GetMapping("/findGame/{gameId}")
    public String findGame(@PathVariable String gameId) {
        // find game
        Optional<SaboteurGame> game = repository.findById(gameId);
        // if game isn't exist
        if (game.isEmpty()) {
            return "The game isn't exist.";
        }
        // show game
        return new ObjectMapper().valueToTree(game.get()).toString();
    }

}
