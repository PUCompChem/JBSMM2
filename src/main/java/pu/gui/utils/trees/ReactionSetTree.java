package pu.gui.utils.trees;

import ambit2.reactions.Reaction;
import pu.filtering.ColorScheme;
import pu.filtering.ICode;
import pu.filtering.IFilter;
import pu.filtering.filters.SetFilter;
import pu.gui.utils.contextmenus.ReactionContextMenu;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;


public class ReactionSetTree extends SetTree
{
	private static final long serialVersionUID = -4305046628531992964L;


	private List<Reaction> reactions = new ArrayList<Reaction>();
	public Map<DefaultMutableTreeNode, Reaction> nodeReactions = new HashMap<DefaultMutableTreeNode, Reaction>();
	public Map<Reaction, DefaultMutableTreeNode> reactionNodes = new HashMap<Reaction, DefaultMutableTreeNode>();


	DefaultMutableTreeNode root;

	public JPanel visualizeCurReaction = null;



	TableInfoPanel reactionInfoPanel;

	public ReactionSetTree()
	{
		initGUI();
	}

	public ReactionSetTree(List<Reaction> reactions)
	{
		this.reactions = reactions;
		initGUI();

	}

	private void initGUI() {
		tree = new JTree();

			 tree.addMouseListener(new ReactionContextMenu(this.tree));

		setIcons("pu/images/reactionIcon.png","pu/images/reactionGroup.png","pu/images/reactionGroup.png" );

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




	public List<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(List<Reaction> reactions) {
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
		Reaction r = nodeReactions.get(node);
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
		for (Reaction r : reactions)
		{
			if (r.isFlagUse())
				rSet.add(r);
		}
		filter.setObjects(rSet);
		return filter;
	}


	private void reactionNodesMapping(){
		for (int i = 0; i < reactions.size(); i++) {
			Reaction cuurentReaction = reactions.get(i);
			DefaultMutableTreeNode currentNode = findNode(cuurentReaction.getName());
			reactionNodes.put(cuurentReaction,currentNode);
		}
	}
	private void nodeReactionsMapping(){
		iterateTree(root);

		}

		private void iterateTree(DefaultMutableTreeNode currentNode){

				for(Reaction reaction : reactions){
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


