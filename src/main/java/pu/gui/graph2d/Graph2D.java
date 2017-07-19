package pu.gui.graph2d;

import java.util.ArrayList;
import java.util.List;

public class Graph2D 
{
	public static enum GraphType {
		TREE, DEAFULT
	}
	
	protected GraphType graphType = GraphType.TREE;
	protected List<Vertex> vertices = new ArrayList<Vertex>(); 

	public GraphType getGraphType() {
		return graphType;
	}

	public void setGraphType(GraphType graphType) {
		this.graphType = graphType;
	}
	
}
