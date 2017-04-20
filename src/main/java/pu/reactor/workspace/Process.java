package pu.reactor.workspace;

public class Process 
{
	public IProcessNode rootNode = null;
	
	public String toJsonString()
	{
		
		String endLine = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("{" + endLine);		
		//TODO
		
		sb.append("}" + endLine);
		return sb.toString();
	}
}
