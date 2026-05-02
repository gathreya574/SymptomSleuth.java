// Gowri Athreya
// 04/21/2026
// SymptomSleuth.java
// Period 2


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

public class SymptomSleuth
{
	public static void main(String[] args)
	{
		SymptomSleuth ss = new SymptomSleuth();
		ss.runIt();
	}
	public void runIt()
	{
		JFrame frame = new JFrame("Symptom Sleuth");
		frame.setSize(960, 600);
		frame.setLocation(200, 140);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SymptomSleuthCards ssc = new SymptomSleuthCards();
        ssc.startMusic();
        frame.getContentPane().add(ssc);
        frame.setVisible(true);
	}
}

class SymptomSleuthCards extends JPanel
{
	private CardLayout allPanelCards;
	private Clip clip;
	private long clipTimePos;
	private Font myFont;
	
	public SymptomSleuthCards()
	{
		allPanelCards = new CardLayout();
		setLayout(allPanelCards);
		try
		{
			myFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Borg.ttf"));
		}
		catch(FontFormatException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		System.out.print(myFont);
			
		PlayerInfo info = new PlayerInfo();
		StartPanel sp = new StartPanel(this, allPanelCards, info, myFont);
		SettingsPanel sgp = new SettingsPanel(this, allPanelCards, info, myFont);
		DoorsPanel dp = new DoorsPanel(this, allPanelCards, info, myFont);
		PeoplePanel pp = new PeoplePanel(this, allPanelCards, info, myFont);
		LeaderBoardPanel lbp = new LeaderBoardPanel(this, allPanelCards, info, myFont);
		//InstructionsPanel ip = new Instructions(this, allPanelCards, info, myFont);
		QuestionsPanel qp = new QuestionsPanel(this, allPanelCards, info, myFont);
		
		add(sp, "start");
		add(sgp, "settings");
		add(dp, "door");
		add(pp,"people");
		add(lbp, "leader");
		//add(ip, "instructions");
		add(qp,"questions");
	}
	public void startMusic()
		{
			try
			{
				File file = new File("sounds/song1.wav");
				AudioInputStream audio = AudioSystem.getAudioInputStream(file);
			
				clip = AudioSystem.getClip();
				clip.open(audio);
			
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
		
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		public void pauseMusic()
		{
			if(clip.isRunning()&&clip!=null)
			{
				
				clipTimePos = clip.getMicrosecondPosition();
				clip.stop();
			}
		}
		
		public void resumeMusic()
		{
			if(clip!=null)
			{
				clip.setMicrosecondPosition(clipTimePos);
				clip.start();
			}
		}
}

class StartPanel extends JPanel implements ActionListener, MouseListener
{
	private SymptomSleuthCards firstPanel;
	private CardLayout allPanelCards;
	private JTextField name;
	private JButton startGame, settingsB;
	private Color myColor;
	private JMenuItem leader, instruction, exit;
	private boolean pressed;
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
		myColor = new Color(76,92,127);
		
		
		
		JLabel title1 = new JLabel("Symptom");
		JLabel title2 = new JLabel("Sleuth");
		
		title1.setFont(myFont.deriveFont(200f));
		title1.setBounds(250,80,700,200);
		title1.setForeground(Color.WHITE);
		
		
		title2.setFont(myFont.deriveFont(200f));
		title2.setBounds(350,250,700,200);
		title2.setForeground(Color.WHITE);
		
		ImageIcon icon = new ImageIcon("images/hospitalBackground.png");
		JLabel background = new JLabel(icon);
		background.setBounds(0,0,960,600);
		background.setLayout(null);
		
		startGame = new JButton("Start Game");
		startGame.setFont(myFont.deriveFont(40f));
		startGame.setForeground(Color.BLACK);
		startGame.setBackground(myColor);
		startGame.setBounds(380, 500, 200, 50);
		startGame.addActionListener(this);
		
		name = new JTextField("Enter your name here");
		name.setFont(myFont.deriveFont(25f));
		name.setBounds(380, 450, 200, 25);
		name.setBackground(myColor);
		name.setForeground(Color.WHITE);
		name.addActionListener(this);
		name.addMouseListener(this);
		
		settingsB = new JButton("Settings");
		settingsB.setFont(myFont.deriveFont(30f));
		settingsB.setBounds(0, 0, 110, 30);
		settingsB.setForeground(Color.BLACK);
		settingsB.setBackground(myColor);
		settingsB.addActionListener(this);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("options");
		leader = new JMenuItem("Leaderboard");
		instruction = new JMenuItem("Instructions");
		exit = new JMenuItem("Exit");
		
		leader.addActionListener(this);
		instruction.addActionListener(this);
		exit.addActionListener(this);
		leader.setBackground(myColor);
		leader.setFont(myFont.deriveFont(40f));
		leader.setForeground(Color.WHITE);
		instruction.setBackground(myColor);
		instruction.setForeground(Color.WHITE);
		instruction.setFont(myFont.deriveFont(40f));
		exit.setBackground(myColor);
		exit.setForeground(Color.WHITE);
		exit.setFont(myFont.deriveFont(40f));
		menu.setBackground(myColor);
		menu.setForeground(Color.WHITE);
		menu.setFont(myFont.deriveFont(35f));
		
		menu.add(leader);
		menu.add(instruction);
		menu.add(exit);
		menuBar.add(menu);
		menuBar.setBounds(880,0,110,30);
		menuBar.setBackground(myColor);
		
		
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
		if(evt.getSource() == name)
		{
			pressed = true;
		}
		
		if(evt.getSource() == settingsB)
		{
			allPanelCards.show(firstPanel, "settings");
		}
		
		if(evt.getSource() == startGame)
		{
			if(pressed && !name.getText().equals(""))
			{
				info.setName(name.getText());
				allPanelCards.show(firstPanel, "door");
			}
		}
		
		if(evt.getSource() == leader)
		{
			allPanelCards.show(firstPanel, "leader");
		}
		
		if(evt.getSource() == exit)
		{
			System.exit(1);
		}
		
	}
    public void mouseClicked(MouseEvent evt) 
	{
		
		if(name.getText().equals("Enter your name here"))
		{
			name.setText("");
		}
		
	}

	public void mouseReleased(MouseEvent evt){}
	public void mousePressed(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}
}

class SettingsPanel extends JPanel implements ActionListener
{
	
	private SymptomSleuthCards firstPanel;
	private CardLayout allPanelCards;
	private JButton on, off, font1, font2, home;
	private PlayerInfo info;
	private Font myFont;
	
	public SettingsPanel(SymptomSleuthCards scIn, CardLayout cardsIn, PlayerInfo infoIn, Font fontIn)
	{
		firstPanel = scIn;
		allPanelCards = cardsIn;
		setLayout(new BorderLayout());
		info = infoIn;
		myFont = fontIn;
		
		
		ImageIcon back = new ImageIcon("images/settings.png");
		JLabel set = new JLabel(back); 
		
		JPanel titles = new JPanel(new GridLayout(2,1));
		
		
		JLabel theSound = new JLabel("Sound");
		theSound.setFont(myFont.deriveFont(70f));
		theSound.setForeground(Color.WHITE);
		JLabel theFont = new JLabel("Font");
		theFont.setFont(myFont.deriveFont(70f));
		theFont.setForeground(Color.WHITE);
		
		titles.add(theSound);
		titles.add(theFont);
		
		JPanel buttons = new JPanel(new GridLayout(2,2));
		
		//JPanel on = new JPanel(new 
		on = new JButton("On");
		
		off = new JButton("Off");
		font1 = new JButton("Times New Roman");
		font2 = new JButton("Sans Serif");
		on.addActionListener(this);
		off.addActionListener(this);
		font1.addActionListener(this);
		font2.addActionListener(this);
		
		buttons.add(on);
		buttons.add(off);
		buttons.add(font1);
		buttons.add(font2);
		
		JPanel homePanel = new JPanel();
		home = new JButton("return back to home");
		home.addActionListener(this);
		homePanel.add(home);
		
		add(set, BorderLayout.CENTER);
		add(homePanel, BorderLayout.SOUTH);
		add(buttons, BorderLayout.EAST);
		add(titles, BorderLayout.WEST);
		
	
		
	}
	public void actionPerformed(ActionEvent evt) 
    {
		if(evt.getSource() == home)
		{
			allPanelCards.show(firstPanel, "start");
		}	
		if(evt.getSource() == on)
		{
			firstPanel.resumeMusic();
		}
		else if(evt.getSource() == off)
		{
			firstPanel.pauseMusic();
		}
			
	}
}

class DoorsPanel extends JPanel implements ActionListener
{
	private SymptomSleuthCards firstPanel;
	private CardLayout allPanelCards;
	private JButton door1, door2, door3;
	private PlayerInfo info;
	private Font myFont;
		
	public DoorsPanel(SymptomSleuthCards scIn, CardLayout cardsIn, PlayerInfo infoIn, Font fontIn)
	{
		firstPanel = scIn;
		allPanelCards = cardsIn;
		info = infoIn;
		myFont = fontIn;
		
		setLayout(new BorderLayout());
		
		JPanel titlePanel = new JPanel();
		JLabel label = new JLabel("Pick one of the doors!");
		label.setFont(myFont.deriveFont(40f));
		titlePanel.add(label);
		
		JPanel doorPanel = new JPanel(new GridLayout(1,3));
		String[] names = {"door1","door2","door3","door4","door5"};
		
		
		
		int level = 1;
		
		if(level<=5)
		{
			ImageIcon doorPic = new ImageIcon("images/" + names[level-1]+".png");
			door1 = new JButton(doorPic);
			door2 = new JButton(doorPic);
			door3 = new JButton(doorPic);
		}
		level++;
		
		door1.addActionListener(this);
		door2.addActionListener(this);
		door3.addActionListener(this);
		doorPanel.add(door1);		
		doorPanel.add(door2);
		doorPanel.add(door3);
		
		add(titlePanel, BorderLayout.NORTH);
		add(doorPanel, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent evt) 
    {
		if(evt.getSource() == door1|| evt.getSource() == door2|| evt.getSource() == door3)
		{
			allPanelCards.show(firstPanel, "people");
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

		setLayout(new BorderLayout());
		
		ImageIcon icon2 = new ImageIcon("images/patient.png");
		JLabel peopleImage = new JLabel(icon2);
		peopleImage.addMouseListener(this);
		
		add(peopleImage, BorderLayout.CENTER);
		
	}
	public void mouseClicked(MouseEvent evt) 
	{
		int x = evt.getX();
		int y = evt.getY();
		
		
		
		if(x>=270 && x<=515&&y>=72&&y<=181)
		{
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
	private JButton home;
	
	public LeaderBoardPanel(SymptomSleuthCards scIn, CardLayout cardsIn, PlayerInfo infoIn, Font fontIn)
	{
		firstPanel = scIn;
		allPanelCards = cardsIn;
		info = infoIn;
		myFont = fontIn;
		
		setLayout(new BorderLayout());
		
		JPanel namesAndScores = new JPanel(new GridLayout(1,2));
		//names.add(info.getName());
		
		JPanel names = new JPanel();
		add(names);
		
		JPanel scores = new JPanel();
		add(scores);
		
		JPanel button = new JPanel();
		home = new JButton("return back to home");
		button.add(home);
		home.addActionListener(this);
		
		add(namesAndScores, BorderLayout.CENTER);
		add(button, BorderLayout.SOUTH);
		
		
	}
	public void actionPerformed(ActionEvent evt) 
    {
		if(evt.getSource() == home)
		{
			allPanelCards.show(firstPanel, "start");
		}
	}
	
}

class QuestionsPanel extends JPanel implements ActionListener
{
	private SymptomSleuthCards firstPanel;
	private CardLayout allPanelCards;
	private PlayerInfo info;
	private Font myFont;
	private JLabel text;
	
	public QuestionsPanel(SymptomSleuthCards scIn, CardLayout cardsIn, PlayerInfo infoIn, Font fontIn)
	{
		firstPanel = scIn;
		allPanelCards = cardsIn;
		info = infoIn;
		myFont = fontIn;
		
		setLayout(new BorderLayout());
		
		JPanel patientText = new JPanel();
		text = new JLabel();
		text.setFont(myFont.deriveFont(50f));
		patientText.add(text);
		add(patientText, BorderLayout.NORTH);
		System.out.println(info.getName());
	}
	public void paintComponent(Graphics g) 
	{
		text.setText("Hello Dr. " + info.getName() + "!!");
		
	}
	
	public void actionPerformed(ActionEvent evt) 
    {}
}
	

class PlayerInfo
{
	private String theName = "";
	
	public String getName()
	{
		return theName;
	}
	public void setName(String n)
	{
		theName = n;
	}
}


		

		
	
		
		
		
