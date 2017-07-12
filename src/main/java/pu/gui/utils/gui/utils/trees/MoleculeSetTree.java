package pu.gui.utils.gui.utils.trees;

import ambit2.base.data.Property;
import ambit2.base.data.StructureRecord;
import ambit2.reactions.Reaction;
import pu.gui.utils.InfoPanel;
import pu.gui.utils.MoleculeInfoPanel;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.openscience.cdk.interfaces.IAtomContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MoleculeSetTree extends SetTree
{
	public static final String moleculeClassProperty = "MoleculeClass";
	
	private List<StructureRecord> structureRecords = new ArrayList<StructureRecord>();
	private List<IAtomContainer> molecules = new ArrayList<IAtomContainer>();
	




	public MoleculeSetTree(List<StructureRecord> structureRecords)
	{
		this.structureRecords = structureRecords;
		initGUI();
	}
	
	public MoleculeSetTree(List<String> smiles, List<String> moleculeClass)
	{
		List<StructureRecord> structureRecords = new ArrayList<StructureRecord>();
		for (int i = 0; i < smiles.size(); i++)
		{
			StructureRecord sr = new StructureRecord();
			sr.setSmiles(smiles.get(i));
			sr.setRecordProperty(new Property(moleculeClassProperty), moleculeClass.get(i));
			structureRecords.add(sr);
		}
		initGUI();
	}

	private void initGUI() {
		infoPanel = new InfoPanel();
		tree = new JTree();
		this.add(tree);
		JScrollPane scrollBar = new JScrollPane(tree);
		this.setLayout(new BorderLayout());
		moleculesToDataTree();
		searchBoxSet();
		this.add(scrollBar, BorderLayout.CENTER);
		this.add(infoPanel, BorderLayout.SOUTH);
	}


	private void moleculesToDataTree(){
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Starting materials");
		tree.setModel(new DefaultTreeModel(root));
		for(StructureRecord str : structureRecords)
		{

		}
	}

	String getMoleculeClass(StructureRecord mol)
	{
		Property propMolClass = new Property(moleculeClassProperty);
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
				infoPanel.ClearText();
				infoPanel.WriteText(getNodeInfoText(node));
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



