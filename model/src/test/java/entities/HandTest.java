package entities;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HandTest {
    @Test
    void equalsContract() {
        EqualsVerifier.forClass(Hand.class).verify();
    }

    @Test
    void compareToTest_equalHighCard() {
        Hand highCard1 = new Hand(List.of(new Card(Card.Rank.CARD_2, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_4, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_6, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_8, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_10, Card.Suit.SPADES),
                new Card(Card.Rank.JACK, Card.Suit.SPADES),
                new Card(Card.Rank.KING, Card.Suit.SPADES)), HandCategory.HIGH_CARD);
        Hand highCard2 = new Hand(List.of(new Card(Card.Rank.CARD_2, Card.Suit.SPADES),
                new Card(Card.Rank.CARD_4, Card.Suit.SPADES),
                new Card(Card.Rank.CARD_6, Card.Suit.SPADES),
                new Card(Card.Rank.CARD_8, Card.Suit.SPADES),
                new Card(Card.Rank.CARD_10, Card.Suit.CLUBS),
                new Card(Card.Rank.JACK, Card.Suit.SPADES),
                new Card(Card.Rank.KING, Card.Suit.SPADES)), HandCategory.HIGH_CARD);
        assertThat(highCard1).isEqualByComparingTo(highCard2);
    }

    @Test
    void compareToTest_highCard() {
        Hand highCard = new Hand(List.of(new Card(Card.Rank.CARD_2, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_4, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_6, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_8, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_10, Card.Suit.SPADES)), HandCategory.HIGH_CARD);
        Hand higherCard = new Hand(List.of(new Card(Card.Rank.CARD_3, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_5, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_7, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_9, Card.Suit.HEARTS),
                new Card(Card.Rank.JACK, Card.Suit.SPADES)), HandCategory.HIGH_CARD);
        assertThat(highCard).isLessThan(higherCard);
    }

    @Test
    void compareToTest_onePair() {
        Hand lowerPair = new Hand(List.of(new Card(Card.Rank.CARD_2, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_2, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_6, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_8, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_10, Card.Suit.SPADES)), HandCategory.ONE_PAIR);
        Hand higherPair = new Hand(List.of(new Card(Card.Rank.CARD_3, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_3, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_7, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_9, Card.Suit.HEARTS),
                new Card(Card.Rank.JACK, Card.Suit.SPADES)), HandCategory.ONE_PAIR);
        assertThat(lowerPair).isLessThan(higherPair);
    }

    @Test
    void compareToTest_equalPair() {
        Hand lowerPair = new Hand(List.of(new Card(Card.Rank.CARD_2, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_2, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_6, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_8, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_10, Card.Suit.SPADES)), HandCategory.ONE_PAIR);
        Hand higherPair = new Hand(List.of(new Card(Card.Rank.CARD_2, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_2, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_7, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_9, Card.Suit.HEARTS),
                new Card(Card.Rank.JACK, Card.Suit.SPADES)), HandCategory.ONE_PAIR);
        assertThat(lowerPair).isLessThan(higherPair);
    }

    @Test
    void compareToTest_straight() {
        Hand lowerStraight = new Hand(List.of(new Card(Card.Rank.CARD_2, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_3, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_4, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_5, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_6, Card.Suit.SPADES)), HandCategory.STRAIGHT);
        Hand higherStraight = new Hand(List.of(new Card(Card.Rank.CARD_3, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_4, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_5, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_6, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_7, Card.Suit.SPADES)), HandCategory.STRAIGHT);
        assertThat(lowerStraight).isLessThan(higherStraight);
    }
}
