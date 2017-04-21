package pu.reactor.json;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import pu.gui.utils.GUIArea;
import pu.gui.utils.GUIBinNode;
import pu.helpers.json.JsonUtilities;
import pu.reactor.workspace.Workspace;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WorkspaceJsonParser 
{
	
	List<String> errors = new ArrayList<String>();
	JsonUtilities jsonUtils = new JsonUtilities();
	JsonNode root = null;
	
	public Workspace loadFromJSON(File jsonFile) throws Exception
	{
		FileInputStream fin = new FileInputStream(jsonFile); 
		ObjectMapper mapper = new ObjectMapper();
		
		
		try {
			root = mapper.readTree(fin);
		} catch (Exception x) {
			throw x;
		} finally {
			try {fin.close();} catch (Exception x) {}	
		}
		
		Workspace workspace = new Workspace();		
		readGUIAreas(workspace);
		
		return workspace;
	}
	
	void readGUIAreas(Workspace workspace)
	{
		
		JsonNode areaNode = root.path("GUI_AREAS");
		if (areaNode.isMissingNode())
		{	
			errors.add("JSON Section \"DATA_ACCESS\" is missing!");
			return;
		}
		else
		{	
			workspace.rootAreaNode = new GUIBinNode();
			readAreaNode(workspace.rootAreaNode, areaNode);
		}	
		
		//Read field TEST
		if (!areaNode.path("TEST").isMissingNode())
		{	
			Boolean b = jsonUtils.extractBooleanKeyword(areaNode, "TEST", false);
			if (b == null)
				errors.add(jsonUtils.getError());
			else
			{	
				workspace.test = b;
			}
		}
		else
		{
			//error
		}
		
	}
	
	public void readAreaNode(GUIBinNode guiBinNode, JsonNode areaNode)
	{	
		//AREA_NAME
		if (!areaNode.path("AREA_NAME").isMissingNode())
		{	
			String name = jsonUtils.extractStringKeyword(areaNode, "AREA_NAME", false);
			if (name == null)
				errors.add("Incorrect AREA_NAME " + jsonUtils.getError());
			else
				guiBinNode.setName(name);
			System.out.println(">> " + name);
		}
		else
			guiBinNode.setName("No name");
		
		
		//IS_HORIZONTAL
		if (!areaNode.path("IS_HORIZONTAL").isMissingNode())
		{	
			Boolean b = jsonUtils.extractBooleanKeyword(areaNode, "IS_HORIZONTAL", false);
			if (b == null)
				errors.add("Incorrect IS_HORIZONTAL " + jsonUtils.getError());
			else
				guiBinNode.setHorizontal(b);
			
		}
		else
			guiBinNode.setHorizontal(true); //default IS_HORIZONTAL = true
		
		//RATIO
		if (!areaNode.path("RATIO").isMissingNode())
		{	
			Integer i = jsonUtils.extractIntKeyword(areaNode, "RATIO", false);
			//System.out.println("*** RATIO " + i);
			if (i == null)
				errors.add("Incorrect RATIO " + jsonUtils.getError());
			else
				guiBinNode.setRatio(i);
		}
		else
			guiBinNode.setRatio(50); //default RATIO = 50	
		
		//Handle CHILDREN
		JsonNode childrenNode = areaNode.path("CHILDREN");
		if (childrenNode.isMissingNode())
		{
			//This is a terminal node with associated GUIArea 
			GUIArea guiArea = new GUIArea();			
			guiBinNode.setGuiArea(guiArea);
		}
		else
		{
			//Set children nodes
			if (childrenNode.isArray())
			{
				if (childrenNode.size() != 2)
				{
					errors.add("Incorrect: field CHILDREN size is not 2!");
				}
				else
				{
					//Recursion: handling children nodes
					GUIBinNode child1 = new GUIBinNode(); 
					guiBinNode.setChild1(child1);
					readAreaNode(child1, childrenNode.get(0));
					
					GUIBinNode child2 = new GUIBinNode(); 
					guiBinNode.setChild2(child2);
					readAreaNode(child2, childrenNode.get(1));
				}
			}
			else
				errors.add("Incorrect: field CHILDREN is not an array!");
		}
		
		
	}
	
	public List<String> getErrors()
	{
		return errors;
	}
	
}
