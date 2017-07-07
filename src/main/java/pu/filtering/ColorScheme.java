package pu.filtering;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ColorScheme 
{
	//mapping between codes and color
	public Map<Integer, Color> codeColors = new HashMap<Integer, Color>();
	
	public static ColorScheme getPredefinedColorScheme(Set<Integer> codes)
	{	
		List<Color> colors = null;
		
		switch (codes.size())
		{
		case 2:
			colors = getPredefined2Colors();
			break;
		}
		
		
		if (colors != null)
		{	
			ColorScheme colSch= new ColorScheme(); 
			int i = 0;
			Iterator<Integer> it = codes.iterator();
			while (it.hasNext())
			{	
				Integer code = it.next();
				colSch.codeColors.put(code, colors.get(i));
				i++;
			}
			return colSch;
		}

		return null;
	}
	
	
	public static List<Color> getPredefined2Colors()
	{
		List<Color> colors = new ArrayList<Color>();
		colors.add(Color.RED);
		colors.add(Color.GREEN);
		return colors;
	}
}
