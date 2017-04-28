package pu.gui.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;
import javax.swing.AbstractButton;

import com.lowagie.text.Font;

public  class PreferenceWindow extends JPanel 
{
	  private static int numberOfCheckedBoxes = 0;
     
 
     public PreferenceWindow(JPanel panel) {
    	super();
    	
    }
    public static void createFrame()
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame frame = new JFrame("Preference");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                try 
                {
                   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                   e.printStackTrace();
                }
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setOpaque(true);
                panel.setLayout(new FlowLayout(FlowLayout.CENTER));
           
                frame.setSize(1000, 1000);

                // TestingStart
                JCheckBox checkBox1 = new JCheckBox("CheckBox1");  
                JCheckBox checkBox2 = new JCheckBox("CheckBox2");  
                JCheckBox checkBox3 = new JCheckBox("CheckBox3");   
               
              
                ActionListener actionListener = new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                      AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                      boolean selected = abstractButton.getModel().isSelected();
                      System.out.println(selected);
                      
                    }
                  };
                  checkBox3.addActionListener(actionListener);
                
                frame.add(checkBox1);
                frame.add(checkBox2);
                frame.add(checkBox3);
                panel.setVisible(true);
                frame.setVisible(true);
                frame.setResizable(false);
                
            }
        });
    }
  
}
