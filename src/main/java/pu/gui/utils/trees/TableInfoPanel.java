package pu.gui.utils.trees;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by gogo on 26.7.2017 г..
 */
public class TableInfoPanel extends JPanel {
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

    public void Write(String input) {
        //  model = new DefaultTableModel();

        String[] lines = input.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String[] components = lines[i].split(": ");
            model.setValueAt(components[0], i, 0);
            model.setValueAt(components[1], i, 1);
        }
        this.remove(table);
        model.fireTableDataChanged();
        this.add(table, BorderLayout.CENTER);
    }
}