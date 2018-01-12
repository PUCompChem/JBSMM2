package pu.gui.utils.trees;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import ambit2.reactions.GenericReaction;

import java.awt.*;

/**
 * Created by gogo on 26.7.2017 г..
 */
public class TableInfoPanel extends JPanel 
{	
 	JTable table;
    DefaultTableModel model;

    public TableInfoPanel() {
        this.setLayout(new BorderLayout());
        model = new DefaultTableModel(3, 2) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        table = new JTable(model);

        this.add(table, BorderLayout.CENTER);
        table.setTableHeader(null);
    }

    public void write(String input) 
    {
    	nullifyTable();
    	model.setValueAt(input, 0, 0);
    	model.fireTableDataChanged();
    }
    
    void nullifyTable()
    {
    	for (int i = 0; i < model.getRowCount(); i++)
    		for (int k = 0; k < model.getColumnCount(); k++)
    			model.setValueAt(null, i, k);
    }
    
    
    public void write(GenericReaction r) 
    {
    	model.setValueAt("Name", 0, 0);
    	model.setValueAt(r.getName(), 0, 1);
    	
    	model.setValueAt("Class", 1, 0);
    	model.setValueAt(r.getReactionClass(), 1, 1);
    	
    	model.setValueAt("Smirks", 2, 0);
    	model.setValueAt(r.getSmirks(), 2, 1);
    	
    	model.fireTableDataChanged();
    }
}