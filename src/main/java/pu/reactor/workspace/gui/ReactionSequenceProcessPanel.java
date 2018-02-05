package pu.reactor.workspace.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.openscience.cdk.interfaces.IAtomContainer;

import com.mchange.v2.cfg.DelayedLogItem.Level;

import ambit2.reactions.retrosynth.IReactionSequenceHandler;
import ambit2.reactions.retrosynth.ReactionSequence;
import ambit2.reactions.retrosynth.ReactionSequenceLevel;
import ambit2.reactions.retrosynth.ReactionSequence.MoleculeStatus;
import ambit2.reactions.rules.scores.ReactionScoreSchema;
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
	List<RSEvent> events = new ArrayList<RSEvent>();
	
	//GUI elements
	DrawTextElement dte;
	JPanel configPanel;
	JCheckBox checkboxAutomaticMode;
	JTable tableWeights;
	DefaultTableModel modelTableWeights;
	
	int mouseTableRow = -1;
	int mouseTableColumn = -1;
	int mouseLevelIndex = -1;
	int mouseMolIndex = -1;
    
	//JComboBox<String> modeComboBox;
	
	
	public ReactionSequenceProcessPanel(ReactionSequenceProcess reactionSequenceProcess)
	{
		this.reactionSequenceProcess = reactionSequenceProcess;
		reactionSequenceProcess.getReactSeq().setReactionSequenceHandler(this);
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
		smartChemTable.getTable().setRowSelectionAllowed(false);
		
		smartChemTable.getTable().addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
            		smartChemTable_MouseMoved(e);
            }
			@Override
            public void mouseDragged(MouseEvent e) {
            		//System.out.println("drug ");
            }
        });
		smartChemTable.getTable().addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent evnt) {
				smartChemTable_MouseClicked(evnt);
		     }

			@Override
			public void mousePressed(MouseEvent e) {
				//System.out.println("mouse presssed");
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				//System.out.println("mouse released");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				//System.out.println("mouse entered");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//System.out.println("mouse exited");
			}
		});
		
		configPanel = new JPanel();
		configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.PAGE_AXIS)); 
		add(configPanel, BorderLayout.EAST);
		
		JLabel labelConfig = new JLabel("Reaction Sequence Config");
		labelConfig.setAlignmentX(Component.LEFT_ALIGNMENT);
		configPanel.add(labelConfig);
		
		checkboxAutomaticMode = new JCheckBox("Automatic mode");
		checkboxAutomaticMode.setSelected(true);
		configPanel.add(checkboxAutomaticMode);
		
		JLabel labelEmptySpace = new JLabel("   ");
		configPanel.add(labelEmptySpace);
		
		//setup weight table
		JLabel labelWeights = new JLabel("Reaction score weights");
		labelWeights.setAlignmentX(Component.LEFT_ALIGNMENT);
		configPanel.add(labelWeights);
		modelTableWeights = new DefaultTableModel(7, 2)
		{
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column > 0)
                		return true;
                else
                		return false;
            }
        };
        
        tableWeights = new JTable(modelTableWeights);
        addReactionScoreSchemaFieldsToTable();
        reactionScoreSchemaToTable(reactionSequenceProcess.getStrategy().reactionScoreSchema);
        tableWeights.setTableHeader(null);
        configPanel.add(tableWeights);
        addLevel();
        IAtomContainer target = reactionSequenceProcess.getReactSeq().getTarget();
		addStructureToLevel(0,target);
       
		
		/*
		String[] modeStrings = { "Manual", "Semi-automatic", "Automatic"};
		modeComboBox = new JComboBox<String>(modeStrings);
		modeComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		modeComboBox.setAlignmentY(Component.TOP_ALIGNMENT);
		//modeComboBox.setPreferredSize(new Dimension(0,0));
		modeComboBox.setMaximumSize(new Dimension(150,20));
		configPanel.add(modeComboBox);
		modeComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                modeComboBox.getBorder()));
		*/
		
        /*
		//Test code
		addLevel();
		for (int i = 0; i < 8; i++)
			addStructureToLevel(1,target);
		*/
		
		
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
	
	protected void addReactionScoreSchemaFieldsToTable()
	{	
		modelTableWeights.setValueAt("basic", 0, 0);
		modelTableWeights.setValueAt("transform", 1, 0);
		modelTableWeights.setValueAt("exp. conditions", 2, 0);
		modelTableWeights.setValueAt("yield", 3, 0);
		modelTableWeights.setValueAt("product complexity", 4, 0);
		modelTableWeights.setValueAt("react. center compl.", 5, 0);
		
		modelTableWeights.fireTableDataChanged();
		
        /*
	public double classcScoreWeight = 0.0;
	public double conditionsScoreWeight = 0.0;
	public double productSimilarityWeight = 0.0;
	public double productStabilityWeight = 0.0;
	public double reactionCenterPositionWeight = 0.0;
	public double reactionCenterComplexityWeight = 0.0;
	public double electronWithdrawingLevelWeight = 0.0;
         */
	}
	
	protected void reactionScoreSchemaToTable(ReactionScoreSchema rss)
	{	
		modelTableWeights.setValueAt(rss.basicScoreWeight, 0, 1);
		modelTableWeights.setValueAt(rss.transformScoreWeight, 1, 1);
		modelTableWeights.setValueAt(rss.experimentalConditionsScoreWeight, 2, 1);
		modelTableWeights.setValueAt(rss.yieldScoreWeight, 3, 1);
		modelTableWeights.setValueAt(rss.productComplexityWeight, 4, 1);
		modelTableWeights.setValueAt(rss.reactionCenterComplexityWeight, 5, 1);
		
		modelTableWeights.fireTableDataChanged();
	}
	
	@Override
	public IProcess getProcess() {
		return reactionSequenceProcess;
	}

	@Override
	public void updatePanel() {
		//Handle all events and clear event list
		for (int i = 0; i < events.size(); i++)
			handleEvent(events.get(i));
		events.clear();
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
	
	public void addStructureToLevel(int levelIndex, IAtomContainer mol)
	{	
		ImageIcon icon = smartChemTable.getDrawer().getImage(mol);
		LevelData ld = levels.get(levelIndex);
		ld.numMolecules++;
		int pos[] = ld.getMoleculePos(ld.numMolecules-1);
		//TODO fix the check for new addint new row
		if (((ld.numMolecules / numStructureColumns) + 1) > ld.numRows)
			insertNewRowInLevel(levelIndex);
			
		dte.text = "M" + levelIndex + "." + ld.numMolecules + " " + 
				MoleculeStatus.getShortString((MoleculeStatus)
						mol.getProperty(ReactionSequence.MoleculeStatusProperty))	;	
		dte.draw(icon.getImage().getGraphics());
		
		smartChemTable.getModel().setValueAt(icon, pos[0], pos[1]);
		smartChemTable.getModel().fireTableDataChanged();
	}

	@Override
	public ReactionSequence getReactionSequence() {
		return reactionSequenceProcess.getReactSeq();
	}

	@Override
	public void setReactionSequence(ReactionSequence sequence) {
	}

	@Override
	public void registerEvent(RSEvent event) {
		events.add(event);
	}	
	
	void handleEvent(RSEvent event) 
	{
		System.out.println("Handle event: " + event.type);
		switch (event.type)
		{
		case RESET:
			reset();
			break;
		case ADD_MANY_LEVELS:
			addAllSequenceLevelsToTable();
			break;
		}
	}
	
	void reset() 
	{
		levels.clear();
		smartChemTable.getModel().setRowCount(0);
		//smartChemTable.getModel().fireTableDataChanged();
		addLevel();
        IAtomContainer target = reactionSequenceProcess.getReactSeq().getTarget();
		addStructureToLevel(0,target);
	}
	
	void addAllSequenceLevelsToTable()
	{
		ReactionSequence reactSeq = reactionSequenceProcess.getReactSeq();
		ReactionSequenceLevel level = reactSeq.getFirstLevel();
		level = level.nextLevel;
		while (level != null)
		{
			addLevel();
			addSequenceLevelToTable(level);
			level = level.nextLevel;
		}
	}
	
	void addSequenceLevelToTable(ReactionSequenceLevel level)
	{
		for (int i = 0; i < level.molecules.size(); i++)
		{
			addStructureToLevel(level.levelIndex, level.molecules.get(i));
		}
	}
	
	void updateCurrentTableRowAndColumn (int row, int column)
	{
		if ((row == mouseTableRow) && (column == mouseTableColumn))
			return;
		
		mouseTableRow = row;
		mouseTableColumn = column;
		mouseLevelIndex = getLevelIndex (mouseTableRow);
		mouseMolIndex = getMoleculeIndex(mouseTableRow, mouseTableColumn, mouseLevelIndex);
		System.out.println("new position in cell: " + row + "  " + column 
				+ "   level = " + mouseLevelIndex + "  molIndex" + mouseMolIndex);
	}
	
	int getLevelIndex(int tableRow)
	{	
		for (int i = 0; i < levels.size(); i++)
		{
			LevelData ld = levels.get(i);
			if ((tableRow >= ld.firstRowIndex) && (tableRow < ld.firstRowIndex + ld.numRows))
				return i;
		}
		return -1;
	}
	
	int getMoleculeIndex(int tableRow, int tableColumn, int levelIndex)
	{
		if (tableColumn == 0)
			return -1;
		if (tableColumn > numStructureColumns)
			return -1;
		LevelData ld = levels.get(levelIndex);
		int levelRowIndex = tableRow - ld.firstRowIndex;
		int molIndex = levelRowIndex * numStructureColumns + tableColumn - 1;
		if (molIndex < ld.numMolecules)
			return molIndex;
		else
			return -1;
	}
	
	//------------- handle smartsChemTable mouse events --------------
	
	public void smartChemTable_MouseMoved(MouseEvent e)
	{	
		Point p = e.getPoint();
		//System.out.println("mouse moved " + p.toString());
		JTable table = smartChemTable.getTable();
		int row = table.rowAtPoint(p);
		int col = table.columnAtPoint(p);
		updateCurrentTableRowAndColumn(row, col);
		
		/*
		if ((row > -1 && row < smartChemTable.getRowCount()) && (col > -1 && col < smartChemTable.getColumnCount())) {

		}
		Point p = event.getPoint();
            int row = table.rowAtPoint(p);
            int col = table.columnAtPoint(p);
            Rectangle currentCell = table.getCellRect(row, col, false);
            p.translate(-currentCell.x, -currentCell.y);
		
		*/
	}
	
	public void smartChemTable_MouseClicked(MouseEvent e)
	{
		ReactionBrowser rb = new ReactionBrowser(this);
		rb.setVisible(true);
	}

}
