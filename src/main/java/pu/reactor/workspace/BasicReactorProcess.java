package pu.reactor.workspace;

import ambit2.base.data.StructureRecord;
import ambit2.reactions.ReactionDataBase;
import ambit2.reactions.reactor.Reactor;
import ambit2.reactions.reactor.ReactorNode;
import ambit2.reactions.reactor.ReactorStrategy;
import pu.reactor.workspace.gui.BasicReactorProcessPanel;

import javax.swing.*;

import org.openscience.cdk.interfaces.IAtomContainer;

import java.io.File;
import java.util.List;

/**
 * Created by gogo on 22.8.2017 г..
 */
public class BasicReactorProcess implements IProcess {
    BasicReactorProcessPanel panel = new BasicReactorProcessPanel(this);

    Reactor reactor = new Reactor();
    ReactorStrategy strategy =  new ReactorStrategy();
    ReactionDataBase reactDB = null;
    IAtomContainer target = null;

    StructureRecord currentStructure = new StructureRecord();
    
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
    public JPanel getPanel() {
        return this.panel;
    }

    @Override
    public void setPanel(JPanel panel) {
        if(panel instanceof BasicReactorProcessPanel){
            this.panel = (BasicReactorProcessPanel)panel;
        }

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
    public void runProcessSteps(int nSteps) {
    	
    	try {
			List<ReactorNode> nodes = reactor.reactNext(nSteps);
			//TODO
			//put info into: (1) Console (2) SmartChemTable (3) StrutureTable
			
			
		} catch (Exception e) {
			
		}
    }
}
