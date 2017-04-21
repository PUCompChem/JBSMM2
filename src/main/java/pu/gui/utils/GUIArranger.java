package pu.gui.utils;


import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Stack;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class GUIArranger 
{	
	private GUIBinNode rootNode = null;
	private JFrame frame = null;
	private HashMap<String, GUIArea> areas = new HashMap<String, GUIArea>();

	public GUIBinNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(GUIBinNode rootNode) {
		this.rootNode = rootNode;
	}
	
	public void setFrame(JFrame frame) {
		this.frame = frame;
		applyArrangerToFrame(frame);
	}

	public JFrame getFrame() {
		return frame;
	}
	
	private void applyArrangerToFrame(JFrame frame)
	{
		if (frame == null)
			return;
		
		areas.clear();
		if (rootNode == null)
			return;
		
		//Splitting the root node
		this.frame = frame;
		
		/*
		JSplitPane rootSplitPane = new JSplitPane();
		if (rootNode.isHorizontal())
			rootSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		else
			rootSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frame.getContentPane().add(rootSplitPane);
		rootNode.setSplitter(rootSplitPane);
		*/
		
		//Recursive approach to manage the binary tree of nodes 
		JComponent component = processBinNode(rootNode);
		frame.getContentPane().add(component);
		//setSpliterPositions(rootNode);
	}
	
	private JComponent processBinNode(GUIBinNode node)
	{	
		if (node.getGuiArea() != null)
		{
			//This is a terminal node
			GUIArea area = new GUIArea();
			area.setCompID(node.getName());
			area.setPanel(new JPanel());
			areas.put(node.getName(), area);
			JLabel lab = new JLabel(); lab.setText(node.getName()); area.getPanel().add(lab);
			area.getPanel().setBackground(Color.WHITE); 
			return area.getPanel();
		}
		
		//Set splitter in the node 
		JSplitPane splitPane = new JSplitPane();			
		if (node.isHorizontal())
			splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		else
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		node.setSplitter(splitPane);
		
		//Handle children recursively
		if (node.getChild1() != null)
		{
			JComponent child1 = processBinNode(node.getChild1());
			if (node.getSplitter().getOrientation() == JSplitPane.HORIZONTAL_SPLIT)
				node.getSplitter().setLeftComponent(child1);
			else
				node.getSplitter().setTopComponent(child1);
		}
		
		if (node.getChild2() != null)
		{
			JComponent child2 = processBinNode(node.getChild2());
			if (node.getSplitter().getOrientation() == JSplitPane.HORIZONTAL_SPLIT)
				node.getSplitter().setRightComponent(child2);
			else
				node.getSplitter().setBottomComponent(child2);
		}
		
		return splitPane;
		
		/* - old code
		//Creating areas
		if (node.getChild1() == null)
		{	
			String areaName = node.getAreaName1();
			GUIArea area = new GUIArea();
			area.setCompID(areaName);
			area.setPanel(new JPanel());
			areas.put(areaName, area);
			if (node.getSplitter().getOrientation() == JSplitPane.HORIZONTAL_SPLIT)
				node.getSplitter().setLeftComponent(area.getPanel());
			else
				node.getSplitter().setTopComponent(area.getPanel());
		}
		
		if (node.getChild2() == null)
		{	
			String areaName = node.getAreaName2();
			GUIArea area = new GUIArea();
			area.setCompID(areaName);
			area.setPanel(new JPanel());
			areas.put(areaName, area);
			
			//if (node.getSplitter() == null)
			//	System.out.println(" node.getSplitter() = null ");
			
			if (node.getSplitter().getOrientation() == JSplitPane.HORIZONTAL_SPLIT)
				node.getSplitter().setRightComponent(area.getPanel());
			else
				node.getSplitter().setBottomComponent(area.getPanel());			
		}
		
		
		//recursion for new splits
		if (node.getChild1() != null)
		{
			GUIBinNode childNode = node.getChild1();
			JSplitPane childSplitPane = new JSplitPane();			
			if (childNode.isHorizontal())
				childSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			else
				childSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			
			childNode.setSplitter(childSplitPane);
			
			//Adding the new splitter(child1) into the left/top
			if (node.getSplitter().getOrientation() == JSplitPane.HORIZONTAL_SPLIT)
				node.getSplitter().setLeftComponent(childSplitPane);
			else
				node.getSplitter().setTopComponent(childSplitPane);
			
			//recursion
			processBinNode(childNode);
		}
		
		if (node.getChild2() != null)
		{
			GUIBinNode childNode = node.getChild2();
			JSplitPane childSplitPane = new JSplitPane();
			if (childNode.isHorizontal())
				childSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			else
				childSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			
			childNode.setSplitter(childSplitPane);
			
			//Adding the new splitter(child2) into the left/top
			if (node.getSplitter().getOrientation() == JSplitPane.HORIZONTAL_SPLIT)
				node.getSplitter().setRightComponent(childSplitPane);
			else
				node.getSplitter().setBottomComponent(childSplitPane);
			
			//recursion
			processBinNode(childNode);
		}
		
		return null;
		*/
	}
	
		
	public void setSpliterPositions()
	{	
		setSpliterPositions(rootNode);
		
		//System.out.println("  w = " + rootNode.getSplitter().getSize().width + "  h = " + rootNode.getSplitter().getSize().height );
		//rootNode.getSplitter().setDividerLocation(200);
	}
	
	private void setSpliterPositions(GUIBinNode node)
	{	
		JSplitPane splitter = node.getSplitter();
		if (splitter == null)
			return;
		
		if (node.isHorizontal())
		{
			int pos = (int) (0.01*node.getRatio()*splitter.getSize().width);
			System.out.println("pos = " + pos + "  ratio = " + node.getRatio() + "  size = " + splitter.getSize().width + "  area = " + node.getName());
			splitter.setDividerLocation(pos);
			splitter.updateUI();
		}
		else
		{
			int pos = (int) (0.01*node.getRatio()*splitter.getSize().height);
			System.out.println("pos = " + pos + "  ratio = " + node.getRatio() + "  size = " + splitter.getSize().height + "  area = " + node.getName());
			splitter.setDividerLocation(pos);
			splitter.updateUI();
		}			
		
		//recursion
		if (node.getChild1() != null)
			setSpliterPositions(node.getChild1());
		
		if (node.getChild2() != null)
			setSpliterPositions(node.getChild2());		
	}
	
	/*
	public void resizeFrameAndResplitPanes(int xSize, int ySize)
	{
		//TODO
	}
	*/
	
	public void setSplitProportionsFromCurrentSize()
	{
		//TODO
	}
	
}
