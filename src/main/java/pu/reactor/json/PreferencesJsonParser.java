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
	List<String> warnings = new ArrayList<String>();
	
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
		


		//DEFAULT_RETRO_STRATEGY
		node = root.path("DEFAULT_RETRO_STRATEGY");
		if (node.isMissingNode())
		{
			warnings.add("JSON Section \"DEFAULT_RETRO_STRATEGY\" is missing!");
		}
		else
		{
			preferences.basicScoreWeight  =	jsonUtils.extractDoubleKeyword(node, "BASIC_SCORE_WEIGHT", false);
			preferences.classcScoreWeight  =	jsonUtils.extractDoubleKeyword(node, "classcScoreWeight", false);
			preferences.transformScoreWeight  =	jsonUtils.extractDoubleKeyword(node, "transformScoreWeight", false);
			preferences.conditionsScoreWeight  =	jsonUtils.extractDoubleKeyword(node, "conditionsScoreWeight", false);
			preferences.experimentalConditionsScoreWeight  =	jsonUtils.extractDoubleKeyword(node, "experimentalConditionsScoreWeight", false);
			preferences.yieldScoreWeight  =	jsonUtils.extractDoubleKeyword(node, "yieldScoreWeight", false);
			preferences.productComplexityWeight  =	jsonUtils.extractDoubleKeyword(node, "productComplexityWeight", false);
			preferences.productSimilarityWeight  =	jsonUtils.extractDoubleKeyword(node, "productSimilarityWeight", false);
			preferences.productStabilityWeight  =	jsonUtils.extractDoubleKeyword(node, "productStabilityWeight", false);
			preferences.reactionCenterPositionWeight  =	jsonUtils.extractDoubleKeyword(node, "reactionCenterPositionWeight", false);
			preferences.reactionCenterComplexityWeight  =	jsonUtils.extractDoubleKeyword(node, "reactionCenterComplexityWeight", false);
			preferences.electronWithdrawingLevelWeight  =	jsonUtils.extractDoubleKeyword(node, "electronWithdrawingLevelWeight", false);
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
	
	public List<String> getWarnings()
	{
		return warnings;
	}
	
	public String getAllWarningsAsString()
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < warnings.size(); i++)
			sb.append(warnings.get(i) + "\n");
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
 
