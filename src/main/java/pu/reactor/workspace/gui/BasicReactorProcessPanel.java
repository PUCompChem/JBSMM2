package pu.reactor.workspace.gui;

import ambit2.base.data.StructureRecord;
import pu.gui.utils.chemTable.SmartChemTable;
import pu.gui.utils.chemTable.SmartChemTableField;
import pu.gui.utils.structTable.StructureTable;
import pu.helpers.StructureSetUtils;
import pu.reactor.workspace.BasicReactorProcess;
import pu.reactor.workspace.IProcess;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;

/**
 * Created by gogo on 27.7.2017 г..
 */
public class BasicReactorProcessPanel extends ProcessPanel
{
   
    private BasicReactorProcess basicReactorProcess;
    private StructurePanel structurePanel;
    private StructureTable structureTable = new StructureTable(5);

    private SmartChemTable smartChemTable = new SmartChemTable();
    
    public BasicReactorProcessPanel(BasicReactorProcess basicReactorProcess)  
    {
        this.setBasicReactorProcess(basicReactorProcess);
        StructureRecord r = StructureSetUtils.getStructureRecordFromString(basicReactorProcess.getTargetInputString());
        structurePanel = new StructurePanel(basicReactorProcess, r);
            initGUI();
    }


    public ActionListener chemTableRadioButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };



    private void initGUI() {

        setLayout(new BorderLayout());
        structurePanel.setPreferredSize(new Dimension(500,300));
        add(structurePanel,BorderLayout.NORTH);


        /**
         * StartTest
         */
        List<StructureRecord> structureRecords = new ArrayList<StructureRecord>();
       java.util.List<SmartChemTableField> fields = new ArrayList<SmartChemTableField>();
        fields.add(new SmartChemTableField("No", SmartChemTableField.Type.VALUE));
        fields.add(new SmartChemTableField("Name", SmartChemTableField.Type.TEXT));
        fields.add(new SmartChemTableField("Structure", SmartChemTableField.Type.STRUCTURE));
        fields.add(new SmartChemTableField("Str2", SmartChemTableField.Type.STRUCTURE));


        smartChemTable = new SmartChemTable(fields);
        List<Object> rowFields = new ArrayList<Object>();
        rowFields.add(1);
        rowFields.add("propane");
        rowFields.add("CCC");
        rowFields.add("CCCO");
        smartChemTable.addTableRow(rowFields);

        rowFields = new ArrayList<Object>();
        rowFields.add(2);
        rowFields.add("pentane");
        rowFields.add("CCCCC");
        rowFields.add("CCCCCO");
        smartChemTable.addTableRow(rowFields);

        smartChemTable.addStructureRecord(structureRecords);

        /**
         * EndTest
         */

        add(structureTable,BorderLayout.CENTER);
        add(smartChemTable,BorderLayout.CENTER);


        structurePanel.parametersPanel.getChTableButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                smartChemTable.setVisible(true);
                structureTable.setVisible(false);
            }
        });
        structurePanel.parametersPanel.getStrTableButton().addActionListener(new ActionListener() {
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
	public void updatePanel() {
		// TODO 
		
	}

}
