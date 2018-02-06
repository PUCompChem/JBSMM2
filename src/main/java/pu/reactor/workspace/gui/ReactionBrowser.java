package pu.reactor.workspace.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.reactions.GenericReactionInstance;
import ambit2.reactions.retrosynth.ReactionSequence;
import ambit2.reactions.retrosynth.ReactionSequenceLevel;
import ambit2.reactions.retrosynth.SyntheticStrategy;
import ambit2.reactions.retrosynth.ReactionSequence.MoleculeStatus;
import pu.gui.utils.ButtonTabComponent;
import pu.reactor.workspace.ReactionSequenceProcess;


public class ReactionBrowser extends JFrame 
{
	private static final long serialVersionUID = 3734241163743648132L;
	
	ReactionBrowserPanel reactBrowserPanel = null;
	ReactionSequenceProcessPanel rspPanel = null;
	JButton applyButton;
	JButton cancelButton;
	JPanel buttonsPanel;	
	List<GenericReactionInstance> reactInstances = null;
	IAtomContainer target = null;

	public ReactionBrowser(ReactionSequenceProcessPanel rspPanel, 
				List<GenericReactionInstance> reactInstances,
				IAtomContainer target) 
	{	
		this.rspPanel = rspPanel;
		this.reactInstances = reactInstances;
		this.target = target;
		initGUI();
		fillReactionInstanceData();
	}
	
	private void initGUI() 
	{
		setLayout(new BorderLayout());
		setBounds(50,50,800,600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		reactBrowserPanel = new ReactionBrowserPanel();
		add(reactBrowserPanel, BorderLayout.CENTER);
		
		buttonsPanel = new JPanel(new FlowLayout());
		this.add(buttonsPanel,BorderLayout.SOUTH);
		
		setCancelButton();
		setApplyButton();
	}
	
	private void setCancelButton(){
		cancelButton = new JButton("Cancel");
		buttonsPanel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();			
			}
		});
	}
	
	public void setApplyButton(){
		applyButton = new JButton("Apply reaction");
		buttonsPanel.add(applyButton);
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyButtonEvent();
			}
		} );
	}
	
	
	void fillReactionInstanceData()
	{	
		reactBrowserPanel.setTarget(target);
		reactBrowserPanel.setReactionInstances(reactInstances);	
		reactBrowserPanel.fillTable();
	}
	
	
	void applyButtonEvent()
	{
		//System.out.println("selectedRow = " + selectedRow);
		int selectedRow = reactBrowserPanel.chemTable.getTable().getSelectedRow();
		if (selectedRow != -1)
		{	
			GenericReactionInstance gri = reactInstances.get(selectedRow);
			ReactionSequenceProcess process = (ReactionSequenceProcess)rspPanel.getProcess();
			ReactionSequence rseq = process.getReactSeq();
			int levInd = rspPanel.mouseLevelIndex;
			int molInd = rspPanel.mouseMolIndex;
			ReactionSequenceLevel level = rseq.getLevel(levInd);
			try {
				int numMolBeforeReact = 0;
				if (level.nextLevel != null)
					numMolBeforeReact += level.nextLevel.molecules.size();
				rseq.generateSequenceStepForReactionInstance(level, molInd, gri);
				if (level.nextLevel != null) 
				{
					rseq.setMoleculeStatus(target, MoleculeStatus.RESOLVED);
					for (int i = numMolBeforeReact; i < level.nextLevel.molecules.size(); i++)
						rspPanel.addStructureToLevel(levInd + 1, level.nextLevel.molecules.get(i));
				}
			}
			catch (Exception e) {
			}
			
		}
		dispose();
	}

}
