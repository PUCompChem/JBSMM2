package pu.gui.utils;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import ambit2.reactions.Reaction;

 
public class ReactionSetTree extends JPanel
{	
	private static final long serialVersionUID = -4305046628531992964L;	
	
	private ArrayList<Reaction> reactions = new ArrayList<Reaction>();
	private JTree tree = new JTree();
	private JPanel visualizeCurReaction = null;
	
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
	
	private Icon loadIcon = UIManager.getIcon("OptionPane.errorIcon");
	 
	 
	public ReactionSetTree()
	{
		initGUI();
	}
	
	private void initGUI()
	{
		this.setLayout(new BorderLayout());
		reactionDataToTree();
		   DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		   renderer.setOpenIcon(loadIcon);
		this.add(tree, BorderLayout.CENTER);
	}
	
	private void reactionDataToTree()
	{
		  tree.setModel( new DefaultTreeModel(root));
		for (Reaction reaction : reactions) {
			String reactionClass = reaction.getReactionClass();
			String[] levels = reactionClass.split(".");
			for (int i = 0; i < levels.length; i++) {
				String currentLevel = levels[i];
				DefaultMutableTreeNode currentNode = searchNode(currentLevel);
				 
				if(i == levels.length-1){
					
				}
			}
		}
	}
	
	private DefaultMutableTreeNode searchNode(String nodeStr) {
	    Enumeration e = root.breadthFirstEnumeration();
	    while (e.hasMoreElements()) {
	        DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
	        if (node.getUserObject().toString().toLowerCase().startsWith(nodeStr.toLowerCase())) {
	            return node;
	        }
	    }
	    return null;
	}
	

	public ArrayList<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(ArrayList<Reaction> reactions) {
		this.reactions = reactions;
		reactionDataToTree();
	}

	public JTree getTree() {
		return tree;
	}

	public JPanel getVisualizeCurReaction() {
		return visualizeCurReaction;
	}

	public void setVisualizeCurReaction(JPanel visualizeCurReaction) {
		this.visualizeCurReaction = visualizeCurReaction;
	}			
	
}
