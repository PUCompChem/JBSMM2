package pu.test;

import pu.helpers.StructureSetUtils;

public class TestStructureSetUtils {

	public static void main(String[] args) throws Exception
	{
		testDuplications(args[0], args[1]);
	}
	
	public static void testDuplications(String strFile1, String strFile2) throws Exception
	{
		int n = StructureSetUtils.getNumberOfDublicatedNotations(strFile1, strFile2);
		System.out.println("Number of duplicated notations = " + n);
	}

}
