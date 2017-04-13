package pu.bioinformatics;

import java.util.Stack;
import java.util.Vector;



public class SequenceAlignment 
{	
	public int scoreType = 0;
	public int scoreEqualBases = 0;
	public int scoreDiffBases = -1;
	public int scoreCT_GA = -1;
	public int scoreGap = -1;
	
	public String seq1;
	public String seq2;
	public int DP[][];
	Stack<DPElement> stack = new Stack<DPElement>();
	
	
	public int getScore(char s, char t)
	{
		switch (scoreType)
		{
		case 1:
			return getScore1(s,t);
		
		default:
			return getScore0(s,t);
		}
	
	}

	public int getScore0(char s, char t)
	{
		if (s==t)
			return scoreEqualBases;
		else 
			return scoreDiffBases;
	}
	
	
	
	
	
	public int getScore1(char s, char t)
	{
		if (s==t)
			return scoreEqualBases;
		
		if (( s =='C' && t == 'T' ) || ( s =='T' && t == 'C' ) ||
				( s =='G' && t == 'A' ) || ( s =='A' && t == 'G' ))
			return scoreCT_GA; 
		
		if (s== '-' || t == '-')			
			return scoreGap;		
		
		 return scoreDiffBases;
	}
	
	
	
	
		
	
	
	public DPElement backTrackAlignment()
	{			
		DPElement el0 = new DPElement();
		el0.row = DP.length - 1;
		el0.col = DP[0].length - 1;
		
		stack.clear();
		stack.push(el0);
		
		while (!stack.isEmpty())   //First depth-search
		{
			DPElement el = stack.pop();
			
			if (el.row ==  0 || el.col == 0)
				return (el);
			
			backTrackDPElement(el);
		}
		
		return null;
	}
	
	public Vector<DPElement> backTrackAllAlignments()
	{
		//TODO
		Vector<DPElement> vDPEl = new Vector<DPElement>();
		DPElement el0 = new DPElement();
		el0.row = DP.length - 1;
		el0.col = DP[0].length - 1;
		
		stack.clear();
		stack.push(el0);
		
		while (!stack.isEmpty())   //First depth-search
		{
			DPElement el = stack.pop();
			
			if (el.row ==  0 || el.col == 0)
				vDPEl.add(el);
			else
				backTrackDPElement(el);
		}
		
		return vDPEl;
	}
	
	void backTrackDPElement(DPElement el)
	{
		//Forward calculation principle:
		//int v1 = DP[i-1][j] + getScore(seq1.charAt(i-1), '-');
		//int v2 = DP[i][j-1] + getScore('-', seq2.charAt(j-1));
		//int v3 = DP[i-1][j-1] + getScore(seq1.charAt(i-1), seq2.charAt(j-1)); 
		
		int i = el.row;
		int j = el.col;
		
		if (DP[i][j] == DP[i-1][j] + getScore(seq1.charAt(i-1), '-') )
		{
			DPElement newEl = new DPElement();
			newEl.parent = el;
			newEl.row = i-1;
			newEl.col = j;
			stack.push(newEl);
		}
		
		if (DP[i][j] == DP[i][j-1] + getScore('-', seq2.charAt(j-1)) )
		{
			DPElement newEl = new DPElement();
			newEl.parent = el;
			newEl.row = i;
			newEl.col = j-1;
			stack.push(newEl);
		}
		
		if (DP[i][j] == DP[i-1][j-1] + getScore(seq1.charAt(i-1), seq2.charAt(j-1)) )
		{
			DPElement newEl = new DPElement();
			newEl.parent = el;
			newEl.row = i-1;
			newEl.col = j-1;
			stack.push(newEl);
		}
		
	}
	
	
	
	public Alignment getAligmentFromBackTracking(DPElement el)
	{	 
		StringBuffer b_seq1 = new StringBuffer();
		StringBuffer b_seq2 = new StringBuffer();		
		DPElement curEl = el;
		
		//Special cases
		if (el.row == 0 && el.col == 1)
		{
			b_seq1.append('-');
			b_seq2.append(seq2.charAt(0));
		}
		else		
			if (el.row == 1 && el.col == 0)
			{
				b_seq1.append(seq1.charAt(0));
				b_seq2.append('-');
			}

		
		
		while (curEl.parent != null)
		{	
			char p[] = getAligmentPair(curEl);
			b_seq1.append(p[0]);
			b_seq2.append(p[1]);
			
			System.out.print("("+curEl.row+","+curEl.col+") ");
			curEl = curEl.parent;
		}
		
		System.out.println("("+curEl.row+","+curEl.col+") ");
		
		Alignment alignment = new Alignment();
		alignment.seq1 = b_seq1.toString();
		alignment.seq2 = b_seq2.toString();
		
		return alignment;
	}
	
	char[] getAligmentPair(DPElement el)
	{
		char p[] = new char[2];
		int rowShift = el.parent.row - el.row;
		int colShift = el.parent.col - el.col;
		
		if (rowShift == 1)
		{
			if(colShift == 1)
			{
				//diagonal movement
				p[0] = seq1.charAt(el.parent.row - 1);
				p[1] = seq2.charAt(el.parent.col - 1);
			}
			else
			{	
				//rowShift = 1, colShift = 1 
				p[0] = seq1.charAt(el.parent.row - 1);
				p[1] = '-';
				
				
			}
			
		}
		else
		{
			//rowShift = 0 then colShift = 1 (obligatory)
			p[0] = '-';
			p[1] = seq2.charAt(el.parent.col - 1);
			
		}	
		
		
		
		
		return p;

	}
	
	public void fillDPMatrix()
	{
		DP = new int[seq1.length()+1][seq2.length()+1];
		DP[0][0] = 0;
		
		int n = seq1.length();
		if (n > seq2.length())
			n = seq2.length();
		
		
		//Column 0
		for (int i = 1; i<= seq1.length(); i++)
			DP[i][0] = DP[i-1][0] + getScore(seq1.charAt(i-1),'-');
		//Row 0
		for (int i = 1; i<= seq2.length(); i++)
			DP[0][i] = DP[0][i-1] + getScore(seq2.charAt(i-1),'-');
		
		for (int k = 1; k <= n; k++)
		{
			//Filling row #k
			for (int i = k; i <= seq2.length(); i++)
				calcDP(k,i);
			
			//Filling column  #k
			for (int i = k+1; i <= seq1.length(); i++)
				calcDP(i,k);
		}
		
	}
	
	public void calcDP(int i, int j)
	{
		//charAt(i-1) is used because charAt is 0-based indexes while DP is 1 based indexed
				
		int v1 = DP[i-1][j] + getScore(seq1.charAt(i-1), '-');
		int v2 = DP[i][j-1] + getScore('-', seq2.charAt(j-1));
		int v3 = DP[i-1][j-1] + getScore(seq1.charAt(i-1), seq2.charAt(j-1));   
			
		int cost;
		cost = Math.max(v3, Math.max(v1, v2));  //The cost is negative i.e. it is a penalty that is way max is searched
		//cost = Math.min(v3, Math.min(v1, v2));   
		DP[i][j] = cost;
		
		//System.out.println("DP(" + i + "," +j + ")  " + seq1.charAt(i-1) + "  " +  seq2.charAt(j-1) + "  " + cost);
	}
	
		
	
	//------------------Helpers------------------------------------------
	
	
	public String DPtoString()
	{
		StringBuffer sb = new StringBuffer();
		
		
		sb.append("           ");
		for (int k=0; k < seq2.length(); k ++)
			sb.append("     " + seq2.charAt(k));
		sb.append("\n");
		
		for (int i=0; i < DP.length;i ++)
		{
			if (i>0)
				sb.append("    " + seq1.charAt(i-1));
			else
				sb.append("     ");
				
			for (int j=0; j < DP[i].length; j ++)
			{
				String s = new String ((new Integer(DP[i][j])).toString());
				
				if (s.length() == 1)
					sb.append("     ");
				if (s.length()== 2)
					sb.append("    ");
				if (s.length()== 3)
					sb.append("   ");
				
				sb.append(DP[i][j]);
				
			}
			sb.append("\n");
		}
		return (sb.toString());
	}
}
