package pu.reactor.workspace;

import pu.reactor.workspace.gui.PreferencesWindow;

public class Preferences 
{
	//Tab: Paths
	public  String reactionDBPath = "./reaction-database.json";
	public  String startingMaterialsPath = "./starting-materials.txt";


	//Tab: Appearance 
	public int strcctureSizeInTables = 200;


	//Tab: StrategiesDefaultValues

	public double basicScoreWeight = 0.0;


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
		}
		
		if (startingMaterialsPath != null)
		{
			if (nFields > 0)
				sb.append("," + endLine);
			else
				sb.append(endLine);
			
			sb.append("\t\"STARTING_MATERIALS_PATH\" : " + "\"" + startingMaterialsPath + "\"");
			nFields++;
		}

		if (startingMaterialsPath != null)
		{

			if (nFields > 0){
				sb.append("," + endLine);
			}
			else {

			}
				sb.append(endLine);
			sb.append("\t\"DEFAULT_RETRO_STRATEGY\":");
			sb.append(endLine);
			sb.append("\t\t{");
			sb.append(endLine);
			sb.append("\t\t\t\"BASIC_SCORE_WEIGHT\" : "  + basicScoreWeight);
			sb.append(endLine);
			sb.append("\t\t}" + endLine);
			nFields++;
		}


		
		sb.append(endLine);
		sb.append("}" + endLine);
		return sb.toString();
	}

 
}
