package tw.waterballsa.gaas.saboteur.domain.commons.utils;

import lombok.experimental.UtilityClass;

import java.awt.*;
import java.util.Collection;

import static java.lang.System.lineSeparator;

/**
 * @author johnny@waterballsa.tw
 */
@UtilityClass
public class LoggingUtils {
    public static void log(Collection<Point> points) {
        int minX = points.stream().mapToInt(p -> p.x).min().orElseThrow();
        int maxX = points.stream().mapToInt(p -> p.x).max().orElseThrow();
        int minY = points.stream().mapToInt(p -> p.y).min().orElseThrow();
        int maxY = points.stream().mapToInt(p -> p.y).max().orElseThrow();

        boolean[][] maze = new boolean[maxY - minY + 1][maxX - minX + 1];
        points.forEach(p -> maze[p.y - minY][p.x - minX] = true);

        System.out.println("=====");
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = maze.length - 1; y >= 0; y--) {
            for (boolean hasRoad : maze[y]) {
                stringBuilder.append(hasRoad ? "O" : "-");
            }
            stringBuilder.append(lineSeparator());
        }
        System.out.println(stringBuilder.substring(0, stringBuilder.length() - 1));
    }

}
