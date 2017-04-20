package pu.reactor.workspace;

import java.util.List;

public interface IProcessNode 
{	
	public String getID();
	public IProcessNode getParent();
	public List<IProcessNode> getChildren();
	public List<INodeConnection> getConnections();
	public void addChild(IProcessNode node, INodeConnection connection);
	public void removeChild(IProcessNode node);
}
