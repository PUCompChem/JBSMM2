package pu.gui.utils.gui.utils.trees;

import ambit2.base.data.Property;
import ambit2.base.data.StructureRecord;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MoleculeSetTree extends JPanel
{
	private List<StructureRecord> molecules = new ArrayList<StructureRecord>();
	private JTree tree;

	public MoleculeSetTree()
	{
		tree = new JTree();
		this.add(tree);
		JScrollPane scrollBar = new JScrollPane(tree);
		this.setLayout(new BorderLayout());
		 DataToTree();
		this.add(scrollBar, BorderLayout.CENTER);
	}



	public void MoleculesToDataTree(){

	}

	String getMoleculeClass(StructureRecord mol)
	{
		Property propMolClass = new Property("MoleculeClass");
		return mol.getRecordProperty(propMolClass).toString();
	}
	private void DataToTree() {
		//TODO
	}

}



