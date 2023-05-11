package tw.waterballsa.gaas.saboteur.spring.repositories.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.waterballsa.gaas.saboteur.domain.Maze;

import java.util.Collection;

import static tw.waterballsa.gaas.saboteur.domain.commons.utils.StreamUtils.mapToList;

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
