package jorotests.MoleculeVisualize;

import ambit2.smarts.SmartsHelper;
import org.openscience.cdk.interfaces.IAtomContainer;
import pu.gui.utils.trees.MoleculeSetTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by gogo on 30.1.2018 г..
 */
public class MainFrame extends JFrame {
    MoleculeSetTree tree;
    JPanel leftPanel = new JPanel();
    IAtomContainer atomContainer;
    MoleculePanel compoundPanel ;
    ArrayList<String> list;
    int i = 0;
    public MainFrame(final ArrayList<String> list) {
        try {
            atomContainer = SmartsHelper.getMoleculeFromSmiles("CCCCC");
        } catch (Exception e) {
            e.printStackTrace();
        }

        compoundPanel = new MoleculePanel(atomContainer);
        this.add(compoundPanel,BorderLayout.CENTER);
        this.setSize(new Dimension(1000, 700));
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("FromSmilesDB to Visualization");
        this.add(leftPanel, BorderLayout.WEST);
        this.list = list;

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));


        leftPanel.setSize(new Dimension(100, 700));
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        Button button = new Button("Next");
        leftPanel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    atomContainer = SmartsHelper.getMoleculeFromSmiles(list.get(i));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                compoundPanel.draw(atomContainer);

                    if(i<list.size()-1){
                        i++;
                    }
                    else{
                        i = 1;
                    }

            }
        });



    }
}