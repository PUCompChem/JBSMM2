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

        comboBox = new JComboBox();
        comboBox.setEditable(true);

        StartButton();
        NextButton();
        StopButton();
        comboBox.setSize(new Dimension(28,25));
        this.add(comboBox);
        GoButton();
    }


    private void  StopButton(){
        stopButton = new JButton();
        ReactionToolBar.this.add(stopButton, BorderLayout.EAST);
        try {

            Image img = ImageIO.read(ClassLoader.getSystemResource("pu/images/stop.png"));

            stopButton.setIcon(new ImageIcon(img));

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
    private void StartButton() {

        startButton = new JButton();
        try {
            Image img = ImageIO.read( ClassLoader.getSystemResource("pu/images/play.png"));
            startButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        ReactionToolBar.this.add(startButton, BorderLayout.EAST);
    }

    public JButton getGoButton() {
        return goButton;
    }

    public void setGoButton(JButton goButton) {
        this.goButton = goButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public void setNextButton(JButton nextButton) {
        this.nextButton = nextButton;
    }

    private void NextButton() {
        nextButton = new JButton();
        try {
            Image img = ImageIO.read( ClassLoader.getSystemResource("pu/images/next.png"));
            nextButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        ReactionToolBar.this.add(nextButton, BorderLayout.EAST);
    }

    private void GoButton(){
        goButton = new JButton("Go");
        ReactionToolBar.this.add(goButton, BorderLayout.EAST);

    }

}