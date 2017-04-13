package pu.reactor.json;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


import pu.helpers.json.JsonUtilities;
import pu.reactor.workspace.Process;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProcessJsonParser 
{
	List<String> errors = new ArrayList<String>();
	JsonUtilities jsonUtils = new JsonUtilities();
	JsonNode root = null;
	
	public Process loadFromJSON(File jsonFile) throws Exception
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
		
		Process process = new Process();
		//TODO
		return process;
	}	
}
