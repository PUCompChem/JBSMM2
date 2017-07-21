package pu.gui.utils;

import ambit2.smarts.SmartsHelper;
import ambit2.ui.Panel2D;
import org.openscience.cdk.interfaces.IAtomContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

/**
 * Created by gogo on 21.7.2017 г..
 */
public class MoleculeDrawing {
    ArrayList<Panel2D> p2dList = new ArrayList<Panel2D>();
    public void add2DMolecule(JPanel panel, String smiles) {
        try {
            IAtomContainer mol = SmartsHelper.getMoleculeFromSmiles(smiles);
            add2DMolecule(panel, mol);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void add2DMolecule(JPanel panel, IAtomContainer struct) {
        Panel2D p = new Panel2D();
        p.setAtomContainer(struct);
        p.setBorder(BorderFactory.createLineBorder(Color.green));

        p2dList.add(p);

        //

        panel.addComponentListener(new ComponentListener() {

            public void componentHidden(ComponentEvent arg0) {
                // TODO Auto-generated method stub

            }

            public void componentMoved(ComponentEvent arg0) {
                // TODO Auto-generated method stub

            }

            public void componentResized(ComponentEvent arg0) {
                // TODO Auto-generated method stub
                System.out.println("componentResized events");
                Panel2D p = p2dList.get(p2dList.size() - 1);
                p.setPreferredSize(arg0.getComponent().getSize());
            }

            public void componentShown(ComponentEvent arg0) {
                // TODO Auto-generated method stub

            }

        });

        panel.add(p);
        //p.setPreferredSize(panel.getSize());
        p.updateUI();

    }

}
