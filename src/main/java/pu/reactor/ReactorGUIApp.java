package pu.reactor;

import static pu.gui.utils.trees.MoleculeSetTree.moleculeClassProperty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.openscience.cdk.silent.SilentChemObjectBuilder;

import ambit2.reactions.ReactionDataBase;
import ambit2.smarts.SMIRKSManager;
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
						reactor = createReactorMainFrame(args[0]);
						//reactor = new ReactorMainFrame(args[0]);
						reactor.setVisible(true);
					}			
					else
					{	
						File f = new File("./preferences.json");
						if (f.exists())
							reactor = createReactorMainFrame("./preferences.json");
						else
						{	
							System.out.println("File ./preferences.json not found!");
							reactor = createReactorMainFrameWithDemoInfo();
						}	
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
		System.out.println("Loading preferences: " + prefFileName);
		Preferences pref = ReactorMainFrame.getPreferences(prefFileName);
		ProcessCommonChemData pccd = new ProcessCommonChemData();
		ReactionDataBase reactionDB = new  ReactionDataBase(pref.reactionDBPath);
		SMIRKSManager smrkMan0 = new SMIRKSManager(SilentChemObjectBuilder.getInstance()); 
		reactionDB.configureGenericReactions(smrkMan0);
		if (!reactionDB.errors.isEmpty())
		{	
			System.out.println(reactionDB.getErrorsAsString());
			reactionDB.excludeReactionsWithConfigErrors();
		}	
		pccd.setReactionDB(reactionDB);
		ReactorMainFrame.setStartingMaterialsDB(pref, pccd);
		ReactorMainFrame reactor = new ReactorMainFrame(pref, pccd);
		return reactor;
	}
	
	public static ReactorMainFrame createReactorMainFrameWithDemoInfo() throws Exception
	{
		System.out.println("Loading demo preferences:");
		Preferences pref = new Preferences();
		ProcessCommonChemData pccd = new ProcessCommonChemData();
		ReactorMainFrame.setStartingMaterialsDB(null, pccd);
		
		//create demo reaction db
		List<String> smirks = new ArrayList<String>();
		smirks.add("[C:1]Cl>>[C:1]");		
		smirks.add("[H][C:1][C:2][H]>>[H][C:1][H].[H][C:2][H]");
		ReactionDataBase reactionDB = new ReactionDataBase(smirks);
		SMIRKSManager smrkMan0 = new SMIRKSManager(SilentChemObjectBuilder.getInstance()); 
		reactionDB.configureGenericReactions(smrkMan0);
		if (!reactionDB.errors.isEmpty())
		{	
			System.out.println(reactionDB.getErrorsAsString());
			reactionDB.excludeReactionsWithConfigErrors();
		}
		pccd.setReactionDB(reactionDB);
		
		ReactorMainFrame reactor = new ReactorMainFrame(pref, pccd);
		return reactor;
	}
	 
}
