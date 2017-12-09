package pu.reactor.workspace.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.openscience.cdk.interfaces.IAtomContainer;

import pu.gui.utils.chemtable.SmartChemTable;
import pu.gui.utils.chemtable.SmartChemTableField;
import pu.reactor.workspace.IProcess;
import pu.reactor.workspace.ReactionSequenceProcess;

public class ReactionSequenceProcessPanel extends ProcessPanel
{
	private static final long serialVersionUID = 6823629188783402290L;
	
	ReactionSequenceProcess reactionSequenceProcess = null;
	SmartChemTable smartChemTable = new SmartChemTable();
	int nStructureColumns = 4;
	boolean useAdditionalInfoColumn = false;
	List<Integer> levelFirstRowIndex = new ArrayList<Integer>();
	List<Integer> levelNumRows = new ArrayList<Integer>();
	
	
	public ReactionSequenceProcessPanel(ReactionSequenceProcess reactionSequenceProcess)
	{
		this.reactionSequenceProcess = reactionSequenceProcess;
		initGUI();
	}
	
	private void initGUI() {

		setLayout(new BorderLayout());
		List<SmartChemTableField> fields = getTableFields();
		smartChemTable = new SmartChemTable(fields);
		add(smartChemTable,BorderLayout.CENTER);
	}
	
	List<SmartChemTableField> getTableFields()
	{
		List<SmartChemTableField> fields = new ArrayList<SmartChemTableField>();
		fields.add(new SmartChemTableField("Level", SmartChemTableField.Type.TEXT));
		for (int i = 0; i < nStructureColumns; i++)
			fields.add(new SmartChemTableField("Structure", SmartChemTableField.Type.STRUCTURE));
		return fields;
	}
	
	@Override
	public IProcess getProcess() {
		return reactionSequenceProcess;
	}

	@Override
	public void updatePanel() {
	}
	
	public int getnStructureColumns() {
		return nStructureColumns;
	}

	public void setnStructureColumns(int nStructureColumns) {
		this.nStructureColumns = nStructureColumns;
		//TODO update structure table and data model
	}

	public boolean isUseAdditionalInfoColumn() {
		return useAdditionalInfoColumn;
	}

	public void setUseAdditionalInfoColumn(boolean useAdditionalInfoColumn) {
		this.useAdditionalInfoColumn = useAdditionalInfoColumn;
		//TODO update structure table and data model
	}
	
	void insertNewRowInLevel(int level)
	{
		if (level >= levelFirstRowIndex.size())
			return;
		int nr = levelNumRows.get(level);
		levelNumRows.set(level, (nr+1));
		//Update the all higher levels
		for (int i = (level+1); i < levelFirstRowIndex.size(); i++)
		{
			int fri = levelFirstRowIndex.get(i);
			levelFirstRowIndex.set(i, (fri+1));
		}
		
		//TODO update table
	}
	
	void removeEmptyRowFromLevel(int level, int localLevelIndex)
	{
		//TODO
	}
	
	void addStructureToLevel(int level, IAtomContainer mol)
	{
		//TODO
	}

}
