package pu.gui.utils.ChemTable;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import ambit2.base.data.StructureRecord;

import org.openscience.cdk.interfaces.IAtomContainer;

import pu.gui.utils.EventsClass;
import pu.gui.utils.MoleculeDrawer;
import pu.gui.utils.StretchIcon;
import pu.gui.utils.ChemTable.SmartChemTableField.Type;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * -Structures and properties
 * -Color range per column 
 * -Selected objects + marked objects
 * -Interactively linked with other GUI components (if an objects is selected at one place it is selected everywhere) 
 * 
 * @author nick
 *
 */

public class SmartChemTable extends JPanel
{
	private static final long serialVersionUID = 13634655675682345L;
	

	List<SmartChemTableField> fields = new ArrayList<SmartChemTableField>();
	MoleculeDrawer drawer = new MoleculeDrawer();
	DefaultTableModel model = new DefaultTableModel(0,4);


	static ComponentListener componentListener = new ComponentListener() {
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}
	public void componentResized(ComponentEvent arg0) {
		System.out.println(arg0.getComponent().getClass());

	}

	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}
};

	JTable table;

	public SmartChemTable(List<SmartChemTableField> fields) 
	{
		setFields(fields);
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());

		table = new JTable(model)
		{
			public Class getColumnClass(int column) {
				Type ftype = fields.get(column).fieldType;
				if (ftype == Type.STRUCTURE || ftype == Type.REACTION) 
					return StretchIcon.class;
				return Object.class;
			}
		};
		add(table,BorderLayout.CENTER);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setRowHeight(200);
		JScrollPane scrollPane = new JScrollPane( table );
		add(scrollPane);



	}
	
	public void setFields(List<SmartChemTableField> fields)
	{
		this.fields = fields;
		model = new DefaultTableModel();
		for (int i = 0; i < fields.size(); i++)
			model.addColumn(fields.get(i).name);
		
	}

	public void addTableRow(List<Object> rowFields)
	{
		Object rowData[] = new Object[fields.size()]; 
		
		for (int i = 0; i < rowFields.size(); i++)
		{	
			Object o = rowFields.get(i);
			if (o == null)
			{
				//TODO
			}

			switch (fields.get(i).fieldType)
			{
			case TEXT:
			case VALUE:	
				rowData[i] = o;
				break;
			case STRUCTURE:
				if (o instanceof String)
				{
							String smi = (String)o;
							rowData[i] = drawer.getImageFromSmiles(smi,table);
				}
				else
					if (o instanceof IAtomContainer)
					{
						//TODO
					}
				break;
			}
		}
		
		model.addRow(rowData);
	}


	public void addStructureRecord(List<StructureRecord> structureRecords) {
		String[] columnNames =new String[] {"No.", "Name", "Smiles", "Structure"};
 	for(StructureRecord  str: structureRecords)
 	{
		List<Object> rowFields = new ArrayList<Object>();
		rowFields.add(str.getDataEntryID());
		rowFields.add(str.getFormula());
		rowFields.add(str.getSmiles());
		rowFields.add(drawer.getImageFromSmiles(str.getSmiles()));
		this.addTableRow(rowFields);
 	}
	}


}
