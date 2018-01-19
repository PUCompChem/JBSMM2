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
	public static void main(final String[] args) throws Exception
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
						//reactor = new ReactorMainFrame( "./preferences.json");
						reactor = createReactorMainFrameWithDemoInfo();
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
		
		//create demo reaction db
		List<String> smirks = new ArrayList<String>();
		smirks.add("[C:1]Cl>>[C:1]");		
		smirks.add("[H][C:1][C:2][H]>>[H][C:1][H].[H][C:2][H]");
		ReactionDataBase reactionDB = new ReactionDataBase(smirks);
		pccd.setReactionDB(reactionDB);
		
		ReactorMainFrame reactor = new ReactorMainFrame(pref, pccd);
		return reactor;
	}
	 
}
