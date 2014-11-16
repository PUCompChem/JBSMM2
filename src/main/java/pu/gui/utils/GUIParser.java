package pu.gui.utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Stack;


public class GUIParser 
{
	private ArrayList<String> errors = new ArrayList<String>();	
	private GUIArranger arranger = null;

	
	//Work variables for the parsing
	private String arrCode = null;
	private int curPos = 0;
	private int nChar = 0;
	private String curToken = null;  //The result of the functions extractName() and extractToken() 
	private GUIBinNode curNode = null;	
	private Stack<GUIBinNode> splits = new Stack<GUIBinNode>();
	private boolean FlagOpenRootNode = false;
	
	public GUIArranger readConfigurationFromFile(String cfgFileName)
	{
		StringBuffer sb = new StringBuffer();
		try
		{	
			File file = new File(cfgFileName);
			RandomAccessFile f = new RandomAccessFile(file,"r");			
			long length = f.length();
			while (f.getFilePointer() < length)
			{	
				String line = f.readLine();
				//System.out.println("line " + n + "  " + line);
				sb.append(line);
			}
			f.close();
		}
		catch (Exception e)
		{	
			errors.add("File error: " + e.getMessage());
		}
		
		return parse(sb.toString());
	}


	public void init()
	{		
		arranger = new GUIArranger();
		errors.clear();
		arranger.setRootNode(null);
		nChar = 0;
		curPos = 0;
		FlagOpenRootNode = false;
		curNode = null;
		curToken = null;
		splits.clear();
	}

	/**
	 * 
	 * @param text
	 * 
	 * The text is parsed according to the following basic syntax
	 * 
	 * split(orientation = h|v, r = [0,100], Area1(), Area2())
	 *   	where ratio between child1 and child2 = r : (100-r)
	 * 
	 * Area(tab) - not implemented yet
	 * 
	 * Preserved words:
	 *   s | split
	 *   h | horizontal
	 *   v | vertical
	 *   t | tab
	 * 
	 */
	public GUIArranger parse(String arrangerCode)
	{
		init();
		arrCode = arrangerCode;
		nChar = arrCode.length();
		
		while ((curPos < nChar) && errors.isEmpty())
		{			
			char c = arrCode.charAt(curPos);

			if (Character.isLetter(c))
			{
				extractName();
				if (curToken.equals("s") || curToken.equals("split"))
				{
					newSplit();					
				}
				else
				{	
					newArea();
				}
				continue;
			}

			if (Character.isDigit(c))
			{
				errors.add("Incorrect area name starting with a digit " + "'" + c +  "'  at position: " + curPos);
				break;
			}

			//Special symbol
			switch (c)
			{
			case ' ':
				curPos++;
				break;
			case '\t':
				curPos++;
				break;	
			case '\n':
				curPos++;
				break;
			case ')':
				endSplit();
				break;

			default: //Unsupported char
				errors.add("Incorrect char " + "'" + c +  "'  at position: " + curPos);
				break;
			}
		}
				
		if (errors.isEmpty())
			return arranger;
		else
			return null;
	}

	private void extractName()
	{
		int startPos = curPos;
		while (curPos < nChar)
		{
			if (Character.isLetterOrDigit(arrCode.charAt(curPos)))
				curPos++;
			else
				break;
		}

		curToken = arrCode.substring(startPos, curPos);
		
		System.out.println("Extracted name: "+curToken);
	}
	
	private void extractToken()
	{
		int startPos = curPos;
		while (curPos < nChar)
		{
			if ( 	(arrCode.charAt(curPos) != ' ')  && 
					(arrCode.charAt(curPos) != '\t') && 
					(arrCode.charAt(curPos) != '\n') )
				curPos++;
			else
				break;
		}

		curToken = arrCode.substring(startPos, curPos);
	}
	
	private Integer extractInteger()
	{
		int startPos = curPos;
		while (curPos < nChar)
		{
			if (Character.isDigit(arrCode.charAt(curPos)))
				curPos++;
			else
				break;
		}
		curToken = arrCode.substring(startPos, curPos);
		Integer iObj = null;
		
		try
		{		
			int i =  Integer.parseInt(curToken);
			iObj = new Integer(i);
		}
		catch(Exception e)
		{
			errors.add("Incorrect integer " + curToken);
			return null;
		}
		
		return iObj;
	}
	
	

	private void newSplit()
	{	
		ommitSpaces();

		if (curPos >= nChar)
		{	
			errors.add("Incorrect split at the end!");
			return;
		}

		if (arrCode.charAt(curPos) != '(')
		{
			errors.add("Incorrect begining of split: expected '('");
			return;
		}
		else
			curPos++;


		if (curNode == null)
		{
			if (FlagOpenRootNode)
			{
				errors.add("Opening zero level split for a second time");
				return;
			}
			
			//Create the first node
			GUIBinNode rootNode = new GUIBinNode();
			arranger.setRootNode(rootNode);
			curNode = rootNode;
			FlagOpenRootNode = true;
		}
		else
		{
			//Create a split of the current Node
			GUIBinNode newNode = new GUIBinNode();
			curNode.parseAreaNum++;
			if (curNode.parseAreaNum == 1)
				curNode.setChild1(newNode);
			else
				if (curNode.parseAreaNum == 2)
					curNode.setChild2(newNode);
				else
				{
					errors.add("Opening a split split at area position 3!");
					return;
				}
			
			newNode.setParent(curNode);
			curNode = newNode;
		}
		

		//Extract split orientation
		ommitSpaces();
		extractToken();
		if (curToken.equals("h") || curToken.equals("horizontal"))
		{
			curNode.setHorizontal(true);
		}
		else
			if (curToken.equals("v") || curToken.equals("vertical"))
			{
				curNode.setHorizontal(false);
			}
			else
			{
				errors.add("Incorrect split orientation: " + curToken);
				return;
			}
		
		
		//Extract split ratio
		ommitSpaces();
		Integer iObj = extractInteger();
		if (iObj == null)
		{
			errors.add("Incorrect split ratio definition.");
			return;
		}
		else
			curNode.setRatio(iObj.intValue());
		
	}
	
	private void endSplit()
	{
		curPos++;
		if (curNode == null)
		{
			errors.add("Incorrect closing of a split ')'");
			return;
		}
		
		//Check current node position
		if (curNode.parseAreaNum < 2)
		{
			errors.add("Incorrect closing of a split: missing area");
			return;
		}
		
		curNode = curNode.getParent();
	}

	private void newArea()
	{
		//The area is created as a terminal node (with no clildren)
		//which is a child of the curNode
		
		if (curNode == null)
		{
			errors.add("Incorrect area definition outside a 'split' !");
			return;
		}
		
		ommitSpaces();

		if (arrCode.charAt(curPos) == '(')
		{
			//Extract all area parameters
			//TODO
		}

		curNode.parseAreaNum++;  //Iterate to next area in this node
		
		if (curNode.parseAreaNum == 1)
			curNode.setAreaName1(curToken);
		else
			if (curNode.parseAreaNum == 2)
				curNode.setAreaName2(curToken);
			else
			{	
				errors.add("More than 2 areas in a split !");
				return;
			}	
	}

	private void ommitSpaces()
	{
		while (curPos < nChar)
		{
			char c = arrCode.charAt(curPos);
			if (c==' ' || c =='\t' || c=='\n')
				curPos++;
			else
				break;
		}
	}
	
	public ArrayList<String> getErrors()
	{
		return errors;
	}
	
}
