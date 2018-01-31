package pu.gui.utils.chemtable;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import ambit2.base.data.StructureRecord;
import ambit2.rendering.IAtomContainerHighlights;

import org.openscience.cdk.interfaces.IAtomContainer;

import pu.gui.utils.MoleculeDrawer;
import pu.gui.utils.StretchIcon;
import pu.gui.utils.chemtable.SmartChemTableField.Type;

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
		
	JTable table;
	List<SmartChemTableField> fields = new ArrayList<SmartChemTableField>();
	MoleculeDrawer drawer = new MoleculeDrawer();
	DefaultTableModel model = new DefaultTableModel(0,4) {

		@Override
		public boolean isCellEditable(int row, int column) {
			//all cells false
			return false;
		}
	};;


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

	

	public SmartChemTable(List<SmartChemTableField> fields) 
	{
		setFields(fields);
		initGUI();
	}
	List<SmartChemTableField> f = new ArrayList<SmartChemTableField>();
	public SmartChemTable(){

		setFields(f);
		initGUI();

	}
	void initGUI() {
		this.setLayout(new BorderLayout());

		table = new JTable(model)
		{
			public Class getColumnClass(int column) {
				Type ftype = fields.get(column).fieldType;
				if (ftype == Type.STRUCTURE || ftype == Type.REACTION) 
					return ImageIcon.class;  //StretchIcon
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
		model = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(int row, int column) {
				//all cells false
				return false;
			}
		};
		for (int i = 0; i < fields.size(); i++)
			model.addColumn(fields.get(i).name);

	}
	
	
	public JTable getTable() {
		return table;
	}
	
	public void setTable(JTable table) {
		this.table = table;
	}
	
	public List<SmartChemTableField> getFields() {
		return fields;
	}
	
	public DefaultTableModel getModel() {
		return model;
	}
	
	public void setModel(DefaultTableModel model) {
		this.model = model;
	}
	
	public MoleculeDrawer getDrawer() {
		return drawer;
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
					rowData[i] = drawer.getImageFromSmiles(smi);
				}
				else if (o instanceof IAtomContainer)
				{
					rowData[i] = drawer.getImage((IAtomContainer)o);
				}
				else if (o instanceof Object[])
				{	
					Object obj[] = (Object[])o;
					if (obj.length == 2)
					{	
						if ((obj[0] instanceof IAtomContainer) &&
								(obj[1] instanceof IAtomContainerHighlights))
						{	
							rowData[i] = drawer.getImage((IAtomContainer)obj[0], 
									(IAtomContainerHighlights)obj[1]);
						}	
					}
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
	
	public void clearTable()
	{	
		/*
		DefaultTableModel dm = (DefaultTableModel)table.getModel();
		dm.getDataVector().removeAllElements();
		dm.fireTableDataChanged(); // notifies the JTable that the model has changed
		*/
		
		//while(model.getRowCount() > 0)
		//	model.removeRow(0);
		
		model.setRowCount(0);		
		table.revalidate();
	}


}
