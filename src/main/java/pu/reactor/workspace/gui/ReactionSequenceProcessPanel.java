package pu.reactor.workspace.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.reactions.retrosynth.IReactionSequenceHandler;
import ambit2.reactions.retrosynth.ReactionSequence;
import pu.gui.utils.chemtable.SmartChemTable;
import pu.gui.utils.chemtable.SmartChemTableField;
import pu.reactor.workspace.IProcess;
import pu.reactor.workspace.ReactionSequenceProcess;

public class ReactionSequenceProcessPanel extends ProcessPanel implements IReactionSequenceHandler
{
	private static final long serialVersionUID = 6823629188783402290L;
	
	class LevelData {
		int firstRowIndex = 0;
		int numRows = 1;
	}
	
	ReactionSequenceProcess reactionSequenceProcess = null;
	SmartChemTable smartChemTable = new SmartChemTable();
	int nStructureColumns = 4;
	boolean useAdditionalInfoColumn = false;
	List<LevelData> levels = new ArrayList<LevelData>();
	
	//GUI elements
	JPanel configPanel;
	
	
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
		
		configPanel = new JPanel();
		configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.PAGE_AXIS)); 
		add(configPanel, BorderLayout.EAST);
		JLabel labelConfig = new JLabel("Config");
		configPanel.add(labelConfig);
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
	
	void insertNewRowInLevel(int levelIndex)
	{
		if (levelIndex >= levels.size())
			return;
		levels.get(levelIndex).numRows++;
		
		//Update the all higher levels
		for (int i = (levelIndex+1); i < levels.size(); i++)
		{
			levels.get(i).firstRowIndex++;
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

	@Override
	public ReactionSequence getReactionSequence() {
		return reactionSequenceProcess.getReactSeq();
	}

	@Override
	public void setReactionSequence(ReactionSequence sequence) {
		// TODO Auto-generated method stub
	}

	@Override
	public void registerEvent(RSEvent event) {
		// TODO Auto-generated method stub
		
	}	

}
