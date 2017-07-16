package pu.io;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class FileUtilities 
{
	public static List<String> readSmilesSetFromFile(File file) throws Exception
	{
		try
		{	
			RandomAccessFile f = new RandomAccessFile(file,"r");			
			long length = f.length();
			List<String> smilesList = new ArrayList<String>();
			
			while (f.getFilePointer() < length)
			{	
				String line = f.readLine();
				line = line.trim();
				if (line.equals(""))
					continue;
				
				smilesList.add(line);
			}
			
			f.close();
			return smilesList;
			
		}
		catch (Exception e)
		{	
			return null;
		}
	}
}
