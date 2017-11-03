package pu.reactor.workspace;

import ambit2.reactions.ReactionDataBase;
import ambit2.reactions.retrosynth.StartingMaterialsDataBase;

public class ProcessCommonChemData 
{
	ReactionDataBase reactionDB = null;
	StartingMaterialsDataBase startingMaterialsDataBase = null;
	
	public ReactionDataBase getReactionDB() {
		return reactionDB;
	}
	
	public void setReactionDB(ReactionDataBase reactionDB) {
		this.reactionDB = reactionDB;
	}
	
	public StartingMaterialsDataBase getStartingMaterialsDataBase() {
		return startingMaterialsDataBase;
	}
	
	public void setStartingMaterialsDataBase(StartingMaterialsDataBase startingMaterialsDataBase) {
		this.startingMaterialsDataBase = startingMaterialsDataBase;
	}
}
