package pu.gui.graph2d;

import java.util.ArrayList;
import java.util.List;

import pu.gui.graph2d.algorithms.BasicTreeGeometryAlgorithm;

public class Graph2D 
{
	public static enum GraphType {
		TREE, DEAFULT
	}
	
	protected List<Vertex> vertices = new ArrayList<Vertex>();
	protected List<Edge> edges = new ArrayList<Edge>();
	protected Vertex treeRoot = null;
	
	protected GraphType graphType = GraphType.TREE;
	protected IGeometryAlgorithm algorithm = null;
	protected EdgeFormat defaultEdgeFormat = null;
	protected VertexFormat defaultVertexFormat = null;
	
	
	public Graph2D()
	{
		init();
	}
	
	void init()
	{
		algorithm = new BasicTreeGeometryAlgorithm();
	}
	
	public Vertex getTreeRoot() {
		return treeRoot;
	}

	public void setTreeRoot(Vertex treeRoot) {
		this.treeRoot = treeRoot;
	}

	
	
	public EdgeFormat getDefaultEdgeFormat() {
		return defaultEdgeFormat;
	}

	public void setDefaultEdgeFormat(EdgeFormat defaultEdgeFormat) {
		this.defaultEdgeFormat = defaultEdgeFormat;
	}

	public VertexFormat getDefaultVertexFormat() {
		return defaultVertexFormat;
	}

	public void setDefaultVertexFormat(VertexFormat defaultVertexFormat) {
		this.defaultVertexFormat = defaultVertexFormat;
	}
	
	public IGeometryAlgorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(IGeometryAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public GraphType getGraphType() {
		return graphType;
	}

	public void setGraphType(GraphType graphType) {
		this.graphType = graphType;
	}
	
	public void renderGraph() {
		//TODO
	}
	
	
}
