package pu.reactor.workspace.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

/**
 * Created by gogo on 28.7.2017 г..
 */
public class NewProcessWizard extends JFrame{
    private JPanel stageCards;
    private JPanel firstStepPanel;
    private JPanel seconStepPanel = new JPanel();
    private JButton finishButton;
    private JButton previousButton;
    private JButton nextButtonSet;

    private JPanel buttonsPanel = new JPanel(new FlowLayout());
    CardLayout cardLayout =  new CardLayout();
    public NewProcessWizard() {
        this.setLayout(new BorderLayout());
        initGUI();
        setSize(new Dimension(500,600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initGUI() {


        this.add(buttonsPanel,BorderLayout.SOUTH);
        //Set StageCards

        PreviousButtonSet();
        NextButtonSet();
        FinishButtonSet();
        FirstStepPanelSet();
        SecondStepPanelSet();

        stageCards = new JPanel(cardLayout);

        stageCards.add(firstStepPanel,"firstStep");
        stageCards.add(seconStepPanel,"secondStep");
        cardLayout.first(stageCards);

        this.add(stageCards,BorderLayout.CENTER);
        this.setVisible(true);

    }
private void FirstStepPanelSet(){
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(3);
        firstStepPanel = new JPanel(flowLayout);
        firstStepPanel.setBorder(new EmptyBorder(50, 10, 10, 10));


    JLabel smilesInputLabel = new JLabel("Smiles:");
    JTextField smilesField = new JTextField(30);
    firstStepPanel.add(smilesInputLabel, BorderLayout.WEST);
    firstStepPanel.add(smilesField);


    JLabel processNameLabel = new JLabel("Process Name:");
    JTextField ProcessNameField = new JTextField(30);
    firstStepPanel.add( processNameLabel );
    firstStepPanel.add(ProcessNameField );

    JLabel loadStrategyLabel = new JLabel("Strategy Path:");
    JTextField loadStrategyField = new JTextField(30);
    firstStepPanel.add( loadStrategyLabel );
    firstStepPanel.add( loadStrategyField );
}
    private void SecondStepPanelSet(){
        seconStepPanel = new JPanel();
        JLabel label = new JLabel ("Advanced Features");
       seconStepPanel.add(label);

    }



    public void FinishButtonSet(){
    finishButton = new JButton("Finish");
    buttonsPanel.add(finishButton);
    finishButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {

        }
    } );
}

    public void PreviousButtonSet(){
        previousButton = new JButton("Previous");
        buttonsPanel.add(previousButton);
        previousButton.addActionListener(new ActionListener() {
            public  void actionPerformed(ActionEvent e) {
                cardLayout.previous(stageCards);
            }
        } );
    }
    public void NextButtonSet(){
        nextButtonSet = new JButton("Next");
        buttonsPanel.add(nextButtonSet);
        nextButtonSet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                cardLayout.next(stageCards);

            }
        } );
    }
}
