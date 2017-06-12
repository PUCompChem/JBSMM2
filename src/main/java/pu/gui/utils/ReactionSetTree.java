package pu.gui.utils;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTree;

import ambit2.reactions.Reaction;

 
public class ReactionSetTree extends JPanel
{	
	private static final long serialVersionUID = -4305046628531992964L;	
	
	private ArrayList<Reaction> reactions = new ArrayList<Reaction>();
	private JTree tree = new JTree();
	private JPanel visualizeCurReaction = null;
	
	public ReactionSetTree()
	{
		initGUI();
	}
	
	private void initGUI()
	{
		this.setLayout(new BorderLayout());
		this.add(tree, BorderLayout.CENTER);
	}
	
	private void reactionDataToTree()
	{
		//TODO
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
