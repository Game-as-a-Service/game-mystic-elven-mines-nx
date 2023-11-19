package com.gaas.mystic.elven.domain;

import com.gaas.mystic.elven.domain.card.PathCard;
import com.gaas.mystic.elven.exceptions.ElvenGameException;
import com.gaas.mystic.elven.utils.LoggingUtils;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Stream;

import static com.gaas.mystic.elven.utils.StreamUtils.findFirst;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * 迷宮 (地圖)
 */
public class Maze {

    // 道路卡片
    private final List<Path> paths;
    private final Path origin;
    private final Set<Point> points;

    public Maze() {
        this(singletonList(new Path(0, 0, PathCard.cross())));
    }

    public Maze(List<Path> paths) {
        this.paths = new ArrayList<>(paths);
        this.points = paths.stream()
                .flatMap(p -> p.getPoints().stream())
                .collect(toSet());
        this.origin = findFirst(paths, p -> p.getRow() == 0 && p.getCol() == 0)
                .orElseThrow(() -> new ElvenGameException("origin should exist at (0,0)."));
    }

    public Optional<Path> getPath(int row, int col) {
        return findFirst(paths, p -> p.getRow() == row && p.getCol() == col);
    }

    public void putPath(int row, int col, PathCard pathCard, boolean flipped) {
        Path path = new Path(row, col, pathCard, flipped);

        if (canConnectToOrigin(path) /*&& doesNotExistPath(row, col)*/) {
            // 前提/不變：(1) 這張新的牌 PathCard 是不是真的連得回去起始點 (2) 該位置不能存在 path
            // 深度優先 來一張一張遞迴地去看 他能不能連回起點

            paths.add(path);
            points.addAll(path.getPoints());
        } else {
            throw new ElvenGameException("Cannot connect to the origin");
        }

    }

    // [paths...] -----> [points]
    private boolean canConnectToOrigin(Path newPath) {
        Set<Point> points = new HashSet<>(this.points);
        points.addAll(newPath.getPoints());
        LoggingUtils.log(points);
        boolean isConnected = canConnectToOriginDFS(newPath.middle(),
                new HashSet<>(), points);
        System.out.println(isConnected? "(Success)" : "(Failed)");
        return isConnected;
    }

    private boolean canConnectToOriginDFS(Point point /*搜尋點*/,
                                          Set<Point> searched,
                                          Set<Point> allPoints) {
        searched.add(point);

        List<Point> adjacentPoints = getAdjacentPoints(point, allPoints);

        for (Point p : adjacentPoints) {
            if (origin.contains(p) ||
                    !searched.contains(p) &&
                            canConnectToOriginDFS(p, searched, allPoints)) {
                return true;
            }
        }
        return false;

    }

    private List<Point> getAdjacentPoints(Point point, Collection<Point> points) {
        return Stream.of(new Point(point.x + 1, point.y),
                        new Point(point.x - 1, point.y),
                        new Point(point.x, point.y + 1),
                        new Point(point.x, point.y - 1))
                .filter(points::contains)
                .collect(toList());
    }

    public List<Path> getPaths() {
        return paths;
    }

    public Path getOrigin() {
        return origin;
    }

    public void removePath(int row, int col) {
        paths.removeIf(p -> p.getRow() == row && p.getCol() == col);
    }
}
