package pu.gui.utils;

import javax.swing.*;
import java.awt.*;

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
		this.add( new JLabel( "Work case panel:" ), BorderLayout.NORTH );
		this.add( new JTextArea(), BorderLayout.CENTER );
	}
	

}
