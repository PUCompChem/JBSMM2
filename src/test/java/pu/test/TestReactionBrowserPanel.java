package pu.test;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import pu.reactor.workspace.gui.ReactionBrowserPanel;


public class TestReactionBrowserPanel extends JFrame
{

	private static final long serialVersionUID = 8249049020022972371L;

	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() 
			{	
				try {
					TestReactionBrowserPanel testFrame = new TestReactionBrowserPanel();
					testFrame.setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
		});
	}
	
	public TestReactionBrowserPanel() throws Exception
	{
		initGUI();
	}
	
	private void initGUI() throws Exception
	{
		setSize(new Dimension(1000,700));
		//setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		revalidate();
		setTitle("Test ReactionApplicationPanel");	
		setLayout(new FlowLayout());
		ReactionBrowserPanel rbp = new ReactionBrowserPanel();
		rbp.setPreferredSize(new Dimension(1000,700));
		add(rbp);
		
		
	}

}
