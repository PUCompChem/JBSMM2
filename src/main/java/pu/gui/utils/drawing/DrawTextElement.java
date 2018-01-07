package pu.gui.utils.drawing;

import java.awt.Color;
import java.awt.Graphics;

public class DrawTextElement implements IDrawElement
{
	public String text = null;
	public int xpos = 0;
	public int ypos = 0;
	public int fontSize = 10;
	public Color textColor = Color.blue;
	public Color backgroundColor = Color.white;
	public int backgroundXPos = 0;
	public int backgroundYPos = 0;
	public int backgroundXSize = -1;
	public int backgroundYSize = -1;

	@Override
	public void draw(Graphics g) 
	{
		//Draw background
		if (backgroundXSize > 0)
		{
			g.setColor(backgroundColor);
			g.fillRect(backgroundXPos, backgroundYPos, 
					backgroundXSize, backgroundYSize);
		}
		//Draw text
		if (text != null)
		{	
			g.setColor(textColor);
			g.drawString(text, xpos, ypos);
		}
	}
	
}
