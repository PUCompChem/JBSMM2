package pu.gui.utils;

import java.awt.BorderLayout;

import javax.swing.*;

public class ConsolePanel extends JPanel
{	
	private static final long serialVersionUID = -8648685179926331982L;
	JTextArea textArea = new JTextArea();

	public ConsolePanel()
	{
		initGUI();
	}
	
	private void initGUI()
	{
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(textArea);

		this.setLayout( new BorderLayout());

		this.add(scrollPane, BorderLayout.CENTER );

	}

	public void Print(String input){
		textArea.append(input);
	}
	public void Println(String input){
		textArea.append(input+"\n");
	}
	public void clearConsole(){
		textArea.selectAll();
		textArea.replaceSelection("");

	}


}
