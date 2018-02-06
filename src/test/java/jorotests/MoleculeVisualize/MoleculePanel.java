package jorotests.MoleculeVisualize;

import org.openscience.cdk.interfaces.IAtomContainer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gogo on 6.2.2018 г..
 */
public class MoleculePanel extends JPanel {

    StructurePanel stucturePanel;


    public MoleculePanel(IAtomContainer atomContainer) {

        stucturePanel = new StructurePanel(atomContainer);
        this.add(stucturePanel,BorderLayout.CENTER);


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
