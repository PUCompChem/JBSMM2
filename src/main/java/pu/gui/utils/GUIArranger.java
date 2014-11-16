package pu.gui.utils;


import java.util.Stack;
import java.util.HashMap;




import javax.swing.JFrame;
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
		JSplitPane rootSplitPane = new JSplitPane();
		if (rootNode.isHorizontal())
			rootSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		else
			rootSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frame.getContentPane().add(rootSplitPane);
		rootNode.setSplitter(rootSplitPane);
		
		//Recursive approach to manage the binary tree 
		processBinNode(rootNode);
	}
	
	private void processBinNode(GUIBinNode node)
	{	
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
		if (node.isHorizontal())
		{
			int pos = (int) (0.01*node.getRatio()*splitter.getSize().width);
			//System.out.println("pos = " + pos + "  ratio = " + node.getRatio());
			splitter.setDividerLocation(pos);
		}
		else
		{
			int pos = (int) (0.01*node.getRatio()*splitter.getSize().height);
			//System.out.println("pos = " + pos + "  ratio = " + node.getRatio());
			splitter.setDividerLocation(pos);
		}
			
		
		//recursive approach
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
