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
	public double classcScoreWeight = 0.0;
	public double transformScoreWeight = 0.0;
	public double conditionsScoreWeight = 0.0;
	public double experimentalConditionsScoreWeight = 0.0;
	public double yieldScoreWeight = 0.0;
	public double productComplexityWeight = 0.0;
	public double productSimilarityWeight = 0.0;
	public double productStabilityWeight = 0.0;
	public double reactionCenterPositionWeight = 0.0;
	public double reactionCenterComplexityWeight = 0.0;
	public double electronWithdrawingLevelWeight = 0.0;


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
			sb.append("\t\t\t\"BASIC_SCORE_WEIGHT\" : "  + basicScoreWeight+",");
			sb.append(endLine);
			sb.append("\t\t\t\"classcScoreWeight\" : "  + classcScoreWeight+",");
			sb.append(endLine);
			sb.append("\t\t\t\"transformScoreWeight\" : "  + transformScoreWeight+",");
			sb.append(endLine);
			sb.append("\t\t\t\"conditionsScoreWeight\" : "  + conditionsScoreWeight+",");
			sb.append(endLine);
			sb.append("\t\t\t\"experimentalConditionsScoreWeight\" : "  + experimentalConditionsScoreWeight+",");
			sb.append(endLine);
			sb.append("\t\t\t\"yieldScoreWeight\" : "  + yieldScoreWeight+",");
			sb.append(endLine);
			sb.append("\t\t\t\"productComplexityWeight\" : "  + productComplexityWeight+",");
			sb.append(endLine);
			sb.append("\t\t\t\"productSimilarityWeight\" : "  + productSimilarityWeight+",");
			sb.append(endLine);
			sb.append("\t\t\t\"productStabilityWeight\" : "  + productStabilityWeight+",");
			sb.append(endLine);
			sb.append("\t\t\t\"reactionCenterPositionWeight\" : "  + reactionCenterPositionWeight+",");
			sb.append(endLine);
			sb.append("\t\t\t\"reactionCenterComplexityWeight\" : "  + reactionCenterComplexityWeight+",");
			sb.append(endLine);
			sb.append("\t\t\t\"electronWithdrawingLevelWeight\" : "  + electronWithdrawingLevelWeight);
			sb.append(endLine);


			sb.append("\t\t}" + endLine);
			nFields++;
		}


		
		sb.append(endLine);
		sb.append("}" + endLine);
		return sb.toString();
	}

 
}
