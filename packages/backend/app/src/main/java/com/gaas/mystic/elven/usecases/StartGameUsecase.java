package com.gaas.mystic.elven.usecases;

import com.gaas.mystic.elven.exceptions.NotFoundException;
import com.gaas.mystic.elven.outport.ElvenGameRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class StartGameUsecase {

    private final ElvenGameRepository elvenGameRepository;

    public void execute(String gameId) {
        // 查
        var game = elvenGameRepository.findById(gameId)
            .orElseThrow(() -> new NotFoundException("Game not found"));

        // 改
        game.startGame();

        // 存
        elvenGameRepository.save(game);
    }
}
