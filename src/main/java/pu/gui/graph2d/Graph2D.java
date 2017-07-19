package pu.gui.graph2d;

import java.util.ArrayList;
import java.util.List;

public class Graph2D 
{
	public static enum GraphType {
		TREE, DEAFULT
	}
	
	protected List<Vertex> vertices = new ArrayList<Vertex>();
	protected List<Edge> edges = new ArrayList<Edge>();
	protected Vertex treeRoot = null;
	
	public Vertex getTreeRoot() {
		return treeRoot;
	}

	public void setTreeRoot(Vertex treeRoot) {
		this.treeRoot = treeRoot;
	}

	protected GraphType graphType = GraphType.TREE;
	protected IGeometryAlgorithm algorithm = null;
	protected EdgeFormat defaultEdgeFormat = null;
	protected VertexFormat defaultVertexFormat = null;
	
	
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
	
	
}
