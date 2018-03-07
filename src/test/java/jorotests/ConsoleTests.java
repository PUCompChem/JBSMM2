package jorotests;

import pu.gui.utils.ConsolePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by gogo on 27.2.2018 г..
 */
public class ConsoleTests {
    public static void main(String[] args) {
        JFrame frame = new JFrame("ConsoleTest");
        frame.setVisible(true);
        frame.setSize(new Dimension(600, 500));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ConsolePanel console = new ConsolePanel();
        JButton button = new JButton("Clear console");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //console.ClearConsole();
            }
        });


        frame.add(console, BorderLayout.CENTER);
      frame.add(button,BorderLayout.SOUTH);
    }
}
