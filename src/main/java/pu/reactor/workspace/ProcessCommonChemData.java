package pu.reactor.workspace;

import java.util.List;

import ambit2.base.data.StructureRecord;
import ambit2.reactions.ReactionDataBase;
import ambit2.reactions.retrosynth.StartingMaterialsDataBase;

public class ProcessCommonChemData 
{
	ReactionDataBase reactionDB = null;
	StartingMaterialsDataBase startingMaterialsDataBase = null;
	List<StructureRecord> structureRecords = null;
	
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

	public List<StructureRecord> getStructureRecords() {
		return structureRecords;
	}

	public void setStructureRecords(List<StructureRecord> structureRecords) {
		this.structureRecords = structureRecords;
	}
}
