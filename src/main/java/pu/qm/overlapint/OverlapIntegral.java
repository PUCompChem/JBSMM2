package pu.qm.overlapint;

import pu.qm.wavefunction.IWaveFunction;

public class OverlapIntegral 
{	
	public double R = 3.0;
	public double dx = 0.001;
	public double calcRadius = 100;
	
	public double x1, y1, z1;
	public double x2, y2, z2;
	public double cx, cy, cz;
	
	//TODO describe orbital orientation ?
	public IWaveFunction wave1 = null;
	public IWaveFunction wave2 = null;
	
	public void setWorkPoints()
	{
		x1 = 0;
		y1 = 0;
		z1 = 0;
		
		x2 = R;
		y2 = 0;
		z2 = 0;
		
		cx = R / 2;
		cy = 0;
		cz = 0;
	}
	
	public double calculate()
	{
		double x,y,z;
		double sum = 0.0;
		int n = (int) (calcRadius / dx);
		
		for (int i = -n; i <= n; i++)
			for (int j = -n; j <= n; j++)
				for (int k = -n; k <= n; k++)
				{
					x = cx + i * dx;
					y = cy + j * dx;
					z = cz + k * dx;
					
					//Calculate relative coordinates according to point 1 and point 2
					double sc1[] = CoordinateUtils.cartesianToSpherical(x-x1, y-y1, z-z1);
					double sc2[] = CoordinateUtils.cartesianToSpherical(x-x2, y-y2, z-z2);
					
					sum += wave1.F(sc1[0], sc1[1], sc1[2]) * wave2.F(sc2[0], sc2[1], sc2[2]);
				}
		
		return sum;
	}
}
