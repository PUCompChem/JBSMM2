package pu.test;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.reactions.GenericReaction;
import ambit2.smarts.SmartsHelper;
import pu.reactor.workspace.gui.ReactionBrowserPanel;
import pu.reactor.workspace.gui.ReactionBrowserPanel.BrowseItem;


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
		setSize(new Dimension(1000,700));
		//setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		revalidate();
		setTitle("Test ReactionApplicationPanel");	
		setLayout(new FlowLayout());
		ReactionBrowserPanel rbp = new ReactionBrowserPanel();
		rbp.setPreferredSize(new Dimension(1000,700));
		add(rbp);
		
		//Setup test info
		IAtomContainer target = SmartsHelper.getMoleculeFromSmiles("CCCCCC");
		rbp.setTarget(target);
		
		GenericReaction r = new GenericReaction(); 
		r.setSmirks("[C:1][H]>>[C:1]O[H]");
		r.setName("aliphatic hydroxilation");
		r.setReactionClass("A");
		
		List<BrowseItem> browseItems = new ArrayList<BrowseItem>();
		
		BrowseItem bi = new BrowseItem();
		bi.reaction = r;
		bi.score = 70;
		bi.products = SmartsHelper.getMoleculeFromSmiles("CCC.CCC");
		browseItems.add(bi);
		
		bi = new BrowseItem();
		bi.reaction = r;
		bi.score = 78;
		bi.products = SmartsHelper.getMoleculeFromSmiles("NCCC.CCCO");
		browseItems.add(bi);
		
		rbp.setBrowseItems(browseItems);	
		rbp.fillTable();
	}

}
