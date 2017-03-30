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
		
		
		return workspace;
	}
	
	void readGUIAreas(Workspace workspace)
	{
		
		JsonNode curNode = root.path("GUI_AREAS");
		if (curNode.isMissingNode())
		{	
			errors.add("JSON Section \"DATA_ACCESS\" is missing!");
			return;
		}
		
		//Read field TEST
		if (!curNode.path("TEST").isMissingNode())
		{	
			Boolean b = jsonUtils.extractBooleanKeyword(curNode, "TEST", false);
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
	
	
}
