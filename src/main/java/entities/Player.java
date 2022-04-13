package entities;

import java.util.Objects;

public class Player implements Comparable<Player>
{
	private String name;
	
	private final Card[] cards = new Card[2];

	@Override
	public int compareTo(Player o)
	{
		return this.name.compareTo(o.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj instanceof Player that) {
			return Objects.equals(this.name, that.name);
		}
		return false;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void giveCards(Card card1, Card card2)
	{
		cards[0] = card1;
		cards[1] = card2;
	}
	
	public Card[] getCards()
	{
		return cards;
	}
	
	public String toString()
	{
		return "[name: " + name + "][cards: " + cards[0] + " " + cards[1] + "]";
	}
}
