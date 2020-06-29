package game;

import entities.Card;
import entities.Hand;
import entities.Player;
import entities.Table;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static game.CardUtils.getHand;
import static game.CardUtils.mergeCardLists;

public class ProbabilityTest implements Runnable
{
    public static void main(String[] args)
    {
        ProbabilityTest tester = new ProbabilityTest();
        Thread t = new Thread(tester);
        t.start();
    }

    private static final Logger LOG = Logger.getLogger(ProbabilityTest.class.getName());

    private final Table table;
    private final Player player;
    private final int[] hands = new int[10];

    public ProbabilityTest()
    {
        table = new Table();
        player = new Player();
        table.addPlayer(player);
    }

    @Override
    public void run()
    {
        int i = 0;
        do {
            table.distributeCards();
            List<Card> cards = mergeCardLists(Arrays.asList(player.getCards()), table.getCards());
            Hand hand = getHand(cards);

            hands[hand.getHandCategory().ordinal()]++;
            i++;

            if (i % 100000 == 0) {
                LOG.log(Level.INFO, "{0} | {1} | {2} | {3} | {4} | {5} | {6} | {7} | {8} | {9}", Arrays.stream(hands).boxed().toArray());
            }
        } while (i != Integer.MAX_VALUE);
    }
}
