package pu.reactor.workspace;

import java.util.List;

public class ProcessNode implements IProcessNode 
{
	String id = null;
	IProcessNode parent = null;
	List<IProcessNode> children = null;
	List<INodeConnection> connections = null;
	
	public String getID() {
		return id;
	}

	public IProcessNode getParent() {		
		return parent;
	}

	public List<IProcessNode> getChildren() {
		return children;
	}

	public List<INodeConnection> getConnections() {
		return connections;
	}

	public void addChild(IProcessNode node, INodeConnection connection) {
		children.add(node);
		connections.add(connection);
	}

	public void removeChild(IProcessNode node) {
		int index = children.indexOf(node);
		if (index >=0 )
		{	
			children.remove(index);
			connections.remove(index);
		}
	}

}

