package com.gaas.mystic.elven.domain;

import com.gaas.mystic.elven.domain.card.Card;
import com.gaas.mystic.elven.domain.card.PathCard;
import com.gaas.mystic.elven.exceptions.ElvenGameException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Deck {

    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public void prepareDeck() {
        // clear deck
        cards.clear();
        // add cards
        addPathCards();
        addDeadEndPathCards();
        // shuffle
        Collections.shuffle(cards);
    }

    private void addPathCards() {
        for (int i = 0; i < 5; i++) {
            cards.add(PathCard.cross());
            cards.add(PathCard.rightCurve());
            cards.add(PathCard.horizontalT());
            cards.add(PathCard.straightT());
        }
        for (int i = 0; i < 4; i++) {
            cards.add(PathCard.leftCurve());
            cards.add(PathCard.horizontal());
        }
        for (int i = 0; i < 3; i++) {
            cards.add(PathCard.straight());
        }
    }

    private void addDeadEndPathCards() {
        cards.add(PathCard.deadEndCross());
        cards.add(PathCard.deadEndLeftCurve());
        cards.add(PathCard.deadEndRightCurve());
        cards.add(PathCard.deadEndHorizontal1());
        cards.add(PathCard.deadEndHorizontal2());
        cards.add(PathCard.deadEndStraight1());
        cards.add(PathCard.deadEndStraight2());
        cards.add(PathCard.deadEndHorizontalT());
        cards.add(PathCard.deadEndStraightT());
    }

    public Card draw() {
        if (cards.isEmpty()) {
            throw new ElvenGameException("Deck is empty");
        }
        return cards.remove(0);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    public List<Card> getCards() {
        return List.copyOf(cards);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

}
