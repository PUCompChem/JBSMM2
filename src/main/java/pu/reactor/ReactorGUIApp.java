package pu.reactor;

import static pu.gui.utils.trees.MoleculeSetTree.moleculeClassProperty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import ambit2.reactions.ReactionDataBase;
import pu.reactor.workspace.Preferences;
import pu.reactor.workspace.ProcessCommonChemData;

public class ReactorGUIApp 
{
	public static void main(String[] args) throws Exception
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() 
			{	
				ReactorMainFrame reactor;
				try {
					if (args.length >= 1)
					{	
						//reactor = createReactorMainFrame(args[0]);
						reactor = new ReactorMainFrame(args[0]);
						reactor.setVisible(true);
					}			
					else
					{	
						reactor = new ReactorMainFrame( "./preferences.json");
						reactor.setVisible(true);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		});
	}
	
	public static ReactorMainFrame createReactorMainFrame(String prefFileName) throws Exception
	{
		Preferences pref = ReactorMainFrame.getPreferences(prefFileName);
		ProcessCommonChemData pccd = new ProcessCommonChemData();
		ReactionDataBase reactionDB = new  ReactionDataBase(pref.reactionDBPath);
		pccd.setReactionDB(reactionDB);
		ReactorMainFrame.setStartingMaterialsDB(pref, pccd);
		ReactorMainFrame reactor = new ReactorMainFrame(pref, pccd);
		return reactor;
	}
	
	public static ReactorMainFrame createReactorMainFrameWithDemoInfo() throws Exception
	{
		Preferences pref = new Preferences();
		ProcessCommonChemData pccd = new ProcessCommonChemData();
		ReactorMainFrame.setStartingMaterialsDB(null, pccd);
		//TODO create demo reaction db
		ReactorMainFrame reactor = new ReactorMainFrame(pref, pccd);
		return reactor;
	}
	 
}
