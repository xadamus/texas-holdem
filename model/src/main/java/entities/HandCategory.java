package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static game.CardUtils.*;
import static game.CardUtils.getPair;

public enum HandCategory {
    HIGH_CARD {
        @Override
        public int compareCards(List<Card> cards1, List<Card> cards2) {
            List<Card> hCard1 = new ArrayList<>(cards1);
            List<Card> hCard2 = new ArrayList<>(cards2);
            hCard1.sort(Collections.reverseOrder());
            hCard2.sort(Collections.reverseOrder());
            int i = 0;
            while (hCard1.get(i).compareTo(hCard2.get(i)) == 0) {
                if (i == 6) break;
                i++;
            }
            return hCard1.get(i).compareTo(hCard2.get(i));
        }
    }, ONE_PAIR {
        @Override
        public int compareCards(List<Card> cards1, List<Card> cards2) {
            int comparison = cards1.get(0).compareTo(cards2.get(0));
            if (comparison == 0) {
                return HIGH_CARD.compareCards(cards1.subList(1, cards1.size()), cards2.subList(1, cards2.size()));
            }
            return comparison;
        }
    }, TWO_PAIR {
        @Override
        public int compareCards(List<Card> cards1, List<Card> cards2) {
            Map<Card.Rank, Integer> multiples1 = findCardsRankMultiples(cards1);
            Map<Card.Rank, Integer> multiples2 = findCardsRankMultiples(cards2);
            Hand hand1 = getPair(cards1, multiples1, false);
            Hand hand2 = getPair(cards2, multiples2, false);
            if (hand1 != null && hand2 != null) {
                Card.Rank rank1 = hand1.getCards().get(0).getRank();
                Card.Rank rank2 = hand2.getCards().get(0).getRank();
                int rComp = rank1.compareTo(rank2);
                if (rComp != 0) return rComp;
                else {
                    List<Card> tmpHand1 = new ArrayList<>(cards1);
                    List<Card> tmpHand2 = new ArrayList<>(cards2);
                    tmpHand1.removeAll(hand1.getCards());
                    tmpHand2.removeAll(hand2.getCards());

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
        public int compareCards(List<Card> cards1, List<Card> cards2) {
            //TODO: remaining cards
            return cards1.get(0).compareTo(cards2.get(0));
        }
    }, STRAIGHT {
        @Override
        public int compareCards(List<Card> cards1, List<Card> cards2) {
            return cards1.get(0).compareTo(cards2.get(0));
        }
    }, FLUSH {
        @Override
        public int compareCards(List<Card> cards1, List<Card> cards2) {
            List<Card> hCard1 = new ArrayList<>(cards1);
            List<Card> hCard2 = new ArrayList<>(cards2);
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
        public int compareCards(List<Card> cards1, List<Card> cards2) {
            Map<Card.Rank, Integer> multiples1 = findCardsRankMultiples(cards1);
            Map<Card.Rank, Integer> multiples2 = findCardsRankMultiples(cards2);
            Hand hand1 = getThreeOfAKind(cards1, multiples1);
            Hand hand2 = getThreeOfAKind(cards2, multiples2);
            if (hand1 != null && hand2 != null) {
                Card.Rank rank1 = hand1.getCards().get(0).getRank();
                Card.Rank rank2 = hand2.getCards().get(0).getRank();
                int rComp = rank1.compareTo(rank2);
                if (rComp != 0) return rComp;
                else {
                    hand1 = getPair(cards1, multiples1, false);
                    hand2 = getPair(cards2, multiples2, false);
                    if (hand1 != null && hand2 != null) {
                        rank1 = hand1.getCards().get(0).getRank();
                        rank2 = hand2.getCards().get(0).getRank();
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
        public int compareCards(List<Card> cards1, List<Card> cards2) {
            //TODO: remaining cards
            return cards1.get(0).compareTo(cards2.get(0));
        }
    }, STRAIGHT_FLUSH {
        @Override
        public int compareCards(List<Card> cards1, List<Card> cards2) {
            return cards1.get(0).compareTo(cards2.get(0));
        }
    }, ROYAL_FLUSH {
        @Override
        public int compareCards(List<Card> cards1, List<Card> cards2) {
            return 0;
        }
    };

    public abstract int compareCards(List<Card> cards1, List<Card> cards2);
}
