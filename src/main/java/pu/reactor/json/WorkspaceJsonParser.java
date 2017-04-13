package pu.reactor.json;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import pu.helpers.json.JsonUtilities;
import pu.reactor.workspace.Workspace;
import ambit2.base.json.JSONUtils;

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
			readAreaNode(workspace, areaNode);
		
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
	
	public void readAreaNode(Workspace workspace, JsonNode areaNode)
	{
		//TODO
	}
	
	
	
	
}
