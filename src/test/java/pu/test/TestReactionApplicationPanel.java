package pu.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import pu.gui.utils.ReactionApplicationPanel;
import pu.reactor.ReactorMainFrame;

public class TestReactionApplicationPanel extends JFrame
{
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() 
			{		
				
				try {
					TestReactionApplicationPanel testFrame = new TestReactionApplicationPanel();
					testFrame.setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
		});

	}
	
	public TestReactionApplicationPanel() throws Exception 
	{
		super();
		initGUI();
	}
	
	private void initGUI() throws Exception
	{
		setSize(new Dimension(400,600));

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		revalidate();
		setTitle("Test ReactionApplicationPanel");	
		setLayout(new FlowLayout());
		add(new ReactionApplicationPanel());
		
	}
}
