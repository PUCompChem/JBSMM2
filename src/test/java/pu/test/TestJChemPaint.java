package pu.test;

import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.jchempaint.editor.MoleculeEditAction;
import ambit2.smarts.SmartsHelper;

public class TestJChemPaint {

	public static void main(String[] args) throws Exception
	{
		testJCP("CCCCC");

	}
	
	public static void testJCP(String smiles) throws Exception
	{
		System.out.println("JCP input smiles: " + smiles);
		IAtomContainer mol = SmartsHelper.getMoleculeFromSmiles(smiles);
		MoleculeEditAction molEdAction = new MoleculeEditAction(mol);
		molEdAction.editMolecule(true, null);
		//TODO ...
		IAtomContainer resultMol = molEdAction.getMolecule();
		String resultSmiles = SmartsHelper.moleculeToSMILES(resultMol, true);
		System.out.println("JCP result smiles: " + resultSmiles);
	}

}
