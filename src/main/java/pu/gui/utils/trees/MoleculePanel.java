package pu.gui.utils.trees;

import org.openscience.cdk.interfaces.IAtomContainer;
import pu.gui.utils.panels.*;

import javax.swing.*;
import java.awt.*;
/**
 * Created by gogo on 4.7.2017 г..
 */
public class MoleculePanel extends JPanel {

    StructurePanel stucturePanel;


    public MoleculePanel(IAtomContainer atomContainer) {

        stucturePanel = new StructurePanel(atomContainer);
        this.add(stucturePanel, BorderLayout.CENTER);


    }

    public MoleculePanel() {




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

