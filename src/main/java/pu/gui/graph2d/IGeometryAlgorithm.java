package pu.gui.graph2d;

public interface IGeometryAlgorithm 
{
	public void setGraph(Graph2D graph);
	public Graph2D getGraph();
	public void setRoot(Vertex v);
	public void addVertexChild(Vertex parent, Vertex child);
	public void remoceVertexChild(Vertex parent, Vertex child);
	
	//TODO add geometry operations: zooming, scaling, adding/removinf nodes
	
}
