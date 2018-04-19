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
	JTextField classcScoreWeight = new JTextField(20);
	JTextField transformScoreWeight = new JTextField(20);
	JTextField conditionsScoreWeight = new JTextField(20);
	JTextField experimentalConditionsScoreWeight = new JTextField(20);
	JTextField yieldScoreWeight = new JTextField(20);
	JTextField productComplexityWeight = new JTextField(20);
	JTextField productSimilarityWeight = new JTextField(20);
	JTextField productStabilityWeight = new JTextField(20);
	JTextField reactionCenterPositionWeight = new JTextField(20);
	JTextField reactionCenterComplexityWeight = new JTextField(20);
	JTextField electronWithdrawingLevelWeight = new JTextField(20);





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

		labelPanel = new JPanel(new GridLayout(12, 1,5,8));
		fieldPanel = new JPanel(new GridLayout(12, 1,0,4));
		strategyPanel.add(labelPanel, BorderLayout.WEST);
		strategyPanel.add(fieldPanel, BorderLayout.CENTER);

		labelPanel.add(new JLabel("basic score weight"));
		fieldPanel.add(basicScoreWeight);
		labelPanel.add(new JLabel("classic Score Weightt"));
		fieldPanel.add(classcScoreWeight);
		labelPanel.add(new JLabel("transformScoreWeight"));
		fieldPanel.add(transformScoreWeight);
		labelPanel.add(new JLabel("conditionsScoreWeight"));
		fieldPanel.add(conditionsScoreWeight);
		labelPanel.add(new JLabel("experimentalConditionsScoreWeight"));
		fieldPanel.add(experimentalConditionsScoreWeight);
		labelPanel.add(new JLabel("yieldScoreWeight"));
		fieldPanel.add(yieldScoreWeight);
		labelPanel.add(new JLabel("productComplexityWeight"));
		fieldPanel.add(productComplexityWeight);
		labelPanel.add(new JLabel("productSimilarityWeight"));
		fieldPanel.add(productSimilarityWeight);
		labelPanel.add(new JLabel("productStabilityWeight"));
		fieldPanel.add(productStabilityWeight);
		labelPanel.add(new JLabel("reactionCenterPositionWeight"));
		fieldPanel.add(reactionCenterPositionWeight);
		labelPanel.add(new JLabel("reactionCenterComplexityWeight"));
		fieldPanel.add(reactionCenterComplexityWeight);
		labelPanel.add(new JLabel("electronWithdrawingLevelWeight"));
		fieldPanel.add(electronWithdrawingLevelWeight);




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
		classcScoreWeight.setText(String.valueOf(preferences.classcScoreWeight));
		transformScoreWeight.setText(String.valueOf(preferences.transformScoreWeight));
		conditionsScoreWeight.setText(String.valueOf(preferences.conditionsScoreWeight));
		experimentalConditionsScoreWeight.setText(String.valueOf(preferences.experimentalConditionsScoreWeight));
		yieldScoreWeight.setText(String.valueOf(preferences.yieldScoreWeight));
		productComplexityWeight.setText(String.valueOf(preferences.productComplexityWeight));
		productSimilarityWeight.setText(String.valueOf(preferences.productSimilarityWeight));
		productStabilityWeight.setText(String.valueOf(preferences.productStabilityWeight));
		reactionCenterPositionWeight.setText(String.valueOf(preferences.reactionCenterPositionWeight));
		reactionCenterComplexityWeight.setText(String.valueOf(preferences.reactionCenterComplexityWeight));
		electronWithdrawingLevelWeight.setText(String.valueOf(preferences.electronWithdrawingLevelWeight));
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
		preferences.classcScoreWeight = Double.parseDouble(classcScoreWeight.getText());
		preferences.transformScoreWeight = Double.parseDouble(transformScoreWeight.getText());
		preferences.conditionsScoreWeight = Double.parseDouble(conditionsScoreWeight.getText());
		preferences.experimentalConditionsScoreWeight = Double.parseDouble(experimentalConditionsScoreWeight.getText());
		preferences.yieldScoreWeight = Double.parseDouble(yieldScoreWeight.getText());
		preferences.productComplexityWeight = Double.parseDouble(productComplexityWeight.getText());
		preferences.productSimilarityWeight = Double.parseDouble(productSimilarityWeight.getText());
		preferences.productStabilityWeight = Double.parseDouble(productStabilityWeight.getText());
		preferences.reactionCenterPositionWeight = Double.parseDouble(reactionCenterPositionWeight.getText());
		preferences.reactionCenterComplexityWeight = Double.parseDouble(reactionCenterComplexityWeight.getText());
		preferences.electronWithdrawingLevelWeight = Double.parseDouble(electronWithdrawingLevelWeight.getText());

		/*
		System.out.println("preferencesFilePath " + preferencesFilePath);
		
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
		*/
		
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
