package jorotests.MoleculeVisualize;


import ambit2.ui.Panel2D;
import org.openscience.cdk.interfaces.IAtomContainer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by gogo on 1.2.2018 г..
 */
public class StructurePanel extends JPanel{


    protected Panel2D picturePanel = null;

    IAtomContainer mol;

    public StructurePanel(IAtomContainer atomContainer) {

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        picturePanel = new Panel2D();

        picturePanel.setEditable(false);

        picturePanel.setBackground(new Color(255, 255, 255));
        this.add( picturePanel,BorderLayout.CENTER);

        try {
            mol = atomContainer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        display(mol);

    }


    protected void display(IAtomContainer ac) {
        if (picturePanel != null) {

            picturePanel.setAtomContainer(ac,true);
            picturePanel.setSelector(null);

        }

    }

}
