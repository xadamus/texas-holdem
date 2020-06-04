package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static game.CardUtils.*;

public class Hand implements Comparable<Hand>
{
    public enum HandCategory { HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH, ROYAL_FLUSH }

    private List<Card> handCards;
    private HandCategory handCategory;

    public Hand(List<Card> handCards, HandCategory handCategory)
    {
        this.handCards = handCards;
        this.handCategory = handCategory;
    }

    public List<Card> getHandCards()
    {
        return handCards;
    }

    public HandCategory getHandCategory()
    {
        return handCategory;
    }

    @Override
    public int compareTo(Hand h)
    {
        if (this.handCategory != h.handCategory)
            return Integer.compare(this.handCategory.ordinal(), h.handCategory.ordinal());
        else
        {
            Map<Card.Rank,Integer> multiples1, multiples2;
            Hand hand1, hand2;
            switch (handCategory)
            {
                case ROYAL_FLUSH:
                    return 0;

                case STRAIGHT_FLUSH:
                case FOUR_OF_A_KIND:
                case STRAIGHT:
                case THREE_OF_A_KIND:
                case ONE_PAIR:
                    return this.handCards.get(0).compareTo(h.handCards.get(0));

                case FULL_HOUSE:
                    multiples1 = findCardsRankMultiples(this.handCards);
                    multiples2 = findCardsRankMultiples(h.handCards);
                    hand1 = getThreeOfAKind(this.handCards, multiples1);
                    hand2 = getThreeOfAKind(h.handCards, multiples2);
                    if (hand1 != null && hand2 != null)
                    {
                        Card.Rank rank1 = hand1.getHandCards().get(0).getRank();
                        Card.Rank rank2 = hand2.getHandCards().get(0).getRank();
                        int rComp = rank1.compareTo(rank2);
                        if (rComp != 0) return rComp;
                        else
                        {
                            hand1 = getPair(this.handCards, multiples1, false);
                            hand2 = getPair(h.handCards, multiples2, false);
                            if (hand1 != null && hand2 != null)
                            {
                                rank1 = hand1.getHandCards().get(0).getRank();
                                rank2 = hand2.getHandCards().get(0).getRank();
                                return rank1.compareTo(rank2);
                            }
                            else
                            {
                                throw new IllegalStateException();
                            }
                        }
                    }
                    else
                    {
                        throw new IllegalStateException();
                    }

                case TWO_PAIR:
                    multiples1 = findCardsRankMultiples(this.handCards);
                    multiples2 = findCardsRankMultiples(h.handCards);
                    hand1 = getPair(this.handCards, multiples1, false);
                    hand2 = getPair(h.handCards, multiples2, false);
                    if (hand1 != null && hand2 != null)
                    {
                        Card.Rank rank1 = hand1.getHandCards().get(0).getRank();
                        Card.Rank rank2 = hand2.getHandCards().get(0).getRank();
                        int rComp = rank1.compareTo(rank2);
                        if (rComp != 0) return rComp;
                        else
                        {
                            List<Card> tmpHand1 = new ArrayList<>(this.handCards);
                            List<Card> tmpHand2 = new ArrayList<>(h.handCards);
                            tmpHand1.removeAll(hand1.getHandCards());
                            tmpHand2.removeAll(hand2.getHandCards());

                            rank1 = tmpHand1.get(0).getRank();
                            rank2 = tmpHand2.get(0).getRank();
                            return rank1.compareTo(rank2);
                        }
                    }
                    else
                    {
                        throw new IllegalStateException();
                    }

                case FLUSH:
                case HIGH_CARD:
                    List<Card> hCard1 = new ArrayList<>(this.handCards);
                    List<Card> hCard2 = new ArrayList<>(h.handCards);
                    Collections.sort(hCard1, Collections.reverseOrder());
                    Collections.sort(hCard2, Collections.reverseOrder());
                    int i = 0;
                    while (hCard1.get(i).compareTo(hCard2.get(i)) == 0)
                    {
                        if ((this.getHandCategory() == HandCategory.FLUSH)?(i == 4):(i == 6)) break;
                        i++;
                    }
                    return hCard1.get(i).compareTo(hCard2.get(i));

                default:
                    return 0;
            }
        }
    }
}
