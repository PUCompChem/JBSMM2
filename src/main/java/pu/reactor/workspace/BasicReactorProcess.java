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
	public List<ReactorNode> resultNodes = null;

	//StructureRecord currentStructure = new StructureRecord();

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

		String endLine = "\n";
		StringBuffer sb = new StringBuffer();
		int nFields = 0;
		sb.append("{" + endLine);
		String strategyJSON = strategy.toJSONString("\t");
		sb.append(strategyJSON);
		sb.append(endLine);

		sb.append("}" + endLine);
		return sb.toString();
	}

	@Override
	public void loadFromJson(File file) {

	}

	@Override
	public void initProcess() throws Exception
	{
		reactDB.configureGenericReactions(reactor.getSMIRKSManager());
		reactor.setReactionDataBase(reactDB);
		reactor.setStrategy(strategy);

		strategy.FlagStoreFailedNodes = true;
		strategy.FlagStoreSuccessNodes = true;
		strategy.maxNumOfSuccessNodes = 0;  //if 0 then the reactor will stop after the first success node

		strategy.FlagCheckNodeDuplicationOnPush = true;
		strategy.FlagTraceReactionPath = true;
		strategy.FlagLogMainReactionFlow = true;
		strategy.FlagLogReactionPath = true;
		strategy.FlagLogNameInReactionPath = false;
		strategy.FlagLogExplicitHToImplicit = true;

		reactor.initializeReactor(target); //TODO

		//Setup Smirks manager
		reactor.getSMIRKSManager().setFlagProcessResultStructures(true);
		reactor.getSMIRKSManager().setFlagClearImplicitHAtomsBeforeResultProcess(false);
		reactor.getSMIRKSManager().setFlagAddImplicitHAtomsOnResultProcess(false);
		reactor.getSMIRKSManager().setFlagConvertExplicitHToImplicitOnResultProcess(false);

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
			resultNodes = nodes;
			System.out.println("--Handled " + nodes.size() + " nodes");
			for (int i = 0; i < nodes.size(); i++)
			{
				ReactorNode node = nodes.get(i);
				System.out.println(node.toString(reactor));
			}

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
