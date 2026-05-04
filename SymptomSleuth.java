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
		PlayerInfo info = new PlayerInfo();
		// create all game screes ad pass all data(variables)
		StartPanel sp = new StartPanel(this, allPanelCards, info, myFont);
		SettingsPanel sgp = new SettingsPanel(this, allPanelCards, info, myFont);
		DoorsPanel dp = new DoorsPanel(this, allPanelCards, info, myFont);
		PeoplePanel pp = new PeoplePanel(this, allPanelCards, info, myFont);
		LeaderBoardPanel lbp = new LeaderBoardPanel(this, allPanelCards, info, myFont);
		InstructionsPanel ip = new InstructionsPanel(this, allPanelCards, info, myFont);
		QuestionsPanel qp = new QuestionsPanel(this, allPanelCards, info, myFont);
		
		// add each panel to CardLayout with a name
		add(sp, "start");
		add(sgp, "settings");
		add(dp, "door");
		add(pp,"people");
		add(lbp, "leader");
		add(ip, "instructions");
		add(qp,"questions");
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
	private PlayerInfo info;
	private Font myFont;
		
	public StartPanel(SymptomSleuthCards scIn, CardLayout cardsIn, PlayerInfo infoIn, Font fontIn)
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
		JLabel title2 = new JLabel("Sleuth");
		// set font, size, position, color
		title1.setFont(myFont.deriveFont(200f));
		title1.setBounds(250,80,700,200);
		title1.setForeground(new Color (15, 25, 45));
		title2.setFont(myFont.deriveFont(200f));
		title2.setBounds(350,250,700,200);
		title2.setForeground(new Color (15, 25, 45));
		
		// background image usiing image icon and JLabel
		ImageIcon icon = new ImageIcon("images/hospitalBackground.png");
		JLabel background = new JLabel(icon);
		background.setBounds(0,0,960,600);
		background.setLayout(null);
		// start button	
		startGame = new JButton("Start Game");
		startGame.setFont(myFont.deriveFont(40f));
		startGame.setForeground(Color.BLACK);
		startGame.setBackground(myColor);
		startGame.setBounds(380, 500, 200, 50);
		startGame.addActionListener(this);
		// text field for name
		name = new JTextField("Enter your name here");
		name.setFont(myFont.deriveFont(25f));
		name.setBounds(380, 450, 200, 25);
		name.setBackground(Color.WHITE);
		name.setForeground(Color.BLACK);
		name.addActionListener(this);
		// detect mouse click to clear text
		name.addMouseListener(this);
		// settings button
		settingsB = new JButton("Settings");
		settingsB.setFont(myFont.deriveFont(30f));
		settingsB.setBounds(0, 0, 110, 30);
		settingsB.setForeground(Color.BLACK);
		settingsB.setBackground(Color.WHITE);
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
		leader.setBackground(Color.WHITE);
		leader.setFont(myFont.deriveFont(40f));
		leader.setForeground(Color.BLACK);
		instruction.setBackground(Color.WHITE);
		instruction.setForeground(Color.BLACK);
		instruction.setFont(myFont.deriveFont(40f));
		exit.setBackground(Color.WHITE);
		exit.setForeground(Color.BLACK);
		exit.setFont(myFont.deriveFont(40f));
		menu.setBackground(Color.WHITE);
		menu.setForeground(Color.BLACK);
		menu.setFont(myFont.deriveFont(35f));
		
		menu.add(leader);
		menu.add(instruction);
		menu.add(exit);
		menuBar.add(menu);
		menuBar.setBounds(880,0,110,30);
		menuBar.setBackground(myColor);
		
		// add everything to background
		background.add(name);
		background.add(settingsB);
		background.add(startGame);
		background.add(title1);
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
	private PlayerInfo info;// stores player data
	private Font myFont;// custom font used for styling

	
	public SettingsPanel(SymptomSleuthCards scIn, CardLayout cardsIn, PlayerInfo infoIn, Font fontIn)
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
		off = new JButton("Off");// turn music off
		off.setFont(myFont.deriveFont(50f));
		font1 = new JButton("Times New Roman");// place holder for font1 ("Times New Roman" != font1)
		font1.setFont(myFont.deriveFont(50f));
		font2 = new JButton("Sans Serif");// place holder for font2 ("SansSerif" != font2)
		font2.setFont(myFont.deriveFont(50f));
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
	private JButton door1, door2, door3;// buttons representing the 3 doors
	private PlayerInfo info;
	private Font myFont;
		
	public DoorsPanel(SymptomSleuthCards scIn, CardLayout cardsIn, PlayerInfo infoIn, Font fontIn)
	{
		firstPanel = scIn;
		allPanelCards = cardsIn;
		info = infoIn;
		myFont = fontIn;
		
		setLayout(new BorderLayout());	// set layout of this panel to BorderLayout
		
		JPanel titlePanel = new JPanel();// create a panel to hold the title
		JLabel label = new JLabel("Pick one of the doors!");// create label with instructions
		label.setFont(myFont.deriveFont(40f));// apply custom font and size
		titlePanel.add(label);// add label to title panel
		
		JPanel doorPanel = new JPanel(new GridLayout(1,3));// create panel with 1 row and 3 columns (for 3 doors)
		String[] names = {"door1","door2","door3","door4","door5"};// array storing names of door image files
		
		
		
		int level = 1;// current level of the game (used to pick which door image to use)
		
		if(level<=5)// check if level is within range of available images
		{
			ImageIcon doorPic = new ImageIcon("images/" + names[level-1]+".png");	// create image path using level 
			
			// create 3 buttons using the same door image
			door1 = new JButton(doorPic);
			door2 = new JButton(doorPic);
			door3 = new JButton(doorPic);
		}
		level++;// increase level (this doesn’t really affect anything yet)
		// add action listeners so buttons respond when clicked
		door1.addActionListener(this);
		door2.addActionListener(this);
		door3.addActionListener(this);
		
		// add doors to panel
		doorPanel.add(door1);		
		doorPanel.add(door2);
		doorPanel.add(door3);
		
		add(titlePanel, BorderLayout.NORTH);//. add tite to the top
		add(doorPanel, BorderLayout.CENTER);// add dorrs in the center
	}

	public void actionPerformed(ActionEvent evt) 
    {
		// check if ANY of the 3 doors was clicked
		if(evt.getSource() == door1|| evt.getSource() == door2|| evt.getSource() == door3)
		{
			allPanelCards.show(firstPanel, "people");// switch to the "people" screen
		}	
	}
			
			
			
	
}
class PeoplePanel extends JPanel implements MouseListener
{
	private SymptomSleuthCards firstPanel;
	private CardLayout allPanelCards;
	private PlayerInfo info;
	private Font myFont;
	
	public PeoplePanel(SymptomSleuthCards scIn, CardLayout cardsIn, PlayerInfo infoIn, Font fontIn)
	{
		firstPanel = scIn;
		allPanelCards = cardsIn;
		info = infoIn;
		myFont = fontIn;

		// set layout to BorderLayout
		setLayout(new BorderLayout());
		// load patient image from file
		ImageIcon icon2 = new ImageIcon("images/patient.png");
		JLabel peopleImage = new JLabel(icon2);// place image inside a JLabel so it can be displayed
		peopleImage.addMouseListener(this);// add mouse listener to detect clicks on the image
		
		add(peopleImage, BorderLayout.CENTER);	// add image to center of screen
		
	}
	public void mouseClicked(MouseEvent evt) 
	{
		// get x and y coordinates of where the user clicked
		int x = evt.getX();
		int y = evt.getY();
		
		
		// check if click is inside the speech bubble area
		// (these numbers define a rectangular region on the image)
		if(x>=270 && x<=515&&y>=72&&y<=181)
		{
			// if clicked inside the correct area:
			
			// switch to the "questions" screen
			
			allPanelCards.show(firstPanel, "questions");
		}

		
		
	}

	public void mouseReleased(MouseEvent evt){}
	public void mousePressed(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}
}

class LeaderBoardPanel extends JPanel implements ActionListener
{
	private SymptomSleuthCards firstPanel;
	private CardLayout allPanelCards;
	private PlayerInfo info;
	private Font myFont;
	private JButton home;// button to return to start screen
	
	public LeaderBoardPanel(SymptomSleuthCards scIn, CardLayout cardsIn, PlayerInfo infoIn, Font fontIn)
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
	private PlayerInfo info;
	private Font myFont;
	private JLabel text;// label used to display greeting message
	
	public QuestionsPanel(SymptomSleuthCards scIn, CardLayout cardsIn, PlayerInfo infoIn, Font fontIn)
	{
		firstPanel = scIn;
		allPanelCards = cardsIn;
		info = infoIn;
		myFont = fontIn;
		
		setLayout(new BorderLayout());
		
		JPanel patientText = new JPanel();// create panel to hold text label
		text = new JLabel();// create label (initially empty)
		text.setFont(myFont.deriveFont(50f));// set font size for label
		patientText.add(text);// add label to panel
		add(patientText, BorderLayout.NORTH);	// add panel to top of screen
		
	}
	public void paintComponent(Graphics g) 
	{
		text.setText("Hello Dr. " + info.getName() + "!!");// update label text every time panel is drawn
		
	}
	
	public void actionPerformed(ActionEvent evt) 
    {}
}

class InstructionsPanel extends JPanel implements ActionListener
{
	private SymptomSleuthCards firstPanel;
	private CardLayout allPanelCards;
	private PlayerInfo info;
	private Font myFont;
	private JButton home;// button to return to home screen
	
	public InstructionsPanel(SymptomSleuthCards scIn, CardLayout cardsIn, PlayerInfo infoIn, Font fontIn)
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
	

class PlayerInfo
{
	private String theName = ""; // stores player name
	
	public String getName()
	{
		return theName;// return stored name
	}
	public void setName(String n)
	{
		theName = n; // save new name
	}
}

	
		
		
