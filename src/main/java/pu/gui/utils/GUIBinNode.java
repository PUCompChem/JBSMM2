/**
 * 
 */
package pu.gui.utils;

import javax.swing.JSplitPane;

public class GUIBinNode
{
	private GUIBinNode parent = null;
	private GUIBinNode child1 = null;
	private GUIBinNode child2 = null;
	private String areaName1 = null;
	private String areaName2 = null;
	private String name = null;
	private boolean isHorizontal = true;
	private int ratio = 50;
	public int parseAreaNum = 0;
	
	private JSplitPane splitter = null;		
	private GUIArea guiArea = null; //If this node is terminal then area field is set (i.e. it is not null)
	
	public GUIBinNode getParent() {
		return parent;
	}

	public void setParent(GUIBinNode parent) {
		this.parent = parent;
	}

	public GUIBinNode getChild1() {
		return child1;
	}

	public void setChild1(GUIBinNode child1) {
		this.child1 = child1;
	}

	public GUIBinNode getChild2() {
		return child2;
	}

	public void setChild2(GUIBinNode child2) {
		this.child2 = child2;
	}

	public boolean isHorizontal() {
		return isHorizontal;
	}

	public void setHorizontal(boolean isHorizontal) {
		this.isHorizontal = isHorizontal;
	}

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	
	public String generateNodeCode()
	{
		//Recursively call the children
		String s1,s2;
		if (child1 == null)
			s1 = areaName1;
		else
			s1 = child1.generateNodeCode();
		
		if (child2 == null)
			s2 = areaName2;
		else
			s2 = child2.generateNodeCode();
		
		String s = "split( " + (isHorizontal?"h":"v") + " " + ratio + " " + s1 + " " + s2 +" )";
		return s;
	}

	public String getAreaName1() {
		return areaName1;
	}

	public void setAreaName1(String areaName1) {
		this.areaName1 = areaName1;
	}

	public String getAreaName2() {
		return areaName2;
	}

	public void setAreaName2(String areaName2) {
		this.areaName2 = areaName2;
	}

	public JSplitPane getSplitter() {
		return splitter;
	}

	public void setSplitter(JSplitPane splitter) {
		this.splitter = splitter;
	}

	
	public GUIArea getGuiArea() {
		return guiArea;
	}

	public void setGuiArea(GUIArea guiArea) {
		this.guiArea = guiArea;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}