package pu.qm;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class IOUtils 
{
	public ArrayList<String> errors = new ArrayList<String>();
	
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
		
		int n = 0;
		while (f.getFilePointer() < length)
		{
		
		}
		
		
		f.close();
		return null;
	}
	
}
