package pu.gui.utils;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.renderer.selection.IChemObjectSelection;
import org.openscience.cdk.renderer.selection.SingleSelection;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import ambit2.core.data.MoleculeTools;
import ambit2.rendering.IAtomContainerHighlights;

public class AtomContainerHightlights implements IAtomContainerHighlights
{	
	private static final long serialVersionUID = 2387697863334234L;
	
	public static enum SelectionMetod {
		ATOM_INDEX_LIST, ATOM_LIST
	}
	
	boolean enabled = true;
	boolean highlightBonds = false;
	SelectionMetod selectionMethod = SelectionMetod.ATOM_INDEX_LIST;
	List<Integer> indexList = new ArrayList<Integer>();
	List<Integer> atomList = new ArrayList<Integer>();
	
		
	public boolean isHighlightBonds() {
		return highlightBonds;
	}

	public void setHighlightBonds(boolean highlightBonds) {
		this.highlightBonds = highlightBonds;
	}

	public SelectionMetod getSelectionMethod() {
		return selectionMethod;
	}

	public void setSelectionMethod(SelectionMetod selectionMethod) {
		this.selectionMethod = selectionMethod;
	}
	
	public List<Integer> getIndexList() {
		return indexList;
	}

	public void setIndexList(List<Integer> indexList) {
		this.indexList = indexList;
	}

	public List<Integer> getAtomList() {
		return atomList;
	}

	public void setAtomList(List<Integer> atomList) {
		this.atomList = atomList;
	}

	@Override
	public void close() throws Exception {		
	}

	@Override
	public long getID() {
		return 0;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void open() throws Exception {
	}

	@Override
	public IChemObjectSelection process(IAtomContainer mol) throws Exception 
	{
		
		switch (selectionMethod)
		{
		case ATOM_INDEX_LIST:
			return getIndexListSelection(mol);
		case ATOM_LIST:
			return getAtomListSelection(mol);
		}
		
		return null;
	}
	
	IChemObjectSelection getIndexListSelection(IAtomContainer mol)
	{
		final IAtomContainer selectedMol = MoleculeTools
				.newMolecule(SilentChemObjectBuilder.getInstance());
		
		
		for (Integer i : indexList)
		{	
			if (i.intValue() < mol.getAtomCount())
			{
				IAtom at = mol.getAtom(i.intValue());
				selectedMol.addAtom(at);
			}
		}	
		
		if (highlightBonds)
		{
			//TODO
		}
		
		return new SingleSelection<IChemObject>(selectedMol);
	}
	
	IChemObjectSelection getAtomListSelection(IAtomContainer mol)
	{
		//TODO
		return null;
	}
	

	@Override
	public void setEnabled(boolean arg0) {
		enabled = arg0;
	}

}
