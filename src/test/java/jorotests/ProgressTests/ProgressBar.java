package jorotests.ProgressTests;

import javax.swing.*;
import java.awt.*;



/**
 * Created by gogo on 13.3.2018 г..
 */
public class ProgressBar extends JFrame    {
    private JProgressBar progressBar;
    private double percentage;
    private String info;
    private JLabel label = new JLabel("Start");

    public ProgressBar() {


        getContentPane().setLayout(
                new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)
        );

        this.setSize(new Dimension(500, 100));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue((int) percentage);
        progressBar.setStringPainted(true);
        this.add(label);
        this.add(progressBar);
        this.setLocationRelativeTo(null);

        progressBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
    }


    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
        progressBar.setValue((int)percentage);

    }


    public String getInfo() {
        return info;
    }


    public void setInfo(String info) {
        this.info = info;
        label.setText(info);
    }

    }



