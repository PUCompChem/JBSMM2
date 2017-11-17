package pu.gui.utils;

import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPopupMenu;

public abstract class GenericContextMenu implements MouseListener
{
	
	List<Object> guiObjects = new ArrayList<Object>();
	
	JPopupMenu popMenu = null;
	
	//public abstract void handleEvent();

	public List<Object> getGuiObjects() {
		return guiObjects;
	}

	public void setGuiObjects(List<Object> guiObjects) {
		this.guiObjects = guiObjects;
	}

	public JPopupMenu getPopMenu() {
		return popMenu;
	}

	public void setPopMenu(JPopupMenu popMenu) {
		this.popMenu = popMenu;
	}
}
