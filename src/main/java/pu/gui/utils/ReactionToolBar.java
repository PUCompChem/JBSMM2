package pu.gui.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gogo on 24.7.2017 г..
 */
public class ReactionToolBar extends JToolBar {


    public JComboBox getComboBox() {
        return comboBox;
    }

    private JComboBox  comboBox;
    private JButton go;



    public ReactionToolBar() {
        initGUI();

    }

    private void initGUI() {
        comboBox = new JComboBox();
        comboBox.setEditable(true);
        go = new JButton("Go");
        this.add(comboBox,BorderLayout.CENTER);
        this.add(go, BorderLayout.EAST);
    }
}
