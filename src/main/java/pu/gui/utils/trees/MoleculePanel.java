package pu.gui.utils.trees;

import org.openscience.cdk.interfaces.IAtomContainer;
import pu.gui.utils.panels.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
/**
 * Created by gogo on 4.7.2017 г..
 */
public class MoleculePanel extends JPanel {

    StructurePanel stucturePanel;
    JTable informationTable;
    DefaultTableModel informationTableModel = new DefaultTableModel(3,2);
    boolean setVisibleInfoTableFlag = false;
    public MoleculePanel() {

    }



    public MoleculePanel(IAtomContainer atomContainer) {

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        informationTable = new JTable(informationTableModel);

        stucturePanel = new StructurePanel(atomContainer);
        this.add(stucturePanel);
        this.add(informationTable);
        if(setVisibleInfoTableFlag == false){

            informationTable.setVisible(false);
        }
    }


    public void draw(IAtomContainer atomContainer){
        stucturePanel.display(atomContainer);

    }
    public  JLabel createLabel(String title, String tooltip) {
        JLabel label = new JLabel(title);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setOpaque(true);
        label.setBackground(Color.blue);
        label.setForeground(Color.GREEN);
        label.setToolTipText(tooltip);
        return label;
    }


}

