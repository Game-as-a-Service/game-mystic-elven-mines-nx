package com.gaas.mystic.elven.presenters.views;

import com.gaas.mystic.elven.domain.card.Card;
import com.gaas.mystic.elven.domain.card.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardView {
    private CardType type;
    private String name;

    public static CardView toView(Card card) {
        return new CardView(card.getType(), card.getName());
    }
}
