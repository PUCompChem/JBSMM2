package pu.reactor.workspace.gui.wizards;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import pu.gui.utils.ButtonTabComponent;
import pu.gui.utils.trees.SetTree;
import pu.reactor.workspace.BasicReactorProcess;
import pu.reactor.workspace.ProcessCommonChemData;
import pu.reactor.workspace.gui.BasicReactorProcessPanel;
import pu.reactor.workspace.gui.ReactorProcessTabsSet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by gogo on 28.7.2017 г..
 */
public class BasicReactorWizard extends ProcessWizard
{
	private JPanel stageCards;
	private JPanel firstStepPanel;
	private JPanel seconStepPanel = new JPanel();
	private JButton applyButton;
	//private JButton previousButton;
	//private JButton nextButtonSet;
	private JButton pathButton;
	JTextField smilesField;
	JTextField processNameField;


	CardLayout cardLayout =  new CardLayout();

	private ProcessCommonChemData processCommonChemData = null;
	private JButton cancelButton;


	public BasicReactorWizard(ReactorProcessTabsSet processTabs, ProcessCommonChemData processCommonChemData) {
		this.processTabs = processTabs;
		this.processCommonChemData = processCommonChemData;

		//this.setLayout(new BorderLayout());
		initGUI();
		setSize(new Dimension(400,600));

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	protected void initGUI() {


		this.setSize(new Dimension(320,50));
		this.revalidate();
		this.add(buttonsPanel,BorderLayout.SOUTH);
		//Set StageCards
		this.setTitle("Basic Reactor Wizard");
	    BasicApplyButtonSet();
		setCancelButton();

		FirstStepPanelSet();


		stageCards = new JPanel(cardLayout);

		stageCards.add(firstStepPanel,"firstStep");
		stageCards.add(seconStepPanel,"secondStep");
		cardLayout.first(stageCards);

		this.add(stageCards,BorderLayout.CENTER);
		this.setVisible(true);

	}
	private void FirstStepPanelSet(){
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(3);
		firstStepPanel = new JPanel(flowLayout);
		firstStepPanel.setSize(new Dimension(100,100));

		firstStepPanel.setBorder(new EmptyBorder(50, 10, 10, 0));


		JLabel smilesInputLabel = new JLabel("Molecule (Smiles/InChI):");
		smilesInputLabel.setBorder(new EmptyBorder(0, 0, 0, 45));
		smilesField = new JTextField(30);
		firstStepPanel.add(smilesInputLabel, BorderLayout.WEST);
		firstStepPanel.add(smilesField);


		JLabel processNameLabel = new JLabel("Process Name:");
		processNameField = new JTextField(30);
		processNameField.setText("New Process");
		firstStepPanel.add( processNameLabel );
		firstStepPanel.add(processNameField);

		JLabel loadStrategyLabel = new JLabel("Strategy Path:");
		loadStrategyLabel.setBorder(new EmptyBorder(0, 0, 0, 7));
		pathButton = new JButton("File...");
		pathButton.setSize(10,70);
		final JTextField loadStrategyField = new JTextField(30);
		firstStepPanel.add( loadStrategyLabel );

		firstStepPanel.add( loadStrategyField );
		firstStepPanel.add(pathButton);

		pathButton.addActionListener(new ActionListener() {
			public  void actionPerformed(ActionEvent e) {
				loadStrategyField.setText(SetFileChooser());
			}
		} );
	}
	
	/*
	private void SecondStepPanelSet(){
		seconStepPanel = new JPanel();
		JLabel label = new JLabel ("Advanced Features");
		seconStepPanel.add(label);

	}
	*/



	public void BasicApplyButtonSet(){
		applyButton = new JButton("Create Process");
		buttonsPanel.add(applyButton);
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try{
					BasicReactorProcess basicReactorProcess = new BasicReactorProcess();
					basicReactorProcess.reactDB = processCommonChemData.getReactionDB();				
					basicReactorProcess.name = processNameField.getText();
					basicReactorProcess.setTargetInputString(smilesField.getText());					
					BasicReactorProcessPanel brpPanel = new BasicReactorProcessPanel(basicReactorProcess);
					basicReactorProcess.initProcess();

					processTabs.addProcessPanel(brpPanel);
					int currentPosition = processTabs.getTabCount()-1;
					processTabs.setTabComponentAt(currentPosition, new ButtonTabComponent(processTabs));
					processTabs.setSelectedIndex(currentPosition);

					setVisible(false);
				}
				catch (Exception exp)
				{}
			}

		} );
	}






	public String SetFileChooser(){
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("D:"));
		fc.setDialogTitle("File Chooser");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setVisible(true);
		if (fc.showOpenDialog(pathButton) == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile().getAbsolutePath();
		}
		else return "";
	}


}
