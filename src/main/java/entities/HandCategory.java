package entities;

import java.util.*;

import static game.CardUtils.*;
import static game.CardUtils.getPair;

public enum HandCategory {
    HIGH_CARD {
        @Override
        public int compareCards(Hand hand1, Hand hand2) {
            List<Card> cards1 = new ArrayList<>(hand1.getCards());
            List<Card> cards2 = new ArrayList<>(hand2.getCards());
            return compareCards(cards1, cards2);
        }
    }, ONE_PAIR {
        @Override
        public int compareCards(Hand hand1, Hand hand2) {
            int comparison = hand1.getCards().get(0).compareTo(hand2.getCards().get(0));
            if (comparison == 0) {
                return compareCards(hand1.getSupplementaryCards(), hand2.getSupplementaryCards());
            }
            return comparison;
        }
    }, TWO_PAIR {
        @Override
        public int compareCards(Hand hand1, Hand hand2) {
            Map<Card.Rank, Integer> multiples1 = findCardsRankMultiples(hand1.getCards());
            Map<Card.Rank, Integer> multiples2 = findCardsRankMultiples(hand2.getCards());
            Hand pair1 = getPair(hand1.getCards(), multiples1, false);
            Hand pair2 = getPair(hand2.getCards(), multiples2, false);

            if (pair1 != null && pair2 != null) {
                Card.Rank rank1 = pair1.getCards().get(0).getRank();
                Card.Rank rank2 = pair2.getCards().get(0).getRank();
                int rComp = rank1.compareTo(rank2);
                if (rComp != 0) return rComp;
                else {
                    List<Card> tmpHand1 = new ArrayList<>(hand1.getCards());
                    List<Card> tmpHand2 = new ArrayList<>(hand2.getCards());
                    tmpHand1.removeAll(pair1.getCards());
                    tmpHand2.removeAll(pair2.getCards());

                    rank1 = tmpHand1.get(0).getRank();
                    rank2 = tmpHand2.get(0).getRank();
                    return rank1.compareTo(rank2);
                }
            } else {
                throw new IllegalStateException();
            }
        }
    }, THREE_OF_A_KIND {
        @Override
        public int compareCards(Hand hand1, Hand hand2) {
            int threeComparison = hand1.getCards().get(0).compareTo(hand2.getCards().get(0));
            if (threeComparison == 0) {
                return compareCards(hand1.getSupplementaryCards(), hand2.getSupplementaryCards());
            }
            return threeComparison;
        }
    }, STRAIGHT {
        @Override
        public int compareCards(Hand hand1, Hand hand2) {
            return hand1.getCards().get(0).compareTo(hand2.getCards().get(0));
        }
    }, FLUSH {
        @Override
        public int compareCards(Hand hand1, Hand hand2) {
            List<Card> hCard1 = new ArrayList<>(hand1.getCards());
            List<Card> hCard2 = new ArrayList<>(hand2.getCards());
            hCard1.sort(Collections.reverseOrder());
            hCard2.sort(Collections.reverseOrder());
            int i = 0;
            while (hCard1.get(i).compareTo(hCard2.get(i)) == 0) {
                if (i == 4) break;
                i++;
            }
            return hCard1.get(i).compareTo(hCard2.get(i));
        }
    }, FULL_HOUSE {
        @Override
        public int compareCards(Hand hand1, Hand hand2) {
            Map<Card.Rank, Integer> multiples1 = findCardsRankMultiples(hand1.getCards());
            Map<Card.Rank, Integer> multiples2 = findCardsRankMultiples(hand2.getCards());
            Hand threeOfAKind1 = getThreeOfAKind(hand1.getCards(), multiples1);
            Hand threeOfAKind2 = getThreeOfAKind(hand2.getCards(), multiples2);
            if (threeOfAKind1 != null && threeOfAKind2 != null) {
                Card.Rank rank1 = threeOfAKind1.getCards().get(0).getRank();
                Card.Rank rank2 = threeOfAKind2.getCards().get(0).getRank();
                int rComp = rank1.compareTo(rank2);
                if (rComp != 0) return rComp;
                else {
                    Hand pair1 = getPair(hand1.getCards(), multiples1, false);
                    Hand pair2 = getPair(hand2.getCards(), multiples2, false);
                    if (pair1 != null && pair2 != null) {
                        rank1 = pair1.getCards().get(0).getRank();
                        rank2 = pair2.getCards().get(0).getRank();
                        return rank1.compareTo(rank2);
                    } else {
                        throw new IllegalStateException();
                    }
                }
            } else {
                throw new IllegalStateException();
            }
        }
    }, FOUR_OF_A_KIND {
        @Override
        public int compareCards(Hand hand1, Hand hand2) {
            int fourOfAKindComparison = hand1.getCards().get(0).compareTo(hand2.getCards().get(0));
            if (fourOfAKindComparison == 0) {
                return hand1.getSupplementaryCards().get(0).compareTo(hand2.getSupplementaryCards().get(0));
            }
            return fourOfAKindComparison;
        }
    }, STRAIGHT_FLUSH {
        @Override
        public int compareCards(Hand hand1, Hand hand2) {
            return hand1.getCards().get(0).compareTo(hand2.getCards().get(0));
        }
    }, ROYAL_FLUSH {
        @Override
        public int compareCards(Hand hand1, Hand hand2) {
            return 0;
        }
    };

    public static int compareCards(List<Card> cards1, List<Card> cards2) {
        if (cards1.isEmpty() || cards2.isEmpty()) {
            return 0;
        }

        List<Card> sortedCards1 = cards1.stream().sorted(Comparator.reverseOrder()).toList();
        List<Card> sortedCards2 = cards2.stream().sorted(Comparator.reverseOrder()).toList();
        int i = 0;
        while (sortedCards1.get(i).compareTo(sortedCards2.get(i)) == 0) {
            if (i + 1 >= sortedCards1.size() || i + 1 >= sortedCards2.size()) break;
            i++;
        }
        return sortedCards1.get(i).compareTo(sortedCards2.get(i));
    }

    public abstract int compareCards(Hand hand1, Hand hand2);
}
