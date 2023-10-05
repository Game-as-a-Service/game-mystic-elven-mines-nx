package com.gaas.mystic.elven.domain.card;

import com.gaas.mystic.elven.domain.Maze;
import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.events.DomainEvent;
import com.gaas.mystic.elven.exceptions.ElvenGameException;
import lombok.Value;

import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;

/**
 * 道路卡
 */
@Value
public class PathCard implements Card {

    public static final String CROSS = "十字";
    public static final String DEAD_END_CROSS = "十字死路";
    public static final String LEFT_CURVE = "左彎";
    public static final String DEAD_END_LEFT_CURVE = "左彎死路";
    public static final String RIGHT_CURVE = "右彎";
    public static final String DEAD_END_RIGHT_CURVE = "右彎死路";
    public static final String HORIZONTAL = "橫線";
    public static final String DEAD_END_HORIZONTAL_1 = "橫線死路1";
    public static final String DEAD_END_HORIZONTAL_2 = "橫線死路2";
    public static final String STRAIGHT = "直線";
    public static final String DEAD_END_STRAIGHT_1 = "直線死路1";
    public static final String DEAD_END_STRAIGHT_2 = "直線死路2";
    public static final String HORIZONTAL_T = "橫T";
    public static final String DEAD_END_HORIZONTAL_T = "橫T死路";
    public static final String STRAIGHT_T = "直T";
    public static final String DEAD_END_STRAIGHT_T = "直T死路";


    String name;
    boolean[] path;

    public static PathCard cross() {
        return new PathCard(CROSS, new boolean[]{
            false, true, false,
            true, true, true,
            false, true, false});
    }

    public static PathCard deadEndCross() {
        return new PathCard(DEAD_END_CROSS, new boolean[]{
            false, true, false,
            true, false, true,
            false, true, false});
    }

    public static PathCard leftCurve() {
        return new PathCard(LEFT_CURVE, new boolean[]{
            false, true, false,
            true, true, false,
            false, false, false});
    }

    public static PathCard deadEndLeftCurve() {
        return new PathCard(DEAD_END_LEFT_CURVE, new boolean[]{
            false, true, false,
            true, false, false,
            false, false, false});
    }

    public static PathCard rightCurve() {
        return new PathCard(RIGHT_CURVE, new boolean[]{
            false, false, false,
            true, true, false,
            false, true, false});
    }

    public static PathCard deadEndRightCurve() {
        return new PathCard(DEAD_END_RIGHT_CURVE, new boolean[]{
            false, false, false,
            true, false, false,
            false, true, false});
    }

    public static PathCard horizontal() {
        return new PathCard(HORIZONTAL, new boolean[]{
            false, true, false,
            false, true, false,
            false, true, false});
    }

    public static PathCard deadEndHorizontal1() {
        return new PathCard(DEAD_END_HORIZONTAL_1, new boolean[]{
            false, true, false,
            false, false, false,
            false, false, false});
    }

    public static PathCard deadEndHorizontal2() {
        return new PathCard(DEAD_END_HORIZONTAL_2, new boolean[]{
            false, true, false,
            false, false, false,
            false, true, false});
    }

    public static PathCard straight() {
        return new PathCard(STRAIGHT, new boolean[]{
            false, false, false,
            true, true, true,
            false, false, false});
    }

    public static PathCard deadEndStraight1() {
        return new PathCard(DEAD_END_STRAIGHT_1, new boolean[]{
            false, false, false,
            true, false, false,
            false, false, false});
    }

    public static PathCard deadEndStraight2() {
        return new PathCard(DEAD_END_STRAIGHT_2, new boolean[]{
            false, false, false,
            true, false, true,
            false, false, false});
    }

    public static PathCard horizontalT() {
        return new PathCard(HORIZONTAL_T, new boolean[]{
            false, true, false,
            true, true, false,
            false, true, false});
    }

    public static PathCard deadEndHorizontalT() {
        return new PathCard(DEAD_END_HORIZONTAL_T, new boolean[]{
            false, true, false,
            true, false, false,
            false, true, false});
    }

    public static PathCard straightT() {
        return new PathCard(STRAIGHT_T, new boolean[]{
            false, true, false,
            true, true, true,
            false, false, false});
    }

    public static PathCard deadEndStraightT() {
        return new PathCard(DEAD_END_STRAIGHT_T, new boolean[]{
            false, true, false,
            true, false, true,
            false, false, false});
    }

    @Override
    public List<DomainEvent> execute(Card.Parameters parameters) {
        Parameters params = (Parameters) parameters;
        Player player = parameters.player;
        Card playCard = parameters.card;

        if (!player.allToolsAreAvailable()) {
            throw new ElvenGameException("Can play path cards only if his tools are all available.");
        }

        Maze maze = parameters.game.getMaze();
        if (playCard instanceof PathCard) {
            maze.putPath(params.row, params.col, (PathCard) playCard, params.flipped);
        } else {
            throw new IllegalStateException("Unexpected card type.");
        }
        return Collections.emptyList();

    }

    public static class Parameters extends Card.Parameters {
        int row, col;
        boolean flipped;

        public Parameters(String playerId, int handCardIndex, int row, int col, boolean flipped) {
            super(playerId, handCardIndex);
            this.row = row;
            this.col = col;
            this.flipped = flipped;
        }
    }

    public boolean hasRoad(int x, int y) {
        // x: -1 --> 0
        // 0 --> 1
        // 1 --> 2
        // y: 1 --> 0
        // 0 --> 1
        // -1 --> 2
        return path[abs(y - 1) * 3 + x + 1];
    }

    public boolean looksLike(String presentation) {
        char[] chars = presentation.replaceAll("[\r\n]", "").toCharArray();

        for (int i = 0; i < path.length; i++) {
            if (path[i] != (chars[i] == '0')) {
                return false;
            }
        }
        return true;
    }

}

