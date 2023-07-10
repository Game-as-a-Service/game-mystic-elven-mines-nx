package tw.waterballsa.gaas.saboteur.domain;

import lombok.Value;
import tw.waterballsa.gaas.saboteur.domain.events.DomainEvent;
import tw.waterballsa.gaas.saboteur.domain.exceptions.SaboteurGameException;

import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.System.lineSeparator;

/**
 * @author johnny@waterballsa.tw
 */
@Value
public class PathCard implements Card {
    public static final String 十字路口 = "十字路口";
    public static final String T型死路 = "T型死路";
    public static final String 一字型 = "一字型";
    public static final String 右彎 = "右彎";
    String name;
    boolean[] path;

    public static PathCard 十字路口() {
        return new PathCard(十字路口, new boolean[]{
            false, true, false,
            true, true, true,
            false, true, false});
    }

    public static PathCard T型死路() {
        return new PathCard(T型死路, new boolean[]{
            false, true, false,
            true, false, true,
            false, false, false});
    }

    public static PathCard 一字型() {
        return new PathCard(一字型, new boolean[]{
            false, false, false,
            true, true, true,
            false, false, false});
    }

    public static PathCard 右彎() {
        return new PathCard(右彎, new boolean[]{
            false, false, false,
            true, true, false,
            false, true, false});
    }

    @Override
    public List<DomainEvent> execute(Card.Parameters parameters) {
        Parameters params = (Parameters) parameters;
        Player player = parameters.player;
        Card playCard = parameters.card;

        if (!player.allToolsAreAvailable()) {
            throw new SaboteurGameException("Can play path cards only if his tools are all available.");
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

