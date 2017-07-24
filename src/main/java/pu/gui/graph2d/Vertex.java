package pu.gui.graph2d;

public class Vertex 
{
	public static enum VertexStatus {
		EXPANDED, CLOSED, EXPANDED_INACTIVE, CLOSED_INACTIVE, HIDDEN
	}
	
	public VertexFormat format = null;
	public VertexParameters params = null;
	public VertexZone zone = null;
	public VertexStatus status = VertexStatus.EXPANDED;
	
	public Object vertexObject = null;
	
}
