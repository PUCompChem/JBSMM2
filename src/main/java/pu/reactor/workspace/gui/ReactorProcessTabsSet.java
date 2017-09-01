package pu.reactor.workspace.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import pu.reactor.workspace.BasicReactorProcess;
import pu.reactor.workspace.IProcess;

/**
 * Created by gogo on 28.7.2017 г..
 */
public class ReactorProcessTabsSet extends JTabbedPane 
{
	public List<IProcess> processes = new ArrayList<IProcess>();
	
	
	public void addProcess(IProcess process)
	{
		if (process instanceof BasicReactorProcess)
		{
			BasicReactorProcess brp = (BasicReactorProcess) process;
			this.add(brp.name, brp.panel);
		}
	}
	
	/*	
	public void add(BasicReactorProcessPanel processPanel){
        this.add(processPanel.getProcessName(), processPanel);
    }
    */
}
