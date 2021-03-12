package pu.gcm.hnmr;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import ambit2.groupcontribution.nmr.HNMRShifts;
import ambit2.groupcontribution.nmr.nmr_1h.HShift;
import ambit2.smarts.SmartsHelper;
import ambit2.ui.Panel2D;
import pu.gui.utils.chemtable.StructureTable;

public class HNMRSimplePanel extends JPanel 
{

	//GUI components
	JPanel leftArea, rightArea;
	JPanel targetPanel, buttonsPanel, configPanel;
	JTextField smilesField;
	JTextArea resultTextArea;
	Panel2D panel2d;	
	JButton runButton;

	//Chem data
	String inputSmiles = null;
	IAtomContainer targetMol = null;
	HNMRShifts hnmrShifts = null;
	
	//Config flags
	String defaultConfigFile = "./hnmr-knowledgebase.txt";
	String configFile = "hnmr-knowledgebase.txt";
	boolean printExplanation = true;
	boolean printLog = false;
	boolean spinSplit = true;
	


	public HNMRSimplePanel() throws Exception
	{
		initGUI();
		initHNMR();

		//Setup initial demo chem data
		try {
			targetMol = SmartsHelper.getMoleculeFromSmiles("CCCC");
			panel2d.setAtomContainer(targetMol);
		} catch (Exception e) {}
		smilesField.setText("CCCC");
		//smirksField.setText("[c:1][H]>>[c:1]O[H]");
		//panel2dMatch.setAtomContainer(targetMol.clone());
	}


	private void initGUI()
	{	
		this.setLayout(new BorderLayout());
		leftArea = new JPanel(new BorderLayout());
		rightArea = new JPanel(new BorderLayout());
		this.add(leftArea, BorderLayout.CENTER);
		this.add(rightArea, BorderLayout.EAST);

		//Target panel
		targetPanel = new JPanel(new BorderLayout());
		//this.add(targetPanel, BorderLayout.NORTH);
		leftArea.add(targetPanel, BorderLayout.NORTH);
		targetPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		JPanel smilesPanel = new JPanel(new FlowLayout());
		JLabel smilesInputLabel = new JLabel("Target molecule (Smiles/InChI): ");
		smilesField = new JTextField(30);
		smilesPanel.add(smilesInputLabel);
		smilesPanel.add(smilesField);		
		targetPanel.add(smilesPanel, BorderLayout.NORTH);
		panel2d = new Panel2D();
		panel2d.setPreferredSize(new Dimension(150,150));
		targetPanel.add(panel2d, BorderLayout.CENTER);


		//Buttons panel (is put within target panel)
		buttonsPanel = new JPanel(new FlowLayout());
		//this.add(buttonsPanel, BorderLayout.SOUTH);
		targetPanel.add(buttonsPanel, BorderLayout.SOUTH);
		buttonsPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		runButton = new JButton("Run");
		buttonsPanel.add(runButton);
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleRunButton(e);	
			}
		});

		resultTextArea = new JTextArea();
		resultTextArea.setEditable(false);
		resultTextArea.setLineWrap(true);

		JScrollPane scroll = new JScrollPane (resultTextArea);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scroll.setPreferredSize(new Dimension(10,10));
		leftArea.add(scroll, BorderLayout.CENTER);


	}
	
	public void initHNMR() 
	{
		String knowledgeBaseFileName = configFile;
		if (knowledgeBaseFileName == null)
		{	
			knowledgeBaseFileName = defaultConfigFile;
			System.out.println("Using default HNMR database: " + defaultConfigFile);
		}
		
		try {
			hnmrShifts = new HNMRShifts(new  File(knowledgeBaseFileName));
			//hnmrShifts.setFlagSpinSplitting(spinSplit);
			//hnmrShifts.getSpinSplitManager().setFlagReportEquivalenceAtomCodes(true);
		}
		catch (Exception x) {
			System.out.println(x.getMessage());
		}
	}



	private void handleRunButton(ActionEvent e)
	{
		try {
			targetMol = SmartsHelper.getMoleculeFromSmiles(smilesField.getText());
			panel2d.setAtomContainer(targetMol);
		} catch (Exception x) {
			System.out.println("Incorrect input smiles: " + x.getMessage());
		}


		inputSmiles = smilesField.getText();
		resultTextArea.setText("test: " + smilesField.getText());
		runForInputSmiles();

	}

	public int runForInputSmiles() 
	{
		System.out.println("Input smiles: " + inputSmiles);
		IAtomContainer mol = null;

		try {
			mol = SmartsHelper.getMoleculeFromSmiles(inputSmiles);
			AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(mol);
		}
		catch (Exception e) {
			System.out.println("Error on creating molecule from SMILES:\n" + e.getMessage());
			return -1;
		}

		return predictForMolecule(mol);		
	}
	

	public int predictForMolecule(IAtomContainer mol) 
	{

		try {
			hnmrShifts.setStructure(mol);
			hnmrShifts.calculateHShifts();
		}
		catch(Exception x) {
			System.out.println("Calculation error:\n" + x.getMessage());
			return -1;
		}

		if (printLog) {
			System.out.println("Log:\n" + hnmrShifts.getCalcLog());
			System.out.println();
		}

		for (HShift hs : hnmrShifts.getHShifts())
			System.out.println(hs.toString());
			//System.out.println(hs.toString(printExplanation));

		return 0;
	}

}
