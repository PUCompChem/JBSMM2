/* TWO CENTRE OVERLAP INTEGRAL -- Translated by Valentin Sheiretsky */

#include <stdio.h>
#include <math.h>

float Z1[20], C1[20], Z2[20], C2[20];
float A[300], B[300], EP[20][20];
int T=0, EP2=0, TT=1;
int N, M;
float P, PT, P1, P2, DI, R, NS;
float A0, B0;
float S, SP;

float slat (float PE, int NN1, int L)
{
  float FAK, FAKT, AK;
  int ii, i, j, NK;
  
  FAK = 1;
  for (ii = 1; ii <= NN1; ii++) FAK *= ii;
  AK = 0;
  NK = NN1 + 1;
  for (i = 1; i <= NK; i++) {
	FAKT = 1;
	ii = NK - i;
	if (ii > 1) for (j = 1; j <= ii; j++) FAKT *= j;
	if (L < 0) AK += pow(L, (NN1 - i)) / (FAKT * pow(PE,i));
	else AK += 1 / (pow(PE,i) * FAKT);
  }
  return AK * exp (-L * PE) * FAK;
}

int main ()
{
  int i, j, k, l, i1;
  
  printf ("===========================\n");
  printf ("TWO CENTRE OVERLAP INTEGRAL\n");
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
  S = 0;
  printf ("\n");
  for (i = 1; i <= N; i++) {
    for (j = 1; j <= M; j++) {
	  printf ("I = %d, J = %d\n", i, j);
	  P = ( (Z1[i] + Z2[j]) * R) / 2;
	  printf ("P = %e\n", P);
	  PT = ( (Z1[i] - Z2[j]) * R) / 2;
	  printf ("PT = %e\n", PT);
	  A0 = (1/P) * exp (-P);
	  printf ("A(0) = %e\n", A0);
	  if (PT != 0) B0 = - exp(-PT) * (1/PT) - exp(PT) * (-1/PT);
	  else B0 = 2;
	  printf ("B(0) = %e\n", B0);
	  for (l = 1; l <= T; l++) {
	    A[l] = slat (P, l, 1);
		printf ("A(%d) = %e\n", l, A[l]);
		if (PT != 0) B[l] = - slat (PT, l, 1) - slat (PT, l, -1);
	    else B[l] = (1 + pow(-1, l)) / (l+1);
        printf ("B(%d) = %e\n", l, B[l]);
	  }
	  for (i1 = 1; i1 <= T; i1++) A[T+2-i1] = A[T+1-i1];
	  A[1] = A0;
	  for (i1 = 1; i1 <= T; i1++) B[T+2-i1] = B[T+1-i1];
	  B[1] = B0;
	  SP = 0;
	  for (l = 1; l <= TT; l++)
	    for (k = 1; k <= TT; k++)
		  SP += - A[l] * B[k] * EP[l][k];
	  S += C1[i] * C2[j] * SP;
	}
  }
  S *= pow((R/2), NS);
  if (DI >= 1) S *= (P1/P2);
  else S *= sqrt(P1/P2);
  printf ("\nOVERLAP INTEGRAL S = %e\n", S);
  return 0;
}
