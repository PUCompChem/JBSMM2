package pu.reactor.workspace.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.JTree;
import javax.swing.SwingConstants;
import java.awt.event.*;

import pu.reactor.workspace.Preferences;

public class PreferencesWindow extends JFrame {
	 
	Preferences preferences; 
	
	JPanel mainPanel;
	
	JTree tree = new JTree();
	JPanel TreePanel = new JPanel();
	JPanel ToolsWindowPanel;
	private JTextField reactionDBPathField; 
	private JLabel reactionDBPathLabel;
	
	private JTextField startingMaterialsPathField;	
	
	private JButton button;
	private JButton OKbutton;
	private JCheckBox JcheckBoxText;
	
	/*
	 * Constructor
	 * @param no parameters
	 */
	public PreferencesWindow() { 
		this.preferences = new Preferences();  
		initGUI();
		fillGUIComponentsData();
	}
	
	public PreferencesWindow(Preferences prefs) { 
		this.preferences = prefs;  
		initGUI();
		fillGUIComponentsData();
	}
	
	
	void initGUI()
	{       
		BorderLayout layout = new BorderLayout(10,10);
		 
		JPanel saveAndExitPanel = new JPanel();
		add(tree);
		 mainPanel = new JPanel(layout);
		//Setting ToolsWindowPanel
		ToolsWindowPanel = new JPanel();
 	 	ToolsWindowPanel.setLayout(new BoxLayout(ToolsWindowPanel, BoxLayout.Y_AXIS));
 	 	ToolsWindowPanel.setBackground(Color.WHITE); 
 	 	TreePanel.add(tree);
		reactionDBPathLabel = new JLabel("reaction database path:");
		reactionDBPathField = new JTextField(30);
		startingMaterialsPathField = new JTextField(30);
		ToolsWindowPanel.add(reactionDBPathLabel,FlowLayout.LEFT);
		ToolsWindowPanel.add(reactionDBPathField,FlowLayout.LEFT);
		ToolsWindowPanel.add(startingMaterialsPathField);
		
		   button = SettingApplyButton(reactionDBPathField, preferences);
		   OKbutton  = settingOKButton(reactionDBPathField, preferences);
		   JcheckBoxText = CreateCheckBoxTest(preferences);
			
		   saveAndExitPanel.add(button);
		   saveAndExitPanel.add(OKbutton);
		   ToolsWindowPanel.add(JcheckBoxText);
		 mainPanel.add(TreePanel,BorderLayout.WEST);
		 mainPanel.add(ToolsWindowPanel, BorderLayout.EAST);
		 mainPanel.add(saveAndExitPanel, BorderLayout.SOUTH);
		 add(mainPanel);
	      setSize(500, 500);
	      setVisible(true);
	      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	      
	}
	
	void fillGUIComponentsData()
	{
		reactionDBPathField.setText(preferences.reactionDBPath);
		//TODO
	}
	
	
	/*
	 * create_and_set_apply_button
	 * @param no parameters 
	 * @return void;
	 */
	private JButton SettingApplyButton(JTextField reactionDBPathField, Preferences preferences){
		 
		 JButton button = new JButton("Apply");
		 JPanel applyButtonPanel = new JPanel(); 
	     applyButtonPanel.add(button, BorderLayout.WEST);
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
	     return button;
	}
	private JButton settingOKButton(JTextField reactionDBPathField, Preferences preferences){
		
		JButton button = new JButton("OK");
		 JPanel OKButtonPanel = new JPanel(); 
	     OKButtonPanel.add(button,BorderLayout.EAST);
	     add(OKButtonPanel);
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
			dispose();
			}
	    	
	     });
	return button;
	}
	private JCheckBox CreateCheckBoxTest(Preferences preferences){
		JCheckBox JcheckBoxText = new JCheckBox("checkBoxTest");
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
		 return JcheckBoxText;
	}
	
	private void CreateTreeTable(){
		
	}

	 

}
