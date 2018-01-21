package pu.reactor.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.reactions.ReactionDataBase;
import ambit2.reactions.retrosynth.ReactionSequence;
import ambit2.reactions.retrosynth.SyntheticStrategy;
import ambit2.smarts.SMIRKSManager;
import ambit2.smarts.SmartsHelper;

public class ReactionSequenceProcess implements IProcess
{	
	String name = "new reaction sequence";
	String targetInputString = null;
	IAtomContainer target = null;
	ReactionDataBase reactDB = null;
	SyntheticStrategy strategy = null;
	ReactionSequence reactSeq = new ReactionSequence();
	Set<ReactionSequence> reactSeqVesrions = new HashSet<ReactionSequence>();
	
	public void setTargetInputString(String targetInputString)
	{
		this.targetInputString = targetInputString;
		try
		{
			target = SmartsHelper.getMoleculeFromSmiles(targetInputString,true);
		}
		catch (Exception x)
		{}
	}
	
	public String getTargetInputString() {
		return targetInputString;
	}
	
	public IAtomContainer getTarget() {
		return target;
	}

	public void setTarget(IAtomContainer target) {
		this.target = target;
	}

	public ReactionDataBase getReactDB() {
		return reactDB;
	}

	public void setReactDB(ReactionDataBase reactDB) {
		this.reactDB = reactDB;
	}
			
	public SyntheticStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(SyntheticStrategy strategy) {
		this.strategy = strategy;
	}

	public ReactionSequence getReactSeq() {
		return reactSeq;
	}

	public void setReactSeq(ReactionSequence reactSeq) {
		this.reactSeq = reactSeq;
	}

	public Set<ReactionSequence> getReactSeqVesrions() {
		return reactSeqVesrions;
	}

	public void setReactSeqVesrions(Set<ReactionSequence> reactSeqVesrions) {
		this.reactSeqVesrions = reactSeqVesrions;
	}

	@Override
	public String toJsonString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadFromJson(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {		
		return name;
	}
	
	public void setName(String name) {		
		this.name = name;
	}
	

	@Override
	public void initProcess() throws Exception {
				
		//Temporary test code
		List<String> smirks = new ArrayList<String>();
		smirks.add("[C:1]Cl>>[C:1]");		
		smirks.add("[H][C:1][C:2][H]>>[H][C:1][H].[H][C:2][H]");
		ReactionDataBase rdb = new ReactionDataBase(smirks);
		reactSeq.setReactDB(rdb);
		reactSeq.setTarget(target);
		
		//setup smrkMan
		SMIRKSManager smrkMan = reactSeq.getSmrkMan();
		smrkMan.setFlagProcessResultStructures(true);
		smrkMan.setFlagClearHybridizationBeforeResultProcess(true);
		smrkMan.setFlagClearImplicitHAtomsBeforeResultProcess(false);
		smrkMan.setFlagClearAromaticityBeforeResultProcess(true);
		smrkMan.setFlagAddImplicitHAtomsOnResultProcess(false);
		smrkMan.setFlagConvertAddedImplicitHToExplicitOnResultProcess(false);
		smrkMan.setFlagConvertExplicitHToImplicitOnResultProcess(false);
		smrkMan.setFlagApplyStereoTransformation(false);
		smrkMan.setFlagHAtomsTransformation(false);
		//smrkMan.setFlagHAtomsTransformationMode(FlagHAtomsTransformationMode);
		smrkMan.setFlagAromaticityTransformation(false);
	}

	@Override
	public void runProcess() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runProcessNextSteps(int nSteps) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runProcessNextSteps() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetProcess() throws Exception {
		// TODO Auto-generated method stub
	}

}
