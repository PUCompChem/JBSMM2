package pu.reactor.workspace.gui;

import ambit2.base.data.StructureRecord;
import ambit2.reactions.reactor.Reactor;
import ambit2.reactions.reactor.ReactorNode;
import pu.gui.utils.chemtable.SmartChemTable;
import pu.gui.utils.chemtable.SmartChemTableField;
import pu.gui.utils.chemtable.StructureTable;
//import pu.gui.utils.structTable.StructureTable;
import pu.helpers.StructureSetUtils;
import pu.reactor.workspace.BasicReactorProcess;
import pu.reactor.workspace.IProcess;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by gogo on 27.7.2017 г..
 */
public class BasicReactorProcessPanel extends ProcessPanel
{

	private BasicReactorProcess basicReactorProcess;
	private BasicReactorStatusPanel statusPanel;
	//private StructureTable structureTable = new StructureTable(5);
	private StructureTable structureTable = new StructureTable(3);
	private SmartChemTable smartChemTable = new SmartChemTable();

	public BasicReactorProcessPanel(BasicReactorProcess basicReactorProcess)  
	{
		this.setBasicReactorProcess(basicReactorProcess);
		StructureRecord r = StructureSetUtils.getStructureRecordFromString(basicReactorProcess.getTargetInputString());
		statusPanel = new BasicReactorStatusPanel(basicReactorProcess, r);
		initGUI();
	}

/*
	public ActionListener chemTableRadioButtonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	};
*/
	private void initGUI() {

		setLayout(new BorderLayout());
		statusPanel.setPreferredSize(new Dimension(300,300));
		add(statusPanel,BorderLayout.EAST);
		//List<StructureRecord> structureRecords = new ArrayList<StructureRecord>();
		java.util.List<SmartChemTableField> fields = new ArrayList<SmartChemTableField>();
		//fields.add(new SmartChemTableField("No", SmartChemTableField.Type.VALUE));
		fields.add(new SmartChemTableField("Info", SmartChemTableField.Type.TEXT));
		fields.add(new SmartChemTableField("Structure", SmartChemTableField.Type.STRUCTURE));

		smartChemTable = new SmartChemTable(fields);

		add(smartChemTable,BorderLayout.CENTER);
		add(structureTable,BorderLayout.CENTER);
		
		statusPanel.parametersPanel.getChTableButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				smartChemTable.setVisible(true);
				structureTable.setVisible(false);
			}
		});
		statusPanel.parametersPanel.getStrTableButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				smartChemTable.setVisible(false);
				structureTable.setVisible(true);
			}
		});

	}

	public BasicReactorProcess getBasicReactorProcess() {
		return basicReactorProcess;
	}

	public void setBasicReactorProcess(BasicReactorProcess basicReactorProcess) {
		this.basicReactorProcess = basicReactorProcess;
	}

	@Override
	public IProcess getProcess() {
		return basicReactorProcess;
	}

	@Override
	public void updatePanel() 
	{	
		List<ReactorNode> resultNodes = 	basicReactorProcess.resultNodes;
		if (resultNodes == null)
			return;
		
		Reactor reactor = basicReactorProcess.reactor;
		
		for (int i = 0; i < resultNodes.size(); i++)
		{
			ReactorNode node = resultNodes.get(i);
			if (node.finalizedProducts.isEmpty())
				continue;
			
			List<Object> rowFields = new ArrayList<Object>();
	        
	        String prodSmi = reactor.molToSmiles(
	        			node.finalizedProducts.getAtomContainer(0));
	        rowFields.add(prodSmi);
	        rowFields.add(prodSmi);	        
	        smartChemTable.addTableRow(rowFields);
	        structureTable.addMoleculeAsString(prodSmi);
		}
	}

}
