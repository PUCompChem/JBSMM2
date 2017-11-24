package pu.gui.utils.chemtable;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import ambit2.base.data.StructureRecord;

public class StructureTable extends SmartChemTable
{
	private int numberOfColumns;
	private int currentColumn = -1;
	private int currentRow = -1;
	
	public StructureTable(int numberOfColumns) 
	{
		this.numberOfColumns = numberOfColumns;
		currentColumn = numberOfColumns;
		model = new DefaultTableModel(0,numberOfColumns);
		
		for (int i = 0; i < numberOfColumns; i++)
			fields.add(new SmartChemTableField("Structure", SmartChemTableField.Type.STRUCTURE));
		
		setFields(f);
		initGUI();
		
	}
	
	
	public void addMoleculeAsString(String molString)
	{
		ImageIcon icon = drawer.getImageFromSmiles1(molString);
		if (currentColumn == numberOfColumns)
		{
			//new row is created
			currentRow++;
			Object rowData[] = new Object[fields.size()]; 
			model.addRow(rowData);
			currentColumn = 0;
		}
		
		model.setValueAt(icon, currentRow, currentColumn);
		currentColumn++;
	}
	
	
	public void setMoleculeAsStringAtPos(String molString, int row, int column)
	{
		//TODO
	}
	
	
	@Override
	public void addTableRow(List<Object> rowFields)
	{
		//TODO
	}
	
	@Override
	public void addStructureRecord(List<StructureRecord> structureRecords)
	{

	}
	        
}
