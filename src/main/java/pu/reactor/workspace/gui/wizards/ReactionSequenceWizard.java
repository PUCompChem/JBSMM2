package pu.reactor.workspace.gui.wizards;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import pu.reactor.workspace.ProcessCommonChemData;
import pu.reactor.workspace.gui.ReactorProcessTabsSet;

public class ReactionSequenceWizard extends JFrame
{
	private ReactorProcessTabsSet processTabs = null;
	private ProcessCommonChemData processCommonChemData = null;
	
	public ReactionSequenceWizard(ReactorProcessTabsSet processTabs, ProcessCommonChemData processCommonChemData) {
		this.processTabs = processTabs;
		this.processCommonChemData = processCommonChemData;
		initGUI();
		setSize(new Dimension(400,600));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void initGUI() {

		this.setResizable(false);
		this.setSize(new Dimension(320,50));
		this.revalidate();
		//this.add(buttonsPanel,BorderLayout.SOUTH);
		this.setTitle("New Reaction Sequence");
		
		this.setVisible(true);
	}	
	
	
}
