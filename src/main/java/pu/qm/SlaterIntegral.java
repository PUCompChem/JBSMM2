package pu.qm;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class SlaterIntegral 
{
	static Logger logger = null;
	
	//Parameters
	public int N;
	public int M;
	public int T;	
	public int TT;
	public int EP2;
	
	double P, PT, P1, P2, DI, R, NS;
	double A0, B0;
	double S, SP;
	
	public double A[];
	public double B[];
	public double C1[];
	public double C2[];
	public double Z1[];
	public double Z2[];
	public double EP[][];
	
	public SlaterIntegral()
	{
		setLogger();
	}
	
	
	public double calculate()
	{
		//TODO
		int i, j, k, l, i1;

		logger.info("===========================\n");
		logger.info("TWO CENTRE OVERLAP INTEGRAL\n");
		
		/*
		printf ("===========================\n\n");
		printf ("N = "); scanf ("%d", &N);
		printf ("M = "); scanf ("%d", &M);
		printf ("T = "); scanf ("%d", &T);
		printf ("TT = "); scanf ("%d", &TT);
				
		for (i = 1; i <= N; i++) {
			printf ("Z1(%d) = ", i); scanf ("%f", &Z1[i]);
		}
		for (j = 1; j <= M; j++) {
			printf ("Z2(%d) = ", j); scanf ("%f", &Z2[j]);
		}
		for (i = 1; i <= N; i++) {
			printf ("C1(%d) = ", i); scanf ("%f", &C1[i]);
		}
		for (j = 1; j <= M; j++) {
			printf ("C2(%d) = ", j); scanf ("%f", &C2[j]);
		}
		for (i = 1; i <= TT; i++)
			for (j = 1; j <= TT; j++) {
				printf ("EP[%d][%d] = ", i, j); scanf ("%f", &EP[i][j]);
			}
		
		printf ("P1 = "); scanf ("%f", &P1);
		printf ("P2 = "); scanf ("%f", &P2);
		printf ("DI = "); scanf ("%f", &DI);
		printf ("R  = "); scanf ("%f", &R);
		printf ("NS = "); scanf ("%f", &NS);
		*/
		
		S = 0;
		
		for (i = 1; i <= N; i++) 
		{
			for (j = 1; j <= M; j++) 
			{
				//printf ("I = %d, J = %d\n", i, j);
				P = ( (Z1[i] + Z2[j]) * R) / 2;
				//printf ("P = %e\n", P);
				PT = ( (Z1[i] - Z2[j]) * R) / 2;
				//printf ("PT = %e\n", PT);
				A0 = (1/P) * Math.exp (-P);
				//printf ("A(0) = %e\n", A0);
				
				if (PT != 0) 
					B0 = - Math.exp(-PT) * (1/PT) - Math.exp(PT) * (-1/PT);
				else 
					B0 = 2;
				//printf ("B(0) = %e\n", B0);
				for (l = 1; l <= T; l++) {
					A[l] = slat (P, l, 1);
					//printf ("A(%d) = %e\n", l, A[l]);
					if (PT != 0) 
						B[l] = - slat (PT, l, 1) - slat (PT, l, -1);
					else 
						B[l] = (1 + Math.pow(-1, l)) / (l+1);
					//printf ("B(%d) = %e\n", l, B[l]);
				}
				for (i1 = 1; i1 <= T; i1++) 
					A[T+2-i1] = A[T+1-i1];
				A[1] = A0;
				for (i1 = 1; i1 <= T; i1++) 
					B[T+2-i1] = B[T+1-i1];
				B[1] = B0;
				SP = 0;
				for (l = 1; l <= TT; l++)
					for (k = 1; k <= TT; k++)
						SP += - A[l] * B[k] * EP[l][k];
				S += C1[i] * C2[j] * SP;
			}
		}
		S *= Math.pow((R/2), NS);
		if (DI >= 1) 
			S *= (P1/P2);
		else 
			S *= Math.sqrt(P1/P2);
		//printf ("\nOVERLAP INTEGRAL S = %e\n", S);
		
		return S;
	}

	
	double slat (double PE, int NN1, int L)
	{
		double FAK, FAKT, AK;
		int ii, i, j, NK;

		FAK = 1;
		for (ii = 1; ii <= NN1; ii++) 
			FAK *= ii;
		AK = 0;
		NK = NN1 + 1;
		for (i = 1; i <= NK; i++) 
		{
			FAKT = 1;
			ii = NK - i;
			if (ii > 1) 
				for (j = 1; j <= ii; j++) 
					FAKT *= j;
			if (L < 0) 
				AK += Math.pow(L, (NN1 - i)) / (FAKT * Math.pow(PE,i));
			else 
				AK += 1 / (Math.pow(PE,i) * FAKT);
		}
		return AK * Math.exp (-L * PE) * FAK;
	}
	
	
	private void setLogger()
	{
		if (logger == null)
		logger = Logger.getAnonymousLogger();
		logger.setUseParentHandlers(false);
		Handler conHdlr = new ConsoleHandler();

		conHdlr.setFormatter(new Formatter() {
			public String format(LogRecord record) {
				return 
				/*
		        		record.getLevel() + "  :  "
		            + record.getSourceClassName() + " -:- "
		            + record.getSourceMethodName() + " -:- "
				 */
				record.getMessage() + "\n";
			}
		});

		logger.addHandler(conHdlr);
	}
}
