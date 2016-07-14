package central;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.Hashtable;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JTextPane;

import java.awt.FileDialog;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import library.Files;
import library.GameSystem;

/**
 * 
 * @author Yonglin Wang
 *
 */
public class Settings extends JFrame {
	/**
	 * Include the game system node to access game mechanical level methods.
	 */
	GameSystem gs = new GameSystem();
	/**
	 * These constant integer value defines what 100% and 0% means 
	 * in this setting menu
	 */
	private final int HUNDRED_PERCENT = 100, ZERO_PERCENT = 0;
	/**
	 * This double variable stores the percentage of words that shall be retrieved
	 * from the user dictionary.
	 */
	public double builtInPortion;
	/**
	 * This ArrayList temporarily stores the strings from the user dictionary file.
	 * This allows the program to exam containment, and efficiently write to the text
	 * file.
	 */
	private ArrayList<String> userWordList = new ArrayList<String>();
	private JPanel contentPane;
	private JTextField fileLocation;
	private JLabel lblDictionaryPorportioning;
	private int userDictionLength;
	private Font lblFont = new Font("Calibri", Font.PLAIN, 16);
	private JSlider portionSlider;
	private JTextPane warningTxt;
	private JLabel lblDictionary;
	private JLabel lblAdd;
	private JTextField addWordTxt;
	private JButton btnAdd;
	private JButton btnCancel;
	private JButton btnApply;
	private JTextPane addWordPrompt;
	/**
	 * Create the frame.
	 */
	public Settings() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Settings.class.getResource("/icons/hangman-logo.png")));
		setTitle("Settings - Just Another Hangman Game");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 553, 411);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(194dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(41dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,},
				new RowSpec[] {
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.UNRELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						RowSpec.decode("max(17dlu;default)"),
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),
						FormSpecs.UNRELATED_GAP_ROWSPEC,
						RowSpec.decode("max(19dlu;default)"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,}));

		lblDictionary = new JLabel("DICTIONARY SETTINGS");
		lblDictionary.setFont(new Font("Calibri", Font.BOLD, 18));
		contentPane.add(lblDictionary, "2, 2");

		JLabel lblDictionaryLocations = new JLabel("Location");
		lblDictionaryLocations.setFont(lblFont);
		lblDictionaryLocations.setVerticalAlignment(SwingConstants.TOP);
		contentPane.add(lblDictionaryLocations, "2, 4");

		fileLocation = new JTextField(gs.userDiction.getFileDirectory());
		contentPane.add(fileLocation, "2, 6, 3, 1, fill, fill");
		fileLocation.setColumns(10);
		fileLocation.setBorder(null);


		lblDictionaryPorportioning = new JLabel("Porportioning");
		lblDictionaryPorportioning.setFont(lblFont);
		contentPane.add(lblDictionaryPorportioning, "2, 8");
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer(ZERO_PERCENT), new JLabel("0%"));
		labelTable.put(new Integer(HUNDRED_PERCENT), new JLabel("100%"));
		portionSlider = new JSlider(JSlider.HORIZONTAL,ZERO_PERCENT,HUNDRED_PERCENT,0);
		portionSlider.setBorder(null);
		portionSlider.setForeground(SystemColor.desktop);
		portionSlider.setPaintLabels(true);
		portionSlider.setPaintTicks(true);
		portionSlider.setMajorTickSpacing(5);
		portionSlider.setLabelTable(labelTable);
		portionSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				int value = portionSlider.getValue();
				builtInPortion = (100 - value)*0.01;
			}
		});
		contentPane.add(portionSlider, "2, 10, 5, 1");
		warningTxt = new JTextPane();
		warningTxt.setFont(new Font("Calibri", Font.PLAIN, 11));
		warningTxt.setBackground(SystemColor.menu);
		warningTxt.setEditable(false);
		contentPane.add(warningTxt, "2, 11, 5, 1, fill, top");
		Files f = new Files(fileLocation.getText());
		userDictionLength = f.length();
		if(userDictionLength < 30){
			portionSlider.setEnabled(false);
			portionSlider.setValue(0);
			warningTxt.setText(warningTxt.getText()+"Your dictionary doesn't have enough words, it requires 30 words to work. You currently have " + userDictionLength + " words.");
		}else{ 
			portionSlider.setEnabled(true);
			warningTxt.setText("This slider determines the percentage of words taken from the user dictionary.\r\n");
		}

		final FileDialog fileDialog = new FileDialog(this,"Select Dictionary");
		JButton btnFind = new JButton("...");
		fileDialog.setFile("*.txt");
		btnFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileDialog.setVisible(true);
				if (fileDialog.getDirectory() != null && !fileDialog.getDirectory().isEmpty() && fileDialog.getFile() != null && !fileDialog.getFile().isEmpty()) {
					// doSomething
					fileLocation.setText(fileDialog.getDirectory()+fileDialog.getFile());
					Files f = new Files(fileLocation.getText());
					userDictionLength = f.length();
					if(userDictionLength < 30){
						portionSlider.setValue(0);
						portionSlider.setEnabled(false);
						warningTxt.setText(warningTxt.getText()+"Your dictionary doesn't have enough words, it requires 30 words to work. You currently have " + userDictionLength + " words in the dictionary.");
					}else{ 
						portionSlider.setEnabled(true);
						warningTxt.setText("This slider determines the percentage of words taken from the user dictionary.\r\n");
					}
				}
			}
		});
		btnFind.setFont(lblFont);
		contentPane.add(btnFind, "6, 6");

		lblAdd = new JLabel("Add Words");
		lblAdd.setFont(lblFont);
		contentPane.add(lblAdd, "2, 12");

		addWordTxt = new JTextField();
		addWordTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(check(addWordTxt.getText(),addWordPrompt)){
					gs.userDiction.fileToArrayList(userWordList);
					if(userWordList.contains(addWordTxt.getText())){
						if(infoBox("This word Already exist in the library. Do you still want to add it?", "repeaating words") == JOptionPane.YES_OPTION){
							gs.userDiction.writeStringToFile(addWordTxt.getText().toLowerCase());
							addWordPrompt.setText("The word \"" + addWordTxt.getText() + "\" is written to the user dictionary");
							addWordTxt.setText("");
						}
					}else{
						gs.userDiction.writeStringToFile(addWordTxt.getText().toLowerCase());
						addWordPrompt.setText("The word \"" + addWordTxt.getText() + "\" is written to the user dictionary");
						addWordTxt.setText("");
					}
				}
			}
		});
		addWordTxt.setColumns(10);
		addWordTxt.setBorder(null);
		contentPane.add(addWordTxt, "2, 14, 3, 1, fill, fill");


		addWordPrompt = new JTextPane();
		addWordPrompt.setEditable(false);
		addWordPrompt.setFont(new Font("Calibri", Font.PLAIN, 11));
		addWordPrompt.setBackground(SystemColor.menu);
		contentPane.add(addWordPrompt, "2, 16, 3, 1, fill, fill");

		btnAdd = new JButton("ADD");
		btnAdd.setFont(new Font("Calibri", Font.PLAIN, 16));
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(check(addWordTxt.getText(),addWordPrompt)){
					gs.userDiction.fileToArrayList(userWordList);
					if(userWordList.contains(addWordTxt.getText())){
						if(infoBox("This word Already exist in the library. Do you still want to add it?", "repeaating words") == JOptionPane.YES_OPTION){
							gs.userDiction.writeStringToFile(addWordTxt.getText().toLowerCase());
							addWordPrompt.setText("The word \"" + addWordTxt.getText() + "\" is written to the user dictionary");
							addWordTxt.setText("");
						}
					}else{
						gs.userDiction.writeStringToFile(addWordTxt.getText().toLowerCase());
						addWordPrompt.setText("The word \"" + addWordTxt.getText() + "\" is written to the user dictionary");
						addWordTxt.setText("");
					}
				}
			}
		});
		contentPane.add(btnAdd, "6, 14");

		btnApply = new JButton("APPLY");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String location = fileLocation.getText();
				int count = 0;
				StringBuilder str = new StringBuilder(location);
				for (int i = 0; i < location.length(); i++) {
					if(location.charAt(i) == 92){
						str.insert(i+count, '\\');
						count++;
					}
				}
				System.out.println(str);
				gs.userDiction.close();
				gs.percentage = builtInPortion;
				dispose();
			}
		});
		contentPane.add(btnApply, "4, 20, right, default");

		btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gs.userDiction.close();
			}
		});
		contentPane.add(btnCancel, "6, 20");

	}
	/**
	 * @param word the word that is being checked
	 * @param prompt the text pane to display the prompt
	 * @return if the word is usable
	 */
	private boolean check(String word, JTextPane prompt){
		//check if all characters are alphabetical letters
		char[] chars = word.toCharArray();
		for (char c : chars) {
			if(!Character.isLetter(c)) {
				if(word.contains(" ")){
					prompt.setText("The word cannot contain whitespaces.");
					return false;
				}
				prompt.setText("The word can only contain letters.");
				return false;
			}
		}
		//check if the word is too short
		if(chars.length < 5){
			prompt.setText("The word is too short. You need at least 5 character.");
			return false;
		}
		//check if the word is too long
		if(chars.length > 9){
			prompt.setText("The word is too long. You can have at most 9 character");
			return false;
		}
		return true;
	}
	private int infoBox(String infoMessage, String titleBar)
	{
		return JOptionPane.showConfirmDialog(null, infoMessage, "WARNING: " + titleBar, JOptionPane.YES_NO_OPTION);
	}
}
