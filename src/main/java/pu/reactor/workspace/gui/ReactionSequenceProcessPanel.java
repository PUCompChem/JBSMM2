package pu.reactor.workspace.gui;

import javax.swing.JPanel;

import pu.reactor.workspace.IProcess;
import pu.reactor.workspace.ReactionSequenceProcess;

public class ReactionSequenceProcessPanel extends ProcessPanel
{

	ReactionSequenceProcess reactSeqProcess = null;
	
	@Override
	public IProcess getProcess() {
		return reactSeqProcess;
	}

	@Override
	public void updatePanel() {
		// TODO Auto-generated method stub
		
	}

}
