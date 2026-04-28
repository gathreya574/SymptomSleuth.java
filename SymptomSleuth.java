// Gowri Athreya
// 04/21/2026
// SymptomSleuth.java
// Period 2


import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;


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
        frame.getContentPane().add(ssc);
        frame.setVisible(true);
	}
}

class SymptomSleuthCards extends JPanel
{
	private CardLayout allPanelCards;
	
	public SymptomSleuthCards()
	{
		allPanelCards = new CardLayout();
		setLayout(allPanelCards);
		
		StartPanel sp = new StartPanel(this, allPanelCards);
		SettingsPanel sgp = new SettingsPanel(this, allPanelCards);
		DoorsPanel dp = new DoorsPanel(this, allPanelCards);
		PeoplePanel pp = new PeoplePanel(this, allPanelCards);
		
		add(sp, "start");
		add(sgp, "settings");
		add(dp, "door");
		add(pp,"people");
	}
}

class StartPanel extends JPanel implements ActionListener, MouseListener
{
	private SymptomSleuthCards firstPanel;
	private CardLayout allPanelCards;
	private JTextField name;
	private JButton startGame, settingsB;
	private Font myFont;
	private Color myColor;
	
	public StartPanel(SymptomSleuthCards sc, CardLayout c)
	{
		allPanelCards = c;
		firstPanel = sc;
		//setBackground(Color.BLUE);
		
		setLayout(null);
		
		myFont = new Font("SansSerif", Font.BOLD, 15);
		myColor = new Color(76,92,127);
		
		
		
		JLabel title1 = new JLabel("Symptom");
		JLabel title2 = new JLabel("Sleuth");
		
		title1.setFont(new Font("SansSerif", Font.BOLD, 100));
		title1.setBounds(250,100,600,120);
		title1.setForeground(Color.WHITE);
		
		
		title2.setFont(new Font("SansSerif", Font.BOLD, 100));
		title2.setBounds(300,200,600,120);
		title2.setForeground(Color.WHITE);
		
		ImageIcon icon = new ImageIcon("images/hospitalBackground.png");
		JLabel background = new JLabel(icon);
		background.setBounds(0,0,960,600);
		background.setLayout(null);
		
		startGame = new JButton("Start Game");
		startGame.setFont(new Font("SansSerif", Font.BOLD, 30));
		startGame.setForeground(Color.BLACK);
		startGame.setBackground(myColor);
		startGame.setBounds(380, 500, 200, 50);
		startGame.addActionListener(this);
		
		name = new JTextField("Enter your name here");
		name.setFont(myFont);
		name.setBounds(380, 450, 200, 25);
		name.setBackground(myColor);
		name.setForeground(Color.WHITE);
		name.addActionListener(this);
		name.addMouseListener(this);
		
		settingsB = new JButton("Settings");
		settingsB.setFont(myFont);
		settingsB.setBounds(0, 0, 110, 30);
		settingsB.setForeground(Color.BLACK);
		settingsB.setBackground(myColor);
		settingsB.addActionListener(this);

		
		background.add(name);
		background.add(settingsB);
		background.add(startGame);
		background.add(title1);
		background.add(title2);
		add(background);
		
		
	}
	public void actionPerformed(ActionEvent evt) 
    {
		if(evt.getSource() == settingsB)
		{
			allPanelCards.show(firstPanel, "settings");
		}
		else if(evt.getSource() == startGame)
		{
			allPanelCards.show(firstPanel, "door");
		}
	}
    public void mouseClicked(MouseEvent evt) 
	{

		if(evt.getSource() == name)
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
	private JButton on, off, roman, sansSerif, home;
	
	public SettingsPanel(SymptomSleuthCards sc, CardLayout c)
	{
		firstPanel = sc;
		allPanelCards = c;
		setLayout(new BorderLayout());
		
	/*	ImageIcon back = new ImageIcon("images/hospitalBackground.png");
		JLabel set = new JLabel(back); */
		
		JPanel titles = new JPanel(new GridLayout(2,1));
		JLabel sound = new JLabel("Sound");
		JLabel textFont = new JLabel("Font");
		
		titles.add(sound);
		titles.add(textFont);
		
		JPanel buttons = new JPanel(new GridLayout(2,2));
		
		on = new JButton("On");
		off = new JButton("Off");
		roman = new JButton("Times New Roman");
		sansSerif = new JButton("Sans Serif");
		on.addActionListener(this);
		off.addActionListener(this);
		roman.addActionListener(this);
		sansSerif.addActionListener(this);
		
		buttons.add(on);
		buttons.add(off);
		buttons.add(roman);
		buttons.add(sansSerif);
		
		JPanel homePanel = new JPanel();
		home = new JButton("return back to home");
		home.addActionListener(this);
		homePanel.add(home);
		
		
		add(homePanel, BorderLayout.SOUTH);
		add(buttons, BorderLayout.EAST);
		add(titles, BorderLayout.WEST);
		//add(set, BorderLayout.CENTER);
	
		
	}
	public void actionPerformed(ActionEvent evt) 
    {
		if(evt.getSource() == home)
		{
			allPanelCards.show(firstPanel, "start");
		}	
	}
}

class DoorsPanel extends JPanel implements ActionListener
{
	private SymptomSleuthCards firstPanel;
	private CardLayout allPanelCards;
	private JButton door1, door2, door3;
	
	public DoorsPanel(SymptomSleuthCards sc, CardLayout c)
	{
		firstPanel = sc;
		allPanelCards = c;
		
		setLayout(new BorderLayout());
		
		JPanel titlePanel = new JPanel();
		JLabel label = new JLabel("Pick one of the doors!");
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
	
	public PeoplePanel(SymptomSleuthCards sc, CardLayout c)
	{
		firstPanel = sc;
		allPanelCards = c;
		
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
			System.out.println("Bubble is clicked!");
		}

		
		
	}

	public void mouseReleased(MouseEvent evt){}
	public void mousePressed(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}
}


		
		
		
		
