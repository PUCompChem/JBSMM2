package pu.gui.utils.gui.utils.trees;

import ambit2.base.data.Property;
import ambit2.base.data.StructureRecord;
import ambit2.reactions.Reaction;
import pu.gui.utils.MoleculeInfoPanel;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MoleculeSetTree extends JPanel
{
	private List<StructureRecord> molecules = new ArrayList<StructureRecord>();
	private JTree tree;
	private MoleculeInfoPanel moleculeInfoPanel;

	public MoleculeSetTree()
	{
		moleculeInfoPanel = new MoleculeInfoPanel();
		tree = new JTree();
		this.add(tree);
		JScrollPane scrollBar = new JScrollPane(tree);
		this.setLayout(new BorderLayout());
		 dataToTree();
		this.add(scrollBar, BorderLayout.CENTER);
		this.add(moleculeInfoPanel, BorderLayout.SOUTH);

	}



	public void MoleculesToDataTree(){

	}

	String getMoleculeClass(StructureRecord mol)
	{
		Property propMolClass = new Property("MoleculeClass");
		return mol.getRecordProperty(propMolClass).toString();
	}
	private void dataToTree() {
		//TODO
	}
	private void fromTreeToInfoPane(){
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
						tree.getLastSelectedPathComponent();
				if (node == null)
					return;
				moleculeInfoPanel.ClearText();
				moleculeInfoPanel.WriteText(getNodeInfoText(node));
			}
		});
	}
	String getNodeInfoText(DefaultMutableTreeNode node)
	{
		Reaction r = null;
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

}



