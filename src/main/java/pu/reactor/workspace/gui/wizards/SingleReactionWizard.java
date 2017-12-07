package pu.reactor.workspace.gui.wizards;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by gogo on 3.11.2017 г..
 */
public class SingleReactionWizard extends ProcessWizard{

    public SingleReactionWizard() {
        initGUI();

        JLabel smilesInputLabel = new JLabel("Molecule (Smiles/InChI):");
        smilesInputLabel.setBorder(new EmptyBorder(0, 0, 0, 45));
        smilesField = new JTextField(30);
        firstStepPanel.add(smilesInputLabel, BorderLayout.WEST);
        firstStepPanel.add(smilesField);


        JLabel processNameLabel = new JLabel("Process Name:");
        processNameField = new JTextField(30);
        processNameField.setText("New Process");
        firstStepPanel.add( processNameLabel );
        firstStepPanel.add(processNameField);


    }

}
