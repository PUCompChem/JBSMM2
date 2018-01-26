package pu.test;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.reactions.GenericReaction;
import ambit2.reactions.GenericReactionInstance;
import ambit2.reactions.rules.scores.ReactionScore;
import ambit2.smarts.SmartsHelper;
import pu.reactor.workspace.gui.ReactionBrowserPanel;


public class TestReactionBrowserPanel extends JFrame
{

	private static final long serialVersionUID = 8249049020022972371L;

	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() 
			{	
				try {
					TestReactionBrowserPanel testFrame = new TestReactionBrowserPanel();
					testFrame.setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
		});
	}
	
	public TestReactionBrowserPanel() throws Exception
	{
		initGUI();
	}
	
	private void initGUI() throws Exception
	{
		setSize(new Dimension(1000,800));
		//setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		revalidate();
		setTitle("Test ReactionApplicationPanel");	
		setLayout(new FlowLayout());
		ReactionBrowserPanel rbp = new ReactionBrowserPanel();
		rbp.setPreferredSize(new Dimension(1000,800));
		add(rbp);
		
		//Setup test info
		IAtomContainer target = SmartsHelper.getMoleculeFromSmiles("CCCCCC");
		rbp.setTarget(target);
		
		GenericReaction r = new GenericReaction(); 
		r.setSmirks("[C:1][H]>>[C:1]O[H]");
		r.setName("aliphatic hydroxilation");
		r.setReactionClass("A");
		
		List<GenericReactionInstance> reactInstances = new ArrayList<GenericReactionInstance>();
				
		GenericReactionInstance ri = new GenericReactionInstance();
		ri.reaction = r;
		//ri.target = target;
		ri.reactionScore = new ReactionScore();
		ri.reactionScore.totalScore = 70;
		ri.products = SmartsHelper.getMoleculeFromSmiles("CCC.CCC");
		ri.instanceAtoms = new ArrayList<IAtom>();
		ri.instanceAtoms.add(target.getAtom(0));
		ri.instanceAtoms.add(target.getAtom(1));
		reactInstances.add(ri);
		
		ri = new GenericReactionInstance();	
		ri.reaction = r;
		//ri.target = target;
		ri.reactionScore = new ReactionScore();
		ri.reactionScore.totalScore = 47;
		ri.products = SmartsHelper.getMoleculeFromSmiles("NCCC.CCCO");
		//ri.instanceAtoms = new ArrayList<IAtom>();
		//ri.instanceAtoms.add(target.getAtom(2));
		//ri.instanceAtoms.add(target.getAtom(3));
		reactInstances.add(ri);
		
		ri = new GenericReactionInstance();	
		ri.reaction = r;
		//ri.target = target;
		ri.reactionScore = new ReactionScore();
		ri.reactionScore.totalScore = 78;
		ri.products = SmartsHelper.getMoleculeFromSmiles("NCCC.C1CCO1");
		ri.instanceAtoms = new ArrayList<IAtom>();
		ri.instanceAtoms.add(target.getAtom(4));
		ri.instanceAtoms.add(target.getAtom(5));
		reactInstances.add(ri);
		
		ri = new GenericReactionInstance();	
		ri.reaction = r;
		//ri.target = target;
		ri.reactionScore = new ReactionScore();
		ri.reactionScore.totalScore = 88;
		ri.products = SmartsHelper.getMoleculeFromSmiles("NCCC.C1CCCC1");
		//ri.instanceAtoms = new ArrayList<IAtom>();
		//ri.instanceAtoms.add(target.getAtom(2));
		//ri.instanceAtoms.add(target.getAtom(3));
		reactInstances.add(ri);
		
		rbp.setReactionInstances(reactInstances);	
		rbp.fillTable();
	}

}
