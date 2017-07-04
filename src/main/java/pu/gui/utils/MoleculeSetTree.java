package pu.gui.utils;

import ambit2.base.data.Property;
import ambit2.base.data.StructureRecord;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class MoleculeSetTree 
{
	private List<StructureRecord> molecules = new ArrayList<StructureRecord>();
	private JTree tree;

	public MoleculeSetTree()
	{
		tree = new JTree();

	}


	public void MoleculesToDataTree(){
		//TODO
	}

	String getMoleculeClass(StructureRecord mol)
	{
		Property propMolClass = new Property("MoleculeClass");
		return mol.getRecordProperty(propMolClass).toString();
	}
	
}



