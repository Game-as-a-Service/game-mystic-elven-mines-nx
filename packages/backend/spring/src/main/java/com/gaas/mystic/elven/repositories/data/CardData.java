package com.gaas.mystic.elven.repositories.data;

import com.gaas.mystic.elven.*;
import com.gaas.mystic.elven.exceptions.SaboteurGameException;
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

    // REPAIR & SABOTAGE
    private ToolName toolName;

    // DESTINATION
    private Integer row;
    private Integer col;
    private String name;
    private boolean[] path;

    private Boolean isGold;

    public static CardData toData(Card card) {
        if (card instanceof Repair r) {
            return toRepairCardData(r);
        } else if (card instanceof Sabotage s) {
            return toSabotageCardData(s);
        } else if (card instanceof RockFall r) {
            return toRockFallCardData(r);
        } else if (card instanceof PathCard p) {
            return toPathCardData(p);
        } else if (card instanceof MapCard) {
            return toMapCardData();
        }
        throw new SaboteurGameException("unsupported card class " + card.getClass());
    }

    private static CardData toRepairCardData(Repair card) {
        return CardData.builder()
            .type(Type.REPAIR)
            .toolName(card.getToolName())
            .build();
    }

    private static CardData toSabotageCardData(Sabotage card) {
        return CardData.builder()
            .type(Type.SABOTEUR)
            .toolName(card.getToolName())
            .build();
    }

    private static CardData toRockFallCardData(RockFall card) {
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
            case REPAIR -> new Repair(requireNonNull(toolName));
            case SABOTEUR -> new Sabotage(requireNonNull(toolName));
            case ROCK_FALL -> new RockFall();
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
        REPAIR, SABOTEUR, ROCK_FALL, MAP, DESTINATION
    }

}
