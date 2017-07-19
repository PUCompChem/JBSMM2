package pu.gui.graph2d;

import java.util.ArrayList;
import java.util.List;

public class Vertex 
{
	public List<Vertex> childVertices = new ArrayList<Vertex>();
	public List<Edge> childEdges = new ArrayList<Edge>();
	
	protected Object vertexObject = null;

	public Object getVertexObject() {
		return vertexObject;
	}

	public void setVertexObject(Object vertexObject) {
		this.vertexObject = vertexObject;
	}
	
	public void addChild(Vertex v, Edge e)
	{
		childVertices.add(v);
		childEdges.add(e);
	}
	
	public void removeChild(Vertex v)
	{
		int index = childVertices.indexOf(v);
		if (index != -1)
		{
			childVertices.remove(index);
			childEdges.remove(index);
		}
	}
	
	public void removeChild(int index)
	{	
		if (index >= 0 && index < childVertices.size())
		{
			childVertices.remove(index);
			childEdges.remove(index);
		}
	}
}
