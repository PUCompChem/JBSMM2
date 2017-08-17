package pu.gui.utils.ChemTable;

public class SmartChemTableField 
{
	public static enum Type {
		TEXT, VALUE, STRUCTURE, REACTION, SS_QUERY 
	}
	
	public String name = null;
	public Type fieldType = Type.TEXT;
	public boolean FlagConvertSmilesTo2D = true;
	
	public SmartChemTableField(String name, Type fieldType)
	{
		this.name = name;
		this.fieldType = fieldType;
	}
}
