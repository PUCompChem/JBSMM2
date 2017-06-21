package pu.reactor.workspace.gui;

import java.awt.*;
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


	private Preferences preferences;


	JPanel QuitAndSavePanel;
	JPanel treePanel;
	JPanel optionsMenuPanel;
	
	
	JTree tree;
	JTextField reactionDBPathField;
	
	JButton applyButton; 
	JButton okButton;
	JButton cancelButton;
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
		
		
		//Setting Tree
		  treePanel = new JPanel(new BorderLayout(1,2));
		  treePanel.setBackground(Color.WHITE);
		  tree = CreateTreeTable();
		  treePanel.add(tree);
		  treePanel.setPreferredSize(new Dimension(200, 0));

		//Setting OptionsMenu
		  reactionDBPathField = new JTextField(20);
		  optionsMenuPanel = new JPanel(new FlowLayout(20,20,20)); 
		  optionsMenuPanel.setBackground(Color.WHITE);
		  optionsMenuPanel.add(reactionDBPathField);
		 
		  
		  //Setting QuitAndSaveMenu
		  applyButton = SettingApplyButton(reactionDBPathField,preferences);
		  okButton = settingOKButton(reactionDBPathField,preferences);
		  cancelButton = settingCancelButton();
		  
		  QuitAndSavePanel = new JPanel(new FlowLayout(20,20,20)); 
		  
		  QuitAndSavePanel.add(applyButton);
		  QuitAndSavePanel.add(okButton);
		  QuitAndSavePanel.add(cancelButton);
		  
		  
		//Main Frame settings
		  
		 
		setLayout(new BorderLayout(100,100));
		add(optionsMenuPanel, BorderLayout.CENTER);
		add(treePanel,BorderLayout.WEST);
		add(QuitAndSavePanel,BorderLayout.SOUTH);
		






	      this.setSize(500, 500);
	      this.setVisible(true);
	      this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);   //Disable exit command

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
	private JButton settingCancelButton(){
		JButton button = new JButton("Cancel");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				setVisible(false);
			}
			
		});
		return button;
		
	};
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
	
	private JTree CreateTreeTable(){
		return new JTree();
	}

	 

}
