package pu.reactor.workspace.gui.ReactionWizards;

import pu.gui.utils.ButtonTabComponent;
import pu.reactor.workspace.BasicReactorProcess;
import pu.reactor.workspace.gui.BasicReactorProcessPanel;
import pu.reactor.workspace.gui.ReactorProcessTabsSet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.sun.glass.ui.Cursor.setVisible;

/**
 * Created by gogo on 3.11.2017 г..
 */
public class ProcessWizard extends JFrame {
    private JPanel stageCards;
    private JPanel firstStepPanel;
    private JPanel seconStepPanel = new JPanel();
    private JButton applyButton;
    //private JButton previousButton;
    //private JButton nextButtonSet;

    JTextField smilesField;
    JTextField processNameField;

    private JPanel buttonsPanel = new JPanel(new FlowLayout());
    CardLayout cardLayout =  new CardLayout();
    private ReactorProcessTabsSet processTabs = null;
    private JButton cancelButton;




    private void initGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(400,600));
        this.setResizable(false);
        this.setSize(new Dimension(320,50));
        this.revalidate();
        this.add(buttonsPanel,BorderLayout.SOUTH);
        //Set StageCards
        this.setTitle("Basic Reactor Wizard");

        setCancelButton();
        ApplyButtonSet();
        FirstStepPanelSet();


        stageCards = new JPanel(cardLayout);

        stageCards.add(firstStepPanel,"firstStep");
        stageCards.add(seconStepPanel,"secondStep");
        cardLayout.first(stageCards);

        this.add(stageCards,BorderLayout.CENTER);
        this.setVisible(true);

    }
    private void FirstStepPanelSet() {
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(3);
        firstStepPanel = new JPanel(flowLayout);
        firstStepPanel.setSize(new Dimension(100, 100));

        firstStepPanel.setBorder(new EmptyBorder(50, 10, 10, 0));


        JLabel smilesInputLabel = new JLabel("Molecule (Smiles/InChI):");
        smilesInputLabel.setBorder(new EmptyBorder(0, 0, 0, 45));
        smilesField = new JTextField(30);
        firstStepPanel.add(smilesInputLabel, BorderLayout.WEST);
        firstStepPanel.add(smilesField);


        JLabel processNameLabel = new JLabel("Process Name:");
        processNameField = new JTextField(30);
        processNameField.setText("New Process");
        firstStepPanel.add(processNameLabel);
        firstStepPanel.add(processNameField);


	/*
	private void SecondStepPanelSet(){
		seconStepPanel = new JPanel();
		JLabel label = new JLabel ("Advanced Features");
		seconStepPanel.add(label);

	}
	*/
    }
ActionListener applyButtonActionListener;


    public void ApplyButtonSet(){
        applyButton = new JButton("Create Process");
        buttonsPanel.add(applyButton);
        applyButton.addActionListener(applyButtonActionListener);
    }


    private void setCancelButton(){
        cancelButton = new JButton("Cancel");
        buttonsPanel.add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
	/*
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
	*/

    public String SetFileChooser(JButton button){
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("D:"));
        fc.setDialogTitle("File Chooser");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setVisible(true);
        if (fc.showOpenDialog(button) == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getAbsolutePath();
        }
        else return "";
    }


}

