package com.gaas.mystic.elven.repositories;

import com.gaas.mystic.elven.SaboteurGame;
import com.gaas.mystic.elven.outport.SaboteurGameRepository;
import com.gaas.mystic.elven.repositories.dao.SaboteurGameDAO;
import com.gaas.mystic.elven.repositories.data.SaboteurGameData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.gaas.mystic.elven.repositories.data.SaboteurGameData.toData;

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
public class SpringSaboteurGameRepository implements SaboteurGameRepository {
    private final SaboteurGameDAO dao;

    @Override
    public SaboteurGame save(SaboteurGame saboteurGame) {
        SaboteurGameData data = toData(saboteurGame);
        SaboteurGameData savedData = dao.save(data);
        return savedData.toDomain();
    }

    @Override
    public Optional<SaboteurGame> findById(String id) {
        return dao.findById(id)
                .map(SaboteurGameData::toDomain);
    }
}
