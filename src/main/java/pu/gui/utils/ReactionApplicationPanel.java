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
import javax.swing.border.EtchedBorder;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import pu.gui.utils.chemtable.StructureTable;
import ambit2.core.data.MoleculeTools;
import ambit2.core.helper.CDKHueckelAromaticityDetector;
import ambit2.smarts.SMIRKSManager;
import ambit2.smarts.SMIRKSReaction;
import ambit2.smarts.SmartsConst;
import ambit2.smarts.SmartsHelper;
import ambit2.smarts.SmartsConst.HandleHAtoms;
import ambit2.ui.Panel2D;

public class ReactionApplicationPanel extends JPanel
{
	enum ReactionOperation {
		APPLY, CombinedOverlappedPos, SingleCopyForEachPos
	}
	
	private static final long serialVersionUID = 112332423349877L;

	//GUI components
	JPanel targetPanel, matchPanel, reactionPanel, buttonsPanel;
	JTextField smilesField, smirksField;
	Panel2D panel2d;
	StructureTable structureTable;
	JButton applyButton;

	//Chem data
	SMIRKSManager smrkMan = new SMIRKSManager(SilentChemObjectBuilder.getInstance());
	IAtomContainer targetMol = null;
	
	ReactionOperation FlagReactionOperation = ReactionOperation.SingleCopyForEachPos;
	
	boolean FlagClearAromaticityBeforeProcess = true;
	boolean FlagCheckAromaticityOnTargetProcess = true;
	boolean FlagTargetProcessing = true;
	boolean FlagExplicitHAtoms = true;  
	boolean FlagPrintAtomAttributes = false;
	boolean FlagPrintTransformationData = false;

	boolean FlagProductProcessing = true;
	boolean FlagClearImplicitHAtomsBeforeProductProcess = false;
	boolean FlagClearHybridizationOnProductProcess = true;
	boolean FlagAddImplicitHAtomsOnProductProcess = false;
	boolean FlagImplicitHToExplicitOnProductProcess = false;
	boolean FlagExplicitHToImplicitOnProductProcess = true;

	boolean FlagApplyStereoTransformation = false;
	boolean FlagHAtomsTransformation = false;
	HandleHAtoms FlagHAtomsTransformationMode = HandleHAtoms.IMPLICIT; 
	boolean FlagAromaticityTransformation = false;

	boolean FlagSingleBondAromaticityNotSpecified = false;
	boolean FlagDoubleBondAromaticityNotSpecified = false;

	SmartsConst.SSM_MODE FlagSSMode = SmartsConst.SSM_MODE.SSM_NON_OVERLAPPING;
	SmartsConst.SSM_MODE FlagSSModeForSingleCopyForEachPos = SmartsConst.SSM_MODE.SSM_NON_IDENTICAL;


	public ReactionApplicationPanel()
	{
		initGUI();

		//Setup initial demo chem data
		try {
			targetMol = SmartsHelper.getMoleculeFromSmiles("c1ccccc1");
			panel2d.setAtomContainer(targetMol);
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
			targetMol = SmartsHelper.getMoleculeFromSmiles(smilesField.getText());
			panel2d.setAtomContainer(targetMol);
		} catch (Exception x) {
			System.out.println("Incorrect input smiles: " + x.getMessage());
		}
		
		try {
			String smirks = smirksField.getText();
			IAtomContainer target = targetMol.clone();
			applySMIRKSReaction(smirks, target);
		}	
		catch (Exception x) {
			System.out.println("Error while applying reaction: " + x.getMessage());
		}
	}

	private void applySMIRKSReaction(String smirks, IAtomContainer target) throws Exception
	{
		smrkMan.setFlagSSMode(FlagSSMode);

		// Product processing flags
		smrkMan.setFlagProcessResultStructures(FlagProductProcessing);
		smrkMan.setFlagClearHybridizationBeforeResultProcess(FlagClearHybridizationOnProductProcess);
		smrkMan.setFlagClearImplicitHAtomsBeforeResultProcess(FlagClearImplicitHAtomsBeforeProductProcess);
		smrkMan.setFlagClearAromaticityBeforeResultProcess(FlagClearAromaticityBeforeProcess);
		smrkMan.setFlagAddImplicitHAtomsOnResultProcess(FlagAddImplicitHAtomsOnProductProcess);
		smrkMan.setFlagConvertAddedImplicitHToExplicitOnResultProcess(FlagImplicitHToExplicitOnProductProcess);
		smrkMan.setFlagConvertExplicitHToImplicitOnResultProcess(FlagExplicitHToImplicitOnProductProcess);
		smrkMan.setFlagApplyStereoTransformation(FlagApplyStereoTransformation);
		smrkMan.setFlagHAtomsTransformation(FlagHAtomsTransformation);
		smrkMan.setFlagHAtomsTransformationMode(FlagHAtomsTransformationMode);
		smrkMan.setFlagAromaticityTransformation(FlagAromaticityTransformation);

		smrkMan.getSmartsParser().mSupportSingleBondAromaticityNotSpecified = FlagSingleBondAromaticityNotSpecified;
		smrkMan.getSmartsParser().mSupportDoubleBondAromaticityNotSpecified = FlagDoubleBondAromaticityNotSpecified;

		SMIRKSReaction reaction = smrkMan.parse(smirks);
		if (!smrkMan.getErrors().equals("")) {
			System.out.println(smrkMan.getErrors());
			return;
		}

		if (FlagTargetProcessing)
			this.preProcess(target);
		
		switch (FlagReactionOperation) 
		{
		case APPLY:
			boolean res = smrkMan.applyTransformation(target, reaction);

			if (FlagPrintAtomAttributes) {
				System.out.println("Product:");
				System.out.println("Product atom attributes:\n"
						+ SmartsHelper.getAtomsAttributes(target));
				System.out.println("Product bond attributes:\n"
						+ SmartsHelper.getBondAttributes(target));
			}

			System.out.println("    "
					+ SmartsHelper.moleculeToSMILES(target, true));

			String transformedSmiles = SmartsHelper.moleculeToSMILES(target,
					true);

			if (res)
			{	
				/*
				System.out.println("Reaction application: " + targetSmiles
						+ "  -->  " + transformedSmiles + "    abs. smiles res " + 
						SmilesGenerator.absolute().create(target));
				*/		
			}	
			else
				System.out.println("Reaction not appicable!");
			break;

		case CombinedOverlappedPos:
			IAtomContainerSet resSet = smrkMan
			.applyTransformationWithCombinedOverlappedPos(target, null,
					reaction);
			if (resSet == null)
				System.out.println("Reaction not appicable!");
			else {
				System.out
				.println("Reaction application With Combined Overlapped Positions: ");
				for (int i = 0; i < resSet.getAtomContainerCount(); i++)
					System.out.println(SmartsHelper.moleculeToSMILES(
							resSet.getAtomContainer(i), true));
			}
			break;

		case SingleCopyForEachPos:
			IAtomContainerSet resSet2 = smrkMan
			.applyTransformationWithSingleCopyForEachPos(target, null,
					reaction, FlagSSModeForSingleCopyForEachPos);
			if (resSet2 == null)
				System.out.println("Reaction not appicable!");
			else {
				System.out
				.println("Reaction application With Single Copy For Each Position: ");
				for (int i = 0; i < resSet2.getAtomContainerCount(); i++)
					System.out.println(SmartsHelper.moleculeToSMILES(
							resSet2.getAtomContainer(i), true));
			}
			break;
		}

	}

	public void preProcess(IAtomContainer mol) throws Exception
	{
		if (FlagClearAromaticityBeforeProcess) {
			for (IAtom atom : mol.atoms())
				if (atom.getFlag(CDKConstants.ISAROMATIC))
					atom.setFlag(CDKConstants.ISAROMATIC, false);
			for (IBond bond : mol.bonds())
				if (bond.getFlag(CDKConstants.ISAROMATIC))
					bond.setFlag(CDKConstants.ISAROMATIC, false);
		}

		AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(mol);

		CDKHydrogenAdder adder = CDKHydrogenAdder
				.getInstance(SilentChemObjectBuilder.getInstance());
		adder.addImplicitHydrogens(mol);
		if (FlagExplicitHAtoms)
			MoleculeTools.convertImplicitToExplicitHydrogens(mol);

		if (FlagCheckAromaticityOnTargetProcess)	
			CDKHueckelAromaticityDetector.detectAromaticity(mol);
	}

}
