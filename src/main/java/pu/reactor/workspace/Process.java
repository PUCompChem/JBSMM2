package pu.reactor.workspace;

public class Process 
{
	public IProcessNode rootNode = null;
	
	//TODO mapping/links between Processes and GUI components (JPanesl etc. ) 
	
	
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
