package pu.reactor.workspace.gui;

import javax.swing.JPanel;

import pu.reactor.workspace.IProcess;

public abstract class ProcessPanel extends JPanel 
{
	public abstract IProcess getProcess();
	public abstract void updatePanel();
}
