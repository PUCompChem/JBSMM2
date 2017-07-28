package pu.gui.utils;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.openscience.cdk.interfaces.IAtomContainer;

public class WorkCaseTabSet 
{
	private JTabbedPane tabbedPane;
	private ArrayList<WorkCase> workCases = new ArrayList<WorkCase>();

	public void addWorkCase(WorkCase wcase)
	{
		workCases.add(wcase);
		tabbedPane.addTab(wcase.getName(), null, wcase.getPanel(), wcase.getInfo());
	}
	
	public void removeWorkCase(WorkCase wcase)
	{
		int index = workCases.indexOf(wcase);
		if (index == -1)
			return;
		removeWorkCase(index);
	}
	
	public void removeWorkCase(int index)
	{
		workCases.remove(index);
		tabbedPane.remove(index);
	}
	
	
	public void addMoleculeCase(IAtomContainer mol, String name, String info)
	{
		WorkCase wcase = new WorkCase(mol, name, info);
		addWorkCase(wcase);
	}
	
	
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}
	
	public ArrayList<WorkCase> getWorkCases(){
		return workCases;
	}
	
	
	
	
}
