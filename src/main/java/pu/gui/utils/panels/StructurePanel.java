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
    
    public StructurePanel() {
    	initGUI();
    }	

    public StructurePanel(IAtomContainer atomContainer) {

    	initGUI();

        try {
            mol = atomContainer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        display(mol);
    }
    
    
    void initGUI()
    {
    	 this.setLayout(new BorderLayout());
         this.setBorder(BorderFactory.createLineBorder(Color.black));
         picturePanel = new Panel2D();

         picturePanel.setEditable(false);

         picturePanel.setBackground(new Color(255, 255, 255));
         this.add( picturePanel,BorderLayout.CENTER);
    }


    public void display(IAtomContainer ac) {
        if (picturePanel != null) {

            picturePanel.setAtomContainer(ac,true);
            picturePanel.setSelector(null);
        }

    }

}

