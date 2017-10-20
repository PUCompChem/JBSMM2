package pu.helpers;

import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Set;

import ambit2.base.data.StructureRecord;

public class StructureSetUtils 
{
	public static int getNumberOfDublicatedNotations(String fileName1, String fileName2) throws Exception
	{
		int nDup = 0;
		RandomAccessFile f;
		Set<String> strset1 = new HashSet<String>();
		
		//Load notations from file1
		f = new RandomAccessFile(fileName1,"r");
		long length = f.length();
		
		while (f.getFilePointer() < length)
		{
			String line = f.readLine();
			strset1.add(line.trim());
		}
		f.close();
		
		//Check notations from file2 against file1
		f = new RandomAccessFile(fileName2,"r");
		length = f.length();

		while (f.getFilePointer() < length)
		{
			String line = f.readLine();
			if (strset1.contains(line.trim()))
				nDup++;
		}
		f.close();
		
		return nDup;
	}
	
	public static StructureRecord getStructureRecordFromString(String str)
	{
		StructureRecord r = new StructureRecord();
		r.setSmiles(str);
		//TODO recognize str format
		return r;
	}
}
