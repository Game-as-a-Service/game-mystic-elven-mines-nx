package tw.waterballsa.gaas.saboteur.spring.repositories.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tw.waterballsa.gaas.saboteur.domain.Destination;
import tw.waterballsa.gaas.saboteur.domain.Path;
import tw.waterballsa.gaas.saboteur.domain.PathCard;

import static java.lang.Boolean.TRUE;
import static lombok.AccessLevel.NONE;
import static tw.waterballsa.gaas.saboteur.domain.PathCard.*;

/**
 * @author johnny@waterballsa.tw
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PathData {
    private int row;
    private int col;
    private String pathCardName;
    private boolean flipped;

    // Destination
    @Getter(NONE)
    private Boolean gold;

    public static PathData toData(Path path) {
        PathCard pathCard = path.getPathCard();
        return new PathData(path.getRow(), path.getCol(),
            pathCard.getName(), path.isFlipped(), null);
    }

    public static PathData toData(Destination destination) {
        PathCard pathCard = destination.getPathCard();
        return new PathData(destination.getRow(), destination.getCol(),
            pathCard.getName(), destination.isFlipped(), destination.isGold());
    }

    public Path toDomain() {
        PathCard pathCard = getPathCard();
        return gold == null ? new Path(row, col, pathCard, flipped) :
            new Destination(row, col, pathCard, gold);
    }

    private PathCard getPathCard() {
        return switch (pathCardName) {
            case 十字路口 -> 十字路口();
            case T型死路 -> T型死路();
            case 一字型 -> 一字型();
            case 右彎 -> 右彎();
            default -> throw new IllegalStateException("Unexpected value: " + pathCardName);
        };
    }

    public boolean isGold() {
        return TRUE.equals(gold);
    }
}
