package pu.helpers;

import java.util.ArrayList;

public class Tokenizer 
{	
	public static final int TOK_BRACKET_NORMAL = 1;
	public static final int TOK_BRACKET_CURLY = 2;
	public static final int TOK_BRACKET_SQUARE = 3;
	public static final int TOK_BRACKET_NORMAL_SQUARE = 4;
	public static final int TOK_BRACKET_NORMAL_SQUARE_CURLY = 5;
	public static final int TOK_BRACKET_TRIANGLE = 6;
	
	String s;
	int numSplitters = 1;
	String [] splitters;
	char charSplitter;
	int FlagBrackets = 0;
	int [] openBracketsAtPosition;
	
	int numTokens = 0;
	int numTokensAll = 0; //Including the empty Tokens
	
	ArrayList<TokenPars> tokens = new ArrayList<TokenPars>();
	ArrayList<TokenPars> allTokens = new ArrayList<TokenPars>();
	
	
	public Tokenizer(String s_)
	{
		s = s_;		
		splitters = new String[2];
		numSplitters = 2;
		splitters[0] = " ";
		splitters[1] = "\t"; //Tabulation is added as a default splitter as well
		tokenize();
	}
	
	public Tokenizer(String s_, String splitter_)
	{
		s = s_;
		splitters = new String[1];
		numSplitters = 1;
		splitters[0] = new String(splitter_);
		tokenize();
	}
	
	public Tokenizer(String s_, String splitter_, int FlagBrackets_)
	{
		s = s_;
		splitters = new String[1];
		numSplitters = 1;
		splitters[0] = new String(splitter_);
		FlagBrackets = FlagBrackets_;
		tokenize();
	}
	
	public Tokenizer(String s_, String[] splitters_)
	{
		s = s_;
		numSplitters = splitters_.length;
		splitters = splitters_;
		tokenize();
	}
	
	private void tokenize()
	{
		int pos = 0;
		int copyPos, nextMinPos, i;
		int currentBeginSplitter = -1;
		int ind, splitType = -1;
		
		if (FlagBrackets > 0)
			scanForBrackets();
		
		
		//Check for splitters in the beginning of the string
		boolean startSplitters = false;
		for (i=0; i < numSplitters;i++)
		{
			if (s.startsWith(splitters[i])){
				pos = pos + splitters[i].length();
				startSplitters = true;
				currentBeginSplitter = i;
				break;
			}
		}
		
		while (startSplitters)
		{
			startSplitters = false;
			for (i=0; i < numSplitters; i++){
				if (s.startsWith(splitters[i],pos)){
					allTokens.add(new TokenPars(pos,pos,currentBeginSplitter,i));
					pos = pos + splitters[i].length();
					currentBeginSplitter = i;
					startSplitters = true;
					break;
				}
			}
		}
		
		copyPos = pos;
		
		//Detecting all tokens
		while (pos < s.length()) 
		{
			//Finding the next splitter
			nextMinPos = s.length();
			splitType  = -1;
			for (i=0; i < numSplitters; i++)
			{
				ind = s.indexOf(splitters[i],pos);
				if ((ind != -1)&(ind < nextMinPos))
				{
					nextMinPos = ind;
					splitType = i;
				}
			}
			
			if (splitType == -1)
			{
				//This is the last Token reaching the string End
				tokens.add(new TokenPars(copyPos,s.length(),currentBeginSplitter,splitType));
				allTokens.add(new TokenPars(copyPos,s.length(),currentBeginSplitter,splitType));
				pos = s.length();
				break;
			};
			
			boolean FlagAdd = true;
			if (FlagBrackets > 0)
			{
				if (openBracketsAtPosition[nextMinPos] > 0)
					FlagAdd = false;
			}
			
			if (nextMinPos == copyPos) 
			{	
				//Empty Token is added
				if (FlagAdd)
					allTokens.add(new TokenPars(copyPos,copyPos,currentBeginSplitter,splitType));
				pos = nextMinPos + splitters[splitType].length();
				if (FlagAdd)
					copyPos = pos;
				currentBeginSplitter = splitType;
			}
			else
			{
				//System.out.println(pos+"    "+nextMinPos);
				if (FlagAdd)
				{	
					tokens.add(new TokenPars(copyPos,nextMinPos,currentBeginSplitter,splitType));
					allTokens.add(new TokenPars(copyPos,nextMinPos,currentBeginSplitter,splitType));
				}
				pos = nextMinPos + splitters[splitType].length();
				if (FlagAdd)
					copyPos = pos;
				currentBeginSplitter = splitType;
			}
		}// of while pos < s.length()
		
		numTokens = tokens.size();
		numTokensAll = allTokens.size();
	}//End of tokenize
	
	
	public void scanForBrackets()
	{
		openBracketsAtPosition = new int[s.length()];
		int openBrackets = 0;
		char br1Symbol, br2Symbol;
		
		//By default  normal brackets are used		
		br1Symbol = '(';
		br2Symbol = ')';
				
		if (FlagBrackets == TOK_BRACKET_SQUARE)
		{
			br1Symbol = '[';
			br2Symbol = ']';
		}
		
		if (FlagBrackets == TOK_BRACKET_CURLY)
		{
			br1Symbol = '{';
			br2Symbol = '}';
		}
		
		
		//This is the case for one type of bracket been handled
		for (int i = 0; i < s.length(); i++)
		{
			openBracketsAtPosition[i] = openBrackets;
			
			if (s.charAt(i) == br1Symbol)
				openBrackets++;
			if (s.charAt(i) == br2Symbol)
				openBrackets--;
		}
	}
	
	
	public int tokenCount(){
		return(numTokens);
	}
	
	public int allTokenCount(){
		return(numTokensAll);
	}
	
	
	public String getToken(int tokenNum){
		String retString;
		if ((tokenNum >=0)&(tokenNum <numTokens)) {
			retString = s.substring(
					((TokenPars)tokens.get(tokenNum)).iBegin,
					((TokenPars)tokens.get(tokenNum)).iEnd);
		}
		else
			retString = "";
		return (retString)	;
	}
	
	public String getTokenAll(int tokenNum){
		String retString;
		if ((tokenNum >= 0)&(tokenNum <numTokensAll)) {
			retString = s.substring(
					((TokenPars)allTokens.get(tokenNum)).iBegin,
					((TokenPars)allTokens.get(tokenNum)).iEnd);
		}
		else
			retString = "";
		return (retString)	;
	}
	
	public int getPreSpliterType(int tokenNum){
		if ((tokenNum >= 0)&(tokenNum <numTokens))
			return( ((TokenPars)tokens.get(tokenNum)).splitterBegin );
		else
			return(-1);
	}
	
	public int getPostSpliterType(int tokenNum){
		if ((tokenNum >=0)&(tokenNum <numTokens))
			return( ((TokenPars)tokens.get(tokenNum)).splitterEnd );
		else
			return(-1);
	}
	
	public int getPreSpliterTypeAll(int tokenNum){
		if ((tokenNum >=0)&(tokenNum <numTokensAll))
			return( ((TokenPars)allTokens.get(tokenNum)).splitterBegin );
		else
			return(-1);
	}
	
	public int getPostSpliterTypeAll(int tokenNum){
		if ((tokenNum >=0)&(tokenNum <numTokensAll))
			return( ((TokenPars)allTokens.get(tokenNum)).splitterEnd );
		else
			return(-1);
	}
	
	public String getSplitter(int splitterNum){
		if ((splitterNum >= 0)&(splitterNum <numSplitters))
			return(splitters[splitterNum]);
		else
			return("");
	}
}


class TokenPars
{
	int iBegin; //Token indexes which define the corresponding substring
	int iEnd;
	int splitterBegin, splitterEnd; //Splitter types
	int indexAll; //This is the token corresponding index in the "All" Vector
	
	public TokenPars(int iBegin_, 	int iEnd_, 	int spliterBegin_, int spliterEnd_){
		iBegin = iBegin_;
		iEnd = iEnd_;
		splitterBegin = spliterBegin_;
		splitterEnd = spliterEnd_;
	}
}
