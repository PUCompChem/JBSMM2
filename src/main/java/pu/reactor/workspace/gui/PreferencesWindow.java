package pu.reactor.workspace.gui;

import pu.reactor.workspace.Preferences;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PreferencesWindow extends JFrame 
{

	private Preferences preferences;
	private String preferencesFilePath = null;
	

	JPanel quitAndSavePanel;
	JPanel treePanel;
	JPanel optionsMenuPanel;
	JPanel menusCards;
	
	
	JTree tree;
	JTextField reactionDBPathField;
	JTextField startingMaterialsDBPathField;
	
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
	
	public PreferencesWindow(Preferences prefs, String preferencesFilePath) { 
		this.preferences = prefs; 
		this.preferencesFilePath = preferencesFilePath;
		initGUI();
		fillGUIComponentsData();
	}
	
	
	void initGUI()
	{
		
		
		//Setting Tree
		  treePanel = new JPanel(new BorderLayout(1,2));
		  treePanel.setBackground(Color.WHITE);
		   createTreeTable();
		  treePanel.add(tree);
		  treePanel.setPreferredSize(new Dimension(200, 0));

		  //Set MenusCards
		  menusCards = new JPanel(new CardLayout());
		  
		  
		//Setting OptionsMenu
		  reactionDBPathField = new JTextField(20);
		  startingMaterialsDBPathField = new JTextField(20);
		  optionsMenuPanel = new JPanel(new FlowLayout(20,20,20)); 
		  optionsMenuPanel.setBackground(Color.WHITE);
		  optionsMenuPanel.add(reactionDBPathField);
		  optionsMenuPanel.add(startingMaterialsDBPathField);
		  menusCards.add(optionsMenuPanel);
		  
		  //Setting TestDialogPanel
		  JPanel testPanel = new JPanel();
		  testPanel.add(new JTextField(20));
		  menusCards.add(testPanel,"Card with JButtons");
		 
		  
		  //Setting QuitAndSaveMenu
		  applyButton = setApplyButton();
		  okButton = setOKButton();
		  cancelButton = setCancelButton();
		  
		  quitAndSavePanel = new JPanel(new FlowLayout(20,20,20)); 
		  
		  quitAndSavePanel.add(applyButton);
		  quitAndSavePanel.add(okButton);
		  quitAndSavePanel.add(cancelButton);
		  
		  
		//Main Frame settings
		  
		 
		setLayout(new BorderLayout(100,100));
		add(menusCards, BorderLayout.CENTER);
		add(treePanel,BorderLayout.WEST);
		add(quitAndSavePanel,BorderLayout.SOUTH);
		






	      this.setSize(500, 500);
	      this.setVisible(true);
	      this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);   //Disable exit command

	}

	void fillGUIComponentsData()
	{
		reactionDBPathField.setText(preferences.reactionDBPath);
		startingMaterialsDBPathField.setText(preferences.startingMaterialsPath);
	}

	
	/*
	 * create_and_set_apply_button
	 * @param no parameters 
	 * @return void;
	 */
	private JButton setApplyButton(){
		 
		 JButton button = new JButton("Apply");
		 JPanel applyButtonPanel = new JPanel(); 
	     applyButtonPanel.add(button, BorderLayout.WEST);
	     add(applyButtonPanel);
	     button.addActionListener(new ActionListener() {

	    	 @Override
	    	 public void actionPerformed(ActionEvent e){
	    		 System.out.println("Apply button was clicked"); 
	    		 preferences.reactionDBPath = reactionDBPathField.getText();
	    		 preferences.startingMaterialsPath = startingMaterialsDBPathField.getText();


	    		 if (preferencesFilePath != null)
	    		 {
	    			 BufferedWriter bw  = null;
	    			 try {
	    				 FileWriter fw = new FileWriter(preferencesFilePath);
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
	    	 }

	     });
	     return button;
	}
	private JButton setCancelButton(){
		JButton button = new JButton("Cancel");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				setVisible(false);
			}
			
		});
		return button;
		
	};
	private JButton setOKButton(){
		
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

	
	private void createTreeTable(){
		 DefaultMutableTreeNode top = new DefaultMutableTreeNode("root");
		 
		 DefaultMutableTreeNode a = new DefaultMutableTreeNode("Paths");
		    top.add(a);
		  

		    DefaultMutableTreeNode b = new DefaultMutableTreeNode("Whatever");
		    top.add(b);
		  
		    tree = new JTree(top);
		    tree.setRootVisible(false);
		  
		    
	}
	private void showSpecificMenu(int n){
		
	}

	 

}
