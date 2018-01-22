package pu.reactor.workspace;

import javax.swing.*;

import java.io.File;

public class Process implements IProcess
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

	@Override
	public void loadFromJson(File file) {

	}

	/*
	@Override
	public JPanel getPanel() {
		return null;
	}

	@Override
	public void setPanel(JPanel panel) {

	}
	*/

	@Override
	public void initProcess() {

	}

	@Override
	public void runProcess() {

	}

	@Override
	public void runProcessNextSteps(int nSteps) throws Exception 
	{
		//TODO
	}
	
	@Override
	public void runProcessNextSteps() throws Exception 
	{
		//TODO
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetProcess() throws Exception {
		// TODO Auto-generated method stub
		
	}

	/*	
	@Override
	public JPanel getParametersPanel() {
		// TODO Auto-generated method stub
		return null;
	}
	*/
}
