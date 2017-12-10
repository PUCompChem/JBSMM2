package pu.reactor;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import pu.gui.utils.ReactionApplicationPanel;

public class AmbitSmirksGUI extends JFrame 
{	
	private static final long serialVersionUID = -9216569317812583387L;

	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() 
			{	
				try {
					AmbitSmirksGUI asg = new AmbitSmirksGUI();
					asg.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}					
			}
		});
	}
	
	public AmbitSmirksGUI() throws Exception 
	{
		super();
		initGUI();
	}
	
	private void initGUI() throws Exception
	{
		setSize(new Dimension(1100,795));
		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		revalidate();
		setTitle("Ambit SMIRKS");	
		setLayout(new FlowLayout());
		ReactionApplicationPanel rap = new ReactionApplicationPanel();
		rap.setPreferredSize(new Dimension(1100,760));
		add(rap);
	}

}
