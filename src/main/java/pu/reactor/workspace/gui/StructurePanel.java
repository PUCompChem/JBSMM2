package pu.reactor.workspace.gui;

import ambit2.base.data.StructureRecord;
import ambit2.reactions.reactor.Reactor;
import pu.gui.utils.MoleculeDrawer;
import pu.reactor.workspace.BasicReactorProcess;


import javax.swing.*;
import java.awt.*;

/**
 * Created by gogo on 21.8.2017 г..
 */
public class StructurePanel extends JPanel {
    JPanel strucutre2dPanel = new JPanel();
    JPanel infoPanel = new JPanel();
    private StructureRecord currentStrucutre = new StructureRecord();





    BasicReactorProcess reactorProcess;

   BasicReactorParametersPanel parametersPanel;

    MoleculeDrawer drawer = new MoleculeDrawer();

    public StructurePanel(BasicReactorProcess reactorProcess){
        this.reactorProcess = reactorProcess;
        parametersPanel = new BasicReactorParametersPanel(this.reactorProcess.getStrategy());
        initGUI();
    }


    private void initGUI() {
        this.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();
        strucutre2dPanel.setLayout(new BorderLayout());
        drawer.add2DMolecule(strucutre2dPanel,currentStrucutre.getSmiles());

        cellsDraw(gc);

    }

    private void cellsDraw(GridBagConstraints gc) {


        gc.gridx=0;
        gc.gridy=1;
        gc.weightx = 1.5;
        gc.gridwidth = 1;
        JLabel col1 = new JLabel("strucutre2dPanel");
        col1.setBackground(Color.GRAY);
        col1.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(col1, gc);


        gc.gridx=1;
        gc.gridy=1;
        JLabel col2 = new JLabel("infoPanel");
        col2.setBackground(Color.GRAY);
        col2.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(col2, gc);

        gc.gridx=2;
        gc.gridy=1;
        JLabel col3 = new JLabel("parameters");
        col3.setBackground(Color.GRAY);
        col3.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(col3, gc);



        gc.fill = GridBagConstraints.BOTH;
        gc.gridx=0;
        gc.gridy=2;
        gc.weighty = 2.0;
        gc.gridwidth = 1;
        strucutre2dPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        strucutre2dPanel.setOpaque(true);
        this.add( strucutre2dPanel, gc);

        gc.gridx=1;
        gc.gridy=2;

        infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        infoPanel.setOpaque(true);

        this.add(infoPanel, gc);


        gc.gridx=2;
        gc.gridy=2;

        parametersPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        parametersPanel.setOpaque(true);

        this.add(parametersPanel, gc);

    }

}
