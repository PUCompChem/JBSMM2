package pu.gui.utils;

import ambit2.reactions.Reaction;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

 
public class ReactionSetTree extends JPanel
{	
	private static final long serialVersionUID = -4305046628531992964L;	
	
	private List<Reaction> reactions = new ArrayList<Reaction>();
	public Map<DefaultMutableTreeNode, Reaction> nodeReactions = new HashMap<DefaultMutableTreeNode, Reaction>();
	private JTree tree;
	
	public JPanel visualizeCurReaction = null;
	
	
	private Icon loadIcon = UIManager.getIcon("OptionPane.errorIcon");
	 
	public ReactionSetTree()
	{
		initGUI();
	}
	
	public ReactionSetTree(List<Reaction> reactions)
	{
		this.reactions = reactions;
		initGUI();
		reactionDataToTree();
	}
	
	private void initGUI()
	{
		tree = new JTree();
		TreeLenseMenu treeLenseWindow = new TreeLenseMenu();

		
		this.setLayout(new BorderLayout());
		reactionDataToTree();
		   DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		   renderer.setOpenIcon(loadIcon);
		   
		this.add(tree, BorderLayout.CENTER);
		this.add(treeLenseWindow, BorderLayout.SOUTH);
	}
	
	private void reactionDataToTree()
	{
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		 
		tree.setModel(new DefaultTreeModel(root));
		  
		for (Reaction reaction : reactions) 
		{
			String reactionClass = reaction.getReactionClass();
			String[] levels = reactionClass.split(Pattern.quote("."));
			
			//Find or create reaction class nodes
			DefaultMutableTreeNode currentLevelNode = root;
			for (int i = 0; i < levels.length; i++) 
			{
				String currentLevel = levels[i];
				
				DefaultMutableTreeNode nextLevelNode =  searchChildrenNode(currentLevel, currentLevelNode);
					if (nextLevelNode == null)
				{	
					nextLevelNode = new DefaultMutableTreeNode(currentLevel);
					currentLevelNode.add(nextLevelNode);
				}
				currentLevelNode = nextLevelNode;
			}
			
			//Add reaction node
			DefaultMutableTreeNode reactionNode = new DefaultMutableTreeNode(reaction.getName());
			currentLevelNode.add(reactionNode);
			nodeReactions.put(reactionNode, reaction);
		}
		
	}
	
	
	private DefaultMutableTreeNode searchChildrenNode(String nodeObj, DefaultMutableTreeNode node) 
	{	
		for (int i = 0; i < node.getChildCount(); i++)
		{	
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
			if (nodeObj.equals(child.getUserObject()))
				return child;
		}
	    return null;
	}

	public List<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(List<Reaction> reactions) {
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
