package com.gaas.mystic.elven.presenters.views;

import com.gaas.mystic.elven.domain.GoalCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalCardView {
    private Integer row;
    private Integer col;
    private String name;
    private Boolean flipped;
    private Boolean isShowdown;
    private Boolean isGoal;

    public static GoalCardView toView(GoalCard card) {
        return GoalCardView.builder()
            .row(card.getRow())
            .col(card.getCol())
            .name(card.getPathCard().getName())
            .flipped(card.isFlipped())
            .isShowdown(card.isShowdown())
            .isGoal(card.isShowdown() ? card.isGoal() : null)
            .build();
    }

}
