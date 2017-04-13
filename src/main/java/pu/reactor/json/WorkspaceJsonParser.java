package pu.reactor.json;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

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
			if (i == null)
				errors.add("Incorrect RATIOL " + jsonUtils.getError());
			else
				guiBinNode.setRatio(i);
		}
		else
			guiBinNode.setRatio(50); //default RATIO = 50	
				
		
	}
	
	
	
	
}
