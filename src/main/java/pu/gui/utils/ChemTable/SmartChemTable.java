package pu.gui.utils.ChemTable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ambit2.base.data.StructureRecord;
import javafx.beans.binding.ObjectExpression;
import org.openscience.cdk.interfaces.IAtomContainer;
import pu.gui.utils.MoleculeDrawer;
import pu.gui.utils.StretchIcon;

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
	List<SmartChemTableField> fields = new ArrayList<SmartChemTableField>();
	MoleculeDrawer drawer = new MoleculeDrawer();
	DefaultTableModel model = new DefaultTableModel(0,4);

	JTable table;

	public SmartChemTable() {
		initGUI();

	}
	public SmartChemTable(List<StructureRecord> structureRecord){


		createWitchStructureRecord(structureRecord);
		 initGUI();
	}


	private void initGUI() {
		this.setLayout(new BorderLayout());

		table = new JTable(model)
		{
			 public Class getColumnClass(int column) {
                if (column==3) return StretchIcon.class;
                return Object.class;
            }
		};
		add(table,BorderLayout.CENTER);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setRowHeight(200);
		JScrollPane scrollPane = new JScrollPane( table );
		add(scrollPane);

	}

	public void addTableRow(List<Object> rowFields)
	{
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
				//TODO o.toString();
				break;
			case STRUCTURE:
				if (o instanceof String)
				{
					String smi = (String)o;
					//TODO 2D paint
				}
				else
					if (o instanceof IAtomContainer)
					{
						//TODO
					}
				break;
			}
			
			
		}
	}
		private void createWitchStructureRecord(List<StructureRecord> structureRecords) {
		String[] columnNames =new String[] {"No.", "Name", "Smiles", "Structure"};
		Object[][] tableMatrix = new Object[structureRecords.size()][4];
			for (int i = 0; i < structureRecords.size(); i++)
			{
			StructureRecord structureRecord = structureRecords.get(i);
			tableMatrix[i][0] = structureRecord.getDataEntryID();
			tableMatrix[i][1] = structureRecord.getFormula();
			tableMatrix[i][2] = structureRecord.getSmiles();
			tableMatrix[i][3] = drawer.getImageFromSmiles(structureRecord.getSmiles());
		    }
		   	model = new DefaultTableModel(tableMatrix, columnNames);
			this.setLayout(new BorderLayout());



	}

	
}
