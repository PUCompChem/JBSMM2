package pu.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.openscience.cdk.tools.LoggingTool;


public class TestCases extends TestCase
{
	public LoggingTool logger;
	
	public TestCases() 
	{   
		logger = new LoggingTool(this);
	}
	
	public static Test suite() {
		return new TestSuite(TestCases.class);
	}
	
	
	public void testFakeTest() throws Exception 
	{
		//there should be at least one test
		System.out.println("This is a fake test");
		int x = 3;
		assertEquals(x,3);
	}
}
