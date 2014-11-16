package pu.gui.utils;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ConsolePanel extends JPanel
{	
	private static final long serialVersionUID = -8648685179926331982L;
	
	public ConsolePanel()
	{
		initGUI();
	}
	
	private void initGUI()
	{
		this.setLayout( new BorderLayout() );		
		this.add( new JTextArea(), BorderLayout.CENTER );
	}

}
