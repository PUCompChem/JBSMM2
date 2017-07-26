package pu.gui.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gogo on 29.6.2017 г..
 */
public class InfoPanel extends JPanel{

    private JTextArea textArea;
    public InfoPanel()
    {
        initGUI();
    }

    private void initGUI()
    {
        this.setLayout( new BorderLayout() );
        this.add( new JLabel( " Info:" ), BorderLayout.NORTH );
        textArea = new JTextArea(5, 20);

        textArea.setEditable(false);

        this.add(textArea, BorderLayout.CENTER);

    }
   public void Write(String input){
        textArea.append(input);
   }
   public void ClearText(){
       textArea.setText(null);
   }
}
