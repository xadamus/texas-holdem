package entities;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Hand implements Comparable<Hand> {

    private final List<Card> cards;
    private final List<Card> supplementaryCards;
    private final HandCategory category;

    public Hand(List<Card> cards, HandCategory category) {
        this(cards, Collections.emptyList(), category);
    }

    public Hand(List<Card> cards, List<Card> supplementaryCards, HandCategory category) {
        this.cards = cards;
        this.supplementaryCards = supplementaryCards;
        this.category = category;
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<Card> getSupplementaryCards() {
        return supplementaryCards;
    }

    public HandCategory getCategory() {
        return category;
    }

    @Override
    public int compareTo(Hand h) {
        if (this.category != h.category) {
            return Integer.compare(this.category.ordinal(), h.category.ordinal());
        } else {
            return this.category.compareCards(this, h);
        }
    }

    @Override
    public final int hashCode() {
        return Objects.hash(cards, supplementaryCards, category);
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Hand hand) {
            return Objects.equals(category, hand.category) &&
                    Objects.equals(cards, hand.cards) &&
                    Objects.equals(supplementaryCards, hand.supplementaryCards);
        }
        return false;
    }
}
