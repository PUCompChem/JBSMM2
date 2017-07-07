package pu.filtering.filters;

import java.util.ArrayList;
import java.util.List;

import pu.filtering.IFilter;

public class ListFilter implements IFilter 
{	
	private List<Object> objects = new ArrayList<Object>();
	
	@Override
	public boolean getStatus(Object obj) {
		return objects.contains(obj);
	}

	public List<Object> getObjects() {
		return objects;
	}

	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}
}
