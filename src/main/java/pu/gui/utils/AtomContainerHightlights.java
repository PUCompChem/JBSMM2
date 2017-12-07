package pu.gui.utils;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.renderer.selection.IChemObjectSelection;

import ambit2.rendering.IAtomContainerHighlights;

public class AtomContainerHightlights implements IAtomContainerHighlights
{	
	public static enum HightlightsMetod {
		ATOM_INDEX_LIST, ATOM_LIST
	}
	
	private static final long serialVersionUID = 2387697863334234L;
	
	boolean enabled = true;
	
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
	public IChemObjectSelection process(IAtomContainer arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEnabled(boolean arg0) {
		enabled = arg0;
	}

}
