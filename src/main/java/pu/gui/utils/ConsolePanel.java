package pu.gui.utils;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ConsolePanel extends JPanel
{	
	private static final long serialVersionUID = -8648685179926331982L;
	JTextPane textPane = new  JTextPane();

	public ConsolePanel()
	{
		initGUI();
	}
	
	private void initGUI()
	{
		//textArea.setLineWrap(true);
		//textArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(textPane);

		this.setLayout( new BorderLayout());

		this.add(scrollPane, BorderLayout.CENTER );

	}

	public  void print(String input){
		appendToPane(textPane, input, Color.BLACK);
	}
	public  void print(String input, Color textColor){
	appendToPane(textPane, input, textColor);

}
	public  void println(String input){
		appendToPane(textPane, input+"\n", Color.black);
	}
	public  void println(String input, Color textColor){
	appendToPane(textPane, input+"\n", textColor);

	}

	public void clearConsole(){
		textPane.setText("");
	}

	private void appendToPane(JTextPane tp, String msg, Color c)
	{
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(aset, false);
		tp.replaceSelection(msg);
	}

}
