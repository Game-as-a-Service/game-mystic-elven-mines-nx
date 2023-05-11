package tw.waterballsa.gaas.saboteur.spring.repositories.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tw.waterballsa.gaas.saboteur.spring.repositories.data.SaboteurGameData;

/**
 * @author johnny@waterballsa.tw
 */
@Repository
public interface SaboteurGameDAO extends MongoRepository<SaboteurGameData, String> {
}
