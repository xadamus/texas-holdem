package entities;

import java.awt.*;

public class CardDeck 
{
	private final int CARDS = 52;
	private final Card[] gameCards = new Card[CARDS];
	public Card[] cards = new Card[CARDS];
	private int top = 0;
	private Image reverseCardSprite;

	private Card.Rank[] ranks = Card.Rank.values();
	private Card.Suit[] suits = Card.Suit.values();
	public CardDeck()
	{
		for (int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 13; j++)
			{
				gameCards[(13*i) + j] = new Card(ranks[j], suits[i]);
				cards[(13*i) + j] = gameCards[(13*i) + j];
			}
		}
	}

	public Card[] getGameCards()
	{
		return gameCards;
	}

	public void shuffle()
	{
		top = 0;

		int r;
		Card tmp;
		for(int i = 0; i < CARDS; i += 2)
		{
			r = (int)(Math.random()*100)%CARDS;
			tmp = cards[r];
			cards[r] = cards[i];
			cards[i] = tmp;
		}
	}
	
	public String toString()
	{
		StringBuilder string = new StringBuilder();
		for(Card c : cards)
			string.append(c.toString()).append("|");
		return string.toString();
	}
	
	public Card pop()
	{
		Card card = cards[top];
		top++;
		return card;
	}
	
	public Image getReverseCardSprite()
	{
		return reverseCardSprite;
	}
}
