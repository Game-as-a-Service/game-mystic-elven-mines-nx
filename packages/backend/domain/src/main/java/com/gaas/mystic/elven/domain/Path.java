package com.gaas.mystic.elven.domain;

import com.gaas.mystic.elven.domain.card.PathCard;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 道路 (已放在地圖上的道路卡)
 */
@Data
@AllArgsConstructor
public class Path {
    // 地圖的 index
    protected final int row, col;
    // 道路卡，可以分為十字路口, T 型... 等
    protected final PathCard pathCard;
    // 是否翻轉 (只能轉 180 度)
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
