package pu.reactor.workspace.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.*;

import pu.reactor.workspace.Preferences;

public class PreferencesWindow extends JFrame {
	 
	Preferences preferences; 
	
	JPanel reactionDBPathPanel;
	private JTextField reactionDBPathField; 
	private JLabel reactionDBPathLabel;
	
	private JTextField startingMaterialsPathField;	
	
	private JButton button;
	private JCheckBox JcheckBoxText;
	/*
	 * Constructor
	 * @param no parameters
	 */
	public PreferencesWindow() { 
		this.preferences = new Preferences();  
		
		reactionDBPathPanel = new JPanel(new FlowLayout(SwingConstants.LEADING, 10, 10));
		reactionDBPathPanel.setBounds(5, 5, 280, 50);
		add(reactionDBPathPanel);
		
		reactionDBPathLabel = new JLabel("reaction database path:");
		reactionDBPathField = new JTextField(30);
	 
		reactionDBPathPanel.add(reactionDBPathLabel,BorderLayout.NORTH);
		reactionDBPathPanel.add(reactionDBPathField,BorderLayout.SOUTH);
		 
		 
		 
		 
		
		
		 SettingApplyButton();
		 CreateCheckBoxTest();
	     setSize(500, 500);
	     setVisible(true);
	}
	
	
	/*
	 * create_and_set_apply_button
	 * @param no parameters 
	 * @return void;
	 */
	private void SettingApplyButton(){
		 JPanel applyButtonPanel = new JPanel();
		 applyButtonPanel.setMinimumSize(new Dimension(20,30));
 
		 button = new JButton("Apply");
	     applyButtonPanel.add(button);
	     add(applyButtonPanel);
	     button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e){
				System.out.println("Apply button was clicked"); 
			preferences.reactionDBPath = reactionDBPathField.getText();
			System.out.println(preferences.reactionDBPath);
			BufferedWriter bw  = null;
			try {
			FileWriter fw = new FileWriter("/TestJson.json");
			 bw = new BufferedWriter(fw);
				bw.write(preferences.toJsonString());
			}  
			catch (IOException ioe) {
				   ioe.printStackTrace();
			}
			finally{ 
			   try{
			      if(bw!=null)
				 bw.close();
			   }catch(Exception ex){
			       System.out.println("Error in closing the BufferedWriter"+ex);
			    }
			}
			}
	    	 
	     });
	}
	private void CreateCheckBoxTest(){
		JcheckBoxText = new JCheckBox("checkBoxTest");
		 add(JcheckBoxText,BorderLayout.PAGE_END);
		 JcheckBoxText.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {         
	           if(e.getStateChange()==1){
	        	   preferences.checkBoxTest = true;
	           }else{
	        	   preferences.checkBoxTest = false;
	           }
	         }           
	      });
	}
	
	
// statusLabel.setText("Apple Checkbox: " 
//+ (e.getStateChange()==1?"checked":"unchecked"));
	

}
