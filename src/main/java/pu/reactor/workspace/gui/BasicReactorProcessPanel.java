package pu.reactor.workspace.gui;

import pu.gui.utils.structTable.StructureTable;
import pu.reactor.workspace.BasicReactorProcess;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

/**
 * Created by gogo on 27.7.2017 г..
 */
public class BasicReactorProcessPanel extends JPanel
{
   
    private BasicReactorProcess basicReactorProcess;
    private StructurePanel structurePanel;
    private StructureTable structureTable = new StructureTable(5);
    
    public BasicReactorProcessPanel(BasicReactorProcess basicReactorProcess)  {
        this.setBasicReactorProcess(basicReactorProcess);
        structurePanel = new StructurePanel(basicReactorProcess);
            initGUI();
    }



    private void initGUI() {

        setLayout(new BorderLayout());
        structurePanel.setPreferredSize(new Dimension(500,300));

        add(structurePanel,BorderLayout.NORTH);
        add(structureTable,BorderLayout.CENTER);
    }

	public BasicReactorProcess getBasicReactorProcess() {
		return basicReactorProcess;
	}

	public void setBasicReactorProcess(BasicReactorProcess basicReactorProcess) {
		this.basicReactorProcess = basicReactorProcess;
	}

}
