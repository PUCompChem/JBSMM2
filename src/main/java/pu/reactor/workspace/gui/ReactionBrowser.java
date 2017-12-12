package pu.reactor.workspace.gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.reactions.Reaction;
import ambit2.reactions.ReactionDataBase;
import pu.gui.utils.chemtable.SmartChemTable;
import pu.gui.utils.chemtable.SmartChemTableField;

public class ReactionBrowser extends JFrame 
{
	private static final long serialVersionUID = 7769828597142198311L;
	
	enum BrowserMode {
		REACTION_ONLY, REACTION_APPLICATION
	}
	
	class BrowseItem {
		public Reaction reaction = null;
	}
	
	BrowserMode mode = BrowserMode.REACTION_APPLICATION;
	SmartChemTable chemTable = null;
	List<SmartChemTableField> fields = new ArrayList<SmartChemTableField>();
	IAtomContainer target = null;
	String targetString = "";
	ReactionDataBase reactionDB = null;
	List<BrowseItem> browseItems = new ArrayList<BrowseItem>();
	List<Integer> itemSelection = null;
	
	//Table columns config
	boolean showRowNumber = true;
	boolean showReactionSmirks = true;
	boolean showTarget = true;
	boolean showProducts = true;
	boolean showReactionScore = true;
	boolean showReactionScoreDetails = false;
	boolean showReactionDepiction = false;
	
	public ReactionBrowser() 
	{
		//this.setLayout(new BorderLayout());
		initGUI();
		setSize(new Dimension(400,600));

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void initGUI() 
	{
		configTable();
	}
	
	private void configTable()
	{
		fields.clear();
		
		if (showRowNumber)
			fields.add(new SmartChemTableField("No", SmartChemTableField.Type.VALUE));
		fields.add(new SmartChemTableField("Reaction info", SmartChemTableField.Type.TEXT));
		//if (showReactionDepiction)
		//	fields.add(new SmartChemTableField("Structure", SmartChemTableField.Type.STRUCTURE));
		
		switch(mode)
		{
		case REACTION_ONLY:
			break;
			
		case REACTION_APPLICATION:
			if (showTarget)
				fields.add(new SmartChemTableField("Target structure", SmartChemTableField.Type.STRUCTURE));
			if (showProducts)
				fields.add(new SmartChemTableField("Products", SmartChemTableField.Type.STRUCTURE));
			if (showReactionScore)
				fields.add(new SmartChemTableField("Score", SmartChemTableField.Type.TEXT));
			if (showReactionScoreDetails)
				fields.add(new SmartChemTableField("Score details", SmartChemTableField.Type.TEXT));
			break;
		}
		
		chemTable = new SmartChemTable(fields);
	}
	
	private void fillTable()
	{
		//TODO
	}
	

	public BrowserMode getMode() {
		return mode;
	}

	public void setMode(BrowserMode mode) {
		this.mode = mode;
	}

	public SmartChemTable getChemTable() {
		return chemTable;
	}

	public void setChemTable(SmartChemTable chemTable) {
		this.chemTable = chemTable;
	}

	public IAtomContainer getTarget() {
		return target;
	}

	public void setTarget(IAtomContainer target) {
		this.target = target;
	}

	public String getTargetString() {
		return targetString;
	}

	public void setTargetString(String targetString) {
		this.targetString = targetString;
	}
	
	public List<BrowseItem> getBrowseItems() {
		return browseItems;
	}

	public void setBrowseItems(List<BrowseItem> browseItems) {
		this.browseItems = browseItems;
	}

	public List<Integer> getItemSelection() {
		return itemSelection;
	}

	public void setItemSelection(List<Integer> itemSelection) {
		this.itemSelection = itemSelection;
	}
	
	public boolean isShowRowNumber() {
		return showRowNumber;
	}

	public void setShowRowNumber(boolean showRowNumber) {
		this.showRowNumber = showRowNumber;
	}
	
	public boolean isShowReactionSmirks() {
		return showReactionSmirks;
	}

	public void setShowReactionSmirks(boolean showReactionSmirks) {
		this.showReactionSmirks = showReactionSmirks;
	}

	public boolean isShowTarget() {
		return showTarget;
	}

	public void setShowTarget(boolean showTarget) {
		this.showTarget = showTarget;
	}

	public boolean isShowProducts() {
		return showProducts;
	}

	public void setShowProducts(boolean showProducts) {
		this.showProducts = showProducts;
	}

	public boolean isShowReactionScore() {
		return showReactionScore;
	}

	public void setShowReactionScore(boolean showReactionScore) {
		this.showReactionScore = showReactionScore;
	}

	public boolean isShowReactionDepiction() {
		return showReactionDepiction;
	}

	public void setShowReactionDepiction(boolean showReactionDepiction) {
		this.showReactionDepiction = showReactionDepiction;
	}

}
