package pu.gui.utils.panels;
import ambit2.ui.Panel2D;
import org.openscience.cdk.interfaces.IAtomContainer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gogo on 1.2.2018 г..
 */
public class StructurePanel extends JPanel{


    protected Panel2D picturePanel = null;
    IAtomContainer mol;

    public StructurePanel(IAtomContainer atomContainer) {


        this.setSize(new Dimension(100, 700));
        this.setBorder( BorderFactory.createLineBorder(Color.green));

        this.add(structurePanel(),BorderLayout.CENTER);

        try {
            mol = atomContainer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        display(mol);

    }
    public StructurePanel() {


        this.setSize(new Dimension(100, 700));
        this.setBorder( BorderFactory.createLineBorder(Color.green));

        this.add(structurePanel(),BorderLayout.CENTER);
    }
    protected JPanel structurePanel() {
        JPanel strucPanel = new JPanel();
        strucPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("<html><b>Structure diagram</b></html>");
        label.setOpaque(true);
        // label.setBackground(bgColor);
        //label.setForeground(fColor);
        label.setSize(120,32);
        label.setAlignmentX(CENTER_ALIGNMENT);
        // label.setBorder(BorderFactory.createMatteBorder(5,0,0,0,bgColor));
        strucPanel.add(label,BorderLayout.NORTH);

        picturePanel = new Panel2D();
        picturePanel.setEditable(false);
        // picturePanel.setBorder(BorderFactory.createLineBorder(fColor));
        //picturePanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        picturePanel.setBackground(new Color(255, 255, 255));
        strucPanel.add(picturePanel,BorderLayout.CENTER);
        return strucPanel;
    }
    public void display(IAtomContainer ac) {
        if (picturePanel != null) {

            picturePanel.setAtomContainer(ac,true);
            picturePanel.setSelector(null);

        }

    }

}
