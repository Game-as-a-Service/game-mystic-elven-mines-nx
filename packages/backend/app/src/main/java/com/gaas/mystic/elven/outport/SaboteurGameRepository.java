package com.gaas.mystic.elven.outport;

import com.gaas.mystic.elven.SaboteurGame;

import java.util.Optional;

/**
 * @author johnny@waterballsa.tw
 */
public interface SaboteurGameRepository {
    SaboteurGame save(SaboteurGame saboteurGame);

    Optional<SaboteurGame> findById(String id);
}
