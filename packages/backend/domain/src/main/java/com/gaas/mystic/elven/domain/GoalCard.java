package com.gaas.mystic.elven.domain;

import com.gaas.mystic.elven.domain.card.PathCard;

/**
 * 終點卡 (3 張)
 * 目標 * 1：神聖遺物 - Sacred Relic
 * 其他 * 2：魔法干擾 - Magical Interference
 */
public class GoalCard extends Path {
    private final boolean isGoal;

    public GoalCard(int row, int col, boolean isGoal) {
        this(row, col, PathCard.cross(), isGoal
                /*預設是十字路口*/);
    }

    public GoalCard(int row, int col, PathCard path, boolean isGoal) {
        super(row, col, path, false);
        this.isGoal = isGoal;
    }

    public boolean isGoal() {
        return isGoal;
    }

}
