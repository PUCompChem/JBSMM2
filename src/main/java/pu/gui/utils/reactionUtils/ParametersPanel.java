package pu.gui.utils.reactionUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by gogo on 22.8.2017 г..
 */
public class ParametersPanel extends JPanel {
    DefaultTableModel model = new DefaultTableModel(20,2);

    JTable table = new JTable(model);
    public ParametersPanel() {
        initGUI();
    }

    private void initGUI() {
            setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(table);

            add(scrollPane,BorderLayout.CENTER);
    }
}
