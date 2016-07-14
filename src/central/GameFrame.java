package central;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Stack;
import java.awt.Toolkit;
import library.GameSystem;
import javax.swing.JToggleButton;
import javax.swing.ImageIcon;

public class GameFrame extends JFrame {
	
	//include the internal classes in this class
	Drawing draw = new Drawing();
	MaterialColors mc = new MaterialColors();
	GameSystem gs = new GameSystem();
	Settings setting = new Settings();
	
	/**
	 * This variable determines how many words will there be for every
	 * session. It keeps all the methods in check so they won't run out
	 * of bound.
	 */
	public int numOfWords = 30;
	/**
	 * This constant variable helps define the width of the button array.
	 * It allows easy looping in 2D arrays.
	 */
	private final int BTN_ARRAY_WIDTH = 7;
	/**
	 * This constant variable helps define the height of the button array.
	 * It allows easy looping in 2D arrays.
	 */
	private final int BTN_ARRAY_HEIGHT = 4;
	/**
	 * This ArrayList stores all the word strings retrieved from the dictionaries.
	 * An ArrayList is applied in this case because 
	 */
	private ArrayList<String> listOfWords = new ArrayList<String>();
	private Stack<Integer> posOfChar = new Stack<Integer>();
	private int wordCount = 0;
	private int correctAnswerNum = 0;
	private int errorAnswerNum = 0;
	private int errorCharNum = 0;
	private int[] questionPosition;
	private String playWord;

	private Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.white);
	private JPanel contentPane;
	private JPanel textPanel;
	private JTextField[] displays = new JTextField[15];
	private JButton[][] alphaBtns = new JButton[BTN_ARRAY_HEIGHT][BTN_ARRAY_WIDTH];
	private JTextPane promptWindow;
	private JButton btnSkip;
	
	private Font btnFont = new Font("Calibri", Font.PLAIN, 15);
	private Color txtColor = Color.darkGray;
	private Color interactColor = mc.AMBER;
	private Color interactHoverColor = mc.SEC_AMBER;
	private Color primaryColor = mc.TEAL;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameFrame frame = new GameFrame();
					frame.refreshPlayWord(frame.displays);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public GameFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getLookAndFeel());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		refreshWordList();
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameFrame.class.getResource("/icons/hangman-logo.png")));
		setTitle("Just Another Hangman Game");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 747, 546);
		contentPane = new JPanel();
		contentPane.setBackground(mc.SEC_TEAL);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel paintPanel = new JPanel();
		paintPanel.setBackground(primaryColor);
		paintPanel.setBorder(new EmptyBorder(0, 0, 10, 10));
		contentPane.add(paintPanel, BorderLayout.CENTER);
		paintPanel.setLayout(null);
		draw.setBackground(primaryColor);
		draw.setLocation(0, 12);
		draw.setSize(369, 473);
		paintPanel.add(draw);
		draw.setLayout(null);
		
		JToggleButton expandMenu = new JToggleButton("");
		
		expandMenu.setSelectedIcon(new ImageIcon(GameFrame.class.getResource("/icons/remove.png")));
		expandMenu.setIcon(new ImageIcon(GameFrame.class.getResource("/icons/add.png")));
		expandMenu.setBorder(null);
		expandMenu.setBackground(interactColor);
		expandMenu.setBounds(302, 407, 55, 36);
		draw.add(expandMenu);
		JButton settingBtn = new JButton();
		settingBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				settingBtn.setBackground(interactHoverColor);
				playSound("/sound/click.wav");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				settingBtn.setBackground(interactColor);
			}
		});
		settingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setting.setVisible(true);
			}
		});
		settingBtn.setBackground(interactColor);
		settingBtn.setBorder(null);
		settingBtn.setVisible(false);
		settingBtn.setIcon(new ImageIcon(GameFrame.class.getResource("/icons/setting.png")));
		settingBtn.setBounds(302, 359, 55, 36);
		draw.add(settingBtn);
		
		JButton infoBtn = new JButton();
		infoBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				infoBtn.setBackground(interactHoverColor);
				playSound("/sound/click.wav");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				infoBtn.setBackground(interactColor);
			}
		});
		infoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		infoBtn.setIcon(new ImageIcon(GameFrame.class.getResource("/icons/info.png")));
		infoBtn.setBorder(null);
		infoBtn.setVisible(false);
		infoBtn.setBackground(new Color(255, 193, 7));
		infoBtn.setBounds(302, 311, 55, 36);
		draw.add(infoBtn);

		textPanel = new JPanel();
		textPanel.setBackground(mc.SEC_TEAL);
		textPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
		contentPane.add(textPanel, BorderLayout.NORTH);

		for (int i = 0; i < displays.length; i++) {
			displays[i] = new JTextField();
			displays[i].setEditable(false);
			displays[i].setColumns(2);
			displays[i].setFont(new Font("Calibri", Font.PLAIN, 20));
			displays[i].setHorizontalAlignment(SwingConstants.CENTER);
			displays[i].setBorder(bottomBorder);
			displays[i].setBackground(mc.SEC_TEAL);
			displays[i].setForeground(Color.white);
		}

		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(primaryColor);
		btnPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.add(btnPanel, BorderLayout.WEST);
		btnPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("44px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("46px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("44px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("44px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("44px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("44px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("44px"),},
				new RowSpec[] {
						FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("242px:grow"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("37px"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(37px;default)"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("37px"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("37px"),}));

		promptWindow = new JTextPane();
		promptWindow.setForeground(txtColor);
		promptWindow.setBackground(interactColor);
		promptWindow.setFont(new Font("Calibri", Font.PLAIN, 15));
		promptWindow.setEditable(false);
		promptWindow.setCaretPosition(0);
		promptWindow.setText(">Welcome to Just Another Hangman Game.\r\n>Select a letter from the choices bellow.\r\n>If the keyword contains this letter, it will pop up on \r\n>top, the letter will turn green and your character\r\n>will live happily ever after.\r\n>You have 6 chances before your character dies.\r\n>If you give up, feel free to hit \"SKIP\".\r\n>After u complete 30 words, the game will prompt\r\n>you with a summary.\r\n>Have Fun.");
		JScrollPane scrollPane = new JScrollPane(promptWindow);
		scrollPane.setBorder(null);
		btnPanel.add(scrollPane, "1, 2, 13, 1, fill, fill");
		btnSkip = new JButton("SKIP");
		btnSkip.setForeground(txtColor);
		btnSkip.setBackground(interactColor);
		btnSkip.setFont(btnFont);
		btnSkip.setBorder(null);
		btnSkip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enableButtons(alphaBtns,4,7,interactColor);
				refreshPlayWord(displays);
				errorCharNum = 0;
				draw.key = errorCharNum;
				draw.resetCharacter();
				btnSkip.setText("SKIP");
				addPromptTxt(promptWindow,"You are entering question " + wordCount);
			}
		});
		btnSkip.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnSkip.setBackground(interactHoverColor);
				playSound("/sound/click.wav");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnSkip.setBackground(interactColor);
			}
		});
		btnPanel.add(btnSkip, "11, 10, 3, 1, fill, fill");

		int rowPosition = 4;
		int asciiPos = 65;
		for (int i = 0; i < BTN_ARRAY_HEIGHT; i++) {
			int colPosition = 1;
			for (int j = 0; j < BTN_ARRAY_WIDTH; j++) {
				alphaBtns[i][j] = new JButton();
				alphaBtns[i][j].setText(Character.toString((char)(asciiPos)));
				alphaBtns[i][j].setForeground(txtColor);
				alphaBtns[i][j].setBackground(interactColor);
				alphaBtns[i][j].setFont(btnFont);
				alphaBtns[i][j].setBorder(null);
				JButton button = alphaBtns[i][j];
				alphaBtns[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						validChoice(button);
					}
				});
				alphaBtns[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent arg0) {
						if(button.isEnabled()){
							button.setBackground(interactHoverColor);
							playSound("/sound/click.wav");
						}
					}
					@Override
					public void mouseExited(MouseEvent e) {
						if(button.isEnabled()){
							button.setBackground(interactColor);
						}
					}
				});
				Object btnPosition = colPosition + ", " + rowPosition + ", fill, fill";
				if(!(i == 3 && j >= 5))
					btnPanel.add(alphaBtns[i][j], btnPosition);
				colPosition += 2;
				asciiPos++;
			}
			rowPosition += 2;
		}
		expandMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound("/sound/click.wav");
				if(!expandMenu.isSelected()){
					settingBtn.setVisible(false);
					infoBtn.setVisible(false);
				}else{
					settingBtn.setVisible(true);
					infoBtn.setVisible(true);
				}
			}
		});
		draw.start();
	}
	public void splitStringToTF(String strToSplit, JTextField[] txtFields, String sub){
		gs.findSubPosInString(posOfChar, sub, playWord);

		for (int i = 0; i < posOfChar.size(); i++) {
			txtFields[posOfChar.elementAt(i)].setText(String.valueOf(strToSplit.charAt(posOfChar.elementAt(i))).toUpperCase());
		}
	}
	private void disableButton(JButton arg0, Color c){
		arg0.setBackground(c);
		arg0.setEnabled(false);
	}
	private void enableButtons(JButton[][] aob, int rows, int cols, Color clr){
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				JButton btnTemp = aob[i][j];
				if(!btnTemp.isEnabled()){
					btnTemp.setEnabled(true);
					btnTemp.setBackground(clr);
				}
			}
		}
	}
	private void playSound(String src) {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(GameFrame.class.getResourceAsStream(src));
			clip.open(inputStream);
			clip.start(); 
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	public void refreshWordList(){
		gs.getWordList(listOfWords, numOfWords);
		System.out.println(listOfWords);
		questionPosition = gs.rndOrder(numOfWords);
	}
	private void validChoice(JButton input){
		String btnText = input.getText().toLowerCase();
		String curAnswer = "";
		splitStringToTF(playWord,displays, btnText);
		if(playWord.contains(btnText)){
			playSound("/sound/coin.wav");
			addPromptTxt(promptWindow,"You got a character correct!");
			disableButton(input,mc.LIGHT_GREEN);
		}else{
			disableButton(input,mc.RED);
			playSound("/sound/wrong.wav");
			errorCharNum++;
			draw.charAnimationFlag = true;
			draw.key = errorCharNum;
			draw.start();
			addPromptTxt(promptWindow,"WRONG ANSWER! You have " + (6-errorCharNum) + " more trys");
		}
		for (int i = 0; i < playWord.length(); i++) {
			curAnswer += displays[i].getText();
		}
		if(curAnswer.equalsIgnoreCase(playWord)){
			correctAnswerNum++;
			addPromptTxt(promptWindow,"You got the corrrect answer!");
			addPromptTxt(promptWindow,"Click NEXT to move on.");
			playSound("/sound/win.wav");
			errorCharNum = 0;
			btnSkip.setText("NEXT");
		}
		if(errorCharNum == 6){
			addPromptTxt(promptWindow,"the word is:" + playWord + ". ");
			addPromptTxt(promptWindow,"You lost this round, here comes a new one.");
			addPromptTxt(promptWindow,"Click RETRY to begin.");
			errorAnswerNum++;
			playSound("/sound/fail.wav");
			errorCharNum = 0;
			btnSkip.setText("RETRY");
		}
	}
	public void addPromptTxt(JTextPane panel, String text){
		panel.setText(panel.getText()+"\n>" + text);
	}
	private void refreshPlayWord(JTextField[] txtFields){
		if(wordCount==numOfWords){
			addPromptTxt(promptWindow,"Thirty words are done! You got: ");
			addPromptTxt(promptWindow, correctAnswerNum + " Correct. " + errorAnswerNum + " Wrong. And skiped " + (numOfWords-errorAnswerNum-correctAnswerNum) + " questions.");
			refreshWordList();
			wordCount=0;
		}
		playWord = listOfWords.get(questionPosition[wordCount]);
		System.out.println(playWord);
		textPanel.removeAll();
		textPanel.revalidate();
		repaint();
		for (int i = 0; i < txtFields.length; i++) {
			txtFields[i].setText("");
		}
		for (int i = 0; i < playWord.length(); i++) {
			textPanel.add(displays[i]);
		}
		textPanel.revalidate();
		enableButtons(alphaBtns,4,7,interactColor);
		wordCount++;
	}
}
