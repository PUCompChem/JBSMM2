package pu.reactor.workspace.gui;

import ambit2.reactions.reactor.ReactorStrategy;
import pu.gui.utils.ChemTable.SmartChemTableField;
import pu.gui.utils.StretchIcon;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Types;
import java.util.Objects;

import static ambit2.db.update.propertyannotations.ReadPropertyAnnotations._fields.object;
import static java.lang.Enum.valueOf;

public class BasicReactorParametersPanel extends JPanel {
	DefaultTableModel model = new DefaultTableModel(0,2);
	private ReactorStrategy strategy = new ReactorStrategy();
	JTable table;
	public BasicReactorParametersPanel(ReactorStrategy strategy) {
		//this.strategy = strategy;
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		final Field[] strategyFields = strategy.getClass().getFields();
		for (int i = 0; i < strategyFields.length; i++) {
			Field currentField = strategyFields[i];
			Object[] rowData = new Object[2];
			rowData[0] = currentField.getName();

			currentField.setAccessible(true);


			try {
				Object a =  currentField.get(strategy);
				rowData[1] = a;
			} catch (IllegalAccessException e) {
				System.out.println("doesnt work");
			}


			currentField.setAccessible(true);


			model.addRow(rowData);

		}



		table  = new JTable(model)
		{
			public Class getColumnClass(int column) {
				Type ftype = strategyFields[column].getType();
				if (ftype.toString() == "boolean"){
					return Boolean.class;
				}
				return Object.class;
			}
		};
		table.setRowHeight(30);



		model.addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				for (int i = 0; i < strategyFields.length; i++) {
					Field currentField = strategyFields[i];
					Type currentType = currentField.getType();

					try {
						if (currentType.toString() == "int")
						{
							Object a = currentField.get(strategy);
							if (table.getValueAt(i, 1) != a) {
								currentField.set(strategy, Integer.parseInt(table.getValueAt(i, 1).toString()));
							}
						}

						else if(currentType.toString() == "boolean")
						{
							Object a = currentField.get(strategy);
							if (table.getValueAt(i, 1) != a) {
								currentField.set(strategy, StringToBoolean(table.getValueAt(i, 1).toString()));
							}
						}
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}


				}
				System.out.println(strategy.FlagStopOnMaxLevel);

			}
		});



		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane,BorderLayout.CENTER);
	}


	private boolean StringToBoolean(String str){

		String value = str.toUpperCase();
		if(Objects.equals(value,"TRUE"))
		{
			return true;
		}

		else{
			return false;
		}
	}
}
