package com.gaas.mystic.elven.repositories.data;

import com.gaas.mystic.elven.domain.card.*;
import com.gaas.mystic.elven.domain.tool.ToolName;
import com.gaas.mystic.elven.exceptions.ElvenGameException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.util.Objects.requireNonNull;

/**
 * @author johnny@waterballsa.tw
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardData {
    private Type type;

    // FIX & BROKEN
    private ToolName toolName;

    // DESTINATION
    private Integer row;
    private Integer col;
    private String name;
    private boolean[] path;

    private Boolean isGold;

    public static CardData toData(Card card) {
        if (card instanceof FixCard r) {
            return toFixCardData(r);
        } else if (card instanceof BrokenCard s) {
            return toBrokenCardData(s);
        } else if (card instanceof RockFallCard r) {
            return toRockFallCardData(r);
        } else if (card instanceof PathCard p) {
            return toPathCardData(p);
        } else if (card instanceof MapCard) {
            return toMapCardData();
        }
        throw new ElvenGameException("unsupported card class " + card.getClass());
    }

    private static CardData toFixCardData(FixCard card) {
        return CardData.builder()
            .type(Type.FIX)
            .toolName(card.getToolName())
            .build();
    }

    private static CardData toBrokenCardData(BrokenCard card) {
        return CardData.builder()
            .type(Type.BROKEN)
            .toolName(card.getToolName())
            .build();
    }

    private static CardData toRockFallCardData(RockFallCard card) {
        return CardData.builder()
            .type(Type.ROCK_FALL)
            .build();
    }

    private static CardData toPathCardData(PathCard card) {
        return CardData.builder()
            .type(Type.DESTINATION)
            .name(card.getName())
            .path(card.getPath())
            .build();
    }

    private static CardData toMapCardData() {
        return CardData.builder()
            .type(Type.MAP)
            .build();
    }

    public Card toDomain() {
        return switch (type) {
            case FIX -> new FixCard(requireNonNull(toolName));
            case BROKEN -> new BrokenCard(requireNonNull(toolName));
            case ROCK_FALL -> new RockFallCard();
            case MAP -> new MapCard();
            case DESTINATION -> toPathCard();
        };
    }

    private PathCard toPathCard() {
        return switch (name) {
            case PathCard.十字路口 -> PathCard.十字路口();
            case PathCard.T型死路 -> PathCard.T型死路();
            case PathCard.一字型 -> PathCard.一字型();
            case PathCard.右彎 -> PathCard.右彎();
            default -> new PathCard(name, path);
        };
    }

    public enum Type {
        FIX, BROKEN, ROCK_FALL, MAP, DESTINATION
    }

}
