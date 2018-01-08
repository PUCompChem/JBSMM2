package pu.gui.utils.trees;

import ambit2.reactions.GenericReaction;
import pu.filtering.ColorScheme;
import pu.filtering.ICode;
import pu.filtering.IFilter;
import pu.filtering.filters.SetFilter;
import pu.gui.utils.contextmenus.ReactionContextMenu;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;


public class ReactionSetTree extends SetTree
{
	private static final long serialVersionUID = -4305046628531992964L;


	private List<GenericReaction> reactions = new ArrayList<GenericReaction>();
	public Map<DefaultMutableTreeNode, GenericReaction> nodeReactions = new HashMap<DefaultMutableTreeNode, GenericReaction>();
	public Map<GenericReaction, DefaultMutableTreeNode> reactionNodes = new HashMap<GenericReaction, DefaultMutableTreeNode>();


	DefaultMutableTreeNode root;

	public JPanel visualizeCurReaction = null;



	TableInfoPanel reactionInfoPanel;

	public ReactionSetTree()
	{
		initGUI();
	}

	public ReactionSetTree(List<GenericReaction> reactions)
	{
		this.reactions = reactions;
		initGUI();

	}

	private void initGUI() {
		tree = new JTree();

			 tree.addMouseListener(new ReactionContextMenu(this.tree));

		setIcons("pu/images/reactionGroup.png" );

		JScrollPane scrollBar = new JScrollPane(tree);

		reactionInfoPanel = new TableInfoPanel();

		this.setLayout(new BorderLayout());
		dataToTree();
		this.add(scrollBar, BorderLayout.CENTER);
		this.add(reactionInfoPanel, BorderLayout.SOUTH);
		searchBoxSet();
		nodeReactionsMapping();
		reactionNodesMapping();
		expandAllNodes(tree, 0, tree.getRowCount());
		fromTreeToInfoPanel();
	}




	private void dataToTree()
	{
		nodeReactions.clear();
		reactionNodes.clear();

		 root = new DefaultMutableTreeNode("Reaction set");

		tree.setModel(new DefaultTreeModel(root));

		for (GenericReaction reaction : reactions)
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




	public List<GenericReaction> getReactions() {
		return reactions;
	}

	public void setReactions(List<GenericReaction> reactions) {
		this.reactions = reactions;
		dataToTree();
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

				reactionInfoPanel.Write(getNodeInfoText(node));
			}
		});
	}



	String getNodeInfoText(DefaultMutableTreeNode node)
	{
		GenericReaction r = nodeReactions.get(node);
		if (r == null)
			return node.toString();
		else
		{
			StringBuffer sb = new StringBuffer();
			sb.append("Name: "  + r.getName() + "\n");
			sb.append("Class: " + r.getReactionClass() + "\n");
			sb.append("Smirks: " + r.getSmirks() + "\n");
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
		for (GenericReaction r : reactions)
		{
			if (r.isFlagUse())
				rSet.add(r);
		}
		filter.setObjects(rSet);
		return filter;
	}


	private void reactionNodesMapping(){
		for (int i = 0; i < reactions.size(); i++) {
			GenericReaction cuurentReaction = reactions.get(i);
			DefaultMutableTreeNode currentNode = findNode(cuurentReaction.getName());
			reactionNodes.put(cuurentReaction,currentNode);
		}
	}
	private void nodeReactionsMapping(){
		iterateTree(root);

		}

		private void iterateTree(DefaultMutableTreeNode currentNode){

				for(GenericReaction reaction : reactions){
					if(reaction.getName() == currentNode.toString()){
						nodeReactions.put(currentNode, reaction);
						break;
					};
				}


			for (int i = 0; i < currentNode.getChildCount(); i++) {
				if(currentNode.getChildCount() == 0){
					break;
				}else {
					iterateTree((DefaultMutableTreeNode) currentNode.getChildAt(i));
				}
			}
		}

	}


