package game;/*
package game;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import view.menu.GameMenu;
import view.menu.LoginDialog;
import entities.Card;
import entities.Player;
import entities.Player.Action;
import entities.Table;

@SuppressWarnings("serial")
public class Game extends JPanel implements KeyListener, Runnable
{
	// consts
	public static final int MAX_PLAYERS = 4;
	
	// main
	private GameFrame frame;
	private Thread thread = new Thread(this);
	private GameClient client;
	private GameSpeaker speaker;
	private GameGraphics graphics;
	
	// dimensions
	public final int width;
	public final int height;
	
	// gui
	private Image backgroundImage;
	private int[] posX = { 100, 840, 100, 840 };
	private int[] posY = { 55, 50, 380, 380 };
	
	// state
	private enum State { MENU, GAME };
	private State state;
	
	// składniki
	private Table table;
	private Player player;
	private Player[] players = new Player[MAX_PLAYERS];
	
	// view.menu
	private GameMenu view.menu;
	
	// properties
	private final String propFileName = "config.properties";
	private Properties prop = new Properties();
	
	// hand
	public boolean running = false;
	private int small_blind;
	private int dealer;
	private int pot;
	private int raise;
	private List<Card> flop = new ArrayList<>(3);
	private Card turn;
	private Card river;
	
	public Game(int width, int height, GameFrame frame)
	{
		super();
		
		this.width = width;
		this.height = height;
		this.frame = frame;
		
		setPreferredSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		
		backgroundImage = new ImageIcon(this.getClass().getResource("/resources/backg.jpg")).getImage();
		
		state = State.MENU;
		view.menu = new GameMenu(width, height, this);

		try
		{
			loadProperties();
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(getFrame(), e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		
		client = new GameClient(this);
		client.start();
		
		speaker = new GameSpeaker();
		graphics = new GameGraphics();
		
		addMouseListener(new MouseControl());
		addMouseMotionListener(new MouseControl());
	}
	
	public void playerJoin()
	{
		client.playerJoin();
		state = State.GAME;
		frame.playerJoin();
		repaint();
		
		//table = new Table(player);
	}
	
	public void run()
	{
		
		
		// Inicjalizacja stołu
		*/
/*table = new Table(player);
		
		// Gra
		table.distributeCards();
		System.out.println(player);
		
		state = State.GAME;
		frame.playerJoin();
		repaint();
		
		// Licytacja 1
		System.out.print(player.getName() + ": < pas / sprawdzam / podbijam >? ");
		Scanner in = new Scanner(System.in);
		String action = in.nextLine();
		switch(action)
		{
		case "pas":
			System.out.println("spasowałeś");
			break;
		case "sprawdzam":
			System.out.println("sprawdzasz");
			break;
		case "podbijam":
			System.out.println("podbijasz");
			break;
		}
		
		table.showFlop();
		in.close();*//*

	}
	
	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) {}
	public void keyReleased(KeyEvent key) {}
	
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(backgroundImage, 0, 0, null);
		
		//frame.ssButton.repaint();

		if(state == State.MENU)
		{
			view.menu.drawGameMenu(g);
		}
		else
		{
			g2.setFont(new Font("Arial", Font.BOLD, 16));
			g2.setColor(Color.GREEN);
			
			if(!running)
				g2.setComposite(AlphaComposite.SrcOver.derive(0.4f));
			
			//Image reverseCard = table.cardDeck.getReverseCardSprite();
			
			int x = 100;
			for(int i = 0; i < MAX_PLAYERS; i++)
			{
				if(players[i] != null)
				{
					if(players[i] != player)
					{
						g2.drawImage(graphics.reverseCard, posX[i], posY[i] + 20, null);
						g2.drawImage(graphics.reverseCard, posX[i] + 20, posY[i], null);
						g2.setColor(Color.GREEN);
					}
					else
					{
						if(running)
						{
							g2.drawImage(graphics.getCardImage(player.getGameCards()[0]), posX[i], posY[i] + 20, null);
							g2.drawImage(graphics.getCardImage(player.getGameCards()[1]), posX[i] + 20, posY[i], null);
						}
						else
						{
							g2.drawImage(graphics.reverseCard, posX[i], posY[i] + 20, null);
							g2.drawImage(graphics.reverseCard, posX[i] + 20, posY[i], null);
						}
						g2.setColor(Color.WHITE);
					}
					if(!players[i].isReady()) g2.setColor(Color.GRAY);
					g2.drawString(players[i].getName(), posX[i], posY[i] + 160);
					g2.drawString("$" + players[i].getMoney(), posX[i], posY[i] + 160 + 16);
					if(players[i].getBet() > 0)
					{
						g2.setColor(Color.WHITE);
						g2.drawString("Stawia: $" + players[i].getBet(), posX[i], posY[i] + 160 + 16 + 16);
					}
				}
				x += 200;
			}
			
			if(flop.size() >= 3)
			{
				g2.drawImage(graphics.getCardImage(flop.get(0)), 310, 242, null);
				g2.drawImage(graphics.getCardImage(flop.get(1)), 400, 242, null);
				g2.drawImage(graphics.getCardImage(flop.get(2)), 490, 242, null);
			}
			else
			{
				g2.drawImage(graphics.reverseCard, 310, 242, null);
				g2.drawImage(graphics.reverseCard, 400, 242, null);
				g2.drawImage(graphics.reverseCard, 490, 242, null);
			}
			if(turn != null)
				g2.drawImage(graphics.getCardImage(turn), 580, 242, null);
			else
				g2.drawImage(graphics.reverseCard, 580, 242, null);
			
			if(river != null)
				g2.drawImage(graphics.getCardImage(river), 670, 242, null);
			else
				g2.drawImage(graphics.reverseCard, 670, 242, null);
			
			if(running) g2.setColor(Color.GREEN);
			else g2.setColor(Color.GRAY);
			String potTxt = "Pula: $" + pot;
			Rectangle2D bounds = g2.getFont().getStringBounds(potTxt, g2.getFontRenderContext());
	        g2.drawString(potTxt, (int)(528 + WIDTH / 2 - bounds.getWidth() / 2),(int)(375 + HEIGHT/2 + 10));
		}
		
		g2.dispose();
	}
	
	public GameFrame getFrame()
	{
		return frame;
	}
	
	public void loginPlayer(String name, char[] pass)
	{
		client.loginPlayer(name, new String(pass));
	}
	
	public void loginSuccess(boolean success)
	{
		if(success)
		{
			player = client.getPlayer();
			view.menu.setState(GameMenu.State.LOGGED);
			player = client.getPlayer();
			frame.printMessage("Witaj " + player.getName() + "!");
		}
		else
		{
			JOptionPane.showMessageDialog(frame,
				    "Podany login lub hasło jest niepoprawne.",
				    "Błąd logowania",
				    JOptionPane.WARNING_MESSAGE);
			JDialog loginDialog = new LoginDialog("Logowanie", this);
        	loginDialog.setVisible(true);
		}
	}
	
	public void logoutPlayer()
	{
		client.logoutPlayer();
		view.menu.setState(GameMenu.State.NOT_LOGGED);
		frame.printMessage("Żegnaj " + player.getName() + "!");
	}
	
	public boolean registerPlayer(String name, char[] password, String email)
	{
		client.registerPlayer(name, new String(password));
		return true;
	}
	
	public void registerSuccess(boolean success)
	{
		if(!success)
			JOptionPane.showMessageDialog(getFrame(), "Ten nickname jest zajęty.", "Błąd rejestracji", JOptionPane.WARNING_MESSAGE);
		else
		{
			JOptionPane.showMessageDialog(getFrame(), "Konto zostało zarejestrowane. Zaloguj się!", "Rejestracja", JOptionPane.INFORMATION_MESSAGE);
			JDialog loginDialog = new LoginDialog("Logowanie", this);
        	loginDialog.setVisible(true);
		}
	}
	
	private void loadProperties() throws IOException 
	{
		InputStream inputStream;

		try 
		{
			inputStream = new FileInputStream(propFileName);
			prop.load(inputStream);
		} 
		catch (FileNotFoundException e)
		{
			System.out.println("nie odnaleziono pliku własności " + propFileName);
		}
	}
	
	public void saveProperties()
	{
		OutputStream outputStream;
		try
		{
			outputStream = new FileOutputStream(propFileName);
			prop.store(outputStream, "Java Hold'em Properties");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Properties getProperties()
	{
		return prop;
	}
	
	public void printMessage(String message)
	{
		frame.printMessage(message);
	}
	
	public GameClient getClient()
	{
		return client;
	}
	
	public void playerMessage(String message)
	{
		client.playerMessage(message);
		frame.printMessage(player.getName() + ": " + message);
	}
	
	public GameSpeaker getSpeaker()
	{
		return speaker;
	}
	
	public void addPlayer(int id, String name, int money, int position, boolean ready)
	{
		if(position >= 0 && position < MAX_PLAYERS)
		{
			if(player.getName().equals(name))
				players[position] = player;
			else
				players[position] = new Player(id, name, null, money, position);
			players[position].setReady(ready);
		}
		repaint();
	}
	
	public void removePlayer(String name)
	{
		for(int i = 0; i < MAX_PLAYERS; i++)
		{
			if(players[i] != null)
			{
				if(players[i].getName().equals(name))
				{
					players[i] = null;
					break;
				}
			}
		}
		printMessage(name + " opuścił stół.");
		repaint();
	}
	
	public void fullTable()
	{
		state = State.MENU;
		JOptionPane.showMessageDialog(getFrame(), "Stół jest pełny.", "Błąd", JOptionPane.WARNING_MESSAGE);
		repaint();
	}
	
	public void playerReady()
	{
		player.setReady(true);
		client.playerReady();
		repaint();
	}
	
	public void playerReady(String name)
	{
		for(int i = 0; i < MAX_PLAYERS; i++)
		{
			if(players[i] != null)
			{
				if(players[i].getName().equals(name))
				{
					players[i].setReady(true);
				}
			}
		}
		repaint();
	}
	
	public void playerCards(Card c1, Card c2)
	{
		player.giveCards(c1, c2);
	}
	
	public void playerMove()
	{
		frame.setCheckButton(raise - player.getBet());
		frame.setFoldButton();
		frame.setRaiseButton(player.getMoney());
		
		if(player.getMoney() < raise)
			frame.setCheckButton(-1);
	}
	
	public void playerAction(String input)
	{
		String[] args = input.split(" ", 2);
		String playerName = args[0];
		String action = args[1];
		
		if(action.startsWith("SMALL"))
		{
			Player pl = getPlayer(playerName);
			pl.addBet(small_blind);
			pl.setAction(Action.SMALL);
			raise = small_blind*2;
		}
		else if(action.startsWith("BIG"))
		{
			Player pl = getPlayer(playerName);
			pl.addBet(small_blind*2);
			pl.setAction(Action.BIG);
			raise = small_blind*2;
		}
		else if(action.startsWith("CALL"))
		{
			int bet = Integer.parseInt(action.split(" ", 2)[1]);
			Player pl = getPlayer(playerName);
			pl.addBet(bet);
			pl.setAction(Action.CALL);
			//raise += bet;
		}
		else if(action.startsWith("FOLD"))
		{
			Player pl = getPlayer(playerName);
			pl.setAction(Action.FOLD);
			pl.setReady(false);
			
			int count = 0;
			for(int i = 0; i < MAX_PLAYERS; i++)
			{
				if(players[i] != null)
				{
					if(players[i].isReady())
						count++;
				}
			}
			if(count <= 1)
			{
				stopGame();
			}
		}
		else if(action.startsWith("RAISE"))
		{
			int raise = Integer.parseInt(action.split(" ", 2)[1]);
			Player pl = getPlayer(playerName);
			pl.addBet(raise);
			
			if(pl.getBet() > this.raise)
				this.raise = pl.getBet();
		}
		
		repaint();
	}
	
	public void winner(String playerName, int amount)
	{
        assert playerName != null : "Niezdefiniowany gracz!";

		Player pl = getPlayer(playerName);
		pl.addMoney(amount);
		pot = 0;
		raise = 0;
		repaint();
	}
	
	public void startGame(int dealer, int small_blind)
	{
		this.dealer = dealer;
		this.small_blind = small_blind;
		pot = 0;
		raise = 0;
		
		//thread.start();
		running = true;
		printMessage("Runda rozpoczęta!");
		repaint();
	}
	
	public void stopGame()
	{
		dealer = -1;
		small_blind = 0;
		running = false;
		pot = 0;
		
		for(int i = 0; i < MAX_PLAYERS; i++)
		{
			if(players[i] != null)
			{
				players[i].setBet(0);
			}
		}
		
		flop.clear();
		turn = null;
		river = null;
		thread.interrupt();
		player.isReady();
		
		frame.disableInterface();
		frame.setReadyButton();
	}
	
	public Player getPlayer(String name)
	{
		for(int i = 0; i < MAX_PLAYERS; i++)
		{
			if(players[i] != null)
			{
				if(players[i].getName().equals(name))
				{
					return players[i];
				}
			}
		}
		return null;
	}
	
	public void check()
	{
		frame.enableInterface();
		client.playerCheck(raise - player.getBet());
	}
	
	public void fold()
	{
		frame.enableInterface();
		client.playerFold();
	}
	
	public void raise(int value)
	{
		frame.enableInterface();
		client.playerRaise(value);
	}
	
	public void flop(int pot, Card c1, Card c2, Card c3)
	{
		this.pot = pot;
		flop.add(c1);
		flop.add(c2);
		flop.add(c3);
		
		for(int i = 0; i < MAX_PLAYERS; i++)
		{
			if(players[i] != null)
			{
				players[i].setBet(0);
			}
		}
		raise = 0;
		
		repaint();
	}
	
	public void turn(int pot, Card c1)
	{
		this.pot = pot;
		this.turn = c1;
		
		for(int i = 0; i < MAX_PLAYERS; i++)
		{
			if(players[i] != null)
			{
				players[i].setBet(0);
			}
		}
		raise = 0;
		
		repaint();
	}
	
	public void river(int pot, Card c1)
	{
		this.pot = pot;
		this.river = c1;
		
		for(int i = 0; i < MAX_PLAYERS; i++)
		{
			if(players[i] != null)
			{
				players[i].setBet(0);
			}
		}
		raise = 0;
		
		repaint();
	}
	
	*/
/**
     * Klasa rejestrująca ruchy myszy
     *//*

    private class MouseControl extends MouseAdapter 
    {
        @Override
        public void mouseMoved(MouseEvent e)
        {
        	int mouseX = e.getX();
            int mouseY = e.getY();

        	if(state == State.MENU)
        	{
        		if(view.menu.mouseControl(mouseX, mouseY, false))
        			repaint();
        	}
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            int mouseX = e.getX();
            int mouseY = e.getY();

            if(state == State.MENU)
        	{
        		if(view.menu.mouseControl(mouseX, mouseY, true))
        			repaint();
        	}
        }
    }
}*/
