package pu.filtering.filters;

import java.util.HashSet;
import java.util.Set;

import pu.filtering.IFilter;

public class SetFilter implements IFilter 
{	
	private Set<Object> objects = new HashSet<Object>();
	
	@Override
	public boolean getStatus(Object obj) {
		return objects.contains(obj);
	}

	public Set<Object> getObjects() {
		return objects;
	}

	public void setObjects(Set<Object> objects) {
		this.objects = objects;
	}
}
