package pu.reactor;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import pu.gui.utils.ReactionApplicationPanel;

public class AmbitSmirksGUI extends JFrame 
{

	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() 
			{	
				try {
					AmbitSmirksGUI asg = new AmbitSmirksGUI();
					asg.setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
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
		setSize(new Dimension(1100,750));
		//setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		revalidate();
		setTitle("Ambit SMIRKS");	
		setLayout(new FlowLayout());
		ReactionApplicationPanel rap = new ReactionApplicationPanel();
		rap.setPreferredSize(new Dimension(1000,700));
		add(rap);
	}

}
