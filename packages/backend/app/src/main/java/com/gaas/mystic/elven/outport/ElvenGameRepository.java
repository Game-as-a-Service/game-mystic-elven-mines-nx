package com.gaas.mystic.elven.outport;

import com.gaas.mystic.elven.domain.ElvenGame;

import java.util.Optional;

/**
 * @author johnny@waterballsa.tw
 */
public interface ElvenGameRepository {
    ElvenGame save(ElvenGame elvenGame);

    Optional<ElvenGame> findById(String id);
}
