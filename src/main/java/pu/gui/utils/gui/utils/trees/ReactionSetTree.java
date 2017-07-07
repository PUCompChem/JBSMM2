package pu.gui.utils.gui.utils.trees;

import ambit2.reactions.Reaction;
import pu.filtering.ColorScheme;
import pu.filtering.ICode;
import pu.filtering.IFilter;
import pu.filtering.filters.SetFilter;
import pu.gui.utils.ReactionInfoPanel;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

 
public class ReactionSetTree extends JPanel
{	
	private static final long serialVersionUID = -4305046628531992964L;

	
	private List<Reaction> reactions = new ArrayList<Reaction>();
	public Map<DefaultMutableTreeNode, Reaction> nodeReactions = new HashMap<DefaultMutableTreeNode, Reaction>();
	public Map<Reaction, DefaultMutableTreeNode> reactionNodes = new HashMap<Reaction, DefaultMutableTreeNode>();
	private JTree tree;
	
	public JPanel visualizeCurReaction = null;
 	private JScrollPane scrollBar;


	ReactionInfoPanel reactionInfoPanel;
	 
	public ReactionSetTree()
	{
		initGUI();
	}
	
	public ReactionSetTree(List<Reaction> reactions)
	{
		this.reactions = reactions;
		initGUI();

	}
	
	private void initGUI()
	{
		tree = new JTree();
		JScrollPane scrollBar = new JScrollPane(tree);

		reactionInfoPanel = new ReactionInfoPanel();

		this.setLayout(new BorderLayout());
		dataToTree();
		this.add(scrollBar, BorderLayout.CENTER);
		this.add(reactionInfoPanel, BorderLayout.SOUTH);


		fromTreeToInfoPanel();
	}



	private void dataToTree()
	{	
		nodeReactions.clear();
		reactionNodes.clear();
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Reaction set");
		 
		tree.setModel(new DefaultTreeModel(root));
		  
		for (Reaction reaction : reactions) 
		{
			String reactionClass = reaction.getReactionClass();
			String[] levels = reactionClass.split(Pattern.quote("."));
			
			//Find or create reaction class nodes and 
			//mappings between reactions and nodes
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
			reactionNodes.put(reaction, reactionNode);
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
		dataToTree();
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
	
	private void fromTreeToInfoPanel() {
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
						tree.getLastSelectedPathComponent();
				if (node == null) 
					return;
				reactionInfoPanel.ClearText();
				reactionInfoPanel.WriteText(getNodeInfoText(node));
			}
		});
	}

	
	String getNodeInfoText(DefaultMutableTreeNode node)
	{	
		Reaction r = nodeReactions.get(node);
		if (r == null)
			return node.toString();
		else
		{
			StringBuffer sb = new StringBuffer();
			sb.append(r.getName() + "\n");
			sb.append("Class: " + r.getReactionClass() + "\n");
			sb.append(r.getSmirks() + "\n");
			return sb.toString();
		}
	}
	
	void applyFilter(IFilter filter)
	{
		
	}
	
	void applyColorScheme(ColorScheme scheme, ICode elementCoding)
	{
		//TODO
	}
	
	IFilter getReactionUseFilter()
	{
		SetFilter filter = new SetFilter();
		Set<Object> rSet = new HashSet<Object>(); 
		for (Reaction r : reactions)
		{
			if (r.isFlagUse())
				rSet.add(r);
		}
		filter.setObjects(rSet);
		return filter;
	}

	public final DefaultMutableTreeNode findNode(String searchString) {

		List<DefaultMutableTreeNode> searchNodes = getSearchNodes((DefaultMutableTreeNode)tree.getModel().getRoot());
		DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

		DefaultMutableTreeNode foundNode = null;
		int bookmark = -1;

		if( currentNode != null ) {
			for(int index = 0; index < searchNodes.size(); index++) {
				if( searchNodes.get(index) == currentNode ) {
					bookmark = index;
					break;
				}
			}
		}

		for(int index = bookmark + 1; index < searchNodes.size(); index++) {
			if(searchNodes.get(index).toString().toLowerCase().contains(searchString.toLowerCase())) {
				foundNode = searchNodes.get(index);
				break;
			}
		}

		if( foundNode == null ) {
			for(int index = 0; index <= bookmark; index++) {
				if(searchNodes.get(index).toString().toLowerCase().contains(searchString.toLowerCase())) {
					foundNode = searchNodes.get(index);
					break;
				}
			}
		}
		return foundNode;
	}

	private final List<DefaultMutableTreeNode> getSearchNodes(DefaultMutableTreeNode root) {
		List<DefaultMutableTreeNode> searchNodes = new ArrayList<DefaultMutableTreeNode>();

		Enumeration<?> e = root.preorderEnumeration();
		while(e.hasMoreElements()) {
			searchNodes.add((DefaultMutableTreeNode)e.nextElement());
		}
		return searchNodes;
	}

}
