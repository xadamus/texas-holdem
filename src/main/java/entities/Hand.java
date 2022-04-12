package entities;

import java.util.*;

import static game.CardUtils.*;

public class Hand implements Comparable<Hand> {

    private final List<Card> cards;
    private final HandCategory category;

    public Hand(List<Card> cards, HandCategory category) {
        this.cards = cards;
        this.category = category;
    }

    public List<Card> getCards() {
        return cards;
    }

    public HandCategory getCategory() {
        return category;
    }

    @Override
    public int compareTo(Hand h) {
        if (this.category != h.category) {
            return Integer.compare(this.category.ordinal(), h.category.ordinal());
        } else {
            return this.category.compareCards(this.cards, h.cards);
        }
    }

    @Override
    public final int hashCode() {
        return Objects.hash(cards, category);
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Hand hand) {
            return Objects.equals(category, hand.category)
                    && ((cards == null && hand.cards == null)
                    || (cards != null && hand.cards != null
                    && cards.size() == hand.cards.size()
                    && cards.containsAll(hand.cards)));
        }
        return false;
    }
}
