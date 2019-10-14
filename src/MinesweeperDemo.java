/**
 * @authors: Rafi Stepanians, Adriano Panaccione
 */

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class MinesweeperDemo extends JFrame
{
	private static JButton button1, button2, button3;
	private static JLabel labelWon, labelLost, mineLabel;
	
	public MinesweeperDemo(String title, int width, int height)
	{
		super(title);
		setSize(width, height);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(2, 3));
		
		
		/**
		 * Create labels
		 */
		labelWon = new JLabel("Games won: 0");
		labelLost = new JLabel("Games lost: 0");
		mineLabel = new JLabel("Mines left: x");
		
		/**
		 * Create a button for 8x8 game mode
		 */
		button1 = new JButton("8x8");
		button1.setFont(new Font("Helvetica", Font.PLAIN, 48));
		button1.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						disableButtons();
						Minesweeper mine = new Minesweeper("8x8 Minesweeper", 600, 600, 8);
						mine.setLocationRelativeTo(null);
						mine.setVisible(true);
					}
				});
		
		/**
		 * Create a button for 16x16 game mode
		 */
		button2 = new JButton("16x16");
		button2.setFont(new Font("Helvetica", Font.PLAIN, 48));
		button2.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						disableButtons();
						Minesweeper mine = new Minesweeper("16x16 Minesweeper", 800, 800, 16);
						mine.setLocationRelativeTo(null);
						mine.setVisible(true);
					}
				});
		
		/**
		 * Create a button for 24x24 game mode
		 */
		button3 = new JButton("24x24");
		button3.setFont(new Font("Helvetica", Font.PLAIN, 48));
		button3.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{	
						disableButtons();
						Minesweeper mine = new Minesweeper("24x24 Minesweeper", 2000, 1000, 24);
						mine.setLocationRelativeTo(null);
						mine.setVisible(true);
					}
				});
		
		pane.add(button1);
		pane.add(button2);
		pane.add(button3);
		pane.add(labelWon);
		pane.add(labelLost);
		pane.add(mineLabel);
		
	}
	
	public static void setMineLabel(int mines)
	{
		mineLabel.setText("Mines left: " + String.format("%1d", mines));
	}
	
	public static void setLostLabel(int lostNumber)
	{
		labelLost.setText("Games lost: " + String.format("%1d", lostNumber));
	}
	
	public static void setWonLabel(int wonNumber)
	{
		labelWon.setText("Games won: " + String.format("%1d", wonNumber));
	}
	
	public static void disableButtons()
	{
		button1.setEnabled(false);
		button2.setEnabled(false);
		button3.setEnabled(false);
	}
	
	public static void enableButtons()
	{
		button1.setEnabled(true);
		button2.setEnabled(true);
		button3.setEnabled(true);
	}
	
	public static void main(String[] args)
	{
		MinesweeperDemo gameStart = new MinesweeperDemo("Please choose a game mode", 600, 200);
        gameStart.setLocation(0, 600);
		gameStart.setVisible(true);
	}
}
