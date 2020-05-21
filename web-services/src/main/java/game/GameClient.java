package game;/*
package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

import entities.Card;
import entities.Player;

public class GameClient extends Thread
{
	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;

	private String serverAddress;
	private int port;
	private Player player;
	private String name;
	private String password;

	public GameClient()
	{
		//this.game = game;

		serverAddress = game.getProperties().getProperty("serverAddress");
		port = Integer.parseInt(game.getProperties().getProperty("serverPort"));

		player = new Player();
	}

	public Player getPlayer()
	{
		player.setName(name);
		return player;
	}

	public void registerPlayer(String name, String password)
	{
		if(socket.isConnected())
		{
			this.name = name;
			this.password = password;
			out.println("REGISTER " + name + " " + password);
		}
	}

	public void logoutPlayer()
	{
		if(socket.isConnected())
		{
			out.println("LOGOUT");
		}
	}

	public void loginPlayer(String name, String password)
	{
		this.name = name;
		this.password = password;
		out.println("LOGIN " + name + " " + password);
	}

	public void playerMessage(String message)
	{
		if(socket.isConnected())
		{
			out.println("MESSAGE " + message);
		}
	}

	public void playerJoin()
	{
		if(socket.isConnected())
		{
			out.println("JOIN");
		}
	}

	public void playerReady()
	{
		if(socket.isConnected())
		{
			out.println("READY");
		}
	}

	public void playerCheck(int amount)
	{
		out.println("ACTION CALL " + amount);
	}

	public void playerFold()
	{
		out.println("ACTION FOLD");
	}

	public void playerRaise(int value)
	{
		out.println("ACTION RAISE " + value);
	}

	public void run()
	{
		try
		{
			socket = new Socket(serverAddress, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			while (true)
			{
				String line = in.readLine();
				System.out.println(line);
				if (line.startsWith("WELCOME"))
				{
					game.printMessage("Połączono z serwerem " + serverAddress + ":" + port);
				}
				else if (line.startsWith("LOGGED"))
				{
					player.setLogged(true);
					game.loginSuccess(true);
				}
				else if (line.startsWith("NOTLOGGED"))
				{
					game.loginSuccess(false);
				}
				else if (line.startsWith("REGISTERED"))
				{
					game.registerSuccess(true);
				}
				else if (line.startsWith("REGISTER_ERROR"))
				{
					game.registerSuccess(false);
				}
				else if (line.startsWith("SETMONEY"))
				{
					player.setMoney(Integer.parseInt(line.substring(9)));
				}
				else if (line.startsWith("MESSAGE"))
				{
					String msg = line.substring(8);
					game.printMessage(msg);
				}
				else if (line.startsWith("JOIN"))
				{
					String[] args = line.substring(5).split(" ", 5);
					int id = Integer.parseInt(args[0]);
					String name = args[1];
					int money = Integer.parseInt(args[2]);
					int position = Integer.parseInt(args[3]);
					boolean ready = Boolean.parseBoolean(args[4]);

					game.addPlayer(id, name, money, position, ready);
				}
				else if (line.startsWith("LEAVE"))
				{
					String name = line.substring(6);
					game.removePlayer(name);
				}
				else if (line.startsWith("FULL"))
				{
					game.fullTable();
				}
				else if (line.startsWith("READY"))
				{
					game.playerReady(line.substring(6));
				}
				else if (line.startsWith("SETCARDS"))
				{
					String[] args = line.substring(9).split(" ", 4);
					int c1v = Integer.parseInt(args[0]);
					int c1s = Integer.parseInt(args[1]);
					int c2v = Integer.parseInt(args[2]);
					int c2s = Integer.parseInt(args[3]);
					game.playerCards(new Card(c1v, c1s), new Card(c2v, c2s));
				}
				else if (line.startsWith("START"))
				{
					String[] args = line.substring(6).split(" ", 2);
					int dealer = Integer.parseInt(args[0]);
					int small_blind = Integer.parseInt(args[1]);
					game.startGame(dealer, small_blind);
				}
				else if (line.startsWith("MOVE"))
				{
					game.playerMove();
				}
				else if (line.startsWith("ACTION"))
				{
					game.playerAction(line.substring(7));
				}
				else if (line.startsWith("FLOP"))
				{
					String[] args = line.substring(5).split(" ", 7);
					int pot = Integer.parseInt(args[0]);
					int c1v = Integer.parseInt(args[1]);
					int c1s = Integer.parseInt(args[2]);
					int c2v = Integer.parseInt(args[3]);
					int c2s = Integer.parseInt(args[4]);
					int c3v = Integer.parseInt(args[5]);
					int c3s = Integer.parseInt(args[6]);
					game.flop(pot, new Card(c1v, c1s), new Card(c2v, c2s), new Card(c3v, c3s));
				}
				else if (line.startsWith("TURN"))
				{
					String[] args = line.substring(5).split(" ", 3);
					int pot = Integer.parseInt(args[0]);
					int c1v = Integer.parseInt(args[1]);
					int c1s = Integer.parseInt(args[2]);
					game.turn(pot, new Card(c1v, c1s));
				}
				else if (line.startsWith("RIVER"))
				{
					String[] args = line.substring(6).split(" ", 3);
					int pot = Integer.parseInt(args[0]);
					int c1v = Integer.parseInt(args[1]);
					int c1s = Integer.parseInt(args[2]);
					game.river(pot, new Card(c1v, c1s));
				}
				else if (line.startsWith("WINNER"))
				{
					String[] args = line.substring(7).split(" ", 2);
					String name = args[0];
					int amount = Integer.parseInt(args[1]);
					game.winner(name, amount);
				}
				else if (line.startsWith("END"))
				{
					game.stopGame();
				}
			}
		}
		catch(IOException e)
		{
			game.printMessage("Błąd połączenia: " + e);
		}
	}
}*/
