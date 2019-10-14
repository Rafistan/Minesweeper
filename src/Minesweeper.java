/**
 * @authors: Rafi Stepanians, Adriano Panaccione
 */

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Minesweeper extends JFrame implements KeyListener
{	
	private JButton[][] button, playButton;
	private int mines, displayMines;
	private boolean isCtrlPressed = false;
	private static int lostNumber, wonNumber;
    private Icon mineIcon, flagIcon;
    private Clip clipClick, clipExplose;
    private AudioInputStream aisClick, aisExplose;
    private File clickSound, clickExplose;
	
	public Minesweeper(String title, int width, int height, int grid)
	{
		super(title);
		setSize(width, height);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		
		/**
         * Loading sound from file
         */
        clickSound = new File("sounds/Click.wav");
        clickExplose = new File("sounds/explose.wav");
                
		addKeyListener(this);
		
		button = new JButton[grid][grid];
		playButton = new JButton[grid][grid];
		
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(grid, grid));
		
		/**
		 * Sets the class focusable(Used for holding the control down method)
		 */
		setFocusable(true);
                
        /**
         * Load images from file
         */
        mineIcon = new ImageIcon("images/bomb.png");
        flagIcon = new ImageIcon("images/flag.png");
               
		for(int i = 0; i < grid; i++)
		{
			for(int j = 0; j < grid; j++)
			{
				int x = i;
				int y = j;
				
				button[i][j] = new JButton("");
				playButton[i][j] = new JButton("");
				playButton[i][j].setFont(new Font("Helvetica", Font.PLAIN, 20));
				playButton[i][j].addKeyListener(this);
				playButton[i][j].addActionListener(new ActionListener()
						{
                            @Override
							public void actionPerformed(ActionEvent e)
							{
                                /**
                                 * Make clicking sound everytime the a button is pressed.                                
                                 */
                            	clickingSound();
                                
                                                            
								/**
								 * To place a mine
								 * If control is pressed
								 */
								if (isCtrlPressed && playButton[x][y].getIcon() == null)
								{	
                                    playButton[x][y].setIcon(flagIcon);
                                    displayMines--;
                                    MinesweeperDemo.setMineLabel(displayMines);
								}
								/**
								 * To remove the mine
								 * If control is pressed again
								 */
								else if(isCtrlPressed && playButton[x][y].getIcon() == flagIcon)
								{
									playButton[x][y].setIcon(null);
									displayMines++;
									MinesweeperDemo.setMineLabel(displayMines);
								}
								
								
								/**
								 * If button is a number or a mine
								 */
								else if(!button[x][y].getText().equals(""))
								{
									/**
									 * If clicked on a mine, games is done
									 */
									if(button[x][y].getText().equals("*"))
									{
                                        lostNumber++;
                                        MinesweeperDemo.setLostLabel(lostNumber);
                                        gameIsFinished(grid);
                                        playButton[x][y].setIcon(mineIcon);
                                        explode();
                                        MinesweeperDemo.enableButtons();
									}
                                    else
                                    {
                                        playButton[x][y].setText(button[x][y].getText());
                                        playButton[x][y].setEnabled(false);
                                    }	
								}
								
								
								/**
								 * If button has nothing in it, it's disabled
								 * The disabled button around it are also opened.
								 */
								else if(button[x][y].getText().equals(""))
								{
									playButton[x][y].setEnabled(false);
									openDisabledButtons(x, y, grid);
								}
								
								/**
								 * Checks if the games done
								 */
								if(isGameStillRunning(grid) && displayMines == 0)
								{
									wonNumber++;
									MinesweeperDemo.setWonLabel(wonNumber);
									gameIsFinished(grid);
									MinesweeperDemo.enableButtons();
								}
							}
						});
				pane.add(playButton[i][j]);
			}
		}
		
		/**
		 * Set mines on the grid
		 */
		setMines(grid);
		
		MinesweeperDemo.setMineLabel(displayMines);
		/**
		 * Set numbers around the mines
		 */
		setNumbers(grid);
	}
	
	public void clickingSound()
	{
		try
        {
            aisClick = AudioSystem.getAudioInputStream(clickSound);
            clipClick = AudioSystem.getClip();
            clipClick.open(aisClick);
            clipClick.start();
        }catch(Exception e){
        	
        }
	}
	
    public void explode()
    {
        try{
            aisExplose = AudioSystem.getAudioInputStream(clickExplose);
            clipExplose = AudioSystem.getClip();
            clipExplose.open(aisExplose);
            clipExplose.start();
        }catch(Exception e){
            
        }
    }
    
    
	public void openDisabledButtons(int x, int y, int grid)
	{	
		for(int row = Math.max(x - 1, 0); row <= Math.min(x + 1, grid - 1); row++)
		{
			for(int column = Math.max(y - 1, 0); column <= Math.min(y + 1, grid - 1); column++)
			{
				playButton[row][column].setText(button[row][column].getText());
				
				if(button[row][column].getText().equals("") && playButton[row][column].isEnabled())
				{
					playButton[row][column].setEnabled(false);
					openDisabledButtons(row, column, grid);
				}
				
				playButton[row][column].setEnabled(false);
			}
		}	
	}
	
	public boolean isGameStillRunning(int grid)
	{
		for(int i = 0; i < grid; i++)
		{
			for(int j = 0; j < grid; j++)
			{
				if(playButton[i][j].isEnabled() && playButton[i][j].getIcon() != flagIcon)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public void gameIsFinished(int grid)
	{
		for(int i = 0; i < grid; i++)
		{
			for(int j = 0; j < grid; j++)
			{
                if(button[i][j].getText().equals("*"))
                    playButton[i][j].setIcon(mineIcon);
                playButton[i][j].setEnabled(false);
			}
		}
	}
	
	public void setNumbers(int grid)
	{
		int count = 0;
		
		for(int row = 0; row < grid; row++)
		{
			for(int column = 0; column < grid; column++)
			{
				if(button[row][column].getText().equals(""))
				{
					
					for(int y = Math.max(row - 1, 0); y <= Math.min(row + 1, grid - 1); y++)
					{
						for(int x = Math.max(column - 1, 0); x <= Math.min(column + 1, grid - 1); x++)
						{
                            if(button[y][x].getText().equals("*"))
                                    count++;
						}
					}
					
					if(count != 0)
                    {
                        button[row][column].setText(String.format("%1d", count));
                    }
					else
                        button[row][column].setEnabled(false);
					count = 0;
				}
				
			}
		}
	}
	
	
	public int getMines(int grid)
	{
		if(grid == 8)
			return 10;
		else if(grid == 16)
			return 40;
		else
			return 99; 
	}
	
	public void setMines(int grid)
	{
		mines = getMines(grid); 
		
		displayMines = mines;
		
		while(mines > 0)
		{
			int x;
			int y;
			
			do
			{
				x = (int) (Math.random() * grid);
				y = (int) (Math.random() * grid);
			}while(!button[x][y].getText().equals(""));
			
			button[x][y].setText("*");
			button[x][y].setFont(new Font("Helvetica", Font.BOLD, 24));
			
			mines--;
		}
	}

	@Override
	public void keyPressed(KeyEvent ke)
	{
		isCtrlPressed = ke.isControlDown();
		isCtrlPressed = ke.isMetaDown();
	}
	
	@Override
	public void keyReleased(KeyEvent ke)
	{
		isCtrlPressed = ke.isControlDown();
		isCtrlPressed = ke.isMetaDown();
	}
	
	@Override
	public void keyTyped(KeyEvent ke)
	{
		
	}
	
}
