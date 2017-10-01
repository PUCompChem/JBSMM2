package pu.miscellaneous;

public class PotentialCli {

	public static void main(String[] args) 
	{
		// <standard> <column> <lines> <data file> <ini file>
		
		if (args.length < 5)
		{
			System.out.println("Incorrect number of arguments");
			return;
		}
		
		int standard = -1;
		int column = -1;
		int lines = -1;
		String dataFile = null;
		String iniFile  = null;
		
		try
		{
			
		}
		catch (Exception x)
		{
			System.out.println(x.getMessage());
			return;
		}

	}

}
