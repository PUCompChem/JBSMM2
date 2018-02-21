package pu.reactor.workspace.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.reactions.GenericReaction;
import ambit2.reactions.GenericReactionInstance;
import ambit2.reactions.ReactionDataBase;
import ambit2.reactions.retrosynth.ReactionSequence.SortingMethod;
import pu.gui.utils.AtomContainerHighlights;
import pu.gui.utils.AtomContainerHighlights.SelectionMetod;
import pu.gui.utils.chemtable.SmartChemTable;
import pu.gui.utils.chemtable.SmartChemTableField;
import pu.helpers.Helpers;


public class ReactionBrowserPanel extends JPanel
{	
	private static final long serialVersionUID = -6910252435305959047L;

	enum BrowserMode {
		REACTION_ONLY, REACTION_APPLICATION
	}
	
	
	BrowserMode mode = BrowserMode.REACTION_APPLICATION;
	SmartChemTable chemTable = null;
	List<SmartChemTableField> fields = new ArrayList<SmartChemTableField>();
	IAtomContainer target = null;
	String targetString = "";
	ReactionDataBase reactionDB = null;
	List<GenericReactionInstance> reactionInstances = new ArrayList<GenericReactionInstance>();
	SortingMethod sortMethod = SortingMethod.REACTION_SCORE;
	int itemSelection[] = null;
	int numTableColumns = 0;
	
	//Table columns config
	boolean showRowNumber = true;
	boolean showReactionSmirks = true;
	boolean showTarget = true;
	boolean showProducts = true;
	boolean showReactionScore = true;
	boolean showReactionScoreDetails = false;
	boolean showReactionDepiction = false;
	
	public ReactionBrowserPanel() 
	{	
		initGUI();
	}
	
	void initGUI() 
	{
		setLayout(new BorderLayout());
		configTableFields();
		chemTable = new SmartChemTable(fields);
		add(chemTable, BorderLayout.CENTER);
	}
	
	void configTableFields()
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
				fields.add(new SmartChemTableField("Target", SmartChemTableField.Type.STRUCTURE));
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
	
	public void fillTable()
	{
		if (itemSelection != null)
		{
			for (int i = 0; i < itemSelection.length; i++)
			{	
				GenericReactionInstance inst = reactionInstances.get(itemSelection[i]);
				//System.out.println("Adding: " + itemSelection[i] + "  " + inst.reactionScore.totalScore);
				List<Object> rowFields = getTableRowFields(inst, (i+1));
				chemTable.addTableRow(rowFields);
			}	
		}
		else
		{
			for (int i = 0; i < reactionInstances.size(); i++)
			{
				GenericReactionInstance inst = reactionInstances.get(i);
				List<Object> rowFields = getTableRowFields(inst, (i+1));
				chemTable.addTableRow(rowFields);
			}	
		}
	}
	
	private List<Object> getTableRowFields(GenericReactionInstance instance, int num)
	{
		List<Object> rowFields = new ArrayList<Object>();
		if(showRowNumber)
			rowFields.add(new Integer(num));
		String info = getReactionInfoString(instance.reaction);
		rowFields.add(info);
		
		switch(mode)
		{
		case REACTION_ONLY:
			break;
		case REACTION_APPLICATION:
			if (showTarget)
			{	
				try {
					if (instance.instanceAtoms != null)
					{	
						AtomContainerHighlights selection = new AtomContainerHighlights();
						selection.setSelectionMethod(SelectionMetod.ATOM_INDEX_LIST);
						selection.setIndexList(Helpers.calcIndexList(target, instance.instanceAtoms));
						rowFields.add(Helpers.getCouple(target.clone(), selection));
					}
					else
						rowFields.add(target);
				}
				catch (Exception e) {}
			}	
			if (showProducts)
				rowFields.add(instance.products);
			if (showReactionScore)
				rowFields.add(instance.reactionScore.totalScore);
			if (showReactionScoreDetails)
				rowFields.add("Score details");
			break;
		}
		
		return rowFields;
	}
	
	private String getReactionInfoString(GenericReaction reaction)
	{
		String s = reaction.getName();
		if (showReactionSmirks)
			s = s + "\n" + reaction.getSmirks();
		return s;
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
	
	public List<GenericReactionInstance> getReactionInstances() {
		return reactionInstances;
	}

	public void setReactionInstances(List<GenericReactionInstance> reactionInstances) {
		this.reactionInstances = reactionInstances;
	}

	public int [] getItemSelection() {
		return itemSelection;
	}

	public void setItemSelection(int itemSelection[]) {
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
