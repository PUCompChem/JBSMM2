package pu.reactor.workspace.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import pu.gui.utils.chemtable.SmartChemTable;
import pu.gui.utils.chemtable.SmartChemTableField;
import pu.reactor.workspace.IProcess;
import pu.reactor.workspace.ReactionSequenceProcess;

public class ReactionSequenceProcessPanel extends ProcessPanel
{
	ReactionSequenceProcess reactionSequenceProcess = null;
	private SmartChemTable smartChemTable = new SmartChemTable();
		
	public ReactionSequenceProcessPanel(ReactionSequenceProcess reactionSequenceProcess)
	{
		this.reactionSequenceProcess = reactionSequenceProcess;
		initGUI();
	}
	
	private void initGUI() {

		setLayout(new BorderLayout());
		
		List<SmartChemTableField> fields = new ArrayList<SmartChemTableField>();
		fields.add(new SmartChemTableField("Info", SmartChemTableField.Type.TEXT));
		fields.add(new SmartChemTableField("Structure", SmartChemTableField.Type.STRUCTURE));
		//fields.add(new SmartChemTableField("Structure2", SmartChemTableField.Type.STRUCTURE));
		
		smartChemTable = new SmartChemTable(fields);
		add(smartChemTable,BorderLayout.CENTER);
	}	
	
	@Override
	public IProcess getProcess() {
		return reactionSequenceProcess;
	}

	@Override
	public void updatePanel() {
		// TODO Auto-generated method stub
		
	}

}
