package tw.waterballsa.gaas.saboteur.app.outport;

import tw.waterballsa.gaas.saboteur.domain.SaboteurGame;

import java.util.Optional;

/**
 * @author johnny@waterballsa.tw
 */
public interface SaboteurGameRepository {
    SaboteurGame save(SaboteurGame saboteurGame);

    Optional<SaboteurGame> findById(String id);
}
