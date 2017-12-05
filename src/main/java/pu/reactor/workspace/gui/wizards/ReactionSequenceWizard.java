package pu.reactor.workspace.gui.wizards;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pu.gui.utils.ButtonTabComponent;
import pu.reactor.workspace.ProcessCommonChemData;
import pu.reactor.workspace.ReactionSequenceProcess;
import pu.reactor.workspace.gui.ReactionSequenceProcessPanel;
import pu.reactor.workspace.gui.ReactorProcessTabsSet;

public class ReactionSequenceWizard extends JFrame
{
	private ReactorProcessTabsSet processTabs = null;
	private ProcessCommonChemData processCommonChemData = null;
	
	JButton applyButton;
	JTextField smilesField;
	JTextField processNameField;
	JPanel buttonsPanel = new JPanel(new FlowLayout());
	JButton cancelButton;
	
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
		this.add(buttonsPanel,BorderLayout.SOUTH);
		setCancelButton();
		setApplyButton();
		
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
					//rsp.name = processNameField.getText();
					rsp.setName("Reaction Sequence");
					rsp.setTargetInputString("CCCCC"); //smilesField.getText();
					
					ReactionSequenceProcessPanel rspPanel = new ReactionSequenceProcessPanel(rsp);
					rsp.initProcess();

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
}
