package pu.test;

import javax.swing.JFrame;

import pu.gui.utils.GUIArranger;
import pu.gui.utils.GUIParser;

public class TestGUIArranger {

	public static void main(String[] args) 
	{
		testGUIArranger("split( h 30 Area1  split(v 70 Area4 split( h 50 Ar1 Ar2)  ) ) ");
		
		
		//testGUIArranger("split( h 50 split(v 30 Area1 Area2) split(v 30 Area3 Area4) )  ");
	}
	
	public static void testGUIArranger(String arrangerCode)
	{
		System.out.println("Parsing arranger: " + arrangerCode);
		GUIParser parser = new GUIParser(); 
		GUIArranger arranger = parser.parse(arrangerCode);
		
		if (arranger == null)
		{
			System.out.println("Arranger parsing errors(" + parser.getErrors().size()+"):");
			
			for (String s : parser.getErrors())
				System.out.println(s);
			return;
		}
		
		System.out.println("Arranger to code: " + arranger.getRootNode().generateNodeCode());
		
		
		JFrame vFrame = new JFrame("JSplitPane Sample");
		vFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		vFrame.setSize(1800, 800);		
		arranger.setFrame(vFrame);
		vFrame.setVisible(true);
		arranger.setSpliterPositions();
	}

}
