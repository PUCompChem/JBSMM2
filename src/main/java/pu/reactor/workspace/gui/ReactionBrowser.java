package pu.reactor.workspace.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;


public class ReactionBrowser extends JFrame 
{
	private static final long serialVersionUID = 3734241163743648132L;
	
	ReactionBrowserPanel browser;

	public ReactionBrowser() 
	{	
		initGUI();
	}
	
	private void initGUI() 
	{
		setLayout(new BorderLayout());
		setSize(new Dimension(400,600));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
	}
	
	

}
