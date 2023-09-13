package com.gaas.mystic.elven.repositories;

import com.gaas.mystic.elven.domain.ElvenGame;
import com.gaas.mystic.elven.outport.ElvenGameRepository;
import com.gaas.mystic.elven.repositories.dao.ElvenGameDAO;
import com.gaas.mystic.elven.repositories.data.ElvenGameData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.gaas.mystic.elven.repositories.data.ElvenGameData.toData;

/**
 * Tech Stack
 * DDD
 *  Event storming
 *  戰術設計模式
 *  Repository Pattern...
 *  OOAD
 * BDD
 *  Example Mapping
 *  ATDD 驗收測試驅動開發
 * 架構：CA
 * DevOps:
 *  Walking Skeleton
 * Software Engineering Practice Stack
 * @author johnny@waterballsa.tw
 */
@Repository
@RequiredArgsConstructor
public class SpringElvenGameRepository implements ElvenGameRepository {
    private final ElvenGameDAO dao;

    @Override
    public ElvenGame save(ElvenGame elvenGame) {
        ElvenGameData data = toData(elvenGame);
        ElvenGameData savedData = dao.save(data);
        return savedData.toDomain();
    }

    @Override
    public Optional<ElvenGame> findById(String id) {
        return dao.findById(id)
                .map(ElvenGameData::toDomain);
    }
}
