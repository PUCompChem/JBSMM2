package pu.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.Stack;











import org.openscience.cdk.interfaces.IAtomContainer;
//import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import pu.qm.AppSlater;
import pu.reactor.json.WorkspaceJsonParser;
import pu.reactor.workspace.Workspace;
import ambit2.smarts.SmartsHelper;
import ambit2.smarts.SmartsManager;
import ambit2.smarts.SMIRKSTransform;



public class VariousTests 
{

	
	static SmartsManager man = new SmartsManager(SilentChemObjectBuilder.getInstance());
	
	public static void main(String[] args) throws Exception 
	{
				
		//testStack();
		
		//boolSearch("CCCN", "CCCNCC");
		
		testReadWorkspaceJson("/test-workspace.json");
		
		//testResources();
		
	}
	
	
	public static void testStack() 
	{
		Stack<Integer> st1 = new Stack<Integer>();
		for (int i = 0; i < 5; i++)
			st1.push(i);
		
		Stack<Integer> st2 = new Stack<Integer>();
		st2.addAll(st1);;
		
		Stack<Integer> st3 = (Stack<Integer>) st1.clone();
		
		while (!st1.isEmpty())
			System.out.print("  " + st1.pop());
		System.out.println();
		
		while (!st2.isEmpty())
			System.out.print("  " + st2.pop());
		System.out.println();
		
		while (!st3.isEmpty())
			System.out.print("  " + st3.pop());
		System.out.println();
		
	}
	
	
	
	public static void boolSearch(String smarts, String smiles) throws Exception
	{	
		IAtomContainer mol = SmartsHelper.getMoleculeFromSmiles(smiles);	
		
		man.setQuery(smarts);
		if (!man.getErrors().equals(""))
		{
			System.out.println(man.getErrors());
			return;
		}		
		boolean res = man.searchIn(mol);
		System.out.println("Searching " + smarts + " in " + smiles + "  -->  " + res);
	}
	
	public static void dummyTest()
	{
		SMIRKSTransform.dummy();
	}
	
	public static void testReadWorkspaceJson(String fileName) throws Exception
	{
		WorkspaceJsonParser wsParser = new WorkspaceJsonParser();
		Workspace ws = wsParser.loadFromJSON(new File(fileName));
		
		if (!wsParser.getErrors().isEmpty())
		{	
			System.out.println("Parsing errors");
			for (int i = 0; i < wsParser.getErrors().size(); i++)
				System.out.println(wsParser.getErrors().get(i));
		}		
		
		System.out.println(ws.toJsonString());
	}
	
	public static void testResources() throws Exception
	{
		VariousTests vt = new VariousTests();
		URL resource = vt.getClass().getClassLoader().getResource("pu/test-workspace.json");
		
		File file = new File(resource.getFile());
		
		RandomAccessFile f = new RandomAccessFile(file,"r");			
		long length = f.length();
		while (f.getFilePointer() < length)
		{	
			String line = f.readLine();
			System.out.println(line);
		}
		
	}

}
