package pu.gui.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import pu.gui.utils.chemtable.StructureTable;
import ambit2.smarts.SMIRKSManager;
import ambit2.smarts.SmartsConst;
import ambit2.smarts.SmartsHelper;
import ambit2.smarts.SmartsConst.HandleHAtoms;
import ambit2.ui.Panel2D;

public class ReactionApplicationPanel extends JPanel
{
	
	private static final long serialVersionUID = 112332423349877L;
	
	//GUI components
	JPanel targetPanel, matchPanel, reactionPanel, buttonsPanel;
	JTextField smilesField, smirksField;
	Panel2D panel2d;
	StructureTable structureTable;
	JButton applyButton;
	
	//Chem data
	SMIRKSManager smrkMan = new SMIRKSManager(SilentChemObjectBuilder.getInstance());
	boolean FlagClearAromaticityBeforePreProcess = true;
	//boolean FlagCheckAromaticityOnTargetPreProcess = true;
	//boolean FlagTargetPreprocessing = false;
	//boolean FlagExplicitHAtoms = false;  
	//boolean FlagPrintAtomAttributes = false;
	//boolean FlagPrintTransformationData = false;

	boolean FlagProductPreprocessing = true;
	boolean FlagClearImplicitHAtomsBeforeProductPreProcess = false;
	boolean FlagClearHybridizationOnProductPreProcess = true;
	boolean FlagAddImplicitHAtomsOnProductPreProcess = false;
	boolean FlagImplicitHToExplicitOnProductPreProcess = false;
	boolean FlagExplicitHToImplicitOnProductPreProcess = false;
	
	boolean FlagApplyStereoTransformation = false;
	boolean FlagHAtomsTransformation = false;
	HandleHAtoms FlagHAtomsTransformationMode = HandleHAtoms.IMPLICIT; 
	boolean FlagAromaticityTransformation = false;

	boolean FlagSingleBondAromaticityNotSpecified = false;
	boolean FlagDoubleBondAromaticityNotSpecified = false;

	SmartsConst.SSM_MODE FlagSSMode = SmartsConst.SSM_MODE.SSM_NON_OVERLAPPING;
	//SmartsConst.SSM_MODE FlagSSModeForSingleCopyForEachPos = SmartsConst.SSM_MODE.SSM_NON_IDENTICAL;
	
	
	public ReactionApplicationPanel()
	{
		initGUI();
		
		//Setup initial demo chem data
		try {
			IAtomContainer mol = SmartsHelper.getMoleculeFromSmiles("c1ccccc1");
			panel2d.setAtomContainer(mol);
		} catch (Exception e) {}
		smilesField.setText("c1ccccc1");
		smirksField.setText("[c:1][H]>>[c:1]O[H]");
		
		structureTable.addMoleculeAsString("CCCC");
		structureTable.addMoleculeAsString("CCC(N)CO");
		structureTable.addMoleculeAsString("CCCCC");
		structureTable.addMoleculeAsString("CCCCCCC");
		structureTable.addMoleculeAsString("CCCCNCCO");
	}
	
	private void initGUI()
	{	
		this.setLayout(new BorderLayout());
		
		//Target panel
		targetPanel = new JPanel(new BorderLayout());
		this.add(targetPanel, BorderLayout.NORTH);	
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
		
		//Reaction panel
		reactionPanel = new JPanel(new BorderLayout());
		this.add(reactionPanel, BorderLayout.CENTER);
		//reactionPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		
		JPanel smirksPanel = new JPanel(new FlowLayout());
		JLabel smirksLabel = new JLabel("SMIRKS: ");
		smirksField = new JTextField(30);
		smirksPanel.add(smirksLabel);
		smirksPanel.add(smirksField);
		reactionPanel.add(smirksPanel, BorderLayout.NORTH);
		
		JPanel productsPanel = new JPanel(new BorderLayout());
		JLabel productsLabel = new JLabel("Reaction products:");
		productsPanel.add(productsLabel, BorderLayout.NORTH);
		structureTable = new StructureTable(3);
		productsPanel.add(structureTable, BorderLayout.CENTER);
		reactionPanel.add(productsPanel, BorderLayout.CENTER);
		
		//Buttons panel
		buttonsPanel = new JPanel(new FlowLayout());
		this.add(buttonsPanel, BorderLayout.SOUTH);
		buttonsPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		
		applyButton = new JButton("Apply");
		buttonsPanel.add(applyButton);
		applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleApplyButton(e);	
			}
		});

	}
	
	private void handleApplyButton(ActionEvent e)
	{
		try {
			IAtomContainer mol = SmartsHelper.getMoleculeFromSmiles(smilesField.getText());
			panel2d.setAtomContainer(mol);
		} catch (Exception x) {
			System.out.println("Incorrect input smiles: " + x.getMessage());
		}
		
		
		//smirksField.setText("[c:1][H]>>[c:1]O[H]");
		
		//System.out.println("---- apply ------");
	}
	
	private void applySMIRKSReaction() throws Exception
	{
		smrkMan.setFlagSSMode(FlagSSMode);

		// Product processing flags
		smrkMan.setFlagProcessResultStructures(FlagProductPreprocessing);
		smrkMan.setFlagClearHybridizationBeforeResultProcess(FlagClearHybridizationOnProductPreProcess);
		smrkMan.setFlagClearImplicitHAtomsBeforeResultProcess(this.FlagClearImplicitHAtomsBeforeProductPreProcess);
		smrkMan.setFlagClearAromaticityBeforeResultProcess(this.FlagClearAromaticityBeforePreProcess);
		smrkMan.setFlagAddImplicitHAtomsOnResultProcess(this.FlagAddImplicitHAtomsOnProductPreProcess);
		smrkMan.setFlagConvertAddedImplicitHToExplicitOnResultProcess(this.FlagImplicitHToExplicitOnProductPreProcess);
		smrkMan.setFlagConvertExplicitHToImplicitOnResultProcess(this.FlagExplicitHToImplicitOnProductPreProcess);
		smrkMan.setFlagApplyStereoTransformation(FlagApplyStereoTransformation);
		smrkMan.setFlagHAtomsTransformation(FlagHAtomsTransformation);
		smrkMan.setFlagHAtomsTransformationMode(FlagHAtomsTransformationMode);
		smrkMan.setFlagAromaticityTransformation(FlagAromaticityTransformation);

		smrkMan.getSmartsParser().mSupportSingleBondAromaticityNotSpecified = FlagSingleBondAromaticityNotSpecified;
		smrkMan.getSmartsParser().mSupportDoubleBondAromaticityNotSpecified = FlagDoubleBondAromaticityNotSpecified;
	}
	
}
