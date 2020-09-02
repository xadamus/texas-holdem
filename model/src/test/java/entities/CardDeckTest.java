package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;

class CardDeckTest {

    CardDeck cardDeck;
    EnumMap<Card.Rank, Integer> ranks;
    EnumMap<Card.Suit, Integer> suits;

    @BeforeEach
    void setUp() {
        cardDeck = new CardDeck();
        ranks = new EnumMap<>(Card.Rank.class);
        suits = new EnumMap<>(Card.Suit.class);
    }

    /**
     * Tests if card deck returns a proper set of cards.
     */
    @Test
    void getGameCards() {
        Arrays.stream(cardDeck.getCards()).forEach(card -> {
            ranks.merge(card.getRank(), 1, Integer::sum);
            suits.merge(card.getSuit(), 1, Integer::sum);
        });

        assertThat(ranks.values()).containsOnly(4);
        assertThat(suits.values()).containsOnly(13);
    }

    /**
     * Tests if shuffling preserves cohesion of the card set.
     */
    @Test
    void shuffle() {
        Card[] cards = Arrays.copyOf(cardDeck.getCards(), cardDeck.getCards().length);
        cardDeck.shuffle();
        assertThat(cardDeck.getCards()).containsExactlyInAnyOrder(cards);
        assertThat(cardDeck.getCards()).doesNotContainSequence(cards);
    }

    /**
     * Tests correctness of pulled cards out of the deck.
     */
    @Test
    void pop() {
        for (int i = 0; i < cardDeck.getCards().length; i++) {
            Card card = cardDeck.pop();
            ranks.merge(card.getRank(), 1, Integer::sum);
            suits.merge(card.getSuit(), 1, Integer::sum);
        }

        assertThat(ranks.values()).containsOnly(4);
        assertThat(suits.values()).containsOnly(13);
    }
}
