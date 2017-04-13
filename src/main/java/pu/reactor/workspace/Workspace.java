package pu.reactor.workspace;

import pu.gui.utils.GUIBinNode;

public class Workspace 
{
	
	public boolean test = false;
	public GUIBinNode rootAreaNode = null;
	
	
	public String toJsonString()
	{
		
		String endLine = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("{" + endLine);		
		sb.append("\tGUI_AREAS" + endLine);
		sb.append("\t{" + endLine);
		sb.append("\t\t\"TEST\":" + test);
		
		
		
		sb.append("\t}" + endLine);
		
		
		sb.append("}" + endLine);
		return sb.toString();
	}
}
