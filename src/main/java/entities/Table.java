package entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Table
{
	public CardDeck cardDeck = new CardDeck();
	private List<Card> cards = new ArrayList<>(5);
	private List<Player> players = new LinkedList<>();
	
	public Table()
	{
		cardDeck.shuffle();
	}

	public void addPlayer(Player player)
	{
		players.add(player);
	}
	
	public void distributeCards()
	{
		cardDeck.shuffle();
		cards.clear();

		for(Player player : players)
		{
			player.giveCards(cardDeck.pop(), cardDeck.pop());
		}
		
		for(int i = 0; i < 5; i++)
		{
			cards.add(cardDeck.pop());
		}
	}

	public List<Card> getCards()
	{
		return cards;
	}

	public List<Player> getPlayers()
	{
		return players;
	}

	public CardDeck getCardDeck()
	{

		return cardDeck;
	}

	// stary shit
	
	public void showFlop()
	{
		System.out.println("[flop: " + cards.get(0) + " " + cards.get(1) + " " + cards.get(2) + "]");
	}
	
	public String flop()
	{
		return "" + cards.get(0) + " " + cards.get(1) + " " + cards.get(2);
	}
	
	public void showTurn()
	{
		System.out.println("[turn: " + cards.get(3) + "]");
	}
	
	public String turn()
	{
		return cards.get(3).toString();
	}
	
	public void showRiver()
	{
		System.out.println("[river: " + cards.get(4) + "]");
	}
	
	public String river()
	{
		return cards.get(4).toString();
	}
	
	public String toString()
	{
		return "[flop: " + cards.get(0) + " " + cards.get(1) + " " + cards.get(2) + "][turn: " + cards.get(3) + "][river: " + cards.get(4) + "]";
	}
}
