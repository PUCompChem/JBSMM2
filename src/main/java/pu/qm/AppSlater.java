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
			
		
		printHelp(options, null);

	}
	
	
	
	
	protected static Options getOptions()
	{
		Options options = new Options();
		
		Option help = new Option( "help", "print this message" );
		options.addOption(help);
		
		Option input   = OptionBuilder
        .hasArg()
        .withLongOpt("input")
        .withArgName("file")
        .withDescription("Input file")        
        .create( "i" ); 
		options.addOption(input);
		
		Option output   = OptionBuilder
        .hasArg()
        .withLongOpt("output")
        .withArgName("file")
        .withDescription("Output file")        
        .create( "o" );
		options.addOption(output);
		
		return options;
	}
	
	
	protected static void parseCommand(Options options, String[] args) throws Exception
	{		
		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse( options, args);
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
