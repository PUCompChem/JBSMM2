package pu.reactor.workspace.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

/**
 * Created by gogo on 24.7.2017 г..
 */
public class ReactionToolBar extends JToolBar {


    public JComboBox getComboBox() {
        return comboBox;
    }
    private JButton goButton;
    private JButton startButton;
    private JButton nextButton;
    private JButton stopButton;
    private JComboBox  comboBox;

    public JButton getGo() {
        return goButton;
    }

    public void setGo(JButton go) {
        this.goButton = go;
    }




    public ReactionToolBar() {
        initGUI();

    }

    private void initGUI() {
        GoButton();
        comboBox = new JComboBox();
        comboBox.setEditable(true);

        StartButton();
        NextButton();
        PreviousButton();
        this.add(comboBox,BorderLayout.CENTER);

    }


    private void  PreviousButton(){
        stopButton = new JButton();
        ReactionToolBar.this.add(stopButton, BorderLayout.EAST);
        try {
            Image img = ImageIO.read(getClass().getResource("pu/images/stop.jpg"));
            stopButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
    private void StartButton() {
        startButton = new JButton();
        ReactionToolBar.this.add(startButton, BorderLayout.EAST);
    }
    private void NextButton() {
        nextButton = new JButton();
        ReactionToolBar.this.add(nextButton, BorderLayout.EAST);
    }

    private void GoButton(){
            goButton = new JButton("Go");
            ReactionToolBar.this.add(goButton, BorderLayout.WEST);
    }

}
