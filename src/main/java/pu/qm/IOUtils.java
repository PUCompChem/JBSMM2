package pu.qm;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import pu.helpers.Tokenizer;

public class IOUtils 
{
	public ArrayList<String> errors = new ArrayList<String>();
	
	SlaterIntegral curSIntegral = null;
	int curLine = 0;
	int additionalLinesNeeded = 0;
	ArrayList<String> additionalLines = new ArrayList<String>();
	
	//Loeading flags
	boolean Flag_N = false;
	boolean Flag_M = false;
	boolean Flag_T = false;
	boolean Flag_Z1 = false;
	boolean Flag_Z2 = false;
	boolean Flag_C1 = false;
	boolean Flag_C2 = false;
	boolean Flag_Orbital = false;
	
	/*
	protected String curSectiionName = null;
	protected int curIntValue;
	protected int curIntArray[];
	protected double curDoubleValue;
	protected double curDoubleArray[];
	*/
	
	public SlaterIntegral loadSlaterIntegral(String paramsFile) throws Exception
	{
		errors.clear();
		File file = new File(paramsFile);
		RandomAccessFile f;
		
		try{
			f = new RandomAccessFile(file,"r");
		}
		catch(Exception x)
		{	
			errors.add("");
			return null;
		}
					
		long length = f.length();
		SlaterIntegral sIntegral = new SlaterIntegral();
		curSIntegral = sIntegral;
		
		int n = 0;
		while (f.getFilePointer() < length)
		{
			String line = f.readLine();
			n++;
			line = line.trim();
			if (line.isEmpty())
				continue; //Empty line is skipped
			
			curLine = n;
			handleLine(line);
			
			if (additionalLinesNeeded > 0)
			{
				for (int i = 0; i < additionalLinesNeeded; i++)
				{
					if (f.getFilePointer() < length)
					{	
						String line0 = f.readLine();
						n++;
						line0 = line0.trim();
						if (line0.isEmpty())
							continue; //Empty line is skipped
						additionalLines.add(line0);
					}
					else 
						break;
				}
				handleAddionalLines(line);
			}
		}
		
		f.close();
		return sIntegral;
	}
	
	void handleLine(String line)
	{
		additionalLinesNeeded = 0;
		additionalLines.clear();
		
		int pos = line.indexOf("=");
		if (pos == -1)
		{	
			errors.add("Line " + curLine + " is incorrect: \"" + line +"\"");
			return;
		}
		
		if (pos == line.length()-1)
		{	
			errors.add("Line " + curLine + " is incorrect: \"" + line +"\"");
			return;
		}
		
		String varName = line.substring(0, pos);
		varName = varName.trim();
		
		if (varName.isEmpty())
		{	
			errors.add("Line " + curLine + " is incorrect: \"" + line +"\"");
			return;
		}
		
		String varValue = line.substring(pos+1);
		varValue = varValue.trim();
		
		if (varName.isEmpty())
		{	
			errors.add("Line " + curLine + " is incorrect: \"" + line +"\"");
			return;
		}
		
		//Handle the slater integral variables
		
		if (varName.equals("N"))
		{
			Integer iVal = getInteger(varValue);
			if (iVal == null)
			{	
				errors.add("Line " + curLine + " incorrect parameter N: \"" + line +"\"");
				return;
			}			
			if (iVal <= 0)
			{	
				errors.add("Line " + curLine + " incorrect parameter N is not positive: \"" + line +"\"");
				return;
			}			
			curSIntegral.N = iVal;
			Flag_N = true;
			return;
		}
		
		if (varName.equals("M"))
		{
			Integer iVal = getInteger(varValue);
			if (iVal == null)
			{	
				errors.add("Line " + curLine + " incorrect parameter M: \"" + line +"\"");
				return;
			}			
			if (iVal <= 0)
			{	
				errors.add("Line " + curLine + " incorrect parameter M is not positive: \"" + line +"\"");
				return;
			}			
			curSIntegral.M = iVal;
			Flag_M = true;
			return;
		}
		
		if (varName.equals("T"))
		{
			Integer iVal = getInteger(varValue);
			if (iVal == null)
			{	
				errors.add("Line " + curLine + " incorrect parameter T: \"" + line +"\"");
				return;
			}			
			if (iVal <= 0)
			{	
				errors.add("Line " + curLine + " incorrect parameter T is not positive: \"" + line +"\"");
				return;
			}			
			curSIntegral.T = iVal;
			curSIntegral.TT = curSIntegral.T + 1; 
			Flag_T = true;
			return;
		}
		
		if (varName.equals("TT"))
		{
			//noting is done
			return;
		}
		
		if (varName.equals("Z1"))
		{
			ArrayList<Double> darray = getDoubleArray(varValue);
			if (darray == null)
			{	
				errors.add("Line " + curLine + " incorrect array for parameter Z1: \"" + line +"\"");
				return;
			}
			if (!Flag_N)
			{	
				errors.add("Line " + curLine + " parameter Z1 is being defined before parameter N!");
				return;
			}
			if (darray.size() != curSIntegral.N)
			{	
				errors.add("Line " + curLine + " incorrect array size for parameter Z1. Different from N=" + curSIntegral.N);
				return;
			}
			
			curSIntegral.Z1 = new double[darray.size()];
			for (int i = 0; i < darray.size(); i++)
				curSIntegral.Z1[i] = darray.get(i);
			return;
		}
		
		if (varName.equals("Z2"))
		{
			ArrayList<Double> darray = getDoubleArray(varValue);
			if (darray == null)
			{	
				errors.add("Line " + curLine + " incorrect array for parameter Z2: \"" + line +"\"");
				return;
			}
			if (!Flag_M)
			{	
				errors.add("Line " + curLine + " parameter Z2 is being defined before parameter M!");
				return;
			}
			if (darray.size() != curSIntegral.M)
			{	
				errors.add("Line " + curLine + " incorrect array size for parameter Z2. Different from M=" + curSIntegral.M);
				return;
			}
			
			curSIntegral.Z2 = new double[darray.size()];
			for (int i = 0; i < darray.size(); i++)
				curSIntegral.Z2[i] = darray.get(i);
			return;
		}
		
		if (varName.equals("C1"))
		{
			ArrayList<Double> darray = getDoubleArray(varValue);
			if (darray == null)
			{	
				errors.add("Line " + curLine + " incorrect array for parameter C1: \"" + line +"\"");
				return;
			}
			if (!Flag_N)
			{	
				errors.add("Line " + curLine + " parameter C1 is being defined before parameter N!");
				return;
			}
			if (darray.size() != curSIntegral.N)
			{	
				errors.add("Line " + curLine + " incorrect array size for parameter C1. Different from N=" + curSIntegral.N);
				return;
			}
			
			curSIntegral.C1 = new double[darray.size()];
			for (int i = 0; i < darray.size(); i++)
				curSIntegral.C1[i] = darray.get(i);
			return;
		}
		
		if (varName.equals("C2"))
		{
			ArrayList<Double> darray = getDoubleArray(varValue);
			if (darray == null)
			{	
				errors.add("Line " + curLine + " incorrect array for parameter C2: \"" + line +"\"");
				return;
			}
			if (!Flag_M)
			{	
				errors.add("Line " + curLine + " parameter C2 is being defined before parameter M!");
				return;
			}
			if (darray.size() != curSIntegral.M)
			{	
				errors.add("Line " + curLine + " incorrect array size for parameter C2. Different from M=" + curSIntegral.M);
				return;
			}
			
			curSIntegral.C2 = new double[darray.size()];
			for (int i = 0; i < darray.size(); i++)
				curSIntegral.C2[i] = darray.get(i);
			return;
		}
		
		
		
		
		errors.add("Line " + curLine + " unrecognized parameter: \"" + line +"\"");
		//TODO
	}
	
	void handleAddionalLines(String line)
	{
		//TODO
	}
	
	
	Integer getInteger(String s)
	{
		try 
		{
			Integer i = Integer.parseInt(s);
			return i;
		}
		catch(Exception e){
		}
		return null;
	}
	
	Double getDouble(String s)
	{
		try 
		{
			Double d = Double.parseDouble(s);
			return d;
		}
		catch(Exception e){
		}
		return null;
	}
	
	ArrayList<Double> getDoubleArray(String s)
	{
		Tokenizer tok = new Tokenizer(s);
		ArrayList<Double> darray = new ArrayList<Double>();
		for (int i = 0; i < tok.tokenCount(); i++)
		{
			Double d = getDouble(tok.getToken(i));
			if (d == null)
				return null;
			
			darray.add(d);
		}
		
		return darray;
	}
	
}
