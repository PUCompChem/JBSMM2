package pu.reactor.json;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
 
import pu.helpers.json.JsonUtilities;
import pu.reactor.workspace.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PreferencesJsonParser{
	List<String> errors = new ArrayList<String>();
	JsonUtilities jsonUtils = new JsonUtilities();
	JsonNode root = null;
	
	public Preference loadFromJSON(File jsonFile) throws Exception
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
		
		 Preference reference = new Preference();
		
		 //Read reference json and set the preference class
		 ReadPreferenceMenus(reference);
	   	 return reference;
	}

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
}
 
