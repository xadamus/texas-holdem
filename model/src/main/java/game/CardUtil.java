package game;

import entities.Card;
import entities.Hand;

import java.util.*;
import java.util.stream.Collectors;

public class CardUtil {
    private CardUtil() {
    }

    public static List<Card> getMergedCardList(List<Card> playerCards, List<Card> tableCards) {
        List<Card> cards = new ArrayList<>(tableCards);
        cards.addAll(playerCards);
        return cards;
    }

    public static List<Card> getHighestCards(List<Card> cards, int quantity) {
        List<Card> hCards = new ArrayList<>(cards);
        Collections.sort(hCards);
        return hCards.subList(cards.size() - quantity, cards.size());
    }

    public static Map<Card.Rank, Integer> getCardsMultiples(List<Card> cards) {
        ListIterator<Card> iter = cards.listIterator();
        Map<Card.Rank, Integer> multiple = new HashMap<>(); // <wartość,ilość>
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
            List<Card> rCards = new ArrayList<>(4);
            for (Map.Entry<Card.Rank, Integer> entry : multiples.entrySet()) {
                if (entry.getValue() == 4) {
                    rCards.addAll(cards.stream().filter(card -> card.getRank() == entry.getKey()).collect(Collectors.toList()));
                }
            }
            return new Hand(rCards, Hand.HandCategory.FOUR_OF_A_KIND);
        } else return null;
    }

    public static Hand getFullHouse(List<Card> cards, Map<Card.Rank, Integer> multiples) {
        if (multiples.containsValue(3) && multiples.containsValue(2)) {
            List<Card> rCards = new ArrayList<>(5);
            multiples.entrySet().stream()
                    .filter(entry -> entry.getValue() == 2 || entry.getValue() == 3)
                    .forEach(entry -> rCards.addAll(cards.stream().filter(card -> card.getRank() == entry.getKey()).collect(Collectors.toList())));
            return new Hand(rCards, Hand.HandCategory.FULL_HOUSE);
        } else return null;
    }

    public static Hand getFlush(List<Card> cards) {
        List<Card> rCards = new ArrayList<>(5);
        ListIterator<Card> iter = cards.listIterator();

        while (iter.hasNext()) {
            Card card = iter.next();
            ListIterator<Card> innerIter = cards.listIterator(iter.nextIndex());
            rCards.add(card);

            while (innerIter.hasNext()) {
                Card card2 = innerIter.next();
                if (card.getSuit() == card2.getSuit()) {
                    rCards.add(card2);
                }
            }

            if (rCards.size() == 5) {
                return new Hand(rCards, Hand.HandCategory.FLUSH);
            } else rCards.clear();
        }

        return null;
    }

    public static Hand getStraight(List<Card> cards, Map<Card.Rank, Integer> multiples) {
        if (multiples.containsValue(2) || multiples.isEmpty()) {
            List<Card> rCards = new ArrayList<>(5);
            ListIterator<Card> iter = cards.listIterator();

            while (iter.hasNext()) {
                Card card = iter.next();
                ListIterator<Card> innerIter = cards.listIterator();
                rCards.add(card);

                while (innerIter.hasNext()) {
                    Card cCard = innerIter.next();
                    if (((card.getRank().ordinal() + 1) == cCard.getRank().ordinal() && card != cCard) || (card.getRank() == Card.Rank.ACE && cCard.getRank() == Card.Rank.CARD_2)) {
                        rCards.add(cCard);
                        card = cCard;
                    }
                }

                if (rCards.size() == 5) {
                    return new Hand(rCards, Hand.HandCategory.STRAIGHT);
                } else rCards.clear();
            }
            return null;
        } else return null;
    }

    public static Hand getThreeOfAKind(List<Card> cards, Map<Card.Rank, Integer> multiples) {
        if (multiples.containsValue(3)) {
            List<Card> rCards = new ArrayList<>(3);
            SortedSet<Card.Rank> ranks = new TreeSet<>(multiples.keySet());
            for (Card.Rank rank : ranks) {
                if (multiples.get(rank) == 3) {
                    rCards.addAll(cards.stream().filter(card -> card.getRank() == rank).collect(Collectors.toList()));
                    return new Hand(rCards, Hand.HandCategory.THREE_OF_A_KIND);
                }
            }
        }
        return null;
    }

    public static Hand getPair(List<Card> cards, Map<Card.Rank, Integer> multiples, boolean twoPair) {
        if (multiples.containsValue(2)) {
            List<Card> rCards = new ArrayList<>();
            SortedSet<Card.Rank> ranks = new TreeSet<>(multiples.keySet());
            int pairNum = 0;

            for (Card.Rank rank : ranks) {
                if (multiples.get(rank) == 2) {
                    rCards.addAll(cards.stream().filter(card -> card.getRank() == rank).collect(Collectors.toList()));
                    pairNum++;

                    if ((twoPair) ? (pairNum == 2) : (pairNum == 1))
                        return new Hand(rCards, (twoPair) ? Hand.HandCategory.TWO_PAIR : Hand.HandCategory.ONE_PAIR);
                }
            }
        }
        return null;
    }

    public static Hand getHand(List<Card> cards) {
        Hand hand;
        Map<Card.Rank, Integer> multiples = getCardsMultiples(cards);

        hand = getFourOfAKind(cards, multiples);
        if (hand != null) return hand;

        hand = getFullHouse(cards, multiples);
        if (hand != null) return hand;

        hand = getFlush(cards);

        Hand straightHand = getStraight(cards, multiples);
        if (straightHand != null) {
            if (hand != null) // flush?
            {
                if (straightHand.getHandCards().get(0).getRank() == Card.Rank.CARD_10) // royal flush?
                {
                    return new Hand(straightHand.getHandCards(), Hand.HandCategory.ROYAL_FLUSH);
                }
                return new Hand(straightHand.getHandCards(), Hand.HandCategory.STRAIGHT_FLUSH);
            }
        }

        if (hand != null) return hand; // flush

        if (straightHand != null) return straightHand;

        hand = getThreeOfAKind(cards, multiples);
        if (hand != null) return hand;

        hand = getPair(cards, multiples, true); // two pair
        if (hand != null) return hand;

        hand = getPair(cards, multiples, false); // one pair
        if (hand != null) return hand;

        return new Hand(cards, Hand.HandCategory.HIGH_CARD);
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
