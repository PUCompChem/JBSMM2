package pu.gui.utils;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class WorkCasePanel extends JPanel
{
	
	private static final long serialVersionUID = -5689874597074779151L;

	public WorkCasePanel()
	{
		initGUI();
	}
	
	private void initGUI()
	{
		this.setLayout( new BorderLayout() );
		this.add( new JLabel( "Work case panle:" ), BorderLayout.NORTH );
		this.add( new JTextArea(), BorderLayout.CENTER );
	}
	

}
