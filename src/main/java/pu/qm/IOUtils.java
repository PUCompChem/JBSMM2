package pu.qm;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import pu.helpers.Tokenizer;

public class IOUtils 
{
	public ArrayList<String> errors = new ArrayList<String>();
	
	SlaterIntegral curSIntegral = null;
	int curLine = 0;
	int additionalLinesNeeded = 0;
	ArrayList<String> additionalLines = new ArrayList<String>();
	ArrayList<OrbitalPolynomial> targetOPList = null; 
	
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
		targetOPList = curSIntegral.additionalOrbitalPolynomials; 
		
		int n = 0;
		while (f.getFilePointer() < length)
		{
			String line = f.readLine();
			n++;
			
			line = handleComment(line);
			line = line.trim();
			
			if (line.isEmpty())
				continue; //Empty line is skipped
			
			curLine = n;
			int res = handleLine(line);
			
			if (res < 0)
				continue; //Error
				
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
	
	public List<OrbitalPolynomial> loadOrbitalPolynomials(String paramsFile) throws Exception
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
		
		return null;
	}
	
	
	int handleLine(String line)
	{
		additionalLinesNeeded = 0;
		additionalLines.clear();
		
		int pos = line.indexOf("=");
		if (pos == -1)
		{	
			errors.add("Line " + curLine + " is incorrect: \"" + line +"\"");
			return -1;
		}
		
		if (pos == line.length()-1)
		{	
			errors.add("Line " + curLine + " is incorrect: \"" + line +"\"");
			return -2;
		}
		
		String varName = line.substring(0, pos);
		varName = varName.trim();
		
		if (varName.isEmpty())
		{	
			errors.add("Line " + curLine + " is incorrect: \"" + line +"\"");
			return -3;
		}
		
		String varValue = line.substring(pos+1);
		varValue = varValue.trim();
		
		if (varName.isEmpty())
		{	
			errors.add("Line " + curLine + " is incorrect: \"" + line +"\"");
			return -4;
		}
		
		//Handle the slater integral variables
		
		if (varName.equals("N"))
		{
			Integer iVal = getInteger(varValue);
			if (iVal == null)
			{	
				errors.add("Line " + curLine + " incorrect parameter N: \"" + line +"\"");
				return -5;
			}			
			if (iVal <= 0)
			{	
				errors.add("Line " + curLine + " incorrect parameter N is not positive: \"" + line +"\"");
				return -6;
			}			
			curSIntegral.N = iVal;
			Flag_N = true;
			return 0;
		}
		
		if (varName.equals("M"))
		{
			Integer iVal = getInteger(varValue);
			if (iVal == null)
			{	
				errors.add("Line " + curLine + " incorrect parameter M: \"" + line +"\"");
				return -7;
			}			
			if (iVal <= 0)
			{	
				errors.add("Line " + curLine + " incorrect parameter M is not positive: \"" + line +"\"");
				return -8;
			}			
			curSIntegral.M = iVal;
			Flag_M = true;
			return 0;
		}
		
		if (varName.equals("T"))
		{
			Integer iVal = getInteger(varValue);
			if (iVal == null)
			{	
				errors.add("Line " + curLine + " incorrect parameter T: \"" + line +"\"");
				return -9;
			}			
			if (iVal <= 0)
			{	
				errors.add("Line " + curLine + " incorrect parameter T is not positive: \"" + line +"\"");
				return -10;
			}			
			curSIntegral.T = iVal;
			curSIntegral.TT = curSIntegral.T + 1; 
			Flag_T = true;
			return 0;
		}
		
		if (varName.equals("TT"))
		{
			//noting is done
			return 0;
		}
		
		if (varName.equals("Z1"))
		{
			ArrayList<Double> darray = getDoubleArray(varValue);
			if (darray == null)
			{	
				errors.add("Line " + curLine + " incorrect array for parameter Z1: \"" + line +"\"");
				return -11;
			}
			if (!Flag_N)
			{	
				errors.add("Line " + curLine + " parameter Z1 is being defined before parameter N!");
				return -12;
			}
			if (darray.size() != curSIntegral.N)
			{	
				errors.add("Line " + curLine + " incorrect array size for parameter Z1. Different from N=" + curSIntegral.N);
				return -13;
			}
			
			curSIntegral.Z1 = new double[darray.size()];
			for (int i = 0; i < darray.size(); i++)
				curSIntegral.Z1[i] = darray.get(i);
			return 0;
		}
		
		if (varName.equals("Z2"))
		{
			ArrayList<Double> darray = getDoubleArray(varValue);
			if (darray == null)
			{	
				errors.add("Line " + curLine + " incorrect array for parameter Z2: \"" + line +"\"");
				return -14;
			}
			if (!Flag_M)
			{	
				errors.add("Line " + curLine + " parameter Z2 is being defined before parameter M!");
				return -15;
			}
			if (darray.size() != curSIntegral.M)
			{	
				errors.add("Line " + curLine + " incorrect array size for parameter Z2. Different from M=" + curSIntegral.M);
				return -16;
			}
			
			curSIntegral.Z2 = new double[darray.size()];
			for (int i = 0; i < darray.size(); i++)
				curSIntegral.Z2[i] = darray.get(i);
			return 0;
		}
		
		if (varName.equals("C1"))
		{
			ArrayList<Double> darray = getDoubleArray(varValue);
			if (darray == null)
			{	
				errors.add("Line " + curLine + " incorrect array for parameter C1: \"" + line +"\"");
				return -17;
			}
			if (!Flag_N)
			{	
				errors.add("Line " + curLine + " parameter C1 is being defined before parameter N!");
				return -18;
			}
			if (darray.size() != curSIntegral.N)
			{	
				errors.add("Line " + curLine + " incorrect array size for parameter C1. Different from N=" + curSIntegral.N);
				return -19;
			}
			
			curSIntegral.C1 = new double[darray.size()];
			for (int i = 0; i < darray.size(); i++)
				curSIntegral.C1[i] = darray.get(i);
			return 0;
		}
		
		if (varName.equals("C2"))
		{
			ArrayList<Double> darray = getDoubleArray(varValue);
			if (darray == null)
			{	
				errors.add("Line " + curLine + " incorrect array for parameter C2: \"" + line +"\"");
				return -20;
			}
			if (!Flag_M)
			{	
				errors.add("Line " + curLine + " parameter C2 is being defined before parameter M!");
				return -21;
			}
			if (darray.size() != curSIntegral.M)
			{	
				errors.add("Line " + curLine + " incorrect array size for parameter C2. Different from M=" + curSIntegral.M);
				return -22;
			}
			
			curSIntegral.C2 = new double[darray.size()];
			for (int i = 0; i < darray.size(); i++)
				curSIntegral.C2[i] = darray.get(i);
			return 0;
		}
		
		if (varName.equals("Orbital"))
		{				
			curSIntegral.orbital = varValue;			 
			Flag_Orbital = true;
			return 0;
		}
		
		if (varName.equals("Polynomial"))
		{				
			Tokenizer tok = new Tokenizer(varValue); 
			if (tok.tokenCount() != 2)
			{	
				errors.add("Line " + curLine + " incorrect Polynomial header line: \"" + line +"\"");
				return -23;
			}
			
			Integer iVal = getInteger(tok.getToken(1));
			if (iVal == null)
			{	
				errors.add("Line " + curLine + " incorrect Polynomial header line. Incorrect polynom order: \"" + line +"\"");
				return -24;
			}			
			if (iVal <= 0)
			{	
				errors.add("Line " + curLine + " incorrect Polynomial header line. Nagative polynom order: \"" + line +"\"");
				return -25;
			}
			
			additionalLinesNeeded = iVal;
			return 0;
		}
		
		errors.add("Line " + curLine + " unrecognized parameter: \"" + line +"\"");
		return -100;
	}
	
	void handleAddionalLines(String line)
	{
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
		
		
		if (varName.equals("Polynomial"))
		{				
			Tokenizer tok = new Tokenizer(varValue); 
			if (tok.tokenCount() != 2)
			{	
				errors.add("Line " + curLine + " incorrect Polynomial header line: \"" + line +"\"");
				return;
			}
			
			Integer nObj = getInteger(tok.getToken(1));
			if (nObj == null)
			{	
				errors.add("Line " + curLine + " incorrect Polynomial header line. Incorrect polynom order: \"" + line +"\"");
				return;
			}
			
			if (nObj > additionalLines.size())
			{	
				errors.add("Line " + curLine + " Insufficient number of lines for Polynomial section: \"" + line +"\"");
				return;
			}
			
			OrbitalPolynomial orbPol = new OrbitalPolynomial();
			orbPol.orbital = tok.getToken(0);
			
			orbPol.coeffs = new double [nObj][nObj];
			for (int i = 0; i < nObj; i++ )
			{
				ArrayList<Double> darray = getDoubleArray(additionalLines.get(i));
				if (darray == null)
				{
					errors.add("Line " + curLine + "  \"" + line +"\"" 
							+ "\n  Incorrect matrix row #" + (i+1) + "  " + additionalLines.get(i));
					continue;
				}
				
				if (darray.size() != nObj)
				{
					errors.add("Line " + curLine + "  \"" + line +"\"" 
							+ "\n  Incorrect matrix row #" + (i+1) + "  " + additionalLines.get(i));
					continue;
				}
				
				for (int k = 0; k < nObj; k++)
					orbPol.coeffs[i][k] = darray.get(k);
			}
			
		}
		
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
	
	String handleComment(String line)
	{
		int pos = line.indexOf("#");
		if (pos == -1)
			return line;
		else
			return line.substring(0,pos);
	}
	
}
