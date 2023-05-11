package tw.waterballsa.gaas.saboteur.domain.commons.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tw.waterballsa.gaas.saboteur.domain.Path;
import tw.waterballsa.gaas.saboteur.domain.PathCard;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;
import static tw.waterballsa.gaas.saboteur.domain.commons.utils.LoggingUtils.log;

class LoggingUtilsTest {


    private ByteArrayOutputStream out;

    @BeforeEach
    void setup() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @Test
    void testLog_T型死路() {
        log(new Path(0, 2, PathCard.T型死路()).getPoints());

        assertEquals("=====\n" +
                        "-O-\n" +
                        "O-O"
                , getOutputString().strip());

    }

    @Test
    void testLog() {
        var points = Stream.of(new Path(0, 0, PathCard.十字路口()),
                        new Path(0, 1, PathCard.十字路口()),
                        new Path(0, 2, PathCard.T型死路()),
                        new Path(1, 1, PathCard.右彎()),
                        new Path(-1, 1, PathCard.右彎(), true))
                .flatMap(p -> p.getPoints().stream())
                .collect(toSet());
        log(points);

        assertEquals("=====\n" +
                        "---OO----\n" +
                        "----O----\n" +
                        "-O--O--O-\n" +
                        "OOOOOOO-O\n" +
                        "-O--O----\n" +
                        "----O----\n" +
                        "----OO---"
                , getOutputString().strip());

    }

    private String getOutputString() {
        return out.toString();
    }

}