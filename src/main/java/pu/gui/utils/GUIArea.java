package pu.gui.utils;

import javax.swing.JPanel;

public class GUIArea 
{
	private String compID;
	private boolean isTabbed = false;
	private JPanel panel = null;

	public String getCompID() {
		return compID;
	}

	public void setCompID(String compID) {
		this.compID = compID;
	}

	public boolean isTabbed() {
		return isTabbed;
	}

	public void setTabbed(boolean isTabbed) {
		this.isTabbed = isTabbed;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
}
