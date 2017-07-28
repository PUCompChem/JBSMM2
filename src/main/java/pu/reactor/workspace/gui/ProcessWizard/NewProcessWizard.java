package pu.reactor.workspace.gui.ProcessWizard;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gogo on 28.7.2017 г..
 */
public class NewProcessWizard extends JFrame{
    private JPanel stageCards;

    public NewProcessWizard() {
        this.setLayout(new CardLayout());
        initGUI();
        setSize(new Dimension(500,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initGUI() {

        //Set StageCards
        stageCards = new JPanel(new CardLayout());
        this.add(new JPanel());


    }


}
