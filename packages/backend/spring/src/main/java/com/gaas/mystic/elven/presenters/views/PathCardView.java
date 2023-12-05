package com.gaas.mystic.elven.presenters.views;

import com.gaas.mystic.elven.domain.Path;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PathCardView {

    protected Integer row;
    private Integer col;
    private String name;
    private Boolean flipped;

    public static PathCardView toView(Path card) {
        return PathCardView.builder()
            .row(card.getRow())
            .col(card.getCol())
            .name(card.getPathCard().getName())
            .flipped(card.isFlipped())
            .build();
    }

}
