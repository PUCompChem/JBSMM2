package pu.reactor.json;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


import pu.helpers.json.JsonUtilities;
import pu.reactor.workspace.BasicReactorProcess;
import pu.reactor.workspace.Process;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BasicReactorJsonParser
{
	List<String> errors = new ArrayList<String>();
	JsonUtilities jsonUtils = new JsonUtilities();
	JsonNode root = null;
	
	public BasicReactorProcess loadFromJSON(File jsonFile) throws Exception
	{
		FileInputStream fin = new FileInputStream(jsonFile); 
		ObjectMapper mapper = new ObjectMapper();

		BasicReactorProcess process = new BasicReactorProcess();
		try {
			root = mapper.readTree(fin);
			JsonNode strategyNode = root.path("REACTOR_STRATEGY");

			if (strategyNode.isMissingNode())
			{
				errors.add("JSON Section \"REACTION_DB_PATH\" is missing!");
			}
			else
			{
				setStrategy(process, strategyNode);
			}
		} catch (Exception x) {
			throw x;
		} finally {
			try {fin.close();} catch (Exception x) {}	
		}
		

		//TODO
		return process;
	}
	public  void writeJsonOnFile(File jsonFile, BasicReactorProcess process){
		BufferedWriter bw  = null;
		try {
			FileWriter fw = new FileWriter(jsonFile);
			bw = new BufferedWriter(fw);
			bw.write(process.toJsonString());
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		finally{
			try{
				if(bw!=null)
					bw.close();
			}catch(Exception ex){
				System.out.println("Error in closing the BufferedWriter"+ex);
			}
		}
	}
	private void setStrategy(BasicReactorProcess process, JsonNode strategyNode){
		int maxNumberOfReactions = strategyNode.get("MAX_NUM_OF_REACTIONS").asInt();
		int maxLevel = strategyNode.get("MAX_LEVEL").asInt();
		process.strategy.maxNumOfReactions = maxNumberOfReactions;
		process.strategy.maxLevel = maxLevel;
	}
}
