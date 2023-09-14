package com.gaas.mystic.elven.repositories.data;

import com.gaas.mystic.elven.domain.Maze;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

import static com.gaas.mystic.elven.utils.StreamUtils.mapToList;

/**
 * @author johnny@waterballsa.tw
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MazeData {
    private Collection<PathData> paths;

    public Maze toDomain() {
        var paths = mapToList(this.paths, PathData::toDomain);
        return new Maze(paths);
    }

    public static MazeData toData(Maze maze) {
        var paths = mapToList(maze.getPaths(), PathData::toData);
        return new MazeData(paths);
    }
}
