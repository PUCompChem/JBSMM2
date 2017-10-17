package pu.reactor.workspace.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import pu.gui.utils.ButtonTabComponent;
import pu.reactor.workspace.BasicReactorProcess;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by gogo on 28.7.2017 г..
 */
public class NewBasicReactorWizard extends JFrame
{
	private JPanel stageCards;
	private JPanel firstStepPanel;
	private JPanel seconStepPanel = new JPanel();
	private JButton applyButton;
	private JButton previousButton;
	private JButton nextButtonSet;
	private JButton pathButton;
	JTextField smilesField;
	JTextField processNameField;
	
	private JPanel buttonsPanel = new JPanel(new FlowLayout());
	CardLayout cardLayout =  new CardLayout();
	private ReactorProcessTabsSet processTabs = null;
	
	public NewBasicReactorWizard(ReactorProcessTabsSet processTabs) {
		this.processTabs = processTabs;
		//this.setLayout(new BorderLayout());
		initGUI();
		setSize(new Dimension(500,600));

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void initGUI() {

		this.setResizable(false);
		this.setSize(new Dimension(320,50));
		this.revalidate();
		this.add(buttonsPanel,BorderLayout.SOUTH);
		//Set StageCards


		ApplyButtonSet();
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


		JLabel smilesInputLabel = new JLabel("Smiles:");
		smilesInputLabel.setBorder(new EmptyBorder(0, 0, 0, 45));
		smilesField = new JTextField(30);
		firstStepPanel.add(smilesInputLabel, BorderLayout.WEST);
		firstStepPanel.add(smilesField);


		JLabel processNameLabel = new JLabel("Process Name:");
		processNameField = new JTextField(30);
		processNameField.setText("New Process");
		firstStepPanel.add( processNameLabel );
		firstStepPanel.add(processNameField );

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
	private void SecondStepPanelSet(){
		seconStepPanel = new JPanel();
		JLabel label = new JLabel ("Advanced Features");
		seconStepPanel.add(label);

	}



	public void ApplyButtonSet(){
		applyButton = new JButton("Apply");
		buttonsPanel.add(applyButton);
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					BasicReactorProcess basicReactorProcess = new BasicReactorProcess();
					basicReactorProcess.name = processNameField.getText();
					processTabs.addProcess(basicReactorProcess);
					int currentPosition = processTabs.getTabCount()-1;
					processTabs.setTabComponentAt(currentPosition,
							new ButtonTabComponent(processTabs));
				}	
				catch (Exception exp)
				{}
			}
		} );
	}

	public void PreviousButtonSet(){
		previousButton = new JButton("Previous");
		buttonsPanel.add(previousButton);
		previousButton.addActionListener(new ActionListener() {
			public  void actionPerformed(ActionEvent e) {
				cardLayout.previous(stageCards);
			}
		} );
	}
	public void NextButtonSet(){
		nextButtonSet = new JButton("Next");
		buttonsPanel.add(nextButtonSet);
		nextButtonSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				cardLayout.next(stageCards);

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
