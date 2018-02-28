package pu.test.bioinformatics;

import pu.bioinformatics.*;
import java.util.Vector;

public class Test_SequenceAlignment 
{
	static int scoreType = 0;
	static int scoreEqualBases = 0;
	static int scoreDiffBases = -1;
	
	static int scoreCT_GA = -2;
	static int scoreGap = -10;
	
	
	public static void main(String[] args)
	{
		//testAllSeq("GGTAGAT", "ATATAA");
		//testAllSeq("AGTACGCCG", "ACGTCTGGT");
		//testAllSeq("ATATG", "TTTTTT");
		//testAllSeq("TTGT", "CTAGG");
		
		testAllSeq("AAGA", "CATGG");
	}
	
	
	
	static void test(String seq1, String seq2)
	{
		SequenceAlignment sa = new SequenceAlignment();
		sa.scoreType = scoreType;
		sa.scoreEqualBases = scoreEqualBases;
		sa.scoreDiffBases = scoreDiffBases;
		sa.scoreCT_GA = scoreCT_GA; 
		sa.scoreGap = scoreGap;
		
		sa.seq1 = seq1;
		sa.seq2 = seq2;
		sa.fillDPMatrix();
		System.out.println();
		System.out.println("Aligning: " + seq1 + "   "+seq2);		
		System.out.println();
		System.out.println(sa.DPtoString());
		System.out.println();
		
		
		DPElement el = sa.backTrackAlignment();
		Alignment al = sa.getAligmentFromBackTracking(el);
		System.out.println(al.seq1);
		System.out.println(al.seq2);
		 
	}
	
	static void testAllSeq(String seq1, String seq2)
	{
		SequenceAlignment sa = new SequenceAlignment();
		sa.scoreType = scoreType;
		sa.scoreEqualBases = scoreEqualBases;
		sa.scoreDiffBases = scoreDiffBases;
		sa.scoreCT_GA = scoreCT_GA; 
		sa.scoreGap = scoreGap;
		
		sa.seq1 = seq1;
		sa.seq2 = seq2;
		sa.fillDPMatrix();
		System.out.println();
		System.out.println("Aligning: " + seq1 + "   "+seq2);		
		System.out.println();
		System.out.println(sa.DPtoString());
		System.out.println();
		
		
		Vector<DPElement> vEl = sa.backTrackAllAlignments();
		for (int i = 0; i < vEl.size(); i++)
		{
			Alignment al = sa.getAligmentFromBackTracking(vEl.get(i));
			System.out.println(al.seq1);
			System.out.println(al.seq2);
		}	
		 
	}
}

