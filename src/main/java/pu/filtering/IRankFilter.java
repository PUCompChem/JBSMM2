package pu.filtering;

public interface IRankFilter extends IFilter 
{
	public void setRankThreshold(double threshold);
	public double getRankThreshold(double threshold);
}
