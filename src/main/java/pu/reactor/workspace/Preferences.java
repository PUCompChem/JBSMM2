package pu.reactor.workspace;

import pu.reactor.workspace.gui.PreferencesWindow;

public class Preferences 
{
	public  String reactionDBPath = "./reaction-database.json";
	public  String startingMaterialsPath = "./starting-metarials.json";

	
	public PreferencesWindow createPreferencesWindow()
	{
		//TODO
		
		return null;
	}
	
	public String toJsonString()
	{	
		String endLine = "\n";
		StringBuffer sb = new StringBuffer();
		int nFields = 0;
		sb.append("{" + endLine);		
		
		if (reactionDBPath != null)
		{
			sb.append("\t\"REACTION_DB_PATH\" : " + "\"" + reactionDBPath + "\"");
			nFields++;

		

			if (nFields > 0)
				sb.append("," + endLine);
			else
				sb.append(endLine);
			
			sb.append("\t\"STARTING_MATERIALS_PATH\" : " + "\"" + startingMaterialsPath + "\"");
			nFields++;



		}
		sb.append(endLine);
		
		sb.append("}" + endLine);
		return sb.toString();
	}

 
}
