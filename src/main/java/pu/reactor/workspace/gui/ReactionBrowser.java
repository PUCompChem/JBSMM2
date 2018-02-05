package pu.reactor.workspace.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;


public class ReactionBrowser extends JFrame 
{
	private static final long serialVersionUID = 3734241163743648132L;
	
	ReactionBrowserPanel rbPanel = null;
	ReactionSequenceProcessPanel rspPanel = null;

	public ReactionBrowser(ReactionSequenceProcessPanel rspPanel) 
	{	
		this.rspPanel = rspPanel;
		initGUI();
	}
	
	private void initGUI() 
	{
		setLayout(new BorderLayout());
		setSize(new Dimension(800,600));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		rbPanel = new ReactionBrowserPanel();
		//rbPanel.setPreferredSize(new Dimension(1000,800));
		add(rbPanel, BorderLayout.CENTER);
	}
	
	

}
