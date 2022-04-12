package game;

import entities.Card;
import entities.Hand;
import entities.HandCategory;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CardUtilsTest {

    @Test
    void merging_card_lists() {
        Card card1 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card card2 = new Card(Card.Rank.KING, Card.Suit.CLUBS);

        List<Card> mergedCardList = CardUtils.mergeCardLists(Collections.singletonList(card1),
                Collections.singletonList(card2));

        assertThat(mergedCardList).containsExactly(card1, card2);
    }

    @Test
    void pickHighestCards_oneCard() {
        Card jackOfHearts = new Card(Card.Rank.JACK, Card.Suit.HEARTS);
        Card sevenOfClubs = new Card(Card.Rank.CARD_7, Card.Suit.CLUBS);
        List<Card> highestCards = CardUtils.pickHighestCards(Arrays.asList(jackOfHearts, sevenOfClubs), 1);
        assertThat(highestCards).containsExactly(jackOfHearts);
    }

    @Test
    void pickHighestCards_noCards() {
        Card jackOfHearts = new Card(Card.Rank.JACK, Card.Suit.HEARTS);
        Card sevenOfClubs = new Card(Card.Rank.CARD_7, Card.Suit.CLUBS);
        List<Card> highestCards = CardUtils.pickHighestCards(Arrays.asList(jackOfHearts, sevenOfClubs), 0);
        assertThat(highestCards).isEmpty();
    }

    @Test
    void pickHighestCards_negativeQuantity() {
        Card jackOfHearts = new Card(Card.Rank.JACK, Card.Suit.HEARTS);
        Card sevenOfClubs = new Card(Card.Rank.CARD_7, Card.Suit.CLUBS);
        assertThatIllegalArgumentException().isThrownBy(() -> CardUtils.pickHighestCards(Arrays.asList(jackOfHearts, sevenOfClubs), -1));
    }

    @Test
    void pickHighestCards_exceededQuantity() {
        Card jackOfHearts = new Card(Card.Rank.JACK, Card.Suit.HEARTS);
        Card sevenOfClubs = new Card(Card.Rank.CARD_7, Card.Suit.CLUBS);
        assertThatIllegalArgumentException().isThrownBy(() -> CardUtils.pickHighestCards(Arrays.asList(jackOfHearts, sevenOfClubs), 3));
    }

    @Test
    void findCardsRankMultiples() {
        Map<Card.Rank, Integer> cardsRankMultiples = CardUtils.findCardsRankMultiples(Arrays.asList(
                new Card(Card.Rank.JACK, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_7, Card.Suit.CLUBS),
                new Card(Card.Rank.JACK, Card.Suit.DIAMONDS)));
        assertThat(cardsRankMultiples).containsExactlyInAnyOrderEntriesOf(Map.of(Card.Rank.JACK, 2));
    }

    @Test
    void getFourOfAKind_succeed() {
        Map<Card.Rank, Integer> cardsRankMultiples = Map.of(Card.Rank.JACK, 4);
        List<Card> fourOfAKind = Arrays.asList(
                new Card(Card.Rank.JACK, Card.Suit.HEARTS),
                new Card(Card.Rank.JACK, Card.Suit.DIAMONDS),
                new Card(Card.Rank.JACK, Card.Suit.CLUBS),
                new Card(Card.Rank.JACK, Card.Suit.SPADES));
        List<Card> cards = new ArrayList<>(fourOfAKind);
        cards.add(new Card(Card.Rank.CARD_7, Card.Suit.CLUBS));
        cards.add(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS));

        Hand hand = CardUtils.getFourOfAKind(cards, cardsRankMultiples);
        assertThat(hand).isNotNull();
        assertThat(hand.getCategory()).isEqualTo(HandCategory.FOUR_OF_A_KIND);
        assertThat(hand.getCards()).containsExactlyInAnyOrderElementsOf(fourOfAKind);
    }

    @Test
    void getFourOfAKind_failed() {
        Map<Card.Rank, Integer> cardsRankMultiples = Map.of(Card.Rank.JACK, 3);
        List<Card> cards = List.of(
                new Card(Card.Rank.JACK, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_7, Card.Suit.CLUBS),
                new Card(Card.Rank.JACK, Card.Suit.DIAMONDS),
                new Card(Card.Rank.JACK, Card.Suit.HEARTS),
                new Card(Card.Rank.ACE, Card.Suit.DIAMONDS),
                new Card(Card.Rank.CARD_3, Card.Suit.DIAMONDS),
                new Card(Card.Rank.CARD_2, Card.Suit.SPADES));
        Hand fourOfAKind = CardUtils.getFourOfAKind(cards, cardsRankMultiples);
        assertThat(fourOfAKind).isNull();
    }

    @Test
    void getFullHouse_succeed() {
        Map<Card.Rank, Integer> cardsRankMultiples = Map.of(Card.Rank.JACK, 3, Card.Rank.ACE, 2);
        List<Card> fullHouse = Arrays.asList(
                new Card(Card.Rank.JACK, Card.Suit.HEARTS),
                new Card(Card.Rank.JACK, Card.Suit.DIAMONDS),
                new Card(Card.Rank.JACK, Card.Suit.CLUBS),
                new Card(Card.Rank.ACE, Card.Suit.SPADES),
                new Card(Card.Rank.ACE, Card.Suit.HEARTS));
        List<Card> cards = new ArrayList<>(fullHouse);
        cards.add(new Card(Card.Rank.CARD_7, Card.Suit.CLUBS));
        cards.add(new Card(Card.Rank.CARD_3, Card.Suit.DIAMONDS));
        Hand hand = CardUtils.getFullHouse(cards, cardsRankMultiples);
        assertThat(hand).isNotNull();
        assertThat(hand.getCategory()).isEqualTo(HandCategory.FULL_HOUSE);
        assertThat(hand.getCards()).containsExactlyInAnyOrderElementsOf(fullHouse);
    }

    @Test
    void getFullHouse_failed() {
        Map<Card.Rank, Integer> cardsRankMultiples = Map.of(Card.Rank.JACK, 2, Card.Rank.ACE, 2);
        List<Card> cards = Arrays.asList(
                new Card(Card.Rank.JACK, Card.Suit.HEARTS),
                new Card(Card.Rank.JACK, Card.Suit.CLUBS),
                new Card(Card.Rank.ACE, Card.Suit.SPADES),
                new Card(Card.Rank.ACE, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_7, Card.Suit.CLUBS),
                new Card(Card.Rank.CARD_3, Card.Suit.DIAMONDS),
                new Card(Card.Rank.CARD_4, Card.Suit.SPADES));
        Hand hand = CardUtils.getFullHouse(cards, cardsRankMultiples);
        assertThat(hand).isNull();
    }

    @Test
    void getFlush_succeed() {
        List<Card> fullHouse = Arrays.asList(
                new Card(Card.Rank.JACK, Card.Suit.HEARTS),
                new Card(Card.Rank.JACK, Card.Suit.HEARTS),
                new Card(Card.Rank.ACE, Card.Suit.HEARTS),
                new Card(Card.Rank.ACE, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_7, Card.Suit.HEARTS));
        List<Card> cards = new ArrayList<>(fullHouse);
        cards.add(new Card(Card.Rank.CARD_3, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Rank.CARD_4, Card.Suit.SPADES));
        Hand hand = CardUtils.getFlush(cards);
        assertThat(hand).isNotNull();
        assertThat(hand.getCategory()).isEqualTo(HandCategory.FLUSH);
        assertThat(hand.getCards()).containsExactlyInAnyOrderElementsOf(fullHouse);
    }

    @Test
    void getFlush_failed() {
        List<Card> cards = Arrays.asList(
                new Card(Card.Rank.JACK, Card.Suit.HEARTS),
                new Card(Card.Rank.JACK, Card.Suit.HEARTS),
                new Card(Card.Rank.ACE, Card.Suit.HEARTS),
                new Card(Card.Rank.ACE, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_7, Card.Suit.SPADES),
                new Card(Card.Rank.CARD_3, Card.Suit.DIAMONDS),
                new Card(Card.Rank.CARD_4, Card.Suit.SPADES));
        Hand hand = CardUtils.getFlush(cards);
        assertThat(hand).isNull();
    }

    @Test
    void getStraight_succeed() {
        List<Card> straight = Arrays.asList(
                new Card(Card.Rank.CARD_8, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_9, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_10, Card.Suit.HEARTS),
                new Card(Card.Rank.JACK, Card.Suit.HEARTS),
                new Card(Card.Rank.QUEEN, Card.Suit.DIAMONDS));
        List<Card> cards = new ArrayList<>(straight);
        cards.add(new Card(Card.Rank.CARD_3, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Rank.CARD_4, Card.Suit.SPADES));
        Hand hand = CardUtils.getStraight(cards);
        assertThat(hand).isNotNull();
        assertThat(hand.getCategory()).isEqualTo(HandCategory.STRAIGHT);
        assertThat(hand.getCards()).containsExactlyInAnyOrderElementsOf(straight);
    }

    @Test
    void getStraight_failed() {
        List<Card> cards = Arrays.asList(
                new Card(Card.Rank.CARD_7, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_9, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_10, Card.Suit.HEARTS),
                new Card(Card.Rank.JACK, Card.Suit.HEARTS),
                new Card(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
                new Card(Card.Rank.CARD_3, Card.Suit.DIAMONDS),
                new Card(Card.Rank.CARD_4, Card.Suit.SPADES));
        Hand hand = CardUtils.getStraight(cards);
        assertThat(hand).isNull();
    }

    @Test
    void getThreeOfAKind_succeed() {
        Map<Card.Rank, Integer> cardsRankMultiples = Map.of(Card.Rank.CARD_8, 3);
        List<Card> threeOfAKind = Arrays.asList(
                new Card(Card.Rank.CARD_8, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_8, Card.Suit.DIAMONDS),
                new Card(Card.Rank.CARD_8, Card.Suit.SPADES));
        List<Card> cards = new ArrayList<>(threeOfAKind);
        cards.add(new Card(Card.Rank.CARD_3, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Rank.CARD_4, Card.Suit.SPADES));
        cards.add(new Card(Card.Rank.ACE, Card.Suit.SPADES));
        cards.add(new Card(Card.Rank.QUEEN, Card.Suit.SPADES));
        Hand hand = CardUtils.getThreeOfAKind(cards, cardsRankMultiples);
        assertThat(hand).isNotNull();
        assertThat(hand.getCategory()).isEqualTo(HandCategory.THREE_OF_A_KIND);
        assertThat(hand.getCards()).containsExactlyInAnyOrderElementsOf(threeOfAKind);
    }

    @Test
    void getThreeOfAKind_failed() {
        List<Card> cards = Arrays.asList(
                new Card(Card.Rank.CARD_7, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_9, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_10, Card.Suit.HEARTS),
                new Card(Card.Rank.JACK, Card.Suit.HEARTS),
                new Card(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
                new Card(Card.Rank.CARD_3, Card.Suit.DIAMONDS),
                new Card(Card.Rank.CARD_4, Card.Suit.SPADES));
        Hand hand = CardUtils.getThreeOfAKind(cards, Collections.emptyMap());
        assertThat(hand).isNull();
    }

    @Test
    void getPair_onePair() {
        Map<Card.Rank, Integer> cardsRankMultiples = Map.of(Card.Rank.CARD_8, 2);
        List<Card> pair = Arrays.asList(
                new Card(Card.Rank.CARD_8, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_8, Card.Suit.DIAMONDS));
        List<Card> cards = new ArrayList<>(pair);
        cards.add(new Card(Card.Rank.CARD_3, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Rank.CARD_4, Card.Suit.SPADES));
        cards.add(new Card(Card.Rank.ACE, Card.Suit.SPADES));
        cards.add(new Card(Card.Rank.QUEEN, Card.Suit.SPADES));
        cards.add(new Card(Card.Rank.CARD_2, Card.Suit.SPADES));

        Hand hand = CardUtils.getPair(cards, cardsRankMultiples, false);

        assertThat(hand).isNotNull();
        assertThat(hand.getCategory()).isEqualTo(HandCategory.ONE_PAIR);
        assertThat(hand.getCards()).containsExactlyInAnyOrderElementsOf(List.of(
                new Card(Card.Rank.CARD_8, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_8, Card.Suit.DIAMONDS),
                new Card(Card.Rank.ACE, Card.Suit.SPADES),
                new Card(Card.Rank.QUEEN, Card.Suit.SPADES),
                new Card(Card.Rank.CARD_4, Card.Suit.SPADES)));
    }

    @Test
    void getPair_twoPair() {
        Map<Card.Rank, Integer> cardsRankMultiples = Map.of(Card.Rank.CARD_8, 2, Card.Rank.CARD_10, 2);
        List<Card> twoPair = Arrays.asList(
                new Card(Card.Rank.CARD_8, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_8, Card.Suit.DIAMONDS),
                new Card(Card.Rank.CARD_10, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_10, Card.Suit.CLUBS));
        List<Card> cards = new ArrayList<>(twoPair);
        cards.add(new Card(Card.Rank.QUEEN, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Rank.CARD_3, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Rank.CARD_3, Card.Suit.SPADES));

        Hand hand = CardUtils.getPair(cards, cardsRankMultiples, true);

        assertThat(hand).isNotNull();
        assertThat(hand.getCategory()).isEqualTo(HandCategory.TWO_PAIR);
        assertThat(hand.getCards()).containsExactlyInAnyOrderElementsOf(List.of(
                new Card(Card.Rank.CARD_8, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_8, Card.Suit.DIAMONDS),
                new Card(Card.Rank.CARD_10, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_10, Card.Suit.CLUBS),
                new Card(Card.Rank.QUEEN, Card.Suit.DIAMONDS)));
    }

    @Test
    void getHand_royalFlush() {
        List<Card> royalFlush = Arrays.asList(
                new Card(Card.Rank.CARD_10, Card.Suit.HEARTS),
                new Card(Card.Rank.JACK, Card.Suit.HEARTS),
                new Card(Card.Rank.QUEEN, Card.Suit.HEARTS),
                new Card(Card.Rank.KING, Card.Suit.HEARTS),
                new Card(Card.Rank.ACE, Card.Suit.HEARTS));
        List<Card> cards = new ArrayList<>(royalFlush);
        cards.add(new Card(Card.Rank.CARD_3, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Rank.CARD_4, Card.Suit.SPADES));
        Hand hand = CardUtils.getHand(cards);
        assertThat(hand).isNotNull();
        assertThat(hand.getCategory()).isEqualTo(HandCategory.ROYAL_FLUSH);
        assertThat(hand.getCards()).containsExactlyInAnyOrderElementsOf(royalFlush);
    }

    @Test
    void getHand_highCard() {
        List<Card> cards = Arrays.asList(
                new Card(Card.Rank.CARD_2, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_4, Card.Suit.DIAMONDS),
                new Card(Card.Rank.CARD_6, Card.Suit.HEARTS),
                new Card(Card.Rank.CARD_8, Card.Suit.DIAMONDS),
                new Card(Card.Rank.CARD_10, Card.Suit.CLUBS),
                new Card(Card.Rank.QUEEN, Card.Suit.CLUBS),
                new Card(Card.Rank.ACE, Card.Suit.SPADES));
        Hand hand = CardUtils.getHand(cards);
        assertThat(hand).isNotNull();
        assertThat(hand.getCategory()).isEqualTo(HandCategory.HIGH_CARD);
        assertThat(hand.getCards()).containsExactlyInAnyOrderElementsOf(cards);
    }

    @Test
    void entriesSortedByValues() {
        Map<String, Integer> map = Map.of("C", 3, "A", 1, "B", 2);
        SortedSet<Map.Entry<String, Integer>> sortedEntries = CardUtils.entriesSortedByValues(map);
        assertThat(sortedEntries).hasSize(3);
        assertThat(sortedEntries.first()).extracting(Map.Entry::getKey).isEqualTo("C");
        assertThat(sortedEntries.last()).extracting(Map.Entry::getKey).isEqualTo("A");
    }
}
