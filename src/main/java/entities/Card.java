package entities;

import java.util.Objects;

public class Card implements Comparable<Card>
{
	private final Rank rank;
	private final Suit suit;
	
	public Card(Rank r, Suit s)
	{
		rank = r;
		suit = s;
	}
	
	public Rank getRank()
	{
		return rank;
	}
	
	public Suit getSuit()
	{
		return suit;
	}

	public String toString()
	{
		return String.format("%s%s", rank, suit);
	}

	@Override
	public int compareTo(Card c)
	{
		return Integer.compare(this.rank.ordinal(), c.rank.ordinal());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Card card) {
			if (card == this) return true;
			return this.rank.equals(card.rank) && this.suit.equals(card.suit);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.rank, this.suit);
	}

	public enum Rank
	{
		CARD_2("2"), CARD_3("3"), CARD_4("4"), CARD_5("5"), CARD_6("6"), CARD_7("7"), CARD_8("8"), CARD_9("9"), CARD_10("10"), JACK("J"), QUEEN("Q"), KING("K"), ACE("A");

		private final String name;

		Rank(String name)
		{
			this.name = name;
		}

		@Override
		public String toString()
		{
			return name;
		}
	}

	public enum Suit
	{
		CLUBS("♣"), DIAMONDS("♦"), HEARTS("♥"), SPADES("♠");

		private final String symbol;

		Suit(String symbol)
		{
			this.symbol = symbol;
		}

		@Override
		public String toString()
		{
			return symbol;
		}
	}
}
