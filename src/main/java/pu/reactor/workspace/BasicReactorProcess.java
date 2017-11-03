package pu.reactor.workspace;

import ambit2.base.data.StructureRecord;
import ambit2.reactions.ReactionDataBase;
import ambit2.reactions.reactor.Reactor;
import ambit2.reactions.reactor.ReactorNode;
import ambit2.reactions.reactor.ReactorStrategy;
import ambit2.smarts.SmartsHelper;
import pu.reactor.workspace.gui.BasicReactorProcessPanel;

import javax.swing.*;

import org.openscience.cdk.interfaces.IAtomContainer;

import java.io.File;
import java.util.List;

/**
 * Created by gogo on 22.8.2017 г..
 */
public class BasicReactorProcess implements IProcess 
{
    //public BasicReactorProcessPanel panel = null;
    public String name = "new process";
    
    public Reactor reactor = new Reactor();
    public ReactorStrategy strategy =  new ReactorStrategy();
    public ReactionDataBase reactDB = null;
    String targetInputString = null;
    IAtomContainer target = null;
    public int stepSize = 10;

    StructureRecord currentStructure = new StructureRecord();
    
    public void setTargetInputString(String targetInputString)
    {
    	this.targetInputString = targetInputString;
    	try
    	{
    		target = SmartsHelper.getMoleculeFromSmiles(targetInputString);
    	}
    	catch (Exception x)
    	{}
    }
    
    public String getTargetInputString()
    {
    	return targetInputString;
    }
    
    public IAtomContainer getTarget() {
		return target;
	}

	public void setTarget(IAtomContainer target) {
		this.target = target;
	}

	public ReactorStrategy getStrategy() {
        return strategy;
    }

    public ReactionDataBase getReactDB() {
		return reactDB;
	}

	public void setReactDB(ReactionDataBase reactDB) {
		this.reactDB = reactDB;
	}

	public void setStrategy(ReactorStrategy strategy) {
        this.strategy = strategy;
    }
    
    

    public BasicReactorProcess() throws Exception {
    	
    }
    

    @Override
    public String toJsonString() {
        return null;
    }

    @Override
    public void loadFromJson(File file) {

    }

    @Override
    public void initProcess() throws Exception
    {
    	reactDB.configureReactions(reactor.getSMIRKSManager());
		reactor.setReactionDataBase(reactDB);
		reactor.setStrategy(strategy);
		reactor.initializeReactor(target); //TODO
    }

    @Override
    public void runProcess() 
    {
    	
    }

    @Override
    public void runProcessNextSteps(int nSteps) throws Exception 
    {
    	
    	try {
			List<ReactorNode> nodes = reactor.reactNext(nSteps);
			System.out.println("--Handled " + nodes.size() + " nodes");
			
			//TODO
			//put info into: (1) Console (2) SmartChemTable (3) StrutureTable
			
			
		} catch (Exception e) {
			
		}
    }
    
    public void runProcessNextSteps() throws Exception 
    {

    	runProcessNextSteps(stepSize);
    }

	@Override
	public String getName() {
		return name;
	}
	
}
