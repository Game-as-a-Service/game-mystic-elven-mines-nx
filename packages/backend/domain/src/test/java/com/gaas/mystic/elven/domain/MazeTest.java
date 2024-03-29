package com.gaas.mystic.elven.domain;

import com.gaas.mystic.elven.domain.card.PathCard;
import com.gaas.mystic.elven.exceptions.ElvenGameException;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MazeTest {
    @Test
    void originShouldExistsAtR0C0() {
        assertThrows(ElvenGameException.class,
                () -> new Maze(singletonList(new Path(0, 1, PathCard.cross()))));
        assertThrows(ElvenGameException.class,
                () -> new Maze(singletonList(new Path(0, 4, PathCard.cross()))));

        assertDoesNotThrow(() -> new Maze(singletonList(new Path(0, 0, PathCard.cross()))));
        assertDoesNotThrow(() -> new Maze(singletonList(new Path(0, 0, PathCard.rightCurve()))));
        assertDoesNotThrow(() -> new Maze(singletonList(new Path(0, 0, PathCard.straight()))));
    }

}
