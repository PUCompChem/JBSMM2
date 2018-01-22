package pu.reactor.json;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
 



import pu.gui.utils.GUIBinNode;
import pu.helpers.json.JsonUtilities;
import pu.reactor.workspace.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PreferencesJsonParser{
	List<String> errors = new ArrayList<String>();
	JsonUtilities jsonUtils = new JsonUtilities();
	JsonNode root = null;
	
	public Preferences loadFromJSON(File jsonFile) throws Exception
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
		
		Preferences preferences = new Preferences();
		
		//REACTION_DB_PATH
		JsonNode node = root.path("REACTION_DB_PATH");
		if (node.isMissingNode())
		{	
			errors.add("JSON Section \"REACTION_DB_PATH\" is missing!");
		}
		else
		{	
			String s = jsonUtils.extractStringKeyword(root, "REACTION_DB_PATH", false);
			if (s == null)
				errors.add("Incorrect REACTION_DB_PATH " + jsonUtils.getError());
			else
				preferences.reactionDBPath = s;
		}
		
		//STARTING_MATERIALS_PATH
		node = root.path("STARTING_MATERIALS_PATH");
		if (node.isMissingNode())
		{	
			errors.add("JSON Section \"STARTING_MATERIALS_PATH\" is missing!");
		}
		else
		{	
			String s = jsonUtils.extractStringKeyword(root, "STARTING_MATERIALS_PATH", false);
			if (s == null)
				errors.add("Incorrect STARTING_MATERIALS_PATH " + jsonUtils.getError());
			else
				preferences.startingMaterialsPath = s;
		}
		
		//STARTING_MATERIALS_PATH2
		node = root.path("STARTING_MATERIALS_PATH2");
		if (node.isMissingNode())
		{	
		}
		else
		{	
			String s = jsonUtils.extractStringKeyword(root, "STARTING_MATERIALS_PATH2", false);
			if (s == null)
				errors.add("Incorrect STARTING_MATERIALS_PATH2 " + jsonUtils.getError());
			else
				preferences.startingMaterialsPath2 = s;
		}
		
				
		 //TODO
	   	return preferences;
	}
	 
	public List<String> getErrors()
	{
		return errors;
	}
	
	public String getAllErrorsAsString()
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < errors.size(); i++)
			sb.append(errors.get(i) + "\n");
		return sb.toString();
	}
	

	
	/*
	private void ReadPreferenceMenus(Preference preference) {
		JsonNode checkBoxNode = root.path("CheckBox3");
		if (checkBoxNode.isMissingNode())
		{	
			errors.add("JSON Section \"CheckBox\" is missing!");
			return;
		}
		else
		{	
			Boolean b = jsonUtils.extractBooleanKeyword(checkBoxNode,"check",false);
			preference.BoolValueCheckbox3 = b;
		}	
	}
	
	*/
}
 
