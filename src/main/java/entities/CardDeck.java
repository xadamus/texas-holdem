package entities;

import java.util.random.RandomGenerator;

public class CardDeck {

    public static final int AMOUNT_OF_CARDS = 52;

    private final Card[] cards = new Card[AMOUNT_OF_CARDS];
    private final RandomGenerator random = RandomGenerator.getDefault();
    private int top = 0;

    public CardDeck() {
        Card.Rank[] ranks = Card.Rank.values();
        Card.Suit[] suits = Card.Suit.values();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                cards[(13 * i) + j] = new Card(ranks[j], suits[i]);
            }
        }
    }

    public Card[] getCards() {
        return cards;
    }

    public void shuffle() {
        top = 0;
        for (int i = 0; i < AMOUNT_OF_CARDS; i += 2) {
            int r = random.nextInt(AMOUNT_OF_CARDS);
            Card tmp = cards[r];
            cards[r] = cards[i];
            cards[i] = tmp;
        }
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Card c : cards)
            string.append(c.toString()).append("|");
        return string.toString();
    }

    public Card pop() {
        Card card = cards[top];
        top++;
        return card;
    }

}
