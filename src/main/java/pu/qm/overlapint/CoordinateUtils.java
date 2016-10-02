package pu.qm.overlapint;

public class CoordinateUtils 
{
	
	public static double[] sphericalToCartesian(double r, double theta, double phi)
	{
		double c[] = new double[3];
		//x
		c[0] = r * Math.sin(theta) * Math.cos(phi);
		//y
		c[1] = r * Math.sin(theta) * Math.sin(phi);
		//z
		c[2] = r * Math.cos(theta);
						
		return c;
	}
	
	public static double[] cartesianToSpherical(double x, double y, double z)
	{
		double c[] = new double[3];
		double r = Math.sqrt(x*x + y*y + z*z);
		
		if (r == 0)
		{	
			c[0] = 0;
			c[1] = 0;
			c[2] = 0;
		}
		else
		{
			c[0] = r;
			
			//theta
			c[1] = Math.acos(z/r);
			
			//phi
			if ((x == 0) && (y == 0))
				c[2] = 0;
			else
				c[2] = Math.atan2(y, x);
		}
		
		return c;
	}
	
}
