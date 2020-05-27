package game;

import entities.Card;
import entities.Hand;
import entities.Player;
import entities.Table;

import java.io.*;
import java.util.*;

import static game.CardUtil.*;

public class Game
{
    public static final int MAX_PLAYERS = 4;

    public Game()
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

        List<Card> cardsP1 = getMergedCardList(Arrays.asList(player1.getCards()), table.getCards());
        List<Card> cardsP2 = getMergedCardList(Arrays.asList(player2.getCards()), table.getCards());
        List<Card> cardsP3 = getMergedCardList(Arrays.asList(player3.getCards()), table.getCards());

        System.out.println(table);

        System.out.println(player1);
        System.out.println(cardsP1);

        System.out.println(player2);
        System.out.println(cardsP2);

        System.out.println(player3);
        System.out.println(cardsP3);

        Map<Player,Hand> results = new TreeMap<>();
        results.put(player1, getHand(cardsP1));
        results.put(player2, getHand(cardsP2));
        results.put(player3, getHand(cardsP3));
        for (Map.Entry<Player,Hand> entry : entriesSortedByValues(results))
        {
            System.out.println("" + entry.getKey().getName() + ": " + entry.getValue().getHandCategory()
                + " " + entry.getValue().getHandCards());
        }
    }
}
