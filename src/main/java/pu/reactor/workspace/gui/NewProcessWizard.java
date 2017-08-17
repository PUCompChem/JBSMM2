package pu.reactor.workspace.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by gogo on 28.7.2017 г..
 */
public class NewProcessWizard extends JFrame{
    private JPanel stageCards;
    private JPanel firstStepPanel;
    private JButton finishButton;
    public NewProcessWizard() {
        this.setLayout(new BorderLayout());
        initGUI();
        setSize(new Dimension(500,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initGUI() {


        //Set StageCards
        FinishButtonSet();
        FirstStepPanelSet();
        stageCards = new JPanel(new CardLayout());
        stageCards.add(firstStepPanel );

    }
private void FirstStepPanelSet(){
        firstStepPanel = new JPanel(new BorderLayout());

}
public void FinishButtonSet(){
    finishButton = new JButton("Finish");
    add(finishButton, BorderLayout.SOUTH);
    finishButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {

        }
    } );
}

}
