package pu.reactor.workspace;

import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;

public class MoleculeNode extends ProcessNode 
{
	IAtomContainer molecule = null;

	public IAtomContainer getMolecule() {
		return molecule;
	}

	public void setMolecule(IAtomContainer molecule) {
		this.molecule = molecule;
	}
	

}
