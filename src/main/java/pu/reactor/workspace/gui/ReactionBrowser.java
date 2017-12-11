package pu.reactor.workspace.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.openscience.cdk.interfaces.IAtomContainer;

import pu.gui.utils.chemtable.SmartChemTable;

public class ReactionBrowser extends JFrame 
{
	private static final long serialVersionUID = 7769828597142198311L;
	
	enum BrowserMode {
		REACTION_ONLY, REACTION_APPLICATION
	}
	
	BrowserMode mode = BrowserMode.REACTION_APPLICATION;
	SmartChemTable chemTable = null;
	IAtomContainer target = null;
	String targetString = "";
	
	//Table columns config
	boolean showRowNumber = true;
	
	
	public ReactionBrowser() 
	{
		//this.setLayout(new BorderLayout());
		initGUI();
		setSize(new Dimension(400,600));

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void initGUI() {
		
	}

	public BrowserMode getMode() {
		return mode;
	}

	public void setMode(BrowserMode mode) {
		this.mode = mode;
	}

	public SmartChemTable getChemTable() {
		return chemTable;
	}

	public void setChemTable(SmartChemTable chemTable) {
		this.chemTable = chemTable;
	}

	public IAtomContainer getTarget() {
		return target;
	}

	public void setTarget(IAtomContainer target) {
		this.target = target;
	}

	public String getTargetString() {
		return targetString;
	}

	public void setTargetString(String targetString) {
		this.targetString = targetString;
	}

	public boolean isShowRowNumber() {
		return showRowNumber;
	}

	public void setShowRowNumber(boolean showRowNumber) {
		this.showRowNumber = showRowNumber;
	}

}
