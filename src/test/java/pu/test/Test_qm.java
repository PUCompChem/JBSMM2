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
		sIntergal.loadPredefinedOrbitalPolynomials();
		
		if (io.errors.isEmpty())
		{
			System.out.println("Parameter file OK");
		}
		else
		{
			System.out.println("Parameter file errors:");
			for (int i = 0; i < io.errors.size(); i++)
				System.out.println(io.errors.get(i));
			
			return;
		}
		
		int res = sIntergal.checkOrbitalPolynomial();
		switch(res)
		{
		case 0:
			System.out.println("Oprbital polynomial is OK");
			break;
		case 1:
			System.out.println("Oprbital parameter is not set");
			break;
		case 2:
			System.out.println("Oprbital polynomial is missing for " + sIntergal.orbital);
			break;	
		}
		
		
		System.out.println(sIntergal.toString());
	}
}
