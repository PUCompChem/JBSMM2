package pu.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class TestJavaSplitter  
{

	public static void main(String[] args) 
	{
		test1();
	}


	public static void test1()
	{
		JFrame vFrame = new JFrame("JSplitPane Sample");
		vFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JSplitPane vSplitPane = new JSplitPane();
		vSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		vSplitPane.setDividerLocation(200);
		
		JPanel panel1 = createPanel01();
		vSplitPane.setLeftComponent(panel1);
		JPanel panel1_b = createPanel01();
		vSplitPane.setRightComponent(panel1_b);
		
		
		vFrame.getContentPane().add(vSplitPane, BorderLayout.CENTER);
		vFrame.setSize(800, 800);
		vFrame.setVisible(true);
		
		
		/*
		JFrame hFrame = new JFrame("JSplitPane Sample");
		JSplitPane hSplitPane = new JSplitPane();
		hFrame.getContentPane().add(hSplitPane, BorderLayout.CENTER);
		hFrame.setSize(300, 150);
		hFrame.setVisible(true);
		*/
	}
	
	public static JPanel createPanel01()
	{
		JPanel panel = new JPanel();
		panel.setLayout( new BorderLayout() );
        panel.setPreferredSize( new Dimension( 400, 100 ) );
        panel.setMinimumSize( new Dimension( 100, 50 ) );
        
		panel.add( new JLabel( "Notes:" ), BorderLayout.NORTH );
		panel.add( new JTextArea(), BorderLayout.CENTER );
		
		return panel;
	}

}
