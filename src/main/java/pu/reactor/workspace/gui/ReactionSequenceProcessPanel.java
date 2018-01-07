package pu.reactor.workspace.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.reactions.retrosynth.IReactionSequenceHandler;
import ambit2.reactions.retrosynth.ReactionSequence;
import ambit2.reactions.retrosynth.ReactionSequence.MoleculeStatus;
import ambit2.smarts.SmartsHelper;
import pu.gui.utils.chemtable.SmartChemTable;
import pu.gui.utils.chemtable.SmartChemTableField;
import pu.gui.utils.drawing.DrawTextElement;
import pu.reactor.workspace.IProcess;
import pu.reactor.workspace.ReactionSequenceProcess;

public class ReactionSequenceProcessPanel extends ProcessPanel implements IReactionSequenceHandler
{
	private static final long serialVersionUID = 6823629188783402290L;
	
	class LevelData {
		int firstRowIndex = 0;
		int numRows = 1;
		int numMolecules = 0;
		int[] getMoleculePos(int molIndex){
			int pos[] = new int[2];
			pos[0] = firstRowIndex + molIndex/numStructureColumns;
			pos[1] = (molIndex % numStructureColumns) + 1; 
			return pos;
		}
		public String toString() {
			return "firstRowIndex=" + firstRowIndex + " numRows=" + numRows + 
					" numMolecules" + numMolecules;
		}
	}
	
	ReactionSequenceProcess reactionSequenceProcess = null;
	SmartChemTable smartChemTable = new SmartChemTable();
	int numStructureColumns = 4;
	boolean useAdditionalInfoColumn = false;
	List<LevelData> levels = new ArrayList<LevelData>();
	
	//GUI elements
	JPanel configPanel;
	DrawTextElement dte;
	
	public ReactionSequenceProcessPanel(ReactionSequenceProcess reactionSequenceProcess)
	{
		this.reactionSequenceProcess = reactionSequenceProcess;
		initGUI();
	}
	
	private void initGUI() 
	{
		//Setup DrawTextData
		dte = new DrawTextElement(); 
		dte.xpos = 0;
		dte.ypos = 15;
		dte.textColor = Color.blue;
		dte.backgroundColor = Color.white;
		dte.backgroundXPos = 0;
		dte.backgroundYPos = 0;
		dte.backgroundXSize = 200;
		dte.backgroundYSize = 18;
		
		setLayout(new BorderLayout());
		List<SmartChemTableField> fields = getTableFields();
		smartChemTable = new SmartChemTable(fields);
		add(smartChemTable,BorderLayout.CENTER);
		
		configPanel = new JPanel();
		configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.PAGE_AXIS)); 
		add(configPanel, BorderLayout.EAST);
		JLabel labelConfig = new JLabel("Config");
		configPanel.add(labelConfig);
		
		//Test code
		addLevel();
		addLevel();
		addLevel();
		insertNewRowInLevel(0);
		insertNewRowInLevel(1);
		insertNewRowInLevel(1);
		
		
		IAtomContainer target = reactionSequenceProcess.getReactSeq().getTarget();
		addStructureToLevel(0,target);
		addStructureToLevel(1,target);
		addStructureToLevel(1,target);
		
		/*
		for (int i = 0; i < levels.size(); i++)
		{
			LevelData ld = levels.get(i);
			System.out.println("Level" + i + ": " + ld.toString());
		}
		
		removeRowFromLevel(1,2);
		
		for (int i = 0; i < levels.size(); i++)
		{
			LevelData ld = levels.get(i);
			System.out.println("Level" + i + ": " + ld.toString());
		}
		
		
		LevelData ld = levels.get(1);
		for (int i = 0; i < 15; i++)
		{
			int p[] = ld.getMoleculePos(i);
			System.out.println(" " + i + "  " + p[0] + "  " + p[1]);
		}
		*/
	}
	
	List<SmartChemTableField> getTableFields()
	{
		List<SmartChemTableField> fields = new ArrayList<SmartChemTableField>();
		fields.add(new SmartChemTableField("Level", SmartChemTableField.Type.TEXT));
		for (int i = 0; i < numStructureColumns; i++)
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
	
	public int getNumStructureColumns() {
		return numStructureColumns;
	}

	public void setNumStructureColumns(int numStructureColumns) {
		this.numStructureColumns = numStructureColumns;
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
		LevelData ld = levels.get(levelIndex);
		ld.numRows++;
		
		//Update the all higher levels
		for (int i = (levelIndex+1); i < levels.size(); i++)
			levels.get(i).firstRowIndex++;
		
		//update table
		int rowNum = ld.firstRowIndex + ld.numRows - 1;
		insertEmptyRowInTable(levelIndex, rowNum);
	}
	
	public void addLevel()
	{
		LevelData ldata = new LevelData();
		
		if (levels.isEmpty())		
			ldata.firstRowIndex = 0;
		else
		{
			LevelData ld = levels.get(levels.size()-1);
			ldata.firstRowIndex = ld.firstRowIndex + ld.numRows;
		}
		levels.add(ldata);
		addEmptyRowInTable(levels.size()-1);
	}
	
	public void addEmptyRowInTable(int levelIndex)
	{
		List<Object> rowFields = new ArrayList<Object>();
		rowFields.add("Level " + levelIndex);
		smartChemTable.addTableRow(rowFields);
	}
	
	public void insertEmptyRowInTable(int levelIndex, int rowNum)
	{
		//rowNum is calculated outside this function
		List<Object> rowFields = new ArrayList<Object>();
		rowFields.add("Level " + levelIndex);
		smartChemTable.getModel().insertRow(rowNum, rowFields.toArray());
		smartChemTable.getModel().fireTableDataChanged();
	}
	
	
	public void removeRowFromLevel(int levelIndex, int rowLevelIndex)
	{
		if (levelIndex >= levels.size())
			return;
		LevelData ld = levels.get(levelIndex);
		if (rowLevelIndex >= ld.numRows)
			return;
		ld.numRows--;
		
		//Update the all higher levels
		for (int i = (levelIndex+1); i < levels.size(); i++)		
			levels.get(i).firstRowIndex--;
		
		//update table
		int rowNum = ld.firstRowIndex + rowLevelIndex;
		smartChemTable.getModel().removeRow(rowNum);
		smartChemTable.getModel().fireTableDataChanged();
	}
	
	public void addStructureToLevel(int level, IAtomContainer mol)
	{	
		ImageIcon icon = smartChemTable.getDrawer().getImage(mol);
		LevelData ld = levels.get(level);
		int pos[] = ld.getMoleculePos(ld.numMolecules);
		ld.numMolecules++;
		
		dte.text = "M" + level + "." + ld.numMolecules + " " + 
				MoleculeStatus.getShortString((MoleculeStatus)
						mol.getProperty(ReactionSequence.MoleculeStatusProperty))	;		
		
		dte.draw(icon.getImage().getGraphics());
		
		//TODO update ld.numRows 
		smartChemTable.getModel().setValueAt(icon, pos[0], pos[1]);
		smartChemTable.getModel().fireTableDataChanged();
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
