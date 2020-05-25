package game;

import entities.Card;
import entities.Hand;
import entities.Player;
import entities.Table;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static game.CardUtil.getHand;
import static game.CardUtil.getMergedCardList;

/**
 * Created by Adamus on 2016-04-14.
 */
public class Test implements Runnable
{
    public static void main(String[] args)
    {
        Test tester = new Test();
        Thread t = new Thread(tester);
        t.start();
    }

    private Table table;
    private Player player;
    private int[] hands = new int[10];

    public Test()
    {
        table = new Table();
        player = new Player();
        table.addPlayer(player);
    }

    @Override
    public void run()
    {
        int i = 0;
        while (true)
        {
            table.distributeCards();

            List<Card> cards = getMergedCardList(Arrays.asList(player.getCards()), table.getCards());
            Hand hand = getHand(cards);

            hands[hand.getHandCategory().ordinal()]++;
            i++;

            if (i % 100000 == 0)
            {
                System.out.print("i=" + i + " ");
                for (int h : hands)
                {
                    double d = ((double)h/i)*100;
                    System.out.format("%d ", h);
                }
                System.out.println();
            }
        }
    }
}
