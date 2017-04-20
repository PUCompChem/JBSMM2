package pu.reactor.workspace;

public class SimpleNodeConnection implements INodeConnection 
{
	String info = null;
	
	public SimpleNodeConnection (IProcessNode n1, IProcessNode n2)
	{
		node1 = n1;
		node2 = n2;
	}
	
	public SimpleNodeConnection (IProcessNode n1, IProcessNode n2, String info)
	{
		node1 = n1;
		node2 = n2;
		this.info = info;
	}
	
	IProcessNode node1 = null;
	IProcessNode node2 = null;
	
	public IProcessNode getNode1() {
		return node1;
	}

	public IProcessNode getNode2() {
		return node2;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
