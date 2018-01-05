package pu.reactor.workspace.gui.wizards;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import pu.gui.utils.ButtonTabComponent;
import pu.reactor.workspace.ProcessCommonChemData;
import pu.reactor.workspace.ReactionSequenceProcess;
import pu.reactor.workspace.gui.ReactionSequenceProcessPanel;
import pu.reactor.workspace.gui.ReactorProcessTabsSet;

public class ReactionSequenceWizard extends JFrame
{
	private static final long serialVersionUID = -6840013958453114880L;
	
	private ReactorProcessTabsSet processTabs = null;
	private ProcessCommonChemData processCommonChemData = null;
	
	JButton applyButton;
	JButton cancelButton;
	JTextField smilesField;
	JTextField processNameField;
	JPanel inputPanel;	
	JPanel buttonsPanel;	
	
	public ReactionSequenceWizard(ReactorProcessTabsSet processTabs, ProcessCommonChemData processCommonChemData) {
		this.processTabs = processTabs;
		this.processCommonChemData = processCommonChemData;
		initGUI();
	}
	
	private void initGUI() {

		this.setResizable(false);
		this.setSize(new Dimension(320,50));
		setSize(new Dimension(400,600));

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.revalidate();
		this.setTitle("New Reaction Sequence");		
		buttonsPanel = new JPanel(new FlowLayout());
		this.add(buttonsPanel,BorderLayout.SOUTH);
		setCancelButton();
		setApplyButton();
		
		setInputPanel();
		this.add(inputPanel,BorderLayout.CENTER);
		
		this.setVisible(true);
	}	
	
	private void setCancelButton(){
		cancelButton = new JButton("Cancel");
		buttonsPanel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);				
			}
		});
	}
	
	public void setApplyButton(){
		applyButton = new JButton("Create Process");
		buttonsPanel.add(applyButton);
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try{
					ReactionSequenceProcess rsp = new ReactionSequenceProcess();
					rsp.setReactDB(processCommonChemData.getReactionDB());
					rsp.setName(processNameField.getText());
					rsp.setTargetInputString(smilesField.getText()); 
					rsp.initProcess();
					ReactionSequenceProcessPanel rspPanel = new ReactionSequenceProcessPanel(rsp);
					
					processTabs.addProcessPanel(rspPanel);
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
	
	private void setInputPanel()
	{
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(3);
		inputPanel = new JPanel(flowLayout);
		inputPanel.setSize(new Dimension(100,100));
		inputPanel.setBorder(new EmptyBorder(50, 10, 10, 0));

		JLabel smilesInputLabel = new JLabel("Molecule (Smiles/InChI):");
		smilesInputLabel.setBorder(new EmptyBorder(0, 0, 0, 45));
		smilesField = new JTextField(30);
		inputPanel.add(smilesInputLabel, BorderLayout.WEST);
		inputPanel.add(smilesField);

		JLabel processNameLabel = new JLabel("Process Name:");
		processNameField = new JTextField(30);
		processNameField.setText("New Process");
		inputPanel.add( processNameLabel );
		inputPanel.add(processNameField);
	}
}
