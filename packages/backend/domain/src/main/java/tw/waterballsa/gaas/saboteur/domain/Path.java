package tw.waterballsa.gaas.saboteur.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author johnny@waterballsa.tw
 */
@Data
@AllArgsConstructor
public class Path {
    protected final int row, col;
    protected final PathCard pathCard; // 十字路口, T...
    protected final boolean flipped;

    public Path(int row, int col, PathCard pathCard) {
        this(row, col, pathCard, false);
    }

    public boolean looksLike(String presentation) {
        return pathCard.looksLike(presentation);
    }

    public Set<Point> getPoints() {
        Set<Point> points = new HashSet<>();
        if (pathCard.hasRoad(0, 0)) {
            points.add(middle());
        }
        if (pathCard.hasRoad(0, 1)) {
            points.add(flipped ? bottom() : top());
        }
        if (pathCard.hasRoad(0, -1)) {
            points.add(flipped ? top() : bottom());
        }
        if (pathCard.hasRoad(-1, 0)) {
            points.add(flipped ? right() : left());
        }
        if (pathCard.hasRoad(1, 0)) {
            points.add(flipped ? left() : right());
        }
        return points;
    }

    public Point middle() {
        return new Point(col * 3, row * 3);
    }

    public Point top() {
        return new Point(col * 3, row * 3 + 1);
    }

    public Point bottom() {
        return new Point(col * 3, row * 3 - 1);
    }

    public Point left() {
        return new Point(col * 3 - 1, row * 3);
    }

    public Point right() {
        return new Point(col * 3 + 1, row * 3);
    }

    public boolean contains(Point point) {
        return getPoints().contains(point);
    }
}
