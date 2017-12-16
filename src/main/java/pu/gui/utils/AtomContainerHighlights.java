package pu.gui.utils;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.renderer.selection.IChemObjectSelection;
import org.openscience.cdk.renderer.selection.SingleSelection;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import ambit2.core.data.MoleculeTools;
import ambit2.rendering.IAtomContainerHighlights;
import pu.helpers.Helpers;

public class AtomContainerHighlights implements IAtomContainerHighlights
{	
	private static final long serialVersionUID = 2387697863334234L;
	
	public static enum SelectionMetod {
		ATOM_INDEX_LIST, ATOM_LIST, FRAGMENT_INDICES_LIST, FRAGMENT_ATOMS_LIST
	}
	
	boolean enabled = true;
	boolean highlightBonds = false;
	
	SelectionMetod selectionMethod = SelectionMetod.ATOM_INDEX_LIST;
	List<Integer> indexList = new ArrayList<Integer>();
	List<IAtom> atomList = new ArrayList<IAtom>();
	List<List<Integer>> fragmentIndicesList = new ArrayList<List<Integer>>();
	List<List<IAtom>> fragmentAtomsList = new ArrayList<List<IAtom>>();
	
		
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

	public List<IAtom> getAtomList() {
		return atomList;
	}

	public void setAtomList(List<IAtom> atomList) {
		this.atomList = atomList;
	}
	
	public List<List<Integer>> getFragmentIndicesList() {
		return fragmentIndicesList;
	}

	public void setFragmentIndicesList(List<List<Integer>> fragmentIndicesList) {
		this.fragmentIndicesList = fragmentIndicesList;
	}

	public List<List<IAtom>> getFragmentAtomsList() {
		return fragmentAtomsList;
	}

	public void setFragmentAtomsList(List<List<IAtom>> fragmentAtomsList) {
		this.fragmentAtomsList = fragmentAtomsList;
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
		//case FRAGMENT_INDICES_LIST:
		//	return null;
		case FRAGMENT_ATOMS_LIST:
			return getFragmentAtomsListSelection(mol);
		}
		return null;
	}
	
	@Override
	public void setEnabled(boolean arg0) {
		enabled = arg0;
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
			List<IAtom> atList = Helpers.calcAtomList(mol, indexList);
			List<IBond> boList = Helpers.calcBondList(mol, atList);
			for (IBond bo : boList)
				selectedMol.addBond(bo);
		}
		
		return new SingleSelection<IChemObject>(selectedMol);
		
	}
	
	IChemObjectSelection getAtomListSelection(IAtomContainer mol)
	{
		final IAtomContainer selectedMol = MoleculeTools
				.newMolecule(SilentChemObjectBuilder.getInstance());
		
		for (IAtom at : atomList)
			selectedMol.addAtom(at);
		
		if (highlightBonds)
		{
			List<IBond> boList = Helpers.calcBondList(mol, atomList);
			for (IBond bo : boList)
				selectedMol.addBond(bo);
		}
		
		return new SingleSelection<IChemObject>(selectedMol);
	}
	
	IChemObjectSelection getFragmentAtomsListSelection(IAtomContainer mol)
	{
		final IAtomContainer selectedMol = MoleculeTools
				.newMolecule(SilentChemObjectBuilder.getInstance());
		
		for (List<IAtom> fragAtList : fragmentAtomsList)
		{	
			for (IAtom at : fragAtList)
			{	
				selectedMol.addAtom(at);
			}	

			if (highlightBonds)
			{
				List<IBond> boList = Helpers.calcBondList(mol, fragAtList);
				for (IBond bo : boList)
					selectedMol.addBond(bo);
			}
		}
		
		return new SingleSelection<IChemObject>(selectedMol);
	}
	
	

}
