package entities;

import java.io.PrintWriter;


public class Player implements Comparable<Player>
{
	// SQLite
	private int id;
	private String name;
	private String password;
	private int money;
	
	private boolean logged;
	private boolean playing;
	
	private boolean ready;
	private int bet;
	
	private Card[] cards = new Card[2];
	private int position;
	
	// Server side
	private PrintWriter out;
	public enum Action { NULL, CALL, FOLD, RAISE, SMALL, BIG }
	private Action action;
	private int actionAmount;
	
	public Player()
	{
		logged = false;
		playing = false;
		ready = false;
		bet = 0;
		action = Action.NULL;
	}
	
	public Player(int id, String name, String password, int money, int position)
	{
		this.id = id;
		this.name = name;
		this.password = password;
		this.money = money;
		this.position = position;
	}

	@Override
	public int compareTo(Player o)
	{
		return this.name.compareTo(o.name);
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setMoney(int money)
	{
		this.money = money;
	}
	
	public void addMoney(int money)
	{
		this.money += money;
	}
	
	public int getMoney()
	{
		return money;
	}
	
	public int getId()
	{
		return id;
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
	
	public void setLogged(boolean logged)
	{
		this.logged = logged;
	}
	
	public boolean isLogged()
	{
		return logged;
	}
	
	public void setPosition(int position)
	{
		this.position = position;
	}
	
	public int getPosition()
	{
		return position;
	}
	
	public void setPlaying(boolean playing)
	{
		this.playing = playing;
	}
	
	public boolean isPlaying()
	{
		return playing;
	}
	
	public void setReady(boolean ready)
	{
		this.ready = ready;
	}
	
	public boolean isReady()
	{
		return ready;
	}
	
	public void setBet(int bet)
	{
		this.bet = bet;
	}
	
	public int getBet()
	{
		return bet;
	}
	
	public void addBet(int bet)
	{
		this.money -= bet;
		this.bet += bet;
	}
	
	public String toString()
	{
		return "[name: " + name + "][cards: " + cards[0] + " " + cards[1] + "]";
	}
	
	// Server side
	public void setOut(PrintWriter out)
	{
		this.out = out;
	}
	
	public PrintWriter getOut()
	{
		return out;
	}
	
	public void setAction(Action action)
	{
		this.action = action;
	}
	
	public Action getAction()
	{
		return action;
	}
	
	public void setActionAmount(int amount)
	{
		this.actionAmount = amount;
	}
	
	public int getActionAmount()
	{
		return actionAmount;
	}
}
