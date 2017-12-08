package pu.reactor.workspace.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

import pu.gui.utils.chemtable.SmartChemTable;

public class ReactionBrowser extends JFrame 
{
		
	private static final long serialVersionUID = 7769828597142198311L;
	
	SmartChemTable chemTabl;
	
	public ReactionBrowser() 
	{

		//this.setLayout(new BorderLayout());
		initGUI();
		setSize(new Dimension(400,600));

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void initGUI() {
		
	}
	
	

}
