package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CardDeckTest {
    EnumMap<Card.Rank, Integer> ranks;
    EnumMap<Card.Suit, Integer> suits;

    @BeforeEach
    void setUp() {
        ranks = new EnumMap<>(Card.Rank.class);
        suits = new EnumMap<>(Card.Suit.class);
    }

    @Test
    void card_deck_consists_of_a_proper_set_of_cards() {
        CardDeck cardDeck = createCardDeck();

        Arrays.stream(cardDeck.getCards()).forEach(card -> {
            ranks.merge(card.getRank(), 1, Integer::sum);
            suits.merge(card.getSuit(), 1, Integer::sum);
        });

        assertAll(
                () -> assertThat(ranks.values()).containsOnly(4),
                () -> assertThat(suits.values()).containsOnly(13));
    }

    @Test
    void shuffling_performs_and_preserves_cohesion_of_the_card_set() {
        CardDeck cardDeck = createCardDeck();
        Card[] cards = Arrays.copyOf(cardDeck.getCards(), cardDeck.getCards().length);

        cardDeck.shuffle();

        assertThat(cardDeck.getCards())
                .containsExactlyInAnyOrder(cards)
                .doesNotContainSequence(cards);
    }

    @Test
    void correct_cards_pulled_out_of_the_deck() {
        CardDeck cardDeck = createCardDeck();

        for (int i = 0; i < cardDeck.getCards().length; i++) {
            Card card = cardDeck.pop();
            ranks.merge(card.getRank(), 1, Integer::sum);
            suits.merge(card.getSuit(), 1, Integer::sum);
        }

        assertThat(ranks.values()).containsOnly(4);
        assertThat(suits.values()).containsOnly(13);
    }

    private CardDeck createCardDeck() {
        return new CardDeck();
    }
}
