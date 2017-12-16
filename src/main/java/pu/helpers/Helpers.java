package pu.helpers;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;

public class Helpers 
{
	public static Object[] getCouple(Object a, Object b)
	{
		Object o[] = new Object[2];
		o[0] = a;
		o[1] = b;
		return o;
	}
	
	public static List<IAtom> calcAtomList(IAtomContainer mol, List<Integer> indList)
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
	
	public static List<Integer> calcIndexList(IAtomContainer mol, List<IAtom> atList)
	{
		List<Integer> indList = new ArrayList<Integer>();
		for (IAtom at : atList)
		{	
			indList.add(mol.getAtomNumber(at));
		}
		return indList;
	}
	
	public static List<IBond> calcBondList(IAtomContainer mol, List<IAtom> atList)
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
