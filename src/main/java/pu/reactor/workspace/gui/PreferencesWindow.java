package pu.reactor.workspace.gui;

import pu.reactor.workspace.Preferences;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PreferencesWindow extends JFrame 
{

	public Preferences getPreferences() {
		return preferences;
	}

	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}

	public Preferences preferences;
	public String preferencesFilePath;

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	private JTabbedPane tabbedPane;



	JPanel quitAndSavePanel;

	JPanel optionsMenuPanel;



	JTree tree;
	JTextField reactionDBPathField;
	JTextField startingMaterialsDBPathField;

	//StrategiesFields
	JTextField basicScoreWeight = new JTextField(20);




	public JButton getApplyButton() {
		return applyButton;
	}

	public void setApplyButton(JButton applyButton) {
		this.applyButton = applyButton;
	}

	JButton applyButton;
	JButton okButton;
	JButton cancelButton;
	/*
	 * Constructor
	 * @param no parameters
	 */
	public PreferencesWindow() { 
		this.preferences = new Preferences();

		initGUI();
		fillGUIComponentsData();
	}

	public PreferencesWindow(Preferences prefs, String preferencesFilePath) { 
		this.preferences = prefs; 
		this.preferencesFilePath = preferencesFilePath;


		initGUI();
		fillGUIComponentsData();
	}


	void initGUI()
	{
		this.setSize(new Dimension(500, 700));
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//Sett Tabs
		JTabbedPane	 preferencesTabsPane = new JTabbedPane();
		//Setting OptionsMenu


		optionsMenuPanel = new JPanel();
		optionsMenuPanel.setSize(100,100);
		optionsMenuPanel.setBackground(Color.WHITE);
		reactionDBPathField = new JTextField(20);
		startingMaterialsDBPathField = new JTextField(20);
		JPanel labelPanel = new JPanel(new GridLayout(2, 1));
		JPanel fieldPanel = new JPanel(new GridLayout(2, 1));
		optionsMenuPanel.add(labelPanel, BorderLayout.WEST);
		optionsMenuPanel.add(fieldPanel, BorderLayout.CENTER);
		labelPanel.add(new JLabel("Reaction database path:"));
		fieldPanel.add(reactionDBPathField);
		labelPanel.add(new JLabel("Starting materials database path:"));
		fieldPanel.add(startingMaterialsDBPathField);


		preferencesTabsPane.add("Paths",optionsMenuPanel);


		// StrategyPanel

		JPanel strategyPanel = new JPanel();

		strategyPanel.setSize(100,100);
		strategyPanel.setBackground(Color.WHITE);

		 labelPanel = new JPanel(new GridLayout(2, 1));
		 fieldPanel = new JPanel(new GridLayout(2, 1));
		strategyPanel.add(labelPanel, BorderLayout.WEST);
		strategyPanel.add(fieldPanel, BorderLayout.CENTER);
		labelPanel.add(new JLabel("basic score weight"));
		fieldPanel.add(basicScoreWeight);






		preferencesTabsPane.add("Strategy Panel",strategyPanel);


		//Setting QuitAndSaveMenu
		applyButton = setApplyButton();
		okButton = setOKButton();
		cancelButton = setCancelButton();

		quitAndSavePanel = new JPanel(new FlowLayout(20,20,20)); 

		quitAndSavePanel.add(applyButton);
		quitAndSavePanel.add(okButton);
		quitAndSavePanel.add(cancelButton);


		//Main Frame settings


		setLayout(new BorderLayout(100,100));
		add(preferencesTabsPane, BorderLayout.CENTER);

		add(quitAndSavePanel,BorderLayout.SOUTH);


		this.setVisible(true);
	}

	void fillGUIComponentsData()
	{

		reactionDBPathField.setText(preferences.reactionDBPath);
		startingMaterialsDBPathField.setText(preferences.startingMaterialsPath);

		//DefaultStrategies
		basicScoreWeight.setText(String.valueOf(preferences.basicScoreWeight));

	}


	/*
	 * create_and_set_apply_button
	 * @param no parameters 
	 * @return void;
	 */
	private JButton setApplyButton(){


		JButton button = new JButton("Apply");
		JPanel applyButtonPanel = new JPanel(); 
		applyButtonPanel.add(button, BorderLayout.WEST);
		add(applyButtonPanel);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e){
				System.out.println("Apply button was clicked");
				ApplyPreferences();

			}

		});
		return button;
	}
	private JButton setCancelButton(){
		JButton button = new JButton("Cancel");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				setVisible(false);
			}

		});
		return button;

	};

	private JButton setOKButton(){

		JButton button = new JButton("OK");
		JPanel OKButtonPanel = new JPanel(); 
		OKButtonPanel.add(button,BorderLayout.EAST);
		add(OKButtonPanel);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e){
				ApplyPreferences();
				dispose();
			}

		});
		return button;
	}
	private void ApplyPreferences(){
		preferences.reactionDBPath = reactionDBPathField.getText();
		preferences.startingMaterialsPath = startingMaterialsDBPathField.getText();

		preferences.basicScoreWeight = Double.parseDouble(basicScoreWeight.getText());





		if (preferencesFilePath != null)
		{
			BufferedWriter bw  = null;
			try {
				FileWriter fw = new FileWriter(preferencesFilePath);
				bw = new BufferedWriter(fw);
				bw.write(preferences.toJsonString());
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			finally{
				try{
					if(bw!=null)
						bw.close();
				}catch(Exception ex){
					System.out.println("Error in closing the BufferedWriter"+ex);
				}
			}
		}
	}

	private void createTreeTable(){
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("root");

		DefaultMutableTreeNode a = new DefaultMutableTreeNode("Paths");
		top.add(a);


		DefaultMutableTreeNode b = new DefaultMutableTreeNode("Whatever");
		top.add(b);

		tree = new JTree(top);
		tree.setRootVisible(false);


	}
	private void showSpecificMenu(int n){

	}

	public JButton getOkButton() {
		return okButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}
}
