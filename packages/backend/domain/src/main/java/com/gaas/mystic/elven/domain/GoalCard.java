package com.gaas.mystic.elven.domain;

import com.gaas.mystic.elven.domain.card.PathCard;
import lombok.Getter;
import lombok.Setter;

/**
 * 終點卡 (3 張)
 * 目標 * 1：神聖遺物 - Sacred Relic
 * 其他 * 2：魔法干擾 - Magical Interference
 */
public class GoalCard extends Path {

    @Getter
    @Setter
    // 是否已被翻開
    private boolean isShowdown = false;
    private final boolean isGoal;

    public GoalCard(int row, int col, boolean isGoal) {
        // 預設是十字路口
        this(row, col, PathCard.cross(), isGoal);
    }

    public GoalCard(int row, int col, PathCard path, boolean isGoal) {
        super(row, col, path, false);
        this.isGoal = isGoal;
    }

    public boolean isGoal() {
        return isGoal;
    }

}
