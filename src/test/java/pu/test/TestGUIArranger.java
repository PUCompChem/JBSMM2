package pu.test;

import java.io.File;

import javax.swing.JFrame;

import pu.gui.utils.GUIArranger;
import pu.gui.utils.GUIParser;
import pu.reactor.json.WorkspaceJsonParser;
import pu.reactor.workspace.Workspace;

public class TestGUIArranger {

	public static void main(String[] args) throws Exception
	{
		//testGUIArranger("split( h 30 Area1  split(v 70 Area4 split( h 50 Ar1 Ar2)  ) ) ");
		
		
		//testGUIArranger("split( h 50 split(v 30 Area1 Area2) split(v 30 Area3 Area4) )  ");
		
		testGUIArrangerFromJson("/test-workspace.json");
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
		vFrame.setSize(1000, 600);		
		arranger.setFrame(vFrame);
		vFrame.setVisible(true);
		arranger.setSpliterPositions();
	}
	
	public static void testGUIArrangerFromJson(String worspaceJsonFile) throws Exception
	{
		WorkspaceJsonParser wsParser = new WorkspaceJsonParser();
		Workspace workspace = wsParser.loadFromJSON(new File(worspaceJsonFile));
		
		if (!wsParser.getErrors().isEmpty())
		{	
			System.out.println("Parsing errors");
			for (int i = 0; i < wsParser.getErrors().size(); i++)
				System.out.println(wsParser.getErrors().get(i));
		}		
		
		
		 
		GUIArranger arranger = new GUIArranger();
		arranger.setRootNode(workspace.rootAreaNode);		
		
		JFrame vFrame = new JFrame("Workspace from JSON Test");
		vFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		vFrame.setSize(1000, 600);		
		arranger.setFrame(vFrame);
		vFrame.setVisible(true);
		arranger.setSpliterPositions();
	}
	
	

}
