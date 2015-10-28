package pu.qm;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

public class AppSlater 
{	
	public static void main(String[] args) throws Exception
	{
		Options options = getOptions();
		parseCommand(options, args);
	}
	
	protected static Options getOptions()
	{
		Options options = new Options();
		
		Option help   = OptionBuilder
        .withLongOpt("help")        
        .withDescription("print this message")        
        .create( "h" );
		options.addOption(help);
		
		Option input   = OptionBuilder
        .hasArg()
        .withLongOpt("input")
        .withArgName("file")
        .withDescription("Input parameter file")        
        .create( "i" ); 
		options.addOption(input);
		
		Option output   = OptionBuilder
        .hasArg()
        .withLongOpt("output")
        .withArgName("file")
        .withDescription("Output file")        
        .create( "o" );
		options.addOption(output);
		
		Option polinomCoeafs   = OptionBuilder
        .hasArg()
        .withLongOpt("polynomilals")
        .withArgName("file")
        .withDescription("A file with orbital polynomial coefficents")        
        .create( "p" );
		options.addOption(polinomCoeafs);
		
		return options;
	}
	
	
	protected static void parseCommand(Options options, String[] args) throws Exception
	{		
		CommandLineParser parser = new PosixParser();
		CommandLine line = null;
		
		try{
			line = parser.parse(options, args);
		}
		catch(Exception e)
		{
			System.out.println("Incorrect command line:\n   " + e.getMessage());
			return;
		}
		
		Option opt[] = line.getOptions();
		
		if (opt.length == 0)
		{	
			printHelp(options, null);
			return;
		}	
		
		if (line.hasOption("h"))
		{
			System.out.println("---------------------");
			printHelp(options, null);
			return;
		}
		
		
		String inputFile = null;
		String outputFile = null;
		String polynomFile = null;
		
		
		for (int i = 0; i < opt.length; i++)
		{
			Option o = opt[i];
			if (o.getOpt().equals("i"))
			{
				inputFile = o.getValue();
				continue;
			}
			
			if (o.getOpt().equals("o"))
			{
				outputFile = o.getValue();
				continue;
			}
			
			if (o.getOpt().equals("p"))
			{
				polynomFile = o.getValue();
				continue;
			}
		}
		
		
		runCalculations(inputFile, outputFile, polynomFile);
		
	}
	
	protected static void runCalculations(String inputFile, String outputFile, String polynomFile) throws Exception
	{	
		if (inputFile == null)
		{	
			System.out.println("Missing input File (option -i)");
			return;
		}	
		else
			System.out.println("input File = " + inputFile);
		
		if (outputFile != null)
			System.out.println("output File = " + outputFile);
		
		if (polynomFile != null)
			System.out.println("polynomial File = " + polynomFile);
		
		
		IOUtils io = new IOUtils();
		SlaterIntegral sIntergal = io.loadSlaterIntegral(inputFile);
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
		
		System.out.println("Running calculations ...");
		double result = sIntergal.calculate();
		System.out.println("Result = " + result);
		
	}
	
	protected static void printHelp(Options options, String message) {
		if (message!=null) 
			System.out.println(message);
		
	    HelpFormatter formatter = new HelpFormatter();
	    formatter.printHelp(AppSlater.class.getName(), options );
	    Runtime.getRuntime().runFinalization();						 
		Runtime.getRuntime().exit(0);	
	}

}
