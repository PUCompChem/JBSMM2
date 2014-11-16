package pu.gui.utils;

import org.openscience.cdk.interfaces.IAtomContainer;


/**
 * This class describes the information for particular 'work case'
 * @author nick
 *
 */
public class WorkCase 
{	
	public enum Type {
		MOLECULE, REACTION, MOLECULE_SET
	}

	private Type workCaseType = Type.MOLECULE;
	private String name = "Work case";
	private String info = null;
	private IAtomContainer molecule;
	private WorkCasePanel panel = new WorkCasePanel(); //temporary code
	
	
	public WorkCase(IAtomContainer molecule, String name, String info)
	{
		this.name = name;
		this.molecule = molecule;
		this.info = info;
	}

	public Type getWorkCaseType() {
		return workCaseType;
	}

	public void setWorkCaseType(Type workCaseType) {
		this.workCaseType = workCaseType;
	}

	public IAtomContainer getMolecule() {
		return molecule;
	}

	public void setMolecule(IAtomContainer molecule) {
		this.molecule = molecule;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public WorkCasePanel getPanel() {
		return panel;
	}

	public void setPanel(WorkCasePanel panel) {
		this.panel = panel;
	}
	
}
