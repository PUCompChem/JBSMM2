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

public class AtomContainerHightlights implements IAtomContainerHighlights
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
			List<IAtom> atList = calcAtomList(mol, indexList);
			List<IBond> boList = calcBondList(mol, atList);
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
			List<IBond> boList = calcBondList(mol, atomList);
			for (IBond bo : boList)
				selectedMol.addBond(bo);
		}
		
		return new SingleSelection<IChemObject>(selectedMol);
	}
	
	List<IAtom> calcAtomList(IAtomContainer mol, List<Integer> indList)
	{
		List<IAtom> atList = new ArrayList<IAtom>();
		for (Integer i : indList)
		{	
			if (i.intValue() < mol.getAtomCount())
			{
				IAtom at = mol.getAtom(i.intValue());
				atList.add(at);
			}
		}
		return atList;
	}
	
	List<IBond> calcBondList(IAtomContainer mol, List<IAtom> atList)
	{
		List<IBond> boList = new ArrayList<IBond>();
		for (IAtom at : atList)
		{
			List<IBond> conBoList =  mol.getConnectedBondsList(at);
			for (IBond bo : conBoList)
			{
				if (bo.getAtom(0) == at)
				{
					if (atList.contains(bo.getAtom(1)))
						if (!boList.contains(bo))
							boList.add(bo);
				}
				else //bo.getAtom(1) == at
				{
					if (atList.contains(bo.getAtom(0)))
						if (!boList.contains(bo))
							boList.add(bo);
				}
			}
		}
		return boList;
	}	

}
