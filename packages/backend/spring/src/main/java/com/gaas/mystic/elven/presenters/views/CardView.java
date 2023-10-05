package com.gaas.mystic.elven.presenters.views;

import com.gaas.mystic.elven.domain.card.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardView {
    private String type;
    private String name;

    public static CardView toView(Card card) {
        return CardView.builder()
            .type(card.getType().name())
            .name(card.getName())
            .build();
    }
}
