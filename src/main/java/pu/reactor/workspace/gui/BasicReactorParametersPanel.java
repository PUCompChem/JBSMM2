package pu.reactor.workspace.gui;

import ambit2.reactions.reactor.ReactorStrategy;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Objects;

public class
BasicReactorParametersPanel extends JPanel {
	DefaultTableModel model = new DefaultTableModel(0,2);
	private ReactorStrategy strategy = new ReactorStrategy();
	JTable table;

	// visualizationOptions
	private JPanel visualizationOptions = new JPanel();
	private JRadioButton radioButton;

	public JRadioButton getStrTableButton() {
		return strTableButton;
	}

	public void setStrTableButton(JRadioButton strTableButton) {
		this.strTableButton = strTableButton;
	}

	public JRadioButton getChTableButton() {
		return chTableButton;
	}

	public void setChTableButton(JRadioButton chTableButton) {
		this.chTableButton = chTableButton;
	}

	private JRadioButton strTableButton;
	private JRadioButton chTableButton;


	public BasicReactorParametersPanel(ReactorStrategy strategy) {
		//this.strategy = strategy;
		initGUI2();
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		final Field[] strategyFields = strategy.getClass().getFields();
		for (int i = 0; i < strategyFields.length; i++) {
			Field currentField = strategyFields[i];
			Object[] rowData = new Object[2];
			rowData[0] = currentField.getName();

			currentField.setAccessible(true);


			try {
				Object a =  currentField.get(strategy);
				rowData[1] = a;
			} catch (IllegalAccessException e) {
				System.out.println("doesnt work");
			}


			currentField.setAccessible(true);


			model.addRow(rowData);

		}



		table  = new JTable(model)
		{
			public Class getColumnClass(int column) {
				Type ftype = strategyFields[column].getType();
				if (ftype.toString() == "boolean"){
					return Boolean.class;
				}
				return Object.class;
			}
		};
		table.setRowHeight(30);



		model.addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) 
			{	
				for (int i = 0; i < strategyFields.length; i++) {
					Field currentField = strategyFields[i];
					Type currentType = currentField.getType();

					try {
						if (currentType.toString() == "int")
						{
							Object a = currentField.get(strategy);
							if (table.getValueAt(i, 1) != a) {
								currentField.set(strategy, Integer.parseInt(table.getValueAt(i, 1).toString()));
							}
						}

						else if(currentType.toString() == "boolean")
						{
							Object a = currentField.get(strategy);
							if (table.getValueAt(i, 1) != a) {
								currentField.set(strategy, StringToBoolean(table.getValueAt(i, 1).toString()));
							}
						}
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}


				}
				System.out.println(strategy.FlagStopOnMaxLevel);

			}
		});



		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane,BorderLayout.CENTER);
	}
	
	private void initGUI2() {
		setLayout(new BorderLayout());
		
		setTheVariables();
		setVizualizationOptions();

		
		//TODO column 0 to be set non-editable !!!!
		
		table  = new JTable(model)
		{
			@Override
			public boolean isCellEditable(int row, int col) {
				switch (col) {

					case 1:
						return true;
					default:
						return false;
				}
			}
			/*
			public Class getColumnClass(int column) {
				Type ftype = strategyFields[column].getType();
				if (ftype.toString() == "boolean"){
					return Boolean.class;
				}
				return Object.class;
			}
			*/
		};

		model.addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) 
			{	
				handleTableChange(e);
			}
		});
		
		table.setRowHeight(18);
		JScrollPane scrollPane = new JScrollPane(table);

		add(scrollPane,BorderLayout.CENTER);
		add(visualizationOptions,BorderLayout.SOUTH);
	}

	private void handleTableChange(TableModelEvent e)
	{	
		if (e.getType() != TableModelEvent.UPDATE)
			return;
			
		int row = e.getFirstRow();
		int column = e.getColumn();
		
		Object value = table.getValueAt(row, column);
		String parameter = (String) table.getValueAt(row,0);
		
		if (parameter.equals("maxNumOfReactions"))
			strategy.maxNumOfReactions = updateIntegerStrategyParameter(strategy.maxNumOfReactions, value, row);
		else if (parameter.equals("maxLevel"))
			strategy.maxLevel = updateIntegerStrategyParameter(strategy.maxLevel, value, row);
		else if (parameter.equals("FlagStopOnMaxLevel"))
			strategy.FlagStopOnMaxLevel = updateBooleanStrategyParameter(strategy.FlagStopOnMaxLevel, value, row);
		else if (parameter.equals("maxNumOfNodes"))
			strategy.maxNumOfNodes = updateIntegerStrategyParameter(strategy.maxNumOfNodes, value, row);
		else if (parameter.equals("maxNumOfFailedNodes"))
			strategy.maxNumOfFailedNodes = updateIntegerStrategyParameter(strategy.maxNumOfFailedNodes, value, row);
		else if (parameter.equals("maxNumOfSuccessNodes"))
			strategy.maxNumOfSuccessNodes = updateIntegerStrategyParameter(strategy.maxNumOfSuccessNodes, value, row);
		else if (parameter.equals("FlagCheckReactionConditions"))
			strategy.FlagCheckReactionConditions = updateBooleanStrategyParameter(strategy.FlagCheckReactionConditions, value, row);
		else if (parameter.equals("FlagStoreSuccessNodes"))
			strategy.FlagStoreSuccessNodes = updateBooleanStrategyParameter(strategy.FlagStoreSuccessNodes, value, row);
		else if (parameter.equals("FlagStoreFailedNodes"))
			strategy.FlagStoreFailedNodes = updateBooleanStrategyParameter(strategy.FlagStoreFailedNodes, value, row);
		else if (parameter.equals("FlagTraceParentNodes"))
			strategy.FlagTraceParentNodes = updateBooleanStrategyParameter(strategy.FlagTraceParentNodes, value, row);
		else if (parameter.equals("FlagStoreProducts"))
			strategy.FlagStoreProducts = updateBooleanStrategyParameter(strategy.FlagStoreProducts, value, row);
		else if (parameter.equals("FlagTraceReactionPath"))
			strategy.FlagTraceReactionPath = updateBooleanStrategyParameter(strategy.FlagTraceReactionPath, value, row);
		else if (parameter.equals("FlagCheckNodeDuplicationOnPush"))
			strategy.FlagCheckNodeDuplicationOnPush = updateBooleanStrategyParameter(strategy.FlagCheckNodeDuplicationOnPush, value, row);
		else if (parameter.equals("FlagSuccessNodeOnReachingAllowedProducts"))
			strategy.FlagSuccessNodeOnReachingAllowedProducts = updateBooleanStrategyParameter(strategy.FlagSuccessNodeOnReachingAllowedProducts, value, row);
		else if (parameter.equals("FlagSuccessNodeOnZeroForbiddenProducts"))
			strategy.FlagSuccessNodeOnZeroForbiddenProducts = updateBooleanStrategyParameter(strategy.FlagSuccessNodeOnZeroForbiddenProducts, value, row);
		else if (parameter.equals("FlagFailedNodeOnOneForbiddenProduct "))
			strategy.FlagFailedNodeOnOneForbiddenProduct  = updateBooleanStrategyParameter(strategy.FlagFailedNodeOnOneForbiddenProduct, value, row);
		else if (parameter.equals("FlagReactOneReagentOnly"))
			strategy.FlagReactOneReagentOnly = updateBooleanStrategyParameter(strategy.FlagReactOneReagentOnly, value, row);
		else if (parameter.equals("FlagProcessRemainingStackNodes"))
			strategy.FlagProcessRemainingStackNodes = updateBooleanStrategyParameter(strategy.FlagProcessRemainingStackNodes, value, row);
		else if (parameter.equals("FlagProcessSingleReagentInNode"))
			strategy.FlagProcessSingleReagentInNode = updateBooleanStrategyParameter(strategy.FlagProcessSingleReagentInNode, value, row);
		else if (parameter.equals("FlagRemoveReagentIfAllowedProduct"))
			strategy.FlagRemoveReagentIfAllowedProduct = updateBooleanStrategyParameter(strategy.FlagRemoveReagentIfAllowedProduct, value, row);
		else if (parameter.equals("FlagRemoveReagentIfForbiddenProduct"))
			strategy.FlagRemoveReagentIfForbiddenProduct = updateBooleanStrategyParameter(strategy.FlagRemoveReagentIfForbiddenProduct, value, row);
		else if (parameter.equals("FlagLogMainReactionFlow"))
			strategy.FlagLogMainReactionFlow = updateBooleanStrategyParameter(strategy.FlagLogMainReactionFlow, value, row);
		else if (parameter.equals("FlagLogReactionPath"))
			strategy.FlagLogReactionPath = updateBooleanStrategyParameter(strategy.FlagLogReactionPath, value, row);
		else if (parameter.equals("FlagLogNameInReactionPath"))
			strategy.FlagLogNameInReactionPath = updateBooleanStrategyParameter(strategy.FlagLogNameInReactionPath, value, row);
		else if (parameter.equals("FlagLogExplicitHToImplicit"))
			strategy.FlagLogExplicitHToImplicit = updateBooleanStrategyParameter(strategy.FlagLogExplicitHToImplicit, value, row);
		else if (parameter.equals("FlagLogNumberOfProcessedNodes"))
			strategy.FlagLogNumberOfProcessedNodes = updateBooleanStrategyParameter(strategy.FlagLogNumberOfProcessedNodes, value, row);
		else if (parameter.equals("NodeLogingFrequency"))
			strategy.NodeLogingFrequency =updateIntegerStrategyParameter(strategy.NodeLogingFrequency, value, row);



		
	}

	private boolean updateBooleanStrategyParameter(boolean originalValue, Object valueFromTable, int tableRowNum) {
		try {
			return StringToBoolean(valueFromTable.toString());
		}catch (Exception e){
			table.setValueAt(originalValue, tableRowNum, 1);
			return originalValue;
		}
	}

	private int updateIntegerStrategyParameter(Integer originalValue, Object valueFromTable, int tableRowNum)
	{
		try {

			return Integer.parseInt(String.valueOf(valueFromTable));

		}catch (Exception ex){

		table.setValueAt(originalValue, tableRowNum, 1);
        return originalValue;
		}
	}



	private boolean StringToBoolean(String str) throws Exception {

		String value = str.toUpperCase();
		if(Objects.equals(value,"TRUE"))
		{
			return true;
		}

		else if(Objects.equals(value,"FALSE")){
			return false;
		}
		throw new Exception();
	}

	private void setVizualizationOptions(){


		strTableButton = new JRadioButton("structure table");

		strTableButton.setActionCommand("structure table");
		

		 chTableButton = new JRadioButton("smart chemical table");
		chTableButton.setActionCommand("smart chemical table");
		chTableButton.setSelected(true);





		ButtonGroup group = new ButtonGroup();
		group.add(strTableButton);
		group.add(chTableButton);
		visualizationOptions.add(strTableButton);
		visualizationOptions.add(chTableButton);

	}
	private void setTheVariables() {
		Object[] rowData = new Object[2];
		rowData[0] = "maxNumOfReactions";
		rowData[1] = strategy.maxNumOfReactions;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "maxLevel";
		rowData[1] = strategy.maxLevel;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagStopOnMaxLevel";
		rowData[1] = strategy.FlagStopOnMaxLevel;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "maxNumOfNodes";
		rowData[1] = strategy.maxNumOfNodes;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "maxNumOfFailedNodes";
		rowData[1] = strategy.maxNumOfFailedNodes;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "maxNumOfSuccessNodes";
		rowData[1] = strategy.maxNumOfSuccessNodes;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagCheckReactionConditions";
		rowData[1] = strategy.FlagCheckReactionConditions;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagStoreSuccessNodes";
		rowData[1] = strategy.FlagStoreSuccessNodes;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagStoreFailedNodes";
		rowData[1] = strategy.FlagStoreFailedNodes;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagTraceParentNodes";
		rowData[1] = strategy.FlagTraceParentNodes;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagStoreProducts";
		rowData[1] = strategy.FlagStoreProducts;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagTraceReactionPath";
		rowData[1] = strategy.FlagTraceReactionPath;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagCheckNodeDuplicationOnPush";
		rowData[1] = strategy.FlagCheckNodeDuplicationOnPush;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "allowedProducts";
		rowData[1] = strategy.allowedProducts;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "forbiddenProducts";
		rowData[1] = strategy.forbiddenProducts;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagSuccessNodeOnReachingAllowedProducts";
		rowData[1] = strategy.FlagSuccessNodeOnReachingAllowedProducts;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagReactOneReagentOnly";
		rowData[1] = strategy.FlagReactOneReagentOnly;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagProcessRemainingStackNodes";
		rowData[1] = strategy.FlagProcessRemainingStackNodes;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagProcessSingleReagentInNode";
		rowData[1] = strategy.FlagProcessSingleReagentInNode;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagRemoveReagentIfAllowedProduct";
		rowData[1] = strategy.FlagRemoveReagentIfAllowedProduct;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagRemoveReagentIfForbiddenProduct";
		rowData[1] = strategy.FlagRemoveReagentIfForbiddenProduct;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagLogMainReactionFlow";
		rowData[1] = strategy.FlagLogMainReactionFlow;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagLogReactionPath";
		rowData[1] = strategy.FlagLogReactionPath;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagLogNameInReactionPath";
		rowData[1] = strategy.FlagLogNameInReactionPath;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagLogExplicitHToImplicit ";
		rowData[1] = strategy.FlagLogExplicitHToImplicit ;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "FlagLogNumberOfProcessedNodes";
		rowData[1] = strategy.FlagLogNumberOfProcessedNodes;
		model.addRow(rowData);

		rowData = new Object[2];
		rowData[0] = "NodeLogingFrequency";
		rowData[1] = strategy.NodeLogingFrequency;
		model.addRow(rowData);
	}
}
