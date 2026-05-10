// Gowri Athreya
// 04/21/2026
// SymptomSleuth.java
// Period 2

/*import all required libraries*/

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.FontFormatException;

import java.util.Scanner;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;

/* This is the main class that starts the game. 
 * It creates the window and sets it up.
 */
public class SymptomSleuth
{
	public static void main(String[] args)
	{
		// create instance of class
		SymptomSleuth ss = new SymptomSleuth();
		// call fake main (runIt)
		ss.runIt();
	}
	public void runIt()
	{
		// create main game window
		JFrame frame = new JFrame("Symptom Sleuth");
		// set window size to 960 x 600
		frame.setSize(960, 600);
		// set window position on screen
		frame.setLocation(200, 140);
		// prevent resizing window
		frame.setResizable(false);
		// close program when X is clicked
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// create instance of card layout holder class
        SymptomSleuthCards ssc = new SymptomSleuthCards();
        // call method to start music
        ssc.startMusic();
        // add game to the window
        frame.getContentPane().add(ssc);
        // make it visible
        frame.setVisible(true);
	}
}

/* This class controls all screens in the game using CardLayout. 
 * It stores shared data like player name and font.
 * It also controls background music (on and off).
*/
class SymptomSleuthCards extends JPanel
{
	private CardLayout allPanelCards;// handles switching between screens
	private Clip clip; // stores audio music
	private long clipTimePos;// saves where music was paused
	private Font myFont;// custom font used in game

	
	public SymptomSleuthCards()
	{
		// create CardLayout
		allPanelCards = new CardLayout();
		// apply CardLayout to this panel
		setLayout(allPanelCards);
		
		// load custom font from file
		try
		{
			//create font from external .ttf file
			myFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Borg.ttf"));
		}
		catch(FontFormatException e)
		{
			e.printStackTrace();
		}
		catch(IOException e) //catches error (same as above)
		{
			e.printStackTrace();
		}

		// create player info objecy/instance
		GameData info = new GameData();
		info.loadFiles();
		// create all game screes ad pass all data(variables)
		StartPanel sp = new StartPanel(this, allPanelCards, info, myFont);
		SettingsPanel sgp = new SettingsPanel(this, allPanelCards, info, myFont);
		DoorsPanel dp = new DoorsPanel(this, allPanelCards, info, myFont);
		PeoplePanel pp = new PeoplePanel(this, allPanelCards, info, myFont);
		LeaderBoardPanel lbp = new LeaderBoardPanel(this, allPanelCards, info, myFont);
		InstructionsPanel ip = new InstructionsPanel(this, allPanelCards, info, myFont);
		QuestionsPanel qp = new QuestionsPanel(this, allPanelCards, info, myFont);
		CongratsPanel cp = new CongratsPanel(this, allPanelCards, info, myFont);
		
		
		// add each panel to CardLayout with a name
		add(sp, "start");
		add(sgp, "settings");
		add(dp, "door");
		add(pp,"people");
		add(lbp, "leader");
		add(ip, "instructions");
		add(qp,"questions");
		add(cp, "congrats");
	}
	
	
	public void startMusic()
	{
		try
		{
			// load music file from folder
			File file = new File("sounds/song1.wav");
			// convert file into audio stream
			AudioInputStream audio = AudioSystem.getAudioInputStream(file);
			
			// create clip (sound player)
			clip = AudioSystem.getClip();
			
			// open audio file inside clip
			clip.open(audio);
			
			// loop music forever
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
		catch(Exception e)
		{
			// runs if file missing or audio fails
				e.printStackTrace();
		}
	}
	public void pauseMusic()
	{
		// only pause if music exists and is playing

		if(clip.isRunning()&&clip!=null)
		{
			// save current position in song
			clipTimePos = clip.getMicrosecondPosition();
			// stop music
			clip.stop();
		}
	}
		
	public void resumeMusic()
	{
		// only resume if clip exists
		if(clip!=null)
		{
			// go back to saved position
			clip.setMicrosecondPosition(clipTimePos);
			// start playing again
			clip.start();
		}
	}
}
/* This is the start screen of the game.
 * It lets the user enter their name, start the game, or open settings/menu.
 */

class StartPanel extends JPanel implements ActionListener, MouseListener
{
	private SymptomSleuthCards firstPanel;
	private CardLayout allPanelCards;
	private JTextField name;
	private JButton startGame, settingsB;
	private Color myColor;
	private JMenuItem leader, instruction, exit;
	private boolean pressed;// to see if they pressed enter
	private GameData info;
	private Font myFont;
		
	public StartPanel(SymptomSleuthCards scIn, CardLayout cardsIn, GameData infoIn, Font fontIn)
	{
		allPanelCards = cardsIn;
		firstPanel = scIn;
		pressed = false;
		info = infoIn;
		myFont = fontIn;
		//setBackground(Color.BLUE);
		
		setLayout(null);
		
		
		//myFont = new Font("Monospaced", Font.BOLD, 15);
		
		// custom theme color
		myColor = new Color(76,92,127);
		
		
		// title labels
		JLabel title1 = new JLabel("Symptom");
		//JLabel tilte1Overlap = new JLabel("Symptom");
		JLabel title2 = new JLabel("Sleuth");
		//JLabel tilte2Overlap = new JLabel("Sleuth");
		// set font, size, position, color
		title1.setFont(myFont.deriveFont(200f));
		title1.setBounds(250,80,700,200);
		title1.setForeground(Color.WHITE);
		//tilte1Overlap.setFont(myFont.deriveFont(215f));
		//tilte1Overlap.setBounds(235,80,700,200);
		//tilte1Overlap.setForeground(new Color (15, 25, 45));
		title2.setFont(myFont.deriveFont(200f));
		title2.setBounds(350,250,700,200);
		title2.setForeground(Color.WHITE);
		//title2.setForeground(new Color (15, 25, 45));
		
		// background image usiing image icon and JLabel
		ImageIcon icon = new ImageIcon("images/hospitalBackground.png");
		JLabel background = new JLabel(icon);
		background.setBounds(0,0,960,600);
		background.setLayout(null);
		// start button	
		startGame = new JButton("Start Game");
		startGame.setFont(myFont.deriveFont(40f));
		startGame.setForeground(Color.BLACK);
		startGame.setBackground(Color.WHITE);
		startGame.setBounds(380, 500, 200, 50);
	//	startGame.setOpaque(false);
		///startGame.setFocusPainted(false);
		startGame.setBorderPainted(false);
		startGame.addActionListener(this);
		// text field for name
		name = new JTextField("Enter your name here");
		name.setFont(myFont.deriveFont(25f));
		name.setBounds(380, 450, 200, 25);
		name.setBackground(Color.WHITE);
		name.setForeground(Color.BLACK);
		//name.setOpaque(false);
		name.addActionListener(this);
		// detect mouse click to clear text
		name.addMouseListener(this);
		// settings button
		settingsB = new JButton("Settings");
		settingsB.setFont(myFont.deriveFont(30f));
		settingsB.setBounds(0, 0, 110, 30);
		settingsB.setForeground(Color.WHITE);
		settingsB.setBackground(Color.WHITE);
		settingsB.setOpaque(false);
		settingsB.setFocusPainted(false);
		settingsB.setBorderPainted(false);
		settingsB.addActionListener(this);
		
		// create  a menu with options to go to leaderboard, exit, instructions
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("options");
		leader = new JMenuItem("Leaderboard");
		instruction = new JMenuItem("Instructions");
		exit = new JMenuItem("Exit");
		
		leader.addActionListener(this);
		instruction.addActionListener(this);
		exit.addActionListener(this);
		leader.setFont(myFont.deriveFont(40f));
		leader.setForeground(Color.WHITE);
		leader.setBackground(new Color(15,24,75));
		instruction.setForeground(Color.WHITE);
		instruction.setFont(myFont.deriveFont(40f));
		instruction.setBackground(new Color(15,24,75));
		exit.setForeground(Color.WHITE);
		exit.setFont(myFont.deriveFont(40f));
		exit.setBackground(new Color(15,24,75));
		menu.setForeground(Color.WHITE);
		menu.setFont(myFont.deriveFont(35f));
		menu.setBackground(new Color(15,24,75));

		
		menu.add(leader);
		menu.add(instruction);
		menu.add(exit);
		menuBar.add(menu);
		menuBar.setBounds(880,0,110,30);
		//menuBar.setBackground(Color.WHITE);
		menuBar.setOpaque(false);
		menuBar.setBorderPainted(false);
		
		
		// add everything to background
		background.add(name);
		background.add(settingsB);
		background.add(startGame);
		background.add(title1);
		//background.add(tilte1Overlap);
		background.add(title2);
		background.add(menuBar);
		add(background);
		
		
	}
	public void actionPerformed(ActionEvent evt) 
    {
		if(evt.getSource() == name)// if the component they chose is the JTextField
		{
			pressed = true;//set pressed as true
		}
		
		
		if(evt.getSource() == settingsB)// if the component they chose is the settings button
		{
			allPanelCards.show(firstPanel, "settings"); // go to the screen settings
		}
		
		if(evt.getSource() == startGame)// if the component they chose is the start game button
		{
			if(pressed && !name.getText().equals(""))// if they pressed enter/return AND the text field is not empty
			{
				// save player name globally
				info.setName(name.getText());
				allPanelCards.show(firstPanel, "door");// go to the doors panel
			}
		}
		if(evt.getSource() == instruction)// if the component they chose is the instructions option
		{
			allPanelCards.show(firstPanel, "instructions");// go to the instructions panel
		}
		
		if(evt.getSource() == leader)// if the component they chose is the leader(board) option
		{
			allPanelCards.show(firstPanel, "leader");// go to the leaderboard panel
		}
		
		if(evt.getSource() == exit)// if the component they chose is the exit option
		{
			System.exit(1);// exit the program
		}
		
	}
    public void mouseClicked(MouseEvent evt) 
	{
		
		if(name.getText().equals("Enter your name here"))// clear placeholder text when clicked
		{
			name.setText("");
		}
		
	}

	public void mouseReleased(MouseEvent evt){}
	public void mousePressed(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}
}

/* This class creates the Settings screen.
 * It allows the user to control music (on/off), choose fonts,
 * and return back to the home screen.
 */

class SettingsPanel extends JPanel implements ActionListener
{
	
	private SymptomSleuthCards firstPanel;// reference to main panel system 
	private CardLayout allPanelCards;// layout used to switch between screens
	private JButton on, off, font1, font2, home;// buttons for settings options
	private GameData info;// stores player data
	private Font myFont;// custom font used for styling

	
	public SettingsPanel(SymptomSleuthCards scIn, CardLayout cardsIn, GameData infoIn, Font fontIn)
	{
		firstPanel = scIn;
		allPanelCards = cardsIn;
		setLayout(new BorderLayout());
		info = infoIn;
		myFont = fontIn;
		
		// create background image using image icon and jlabel
		ImageIcon back = new ImageIcon("images/settings.png");
		JLabel set = new JLabel(back); 
		// allow components to be added on top of the background image
		set.setLayout(new BorderLayout());
		// create panel for labels ("Sound" and "Font")
		JPanel titles = new JPanel(new GridLayout(2,1));
		// make panel transparent so background image shows through
		titles.setOpaque(false);
	
		JLabel theSound = new JLabel("Sound");	// create "Sound" label
		theSound.setFont(myFont.deriveFont(70f)); //set custom text and font
		theSound.setForeground(Color.WHITE);// set text color
		JLabel theFont = new JLabel("Font"); // create "Font" label
		theFont.setFont(myFont.deriveFont(70f));
		theFont.setForeground(Color.WHITE);
		
		titles.add(theSound);// add labels to titles panel
		titles.add(theFont);
		
		JPanel buttons = new JPanel(new GridLayout(2,2));// create panel for buttons (2x2 grid)
		// make buttons panel transparent
		buttons.setOpaque(false);
		//JPanel on = new JPanel(new 
		on = new JButton("On");// turn music on
		on.setFont(myFont.deriveFont(50f));
		on.setOpaque(false);
		on.setContentAreaFilled(false);
		//on.setFocusPainted(false);
		on.setBorderPainted(false);
		on.setForeground(Color.WHITE);
		off = new JButton("Off");// turn music off
		off.setFont(myFont.deriveFont(50f));
		off.setOpaque(false);
		off.setContentAreaFilled(false);
		//off.setFocusPainted(false);
		off.setBorderPainted(false);
		off.setForeground(Color.WHITE);
		font1 = new JButton("Times New Roman");// place holder for font1 ("Times New Roman" != font1)
		font1.setFont(myFont.deriveFont(50f));
		font1.setOpaque(false);
		font1.setContentAreaFilled(false);
		//font1.setFocusPainted(false);
		font1.setBorderPainted(false);
		font1.setForeground(Color.WHITE);
		font2 = new JButton("Sans Serif");// place holder for font2 ("SansSerif" != font2)
		font2.setFont(myFont.deriveFont(50f));
		font2.setOpaque(false);
		font2.setContentAreaFilled(false);
		//font2.setFocusPainted(false);
		font2.setBorderPainted(false);
		font2.setForeground(Color.WHITE);
		// add action listeners so buttons respond to clicks
		on.addActionListener(this);
		off.addActionListener(this);
		font1.addActionListener(this);
		font2.addActionListener(this);
		// add buttons to grid panel
		buttons.add(on);
		buttons.add(off);
		buttons.add(font1);
		buttons.add(font2);
		
		// create panel for home button
		JPanel homePanel = new JPanel();
		homePanel.setOpaque(false);// make it transparent
		home = new JButton("return back to home");
		home.setFont(myFont.deriveFont(25f));
		home.addActionListener(this);
		homePanel.add(home);
		
		// add panels to background layout
		set.add(buttons, BorderLayout.EAST);// buttons on right
		set.add(titles, BorderLayout.WEST);// labels on left
		set.add(homePanel, BorderLayout.SOUTH);// home button at bottom
		
		// add everything to main panel
		add(set);
		
		
	
		
	}
	public void actionPerformed(ActionEvent evt) 
    {
		if(evt.getSource() == home)// if home button is clicked
		{
			allPanelCards.show(firstPanel, "start");// switch back to start screen
		}	
		if(evt.getSource() == on)// if "On" button clicked
		{
			firstPanel.resumeMusic();// resume music using main panel method

		}
		else if(evt.getSource() == off)// if "Off" button clicked
		{
			firstPanel.pauseMusic();// pause music
		}
			
	}
}

/* This class creates the "Doors" screen.
 * It displays 3 clickable doors and lets the player choose one.
 * When any door is clicked, the game moves to the "People" screen.
*/
class DoorsPanel extends JPanel implements ActionListener
{
	private SymptomSleuthCards firstPanel;
	private CardLayout allPanelCards;
	private JButton door1, door2, door3;
	private JLabel label; // Moved here so it can be updated
	private GameData info;
	private Font myFont;
	
	private String[] doorFiles = {"door1.png", "door2.png", "door3.png", "door4.png", "door5.png"};
		
	public DoorsPanel(SymptomSleuthCards scIn, CardLayout cardsIn, GameData infoIn, Font fontIn)
	{
		firstPanel = scIn;
		allPanelCards = cardsIn;
		info = infoIn;
		myFont = fontIn;
		
		setLayout(new BorderLayout());	
		
		JPanel titlePanel = new JPanel();
		label = new JLabel("Pick one of the doors!"); // Initial text
		label.setFont(myFont.deriveFont(40f));
		titlePanel.add(label);
		
		door1 = new JButton();
		door2 = new JButton();
		door3 = new JButton();
		
		door1.addActionListener(this);
		door2.addActionListener(this);
		door3.addActionListener(this);
		
		JPanel doorPanel = new JPanel(new GridLayout(1, 3));
		doorPanel.add(door1);		
		doorPanel.add(door2);
		doorPanel.add(door3);
		
		add(titlePanel, BorderLayout.NORTH);
		add(doorPanel, BorderLayout.CENTER);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		// Update the Title with the current level
		label.setText("Level " + info.getLevel() + ": Pick a Door!");
		
		int doorIndex = (info.getLevel() - 1) / 2;
		if (doorIndex > 4) doorIndex = 4;
		
		ImageIcon doorPic = new ImageIcon("images/" + doorFiles[doorIndex]);
		door1.setIcon(doorPic);
		door2.setIcon(doorPic);
		door3.setIcon(doorPic);
	}

	public void actionPerformed(ActionEvent evt) 
    {
		if(evt.getSource() == door1 || evt.getSource() == door2 || evt.getSource() == door3)
		{
			info.setActiveCase(); // Now picks a random patient for the RIGHT level
			allPanelCards.show(firstPanel, "people");
		}	
	}
}
			
			
			
	

class PeoplePanel extends JPanel implements MouseListener, MouseMotionListener
{
	private SymptomSleuthCards firstPanel;
	private CardLayout allPanelCards;
	private GameData info;
	private Font myFont;
	private JLabel bubbleLabel; // the draggable speech bubble
	private int mouseX, mouseY; // used for smooth dragging
	
	// target location for the bubble outline
	private final int GOAL_X = 270; 
	private final int GOAL_Y = 72;
	private final int TARGET_RANGE = 40; // how close they need to get to snap it

	public PeoplePanel(SymptomSleuthCards scIn, CardLayout cardsIn, GameData infoIn, Font fontIn)
	{
		firstPanel = scIn;	
		allPanelCards = cardsIn;
		info = infoIn;
		myFont = fontIn;
		
		// allows us to place the bubble using x and y coordinates
		setLayout(null); 

		// create background image for the patient
		ImageIcon patientIcon = new ImageIcon("images/patient.png");
		JLabel background = new JLabel(patientIcon);
		background.setBounds(0, 0, 960, 600);
		
		// create the bubble image
		ImageIcon bubbleIcon = new ImageIcon("images/bubble.png");
		bubbleLabel = new JLabel(bubbleIcon);
		
		// call method to set a random start position
		resetBubbleLocation();
		
		// set size of the label to match the bubble image
		bubbleLabel.setSize(250, 150);

		// add listeners to the bubble so it can be dragged
		bubbleLabel.addMouseListener(this);
		bubbleLabel.addMouseMotionListener(this);

		// add bubble first so it stays on top of the background
		add(bubbleLabel);
		add(background);
	}

	// moves the bubble to a random spot on the right side
	public void resetBubbleLocation() 
	{
		// pick random X on the right side of the screen
		int startX = (int)(Math.random() * 230) + 480; 
		// pick random Y in the middle area
		int startY = (int)(Math.random() * 350) + 50;
		
		// update bubble position
		bubbleLabel.setLocation(startX, startY);
	}

	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		// if the bubble is at the goal, reset it for the next patient
		if (bubbleLabel.getX() == GOAL_X && bubbleLabel.getY() == GOAL_Y) 
		{
			resetBubbleLocation();
		}
	}

	public void mousePressed(MouseEvent evt)
	{
		// save where the mouse clicked inside the bubble
		mouseX = evt.getX();
		mouseY = evt.getY();
	}

	public void mouseDragged(MouseEvent evt) 
	{
		// move the bubble based on where the mouse is moving
		int newX = bubbleLabel.getX() + evt.getX() - mouseX;
		int newY = bubbleLabel.getY() + evt.getY() - mouseY;
		
		// update location as it drags
		bubbleLabel.setLocation(newX, newY);
	}

	public void mouseReleased(MouseEvent evt) 
	{
		// check if they dropped the bubble close enough to the target
		if (Math.abs(bubbleLabel.getX() - GOAL_X) < TARGET_RANGE && 
			Math.abs(bubbleLabel.getY() - GOAL_Y) < TARGET_RANGE) 
		{
			// snap it exactly into place
			bubbleLabel.setLocation(GOAL_X, GOAL_Y);
			
			// pick the random patient data for this turn
			info.setActiveCase();
			
			// move to the questions screen
			allPanelCards.show(firstPanel, "questions");
		}
	}

	// required methods that we aren't using
	public void mouseClicked(MouseEvent evt) {}
	public void mouseEntered(MouseEvent evt) {}
	public void mouseExited(MouseEvent evt) {}
	public void mouseMoved(MouseEvent evt) {}
}

class LeaderBoardPanel extends JPanel implements ActionListener
{
	private SymptomSleuthCards firstPanel;
	private CardLayout allPanelCards;
	private GameData info;
	private Font myFont;
	private JButton home;// button to return to start screen
	
	public LeaderBoardPanel(SymptomSleuthCards scIn, CardLayout cardsIn, GameData infoIn, Font fontIn)
	{
		firstPanel = scIn;
		allPanelCards = cardsIn;
		info = infoIn;
		myFont = fontIn;
		
		setLayout(new BorderLayout());
		
		JPanel namesAndScores = new JPanel(new GridLayout(1,2));// panel intended to hold names and scores side by side
		//names.add(info.getName());
		
		JPanel names = new JPanel();// panel for names column (currently empty placeholder)
		add(names);	// add names panel to screen
		
		JPanel scores = new JPanel();// panel for scores column (currently empty placeholder)
		add(scores);// add scores panel to screen
		
		JPanel button = new JPanel();// create panel for button at bottom
		home = new JButton("return back to home");// create button to return to home screen
		home.setFont(myFont.deriveFont(25f));// set font size for button
		button.add(home);// add button to panel
		home.addActionListener(this);// allow button to respond to clicks
		
		add(namesAndScores, BorderLayout.CENTER);// add main leaderboard area in center
		add(button, BorderLayout.SOUTH);// add button at bottom of screen
		
		
	}
	public void actionPerformed(ActionEvent evt) 
    {
		if(evt.getSource() == home)// if home button is clicked
		{
			allPanelCards.show(firstPanel, "start");// return to start screen
		}
	}
	
}

class QuestionsPanel extends JPanel implements ActionListener
{
	private SymptomSleuthCards firstPanel;
	private CardLayout allPanelCards;
	private GameData info;
	private Font myFont;
	
	private JTextArea symptomText; // Main symptom display
	private JLabel patientHeader;  // Top info bar
	private JButton[] choiceButtons = new JButton[4]; 

	public QuestionsPanel(SymptomSleuthCards scIn, CardLayout cardsIn, GameData infoIn, Font fontIn)
	{
		firstPanel = scIn;
		allPanelCards = cardsIn;
		info = infoIn;
		myFont = fontIn;
		
		// Set the main layout to BorderLayout
		setLayout(new BorderLayout(10, 10)); // 10px gaps between sections
		
		//patient Header
		patientHeader = new JLabel("", JLabel.CENTER);
		patientHeader.setFont(myFont.deriveFont(40f));
		add(patientHeader, BorderLayout.NORTH);
		
       //Symptoms (Scrollable)
		symptomText = new JTextArea();
		symptomText.setLineWrap(true);
		symptomText.setWrapStyleWord(true);
		symptomText.setEditable(false);
		symptomText.setFont(myFont.deriveFont(30f));
		
		JScrollPane scroll = new JScrollPane(symptomText);
		add(scroll, BorderLayout.CENTER);
		
		//answer Buttons
		JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		for(int i = 0; i < 4; i++)
		{
			choiceButtons[i] = new JButton();
			choiceButtons[i].setFont(myFont.deriveFont(25f));
			choiceButtons[i].addActionListener(this);
			buttonPanel.add(choiceButtons[i]);
		}
		add(buttonPanel, BorderLayout.SOUTH);
	}

	public void paintComponent(Graphics g)
{
	super.paintComponent(g);
	
	// pull the new random data from our active index
	String age = info.getCaseData(1);
	String gender = info.getCaseData(4);
	String symptoms = info.getCaseData(5);
	
	// update the header and the main symptom text
	patientHeader.setText("Level " + info.getLevel() + ": " + gender + " (Age " + age + ")");
	symptomText.setText(symptoms);
	
	// update the buttons 
	for(int i = 0; i < 4; i++)
	{
		choiceButtons[i].setText(info.getCaseData(6 + i)); // loads columns 6, 7, 8, 9
	}
}
	
	public void actionPerformed(ActionEvent evt)
	{
		JButton clicked = (JButton)evt.getSource();
		int correctButtonIndex = Integer.parseInt(info.getCaseData(10).trim());
		String correctAnsText = choiceButtons[correctButtonIndex].getText();
    
		if(clicked.getText().equals(correctAnsText))
		{
			// SUCCESS! 
			info.resetAttempts(); // Reset mistakes for the new level
			info.setLevel(info.getLevel() + 1); // Move to next level
        
			if(info.getLevel() > 10)
			{
				// If they beat level 10, they win!
				allPanelCards.show(firstPanel, "congrats");
			}
			else
			{
				// If they got it right, they go back to the doors 
				// for the NEW level.
				allPanelCards.show(firstPanel, "door");
			}	
		}
		else
		{
			// WRONG ANSWER
			info.useAttempt();
        
			if(info.getAttempts() < 2)
			{
				// First mistake: Go back to doors on the SAME level
				// This allows them to pick a new door/patient for Level X.
				allPanelCards.show(firstPanel, "door");
			}
			else
			{
				// Second mistake: Game Over
				info.resetAttempts();
				info.setLevel(1); 
				allPanelCards.show(firstPanel, "start");
			}
		}
	}	
}

class InstructionsPanel extends JPanel implements ActionListener
{
	private SymptomSleuthCards firstPanel;
	private CardLayout allPanelCards;
	private GameData info;
	private Font myFont;
	private JButton home;// button to return to home screen
	
	public InstructionsPanel(SymptomSleuthCards scIn, CardLayout cardsIn, GameData infoIn, Font fontIn)
	{
		firstPanel = scIn;
		allPanelCards = cardsIn;
		info = infoIn;
		myFont = fontIn;
		
		JTextArea instructions = new JTextArea();// create text area to display instructions
		// set long multi-line instruction text for the game
		instructions.setText("SYMPTOM SLEUTH - PROTOCOL\n\n" +
			"the lights don't turn off here.\n" +
			"the monitors never stop.\n\n" +
			"you've just been assigned to the diagnostic unit.\n\n" +
			"before you begin:\n" +
			"enter your name. when you're done, press the RETURN/ENTER key to proceed. if you don't, the system will not move forward.\n\n" +
			"navigation:\n" +
			"- use the main screen to start cases or access settings\n" +
			"- open settings to adjust sound, visuals, or return to main menu\n" +
			"- buttons respond instantly. choose carefully.\n\n" +
			"hints system:\n" +
			"- you may request a hint during any case\n" +
			"- hints are not tied to any door or path\n" +
			"- hints may highlight missed details or narrow possibilities\n" +
			"- hints do not give the answer directly\n" +
			"- using hints has no impact on score or points\n\n" +
			"listen carefully.\n" +
			"every patient is a puzzle. fragments. contradictions. sometimes nothing that makes sense.\n\n" +
			"your role:\n" +
			"you are the final decision. no second opinions. no safety net.\n" +
			"read symptoms. interpret patterns. name the condition.\n\n" +
			"the process:\n" +
			"- you will encounter 3 doors each round\n" +
			"	- choose 1 of the 3 doors to begin your adventure\n" +
			"- a patient appears on screen\n" +
			"- a nurse stands beside them\n" +
			"- click the speech bubble to hear symptoms\n" +
			"- read everything carefully\n" +
			"- choose the diagnosis\n\n" +
			"limits\n" +
			"- 2 attempts per case\n" +
			"- fail twice and you are removed from duty\n\n" +
			"but understand this\n" +
			"- symptoms overlap\n" +
			"- some cases mislead you\n" +
			"- hesitation has consequences\n\n" +
			"progression\n" +
			"cases start easy, then become more complex and unpredictable\n\n" +
			"scoring\n" +
			"accuracy matters\n" +
			"speed matters\n" +
			"mistakes matter\n\n" +
			"field notes\n" +
			"- patterns save time\n" +
			"- details save lives\n" +
			"- ignore either and you fail both\n\n" +
			"end condition\n" +
			"solve every case or get overwhelmed trying\n\n" +
			"the next patient is already waiting.\n" +
			"don't keep them waiting too long.");
		// allow text to wrap instead of going off screen
		instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);// wrap at word boundaries for readability
        instructions.setEditable(false);// make text area uneditable so user cannot type
        instructions.setFont(myFont.deriveFont(50f));// apply custom font size
        
        JScrollPane scroll = new JScrollPane(instructions);// put text area inside scroll pane so user can scroll through long text
        scroll.setPreferredSize(new Dimension(960, 500));// set size of scrollable area
        add(scroll, BorderLayout.CENTER);// add scroll pane to center of screen
        
        
        JPanel homePanel = new JPanel();// create panel for home button
		home = new JButton("return back to home");// create button to return to start screen
		home.setFont(myFont.deriveFont(25f));// set font size
		home.addActionListener(this);// allow button clicks
		homePanel.add(home);// add button to panel
		
		add(homePanel, BorderLayout.SOUTH);// add button panel to bottom of screen
		
	}
	public void actionPerformed(ActionEvent evt) 
    {
		if(evt.getSource() == home)// if home button is clicked
		{
			allPanelCards.show(firstPanel, "start");// return to start screen
		}
	}
}

class CongratsPanel extends JPanel implements ActionListener
{
	private SymptomSleuthCards firstPanel;
	private CardLayout allPanelCards;
	private GameData info;
	private Font myFont;
	
	public CongratsPanel(SymptomSleuthCards scIn, CardLayout cardsIn, GameData infoIn, Font fontIn)
	{
		firstPanel = scIn;
		allPanelCards = cardsIn;
		info = infoIn;
		myFont = fontIn;
		
		setLayout(new BorderLayout());
		
		JPanel hello = new JPanel();
		JLabel label = new JLabel("CONGRATS! YOU WON THE GAME");
		hello.add(label);
		add(hello, BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent evt)
	{
	}
}

/* This class stores all the shared data for the game, including 
 * the patient files loaded from the text file.
 */
class GameData
{
	private String theName = ""; // stores player name
	private int currentLevel = 1; // tracks levels 1-10
	private int attempts = 0; // tracks the 2 attempts per case
	
	// 2D Array to store 200 cases, each with 11 pieces of info
	private String[][] allCases = new String[200][11]; 
	private int totalQuestionsLoaded = 0; // actual count from file
	private int activeCaseIndex = 0; // the row index of the current patient
    private int currentCaseRow;  // The row currently being played
    
	public GameData()
	{
		// Constructor is empty; call loadFiles() from SymptomSleuthCards
	}

	
	public void loadFiles()
	{
		try
		{
			Scanner infile = new Scanner(new File("questions.txt"));
			int row = 0;
			
			// Loop through the file until empty or we hit the 200 cap
			while(infile.hasNext() && row < 200)
			{
				String line = infile.nextLine();
				
				// Only process lines that aren't empty
				if(!line.trim().equals(""))
				{
					int col = 0;
					
					// Manually "splitting" the string by finding each '|'
					// While there is still a pipe in the string AND we haven't filled 10 columns
				int pipePos = line.indexOf("|"); 

				while(pipePos != -1 && col < 10)
				{
					allCases[row][col] = line.substring(0, pipePos); // Save data before pipe
                   line = line.substring(pipePos + 1);              // Remove saved part + the pipe
                   col++;
    
					// Update pipePos for the next check
					pipePos = line.indexOf("|"); 
				}

				// After the loop, save the very last piece of data (the answer index)
				// which doesn't have a pipe after it
				if (col < 11) 
				{
					allCases[row][col] = line.trim();
				}	
					
					row++;
				}
			}
			totalQuestionsLoaded = row; // store how many were actually loaded
		}
		catch(IOException e)
		{
			// If file is missing, print error to console
			System.err.println("Error: questions.txt not found!");
			e.printStackTrace();
		}
	}


	public void setActiveCase()
	{
		// temporary list to hold row numbers for this level
		int[] matchingRows = new int[totalQuestionsLoaded];
		int matchCount = 0;

		// find every row that matches the player's level
		for (int i = 0; i < totalQuestionsLoaded; i++)
		{
			// check if column 0 (the level) matches
			if (allCases[i][0] != null && Integer.parseInt(allCases[i][0].trim()) == currentLevel)
			{
				matchingRows[matchCount] = i; // save the row index
				matchCount++;
			}
		}

		// if found matches, pick one randomly
		if (matchCount > 0)
		{
			int randomChoice = (int)(Math.random() * matchCount);
			activeCaseIndex = matchingRows[randomChoice]; 
		}
	}

		

	public String getCaseData(int column)
	{
		return allCases[activeCaseIndex][column];
	}

	// GETTERS AND SETTERS
	public String getName() 
	{ 
		return theName;
	}
	public void setName(String n) 
	{ 
		theName = n; 
	}

	public int getLevel() 
	{ 
		return currentLevel; 
	}
	public void setLevel(int l) 
	{ 
		currentLevel = l; 
	}

	public int getAttempts() 
	{ 
		return attempts; 
	}
	public void useAttempt() 
	{ 
		attempts++; 
	}
	public void resetAttempts() 
	{ 
		attempts = 0; 
	}		
	public int getRandomCaseIndex(int level) {
        int[] matchingIndices = new int[allCases.length];
        int count = 0;

        for (int i = 0; i < allCases.length; i++) {
            // Check column 0 for the level match
            if (allCases[i][0] != null && Integer.parseInt(allCases[i][0].trim()) == level) {
                matchingIndices[count] = i;
                count++;
            }
        }

        if (count > 0) {
            int randomChoice = (int)(Math.random() * count);
            return matchingIndices[randomChoice];
        }
        return 0; 
    }

    //  "setter" to update the row
    public void setCurrentCaseRow(int newRow) 
    {
		currentCaseRow = newRow;
	}

}

		
