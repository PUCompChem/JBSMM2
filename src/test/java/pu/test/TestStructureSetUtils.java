package pu.test;

import pu.helpers.StructureSetUtils;

public class TestStructureSetUtils {

	public static void main(String[] args) throws Exception
	{
		testDuplications("/temp3/test__1.txt", "/temp3/test__2.txt");
	}
	
	public static void testDuplications(String strFile1, String strFile2) throws Exception
	{
		int n = StructureSetUtils.getNumberOfDublicatedNotations(strFile1, strFile2);
		System.out.println("Number of duplicated notations = " + n);
	}

}
