package game;

import entities.Card;
import entities.Hand;
import entities.Player;
import entities.Table;

import java.util.*;

import static game.CardUtils.*;
import static java.lang.System.*;

public class Game
{
    public static void main(String[] args) {
        new Game().play();
    }

    public void play()
    {
        Table table = new Table();
        Player player1 = new Player();
        player1.setName("Player 1");
        Player player2 = new Player();
        player2.setName("Player 2");
        Player player3 = new Player();
        player3.setName("Player 3");
        table.addPlayer(player1);
        table.addPlayer(player2);
        table.addPlayer(player3);
        table.distributeCards();

        List<Card> cardsP1 = mergeCardLists(Arrays.asList(player1.getCards()), table.getCards());
        List<Card> cardsP2 = mergeCardLists(Arrays.asList(player2.getCards()), table.getCards());
        List<Card> cardsP3 = mergeCardLists(Arrays.asList(player3.getCards()), table.getCards());

        out.println(table);

        out.println(player1);
        out.println(cardsP1);

        out.println(player2);
        out.println(cardsP2);

        out.println(player3);
        out.println(cardsP3);

        Map<Player,Hand> results = new TreeMap<>();
        results.put(player1, getHand(cardsP1));
        results.put(player2, getHand(cardsP2));
        results.put(player3, getHand(cardsP3));
        for (Map.Entry<Player,Hand> entry : entriesSortedByValues(results))
        {
            out.println("" + entry.getKey().getName() + ": " + entry.getValue().getCategory()
                + " " + entry.getValue().getCards());
        }
    }
}
