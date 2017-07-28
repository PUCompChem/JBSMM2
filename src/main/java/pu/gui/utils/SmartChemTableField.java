package pu.gui.utils;

public class SmartChemTableField 
{
	public static enum Type {
		TEXT, VALUE, STRUCTURE, REACTION, SS_QUERY 
	}
	
	public String name = null;
	public Type fieldType = Type.TEXT;
	public boolean FlagConvertSmilesTo2D = true;
}
