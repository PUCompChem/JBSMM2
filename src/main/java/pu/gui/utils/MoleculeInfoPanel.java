package pu.gui.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gogo on 4.7.2017 г..
 */
public class MoleculeInfoPanel extends JPanel{
    private JTextArea textArea;
    public MoleculeInfoPanel()
    {
        initGUI();
    }

    private void initGUI()
    {
        this.setLayout( new BorderLayout() );
        this.add( new JLabel( "MoleculeInfo:" ), BorderLayout.NORTH );
        textArea = new JTextArea(5, 20);

        textArea.setEditable(false);

        this.add(textArea, BorderLayout.CENTER);

    }
    public void WriteText(String input){
        textArea.append(input);
    }
    public void ClearText(){
        textArea.setText(null);
    }
}
