package pu.reactor.workspace.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import pu.reactor.workspace.BasicReactorProcess;
import pu.reactor.workspace.IProcess;

/**
 * Created by gogo on 28.7.2017 г..
 */
public class ReactorProcessTabsSet extends JTabbedPane 
{
	//public List<IProcess> processes = new ArrayList<IProcess>();
	public Map<IProcess, ProcessPanel> processes = new HashMap<IProcess, ProcessPanel>();
	
	
	public void addProcessPanel(ProcessPanel panel)
	{	
		IProcess process = panel.getProcess();
		processes.put(process, panel);
		this.add(process.getName(), panel);
		/*
		if (process instanceof BasicReactorProcess)
		{
			BasicReactorProcess brp = (BasicReactorProcess) process;
			this.add(brp.name, brp.panel process.getPanel());
		}
		*/
	}
	
	
	//TODO handle tab delete event and remove element from the process map
	
	
}
