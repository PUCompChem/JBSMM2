package pu.reactor.workspace;

import java.io.File;

import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.reactions.ReactionDataBase;
import ambit2.smarts.SmartsHelper;

public class ReactionSequenceProcess implements IProcess
{	
	String name = "new reaction sequence";
	String targetInputString = null;
	ReactionDataBase reactDB = null;
	IAtomContainer target = null;
	
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
	
	public ReactionDataBase getReactDB() {
		return reactDB;
	}

	public void setReactDB(ReactionDataBase reactDB) {
		this.reactDB = reactDB;
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

}
