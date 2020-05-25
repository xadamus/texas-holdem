package game;/*
package game;

import view.GamePanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class GameFrame extends JFrame 
{

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	
	private JPanel panel_2;
	private JButton readyButton;
	private JButton checkButton;
	private JButton foldButton;
	private JButton raiseButton;
	private JLabel raiseLabel;
	private JSlider raiseSlider;
	private JPanel panel_1;
	private JPanel panel_3;
	public JButton ssButton;
	
	private JPanel panel;

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					// Tworzenie ramki
					GameFrame frame = new GameFrame();
					frame.setTitle("Java Hold'em");
					frame.setSize(new Dimension(1286, 630));
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setIconImage(new ImageIcon(this.getClass().getResource("/resources/card.png")).getImage());
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	public GameFrame() 
	{
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel_2 = new JPanel();
		panel_2.setBounds(0, 566, 1056, 37);
		panel_2.setBackground(Color.BLACK);
		contentPane.add(panel_2);
		
		readyButton = new JButton("Gotowy!");
		readyButton.addActionListener(e -> {
            readyButton.setVisible(false);
            enableInterface();
            game.playerReady();
        });
		readyButton.setVisible(false);
		panel_2.add(readyButton);
		
		checkButton = new JButton("Czekam");
		checkButton.addActionListener(e -> game.check());
		panel_2.add(checkButton);
		
		foldButton = new JButton("Pasuję");
		foldButton.addActionListener(e -> game.fold());
		panel_2.add(foldButton);
		
		raiseButton = new JButton("Podbijam");
		raiseButton.addActionListener(e -> game.raise(raiseSlider.getValue()));
		panel_2.add(raiseButton);
		
		raiseLabel = new JLabel("0$");
		raiseLabel.setForeground(Color.white);
		
		raiseSlider = new JSlider();
		raiseSlider.setValue(0);
		raiseSlider.addChangeListener(e -> {
            JSlider slider = (JSlider) e.getSource();
            int value = slider.getValue();
            raiseLabel.setText(value + "$");

            if(value > 0) raiseButton.setEnabled(true);
            else raiseButton.setEnabled(false);
        });
		panel_2.add(raiseSlider);
		
		panel = new JPanel();
		//panel_2.add(panel);
		panel_2.add(raiseLabel);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.BLACK);
		panel_1.setBounds(1054, 0, 226, 566);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 11, 206, 544);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		Font font = new Font("Verdana", Font.BOLD, 10);
		textArea.setFont(font);
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		panel_1.add(textArea);
		
		panel_3 = new JPanel();
		panel_3.setBackground(Color.BLACK);
		panel_3.setBounds(1054, 566, 226, 37);
		contentPane.add(panel_3);
		
		textField = new JTextField();
		panel_3.add(textField);
		textField.setColumns(10);

		JButton sendButton = new JButton("Wyślij");
		sendButton.addActionListener(e ->
		{
            if(textField.getText().length() > 0)
            {
                String txt = textField.getText();
                game.playerMessage(txt);
                textField.setText("");
            }
        });
		panel_3.add(sendButton);
		
		// Tworzenie gry
		game = new GamePanel(1056, 605, this);
		game.setBackground(Color.WHITE);
		game.setBounds(0, 0, 1056, 566);
		game.setLayout(null);
		contentPane.add(game);
		
		disableInterface();
	}
	
	public void playerJoin()
	{
		readyButton.setVisible(true);
	}
	
	public void enableInterface()
	{
		checkButton.setVisible(true);
		checkButton.setEnabled(false);
		
		foldButton.setVisible(true);
		foldButton.setEnabled(false);
		
		raiseButton.setVisible(true);
		raiseButton.setEnabled(false);
		
		raiseSlider.setVisible(true);
		raiseSlider.setEnabled(false);
		
		raiseLabel.setVisible(true);
	}
	
	public void disableInterface()
	{
		checkButton.setVisible(false);
		foldButton.setVisible(false);
		raiseButton.setVisible(false);
		raiseSlider.setVisible(false);
		raiseLabel.setVisible(false);
	}
	
	public void setCheckButton(int amount)
	{
		if(amount == 0)
		{
			checkButton.setEnabled(true);
			checkButton.setText("Czekam");
		}
		else if(amount > 0)
		{
			checkButton.setEnabled(true);
			checkButton.setText("Sprawdzam [$" + amount + "]");
		}
		else
		{
			checkButton.setEnabled(false);
			checkButton.setText("Czekam");
		}
	}
	
	public void setFoldButton()
	{
		foldButton.setEnabled(true);
	}
	public void setRaiseButton(int maxAmount)
	{
		if(maxAmount > 0)
		{
			raiseButton.setEnabled(true);
			raiseSlider.setMaximum(maxAmount);
			raiseSlider.setEnabled(true);
		}
		else
		{
			raiseButton.setEnabled(false);
		}
	}
	
	public void setReadyButton()
	{
		readyButton.setVisible(true);
		readyButton.setEnabled(true);
	}
	
	public void printMessage(String m)
	{
		textArea.append(m + "\n");
	}
}
*/
