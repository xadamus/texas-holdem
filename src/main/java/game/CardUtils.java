package game;

import entities.Card;
import entities.Hand;
import entities.HandCategory;

import java.util.*;
import java.util.stream.Collectors;

public class CardUtils {
    private CardUtils() {
    }

    /**
     * Merges two lists of cards into one list.
     */
    public static List<Card> mergeCardLists(List<Card> list1, List<Card> list2) {
        List<Card> cards = new ArrayList<>(list1.size() + list2.size());
        cards.addAll(list1);
        cards.addAll(list2);
        return cards;
    }

    /**
     * Picks certain amount of highest cards from given list.
     *
     * @param cards    list of cards to pick from
     * @param quantity quantity of cards to pick. The number should
     *                 be between 0 and size of the cards list.
     */
    public static List<Card> pickHighestCards(List<Card> cards, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("negative quantity");
        }
        if (quantity > cards.size()) {
            throw new IllegalArgumentException("quantity exceeded number of given cards");
        }

        if (quantity == 0) {
            return Collections.emptyList();
        }
        List<Card> hCards = new ArrayList<>(cards);
        Collections.sort(hCards);
        return hCards.subList(cards.size() - quantity, cards.size());
    }

    /**
     * Finds cards rank multiples in the given list. The method takes into account
     * only multiples above two.
     */
    public static Map<Card.Rank, Integer> findCardsRankMultiples(List<Card> cards) {
        ListIterator<Card> iter = cards.listIterator();
        Map<Card.Rank, Integer> multiple = new EnumMap<>(Card.Rank.class);
        List<Card.Rank> ranks = new ArrayList<>();

        while (iter.hasNext()) {
            Card.Rank rank = iter.next().getRank();
            int quantity = 1;
            ListIterator<Card> innerIter = cards.listIterator(iter.nextIndex());

            while (innerIter.hasNext()) {
                Card card = innerIter.next();
                if (card.getRank() == rank && !ranks.contains(rank)) {
                    quantity++;
                }
            }

            if (quantity > 1) {
                multiple.put(rank, quantity);
                ranks.add(rank);
            }
        }

        return multiple;
    }

    public static Hand getFourOfAKind(List<Card> cards, Map<Card.Rank, Integer> multiples) {
        if (multiples.containsValue(4)) {
            List<Card> handCards = new ArrayList<>(4);
            List<Card> supplementaryCards = new ArrayList<>(cards);
            for (Map.Entry<Card.Rank, Integer> entry : multiples.entrySet()) {
                if (entry.getValue() == 4) {
                    handCards.addAll(cards.stream().filter(card -> card.getRank() == entry.getKey()).toList());
                    supplementaryCards.removeAll(handCards);
                    break;
                }
            }
            return new Hand(handCards, supplementaryCards, HandCategory.FOUR_OF_A_KIND);
        } else return null;
    }

    public static Hand getFullHouse(List<Card> cards, Map<Card.Rank, Integer> multiples) {
        if (multiples.containsValue(3) && multiples.containsValue(2)) {
            List<Card> handCards = new ArrayList<>(5);
            multiples.entrySet().stream()
                    .filter(entry -> entry.getValue() == 2 || entry.getValue() == 3)
                    .forEach(entry -> handCards.addAll(cards.stream().filter(card -> card.getRank() == entry.getKey()).toList()));
            return new Hand(handCards, Collections.emptyList(), HandCategory.FULL_HOUSE);
        } else return null;
    }

    public static Hand getFlush(List<Card> cards) {
        List<Card> handCards = new ArrayList<>(5);
        ListIterator<Card> iter = cards.listIterator();

        while (iter.hasNext()) {
            Card card = iter.next();
            ListIterator<Card> innerIter = cards.listIterator(iter.nextIndex());
            handCards.add(card);

            while (innerIter.hasNext()) {
                Card card2 = innerIter.next();
                if (card.getSuit() == card2.getSuit()) {
                    handCards.add(card2);
                }
            }

            if (handCards.size() == 5) {
                return new Hand(handCards, Collections.emptyList(), HandCategory.FLUSH);
            } else handCards.clear();
        }

        return null;
    }

    public static Hand getStraight(List<Card> cards) {
        List<Card> handCards = new ArrayList<>(5);

        for (Card card : cards) {
            ListIterator<Card> innerIter = cards.listIterator();
            handCards.add(card);

            while (innerIter.hasNext()) {
                Card cCard = innerIter.next();
                if (((card.getRank().ordinal() + 1) == cCard.getRank().ordinal() && card != cCard) || (card.getRank() == Card.Rank.ACE && cCard.getRank() == Card.Rank.CARD_2)) {
                    handCards.add(cCard);
                    card = cCard;
                }
            }

            if (handCards.size() == 5) {
                return new Hand(handCards, Collections.emptyList(), HandCategory.STRAIGHT);
            } else handCards.clear();
        }
        return null;
    }

    public static Hand getThreeOfAKind(List<Card> cards, Map<Card.Rank, Integer> multiples) {
        if (multiples.containsValue(3)) {
            List<Card> handCards = new ArrayList<>(3);
            List<Card> supplementaryCards = new ArrayList<>(cards);
            SortedSet<Card.Rank> ranks = new TreeSet<>(multiples.keySet());
            for (Card.Rank rank : ranks) {
                if (multiples.get(rank) == 3) {
                    handCards.addAll(cards.stream().filter(card -> card.getRank() == rank).collect(Collectors.toList()));
                    supplementaryCards.removeAll(handCards);
                    return new Hand(handCards, supplementaryCards, HandCategory.THREE_OF_A_KIND);
                }
            }
        }
        return null;
    }

    public static Hand getPair(List<Card> cards, Map<Card.Rank, Integer> multiples, boolean twoPair) {
        List<Card> mergedCards = new ArrayList<>(cards);
        List<Card> handCards = new ArrayList<>();
        int pairNum = 0;

        for (Map.Entry<Card.Rank, Integer> entry : multiples.entrySet()) {
            if (entry.getValue() == 2) {
                handCards.addAll(mergedCards.stream().filter(card -> card.getRank() == entry.getKey()).toList());
                mergedCards.removeAll(handCards);
                pairNum++;

                if (twoPair ? (pairNum == 2) : (pairNum == 1)) {
                    if (twoPair) {
                        return new Hand(handCards, pickHighestCards(mergedCards, 1), HandCategory.TWO_PAIR);
                    } else {
                        return new Hand(handCards, pickHighestCards(mergedCards, 3), HandCategory.ONE_PAIR);
                    }
                }
            }
        }
        return null;
    }

    public static Hand getHand(List<Card> cards) {
        Hand hand;
        Map<Card.Rank, Integer> multiples = findCardsRankMultiples(cards);

        hand = getFourOfAKind(cards, multiples);
        if (hand != null) return hand;

        hand = getFullHouse(cards, multiples);
        if (hand != null) return hand;

        hand = getFlush(cards);

        Hand straightHand = getStraight(cards);
        if (straightHand != null && hand != null) { // flush?
            if (straightHand.getCards().get(0).getRank() == Card.Rank.CARD_10) { // royal flush?
                return new Hand(straightHand.getCards(), Collections.emptyList(), HandCategory.ROYAL_FLUSH);
            }
            return new Hand(straightHand.getCards(), Collections.emptyList(), HandCategory.STRAIGHT_FLUSH);
        }

        if (hand != null) return hand; // flush

        if (straightHand != null) return straightHand;

        hand = getThreeOfAKind(cards, multiples);
        if (hand != null) return hand;

        hand = getPair(cards, multiples, true); // two pair
        if (hand != null) return hand;

        hand = getPair(cards, multiples, false); // one pair
        if (hand != null) return hand;

        return new Hand(cards, Collections.emptyList(), HandCategory.HIGH_CARD);
    }

    /**
     * By polygenelubricants
     */
    public static <K, V extends Comparable<? super V>>
    SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<>(
                (Map.Entry<K, V> e1, Map.Entry<K, V> e2) -> {
                    int res = e2.getValue().compareTo(e1.getValue());
                    return res != 0 ? res : 1;
                });
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}
