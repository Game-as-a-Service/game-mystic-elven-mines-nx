package com.gaas.mystic.elven.repositories.data;

import com.gaas.mystic.elven.domain.Deck;
import com.gaas.mystic.elven.domain.card.*;
import com.gaas.mystic.elven.domain.tool.ToolName;
import com.gaas.mystic.elven.exceptions.ElvenGameException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeckData {

    private List<CardData> cards;

    public static DeckData toData(Deck deck) {
        return DeckData.builder()
            .cards(deck.getCards().stream().map(CardData::toData).toList())
            .build();
    }

    public Deck toDomain() {
        Deck deck = new Deck();
        deck.getCards().addAll(cards.stream().map(CardData::toDomain).toList());
        return deck;
    }





}
