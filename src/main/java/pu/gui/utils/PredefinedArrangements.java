package pu.gui.utils;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.BorderLayout;
import java.util.ArrayList;


public class PredefinedArrangements 
{
	public static final String arrangers[] = {
		"split(v 30 Area1 split(h 80 Area2 Area3))"
		
	};
	
	public static GUIArranger getStandardGUIArranger(int n)
	{
		if (n < 0 || n >= arrangers.length)
			return null;
		GUIParser parser = new GUIParser();
		GUIArranger arranger = parser.parse(arrangers[n]);
		return arranger;
	}
	
	/**
	 * 
	 * @param frame - target frame
	 * @param areas - container variable to be filled with JPanel objects
	 * @param splitter - container variable to be filled with JSplitPane objects
	 */
	public static void getPredifinedAreasForFrame01(JFrame frame, ArrayList<JPanel> areas, ArrayList<JSplitPane> splitters )
	{
		JSplitPane splitter1 = new JSplitPane();
		splitters.add(splitter1);
		splitter1.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitter1.setDividerLocation(250);
		
		JPanel panel1 = new JPanel();
		areas.add(panel1);
		
		splitter1.setLeftComponent(panel1);
		JSplitPane splitter2 = new JSplitPane();
		splitters.add(splitter2);
		splitter2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitter2.setDividerLocation(500);
		splitter1.setRightComponent(splitter2);
		JPanel panel2 = new JPanel();
		areas.add(panel2);
		JPanel panel3 = new JPanel();
		areas.add(panel3);
		splitter2.setTopComponent(panel2);
		splitter2.setBottomComponent(panel3);		
		
		frame.getContentPane().add(splitter1, BorderLayout.CENTER);
	}
	
	
}
