package entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Table {

    private final CardDeck cardDeck = new CardDeck();
    private final List<Card> cards = new ArrayList<>(5);
    private final List<Player> players = new LinkedList<>();

    public Table() {
        cardDeck.shuffle();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void distributeCards() {
        cardDeck.shuffle();
        cards.clear();

        for (Player player : players) {
            player.giveCards(cardDeck.pop(), cardDeck.pop());
        }

        for (int i = 0; i < 5; i++) {
            cards.add(cardDeck.pop());
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public String toString() {
        return "[flop: " + cards.get(0) + " " + cards.get(1) + " " + cards.get(2) + "][turn: " + cards.get(3) + "][river: " + cards.get(4) + "]";
    }

}
