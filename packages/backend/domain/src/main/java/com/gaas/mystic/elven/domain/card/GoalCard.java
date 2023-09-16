package com.gaas.mystic.elven.domain.card;

import com.gaas.mystic.elven.domain.Path;

/**
 * 終點卡
 */
public class GoalCard extends Path {
    private final boolean isGold;

    public GoalCard(int row, int col, boolean isGold) {
        this(row, col, PathCard.cross(), isGold
                /*預設是十字路口*/);
    }

    public GoalCard(int row, int col, PathCard path, boolean isGold) {
        super(row, col, path, false);
        this.isGold = isGold;
    }

    public boolean isGold() {
        return isGold;
    }

}
