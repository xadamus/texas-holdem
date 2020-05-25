package entities;

public class Card implements Comparable<Card>
{
	private final Rank rank;
	private final Suit suit;
	
	public Card(Rank r, Suit s)
	{
		rank = r; //j
		suit = s; //i
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

	public enum Rank
	{
		CARD_2("2"), CARD_3("3"), CARD_4("4"), CARD_5("5"), CARD_6("6"), CARD_7("7"), CARD_8("8"), CARD_9("9"), CARD_10("10"), JACK("J"), QUEEN("Q"), KING("K"), ACE("A");

		private String name;

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

		private String symbol;

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
