package pu.gui.utils;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * -Structures and properties
 * -Color range per column 
 * -Selected objects + marked objects
 * -Interactively linked with other GUI components (if an objects is selected at one place it is selected everywhere) 
 * 
 * @author nick
 *
 */

public class SmartChemTable 
{
	List<SmartChemTableField> fields = new ArrayList<SmartChemTableField>();
	
	public void addTableRow(List<Object> rowFields)
	{
		for (int i = 0; i < rowFields.size(); i++)
		{
			Object o = rowFields.get(i);
			if (o == null)
			{
				//TODO
			}
			
			switch (fields.get(i).fieldType)
			{
			case TEXT:
				//TODO o.toString();
				break;
			case STRUCTURE:
				if (o instanceof String)
				{
					String smi = (String)o;
					//TODO 2D paint
				}
				else
					if (o instanceof IAtomContainer)
					{
						//TODO
					}
				break;
			}
			
			
		}
	}
	
}
