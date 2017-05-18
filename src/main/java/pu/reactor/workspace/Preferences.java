package pu.reactor.workspace;

public class Preferences 
{
	public static String reactionDBPath = null;
	public static String startingMaterialsPath = null;

	public boolean BoolValueCheckbox3 = false;
	
	public Preferences()
	{

	}
	public String toJsonString(){
		return Boolean.toString(BoolValueCheckbox3);
	}


}
