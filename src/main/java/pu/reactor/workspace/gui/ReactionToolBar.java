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
    private JButton resetButton;
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
        resetButton();
        comboBox.setSize(new Dimension(28,25));
        this.add(comboBox);
        GoButton();
    }


    private void  StopButton(){
        stopButton = new JButton();
        stopButton.setToolTipText("stop");
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
        startButton.setToolTipText("start");
        try {
            Image img = ImageIO.read( ClassLoader.getSystemResource("pu/images/play.png"));
            startButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        ReactionToolBar.this.add(startButton, BorderLayout.EAST);
    }
    
    
    public JButton getStartButton() {
		return startButton;
	}

	public void setStartButton(JButton startButton) {
		this.startButton = startButton;
	}

	public JButton getStopButton() {
		return stopButton;
	}

	public void setStopButton(JButton stopButton) {
		this.stopButton = stopButton;
	}

	public JButton getResetButton() {
		return resetButton;
	}

	public void setResetButton(JButton resetButton) {
		this.resetButton = resetButton;
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
        nextButton.setToolTipText("next");
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
        goButton.setToolTipText("Go");
        ReactionToolBar.this.add(goButton, BorderLayout.EAST);

    }
    private void resetButton(){
        resetButton = new JButton("reset");
        resetButton.setToolTipText("reset");
        ReactionToolBar.this.add(resetButton, BorderLayout.EAST);
        try {

            Image img = ImageIO.read(ClassLoader.getSystemResource("pu/images/reset.png"));

            resetButton.setIcon(new ImageIcon(img));

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}