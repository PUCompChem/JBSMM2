package pu.test;

import pu.qm.IOUtils;
import pu.qm.SlaterIntegral;


public class Test_qm 
{
	public static void main(String[] args) throws Exception
	{
		testSlater01("D:/slater.txt");
	}
	
	public static void testSlater01(String paramsFile) throws Exception
	{
		IOUtils io = new IOUtils();
		SlaterIntegral sIntergal = io.loadSlaterIntegral(paramsFile);
		
		if (io.errors.isEmpty())
		{
			System.out.println("Parameter file OK");
		}
		else
		{
			System.out.println("Parameter file errors:");
			for (int i = 0; i < io.errors.size(); i++)
				System.out.println(io.errors.get(i));
		}
	}
}
