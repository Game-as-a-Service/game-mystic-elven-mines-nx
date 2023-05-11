package tw.waterballsa.gaas.saboteur.domain;

import org.junit.jupiter.api.Test;
import tw.waterballsa.gaas.saboteur.domain.exceptions.SaboteurGameException;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

class MazeTest {
    @Test
    void originShouldExistsAtR0C0() {
        assertThrows(SaboteurGameException.class,
                () -> new Maze(singletonList(new Path(0, 1, PathCard.十字路口()))));
        assertThrows(SaboteurGameException.class,
                () -> new Maze(singletonList(new Path(0, 4, PathCard.十字路口()))));

        assertDoesNotThrow(() -> new Maze(singletonList(new Path(0, 0, PathCard.十字路口()))));
        assertDoesNotThrow(() -> new Maze(singletonList(new Path(0, 0, PathCard.右彎()))));
        assertDoesNotThrow(() -> new Maze(singletonList(new Path(0, 0, PathCard.一字型()))));
    }

}