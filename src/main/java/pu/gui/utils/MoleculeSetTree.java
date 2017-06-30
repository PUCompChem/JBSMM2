package pu.gui.utils;

import java.util.ArrayList;
import java.util.List;

import ambit2.base.data.Property;
import ambit2.base.data.StructureRecord;


public class MoleculeSetTree 
{
	private List<StructureRecord> molecules = new ArrayList<StructureRecord>();
	
	
	String getMoleculeClass(StructureRecord mol)
	{
		Property propMolClass = new Property("MoleculeClass");
		return mol.getRecordProperty(propMolClass).toString();
	}
	
}



