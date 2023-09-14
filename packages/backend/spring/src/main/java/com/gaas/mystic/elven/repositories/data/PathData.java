package com.gaas.mystic.elven.repositories.data;

import com.gaas.mystic.elven.domain.card.GoalCard;
import com.gaas.mystic.elven.domain.Path;
import com.gaas.mystic.elven.domain.card.PathCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.lang.Boolean.TRUE;
import static lombok.AccessLevel.NONE;

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

    public static PathData toData(GoalCard goalCard) {
        PathCard pathCard = goalCard.getPathCard();
        return new PathData(goalCard.getRow(), goalCard.getCol(),
            pathCard.getName(), goalCard.isFlipped(), goalCard.isGold());
    }

    public Path toDomain() {
        PathCard pathCard = getPathCard();
        return gold == null ? new Path(row, col, pathCard, flipped) :
            new GoalCard(row, col, pathCard, gold);
    }

    private PathCard getPathCard() {
        return switch (pathCardName) {
            case PathCard.十字路口 -> PathCard.十字路口();
            case PathCard.T型死路 -> PathCard.T型死路();
            case PathCard.一字型 -> PathCard.一字型();
            case PathCard.右彎 -> PathCard.右彎();
            default -> throw new IllegalStateException("Unexpected value: " + pathCardName);
        };
    }

    public boolean isGold() {
        return TRUE.equals(gold);
    }
}
