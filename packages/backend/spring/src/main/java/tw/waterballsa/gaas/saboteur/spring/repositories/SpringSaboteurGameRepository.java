package tw.waterballsa.gaas.saboteur.spring.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tw.waterballsa.gaas.saboteur.app.outport.SaboteurGameRepository;
import tw.waterballsa.gaas.saboteur.domain.SaboteurGame;
import tw.waterballsa.gaas.saboteur.spring.repositories.dao.SaboteurGameDAO;
import tw.waterballsa.gaas.saboteur.spring.repositories.data.SaboteurGameData;

import java.util.Optional;

import static tw.waterballsa.gaas.saboteur.spring.repositories.data.SaboteurGameData.toData;

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
