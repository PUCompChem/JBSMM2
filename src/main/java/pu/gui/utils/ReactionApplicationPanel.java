package pu.gui.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import org.openscience.cdk.AtomContainerSet;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.renderer.selection.IChemObjectSelection;
import org.openscience.cdk.renderer.selection.SingleSelection;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import pu.gui.utils.chemtable.StructureTable;
import ambit2.core.data.MoleculeTools;
import ambit2.core.helper.CDKHueckelAromaticityDetector;
import ambit2.rendering.IAtomContainerHighlights;
import ambit2.smarts.SMIRKSManager;
import ambit2.smarts.SMIRKSReaction;
import ambit2.smarts.SmartsConst;
import ambit2.smarts.SmartsHelper;
import ambit2.smarts.SmartsConst.HandleHAtoms;
import ambit2.ui.Panel2D;
import net.idea.modbcum.i.exceptions.AmbitException;

public class ReactionApplicationPanel extends JPanel
{
	enum ReactionOperation {
		APPLY, CombinedOverlappedPos, SingleCopyForEachPos
	}
	
	private static final long serialVersionUID = 112332423349877L;

	//GUI components
	JPanel leftArea, rightArea;
	JPanel targetPanel, matchPanel, reactionPanel, buttonsPanel, configPanel;
	JTextField smilesField, smirksField;
	Panel2D panel2d;
	Panel2D panel2dMatch;
	StructureTable structureTable;
	JButton applyButton;
	
	JCheckBox checkboxClearAromaticityBeforeProcess;
	JCheckBox checkboxCheckAromaticityOnTargetProcess;
	JCheckBox checkboxTargetProcessing;
	JCheckBox checkboxExplicitHAtoms;  
	JCheckBox checkboxPrintAtomAttributes;
	JCheckBox checkboxPrintTransformationData;
	
	JCheckBox checkboxProductProcessing;
	JCheckBox checkboxClearImplicitHAtomsBeforeProductProcess;
	JCheckBox checkboxClearHybridizationOnProductProcess;
	JCheckBox checkboxAddImplicitHAtomsOnProductProcess;
	JCheckBox checkboxImplicitHToExplicitOnProductProcess;
	JCheckBox checkboxExplicitHToImplicitOnProductProcess;
	
	JCheckBox checkboxApplyStereoTransformation;
	JCheckBox checkboxHAtomsTransformation;	
	JCheckBox checkboxAromaticityTransformation;

	JCheckBox checkboxSingleBondAromaticityNotSpecified;
	JCheckBox checkboxDoubleBondAromaticityNotSpecified;
	
	//Chem data
	SMIRKSManager smrkMan = new SMIRKSManager(SilentChemObjectBuilder.getInstance());
	IAtomContainer targetMol = null;
	IAtomContainerSet result = null;
	
	ReactionOperation FlagReactionOperation = ReactionOperation.SingleCopyForEachPos;
	
	boolean FlagPrintAtomAttributes = false;
	boolean FlagPrintTransformationData = false;
	
	/*
	boolean FlagTargetProcessing = true;
	boolean FlagClearAromaticityBeforeProcess = true;
	boolean FlagCheckAromaticityOnTargetProcess = true;
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
	boolean FlagAromaticityTransformation = false;

	boolean FlagSingleBondAromaticityNotSpecified = false;
	boolean FlagDoubleBondAromaticityNotSpecified = false;
	*/	
	
	HandleHAtoms FlagHAtomsTransformationMode = HandleHAtoms.IMPLICIT; 
	SmartsConst.SSM_MODE FlagSSMode = SmartsConst.SSM_MODE.SSM_NON_OVERLAPPING;
	SmartsConst.SSM_MODE FlagSSModeForSingleCopyForEachPos = SmartsConst.SSM_MODE.SSM_NON_IDENTICAL;


	public ReactionApplicationPanel() throws Exception
	{
		initGUI();

		//Setup initial demo chem data
		try {
			targetMol = SmartsHelper.getMoleculeFromSmiles("c1ccncc1");
			panel2d.setAtomContainer(targetMol);
		} catch (Exception e) {}
		smilesField.setText("c1ccncc1");
		smirksField.setText("[c:1][H]>>[c:1]O[H]");
		
		AtomContainerHightlights selector = new AtomContainerHightlights();
		List<Integer> atomList = new ArrayList<Integer>();
		atomList.add(0);
		atomList.add(1);
		atomList.add(2);
		selector.setIndexList(atomList);
		panel2dMatch.setAtomContainer(targetMol.clone());
		panel2dMatch.setSelector(selector);
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
		
		//Match Panel is within target panel
		matchPanel = new JPanel(new BorderLayout());
		matchPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		targetPanel.add(matchPanel,BorderLayout.EAST);
		panel2dMatch = new Panel2D();
		panel2dMatch.setPreferredSize(new Dimension(150,150));
		matchPanel.add(panel2dMatch,BorderLayout.CENTER);
		JLabel matchLabel = new JLabel("Reaction match sites: ");
		matchPanel.add(matchLabel,BorderLayout.NORTH);

		//Reaction panel
		reactionPanel = new JPanel(new BorderLayout());
		//this.add(reactionPanel, BorderLayout.CENTER);
		leftArea.add(reactionPanel, BorderLayout.CENTER);
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

		//Buttons panel (is put within target panel)
		buttonsPanel = new JPanel(new FlowLayout());
		//this.add(buttonsPanel, BorderLayout.SOUTH);
		targetPanel.add(buttonsPanel, BorderLayout.SOUTH);
		buttonsPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		applyButton = new JButton("Apply");
		buttonsPanel.add(applyButton);
		applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleApplyButton(e);	
			}
		});
		
		
		//Config panel is within rightArea
		rightArea.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		configPanel = new JPanel();
		configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.PAGE_AXIS)); 
		rightArea.add(configPanel, BorderLayout.CENTER);
		JLabel labelConfig = new JLabel("Config");
		configPanel.add(labelConfig);
		setupCheckBoxes();
	}
	
	void setupCheckBoxes()
	{
		checkboxTargetProcessing = 
				new JCheckBox("TargetProcessing");
		checkboxTargetProcessing.setSelected(true);
		configPanel.add(checkboxTargetProcessing);
		
		checkboxClearAromaticityBeforeProcess = 
				new JCheckBox("ClearAromaticityBeforeProcess");
		checkboxClearAromaticityBeforeProcess.setSelected(true);
		configPanel.add(checkboxClearAromaticityBeforeProcess);
		
		checkboxCheckAromaticityOnTargetProcess = 
				new JCheckBox("CheckAromaticityOnTargetProcess");
		checkboxCheckAromaticityOnTargetProcess.setSelected(true);
		configPanel.add(checkboxCheckAromaticityOnTargetProcess);
		
		
		
		checkboxExplicitHAtoms = 
				new JCheckBox("ExplicitHAtoms");
		checkboxExplicitHAtoms.setSelected(true);
		configPanel.add(checkboxExplicitHAtoms);
		
		/*
		checkboxPrintAtomAttributes = 
				new JCheckBox("PrintAtomAttributes");
		checkboxPrintAtomAttributes.setSelected(false);
		configPanel.add(checkboxPrintAtomAttributes);
		
		checkboxPrintTransformationData = 
				new JCheckBox("PrintTransformationData");
		checkboxPrintTransformationData.setSelected(false);
		configPanel.add(checkboxPrintTransformationData);
		*/
		
		
		checkboxProductProcessing = 
				new JCheckBox("ProductProcessing");
		checkboxProductProcessing.setSelected(true);
		configPanel.add(checkboxProductProcessing);
		
		checkboxClearImplicitHAtomsBeforeProductProcess = 
				new JCheckBox("ClearImplicitHAtomsBeforeProductProcess");
		checkboxClearImplicitHAtomsBeforeProductProcess.setSelected(false);
		configPanel.add(checkboxClearImplicitHAtomsBeforeProductProcess);
		
		
		//This control should be always true therefore not added
		checkboxClearHybridizationOnProductProcess = 
				new JCheckBox("ClearHybridizationOnProductProcess");
		checkboxClearHybridizationOnProductProcess.setSelected(true);
		configPanel.add(checkboxClearHybridizationOnProductProcess);
		checkboxClearHybridizationOnProductProcess.setEnabled(false);
		
		checkboxAddImplicitHAtomsOnProductProcess = 
				new JCheckBox("AddImplicitHAtomsOnProductProcess");
		checkboxAddImplicitHAtomsOnProductProcess.setSelected(false);
		configPanel.add(checkboxAddImplicitHAtomsOnProductProcess);
		
		checkboxImplicitHToExplicitOnProductProcess = 
				new JCheckBox("ImplicitHToExplicitOnProductProcess");
		checkboxImplicitHToExplicitOnProductProcess.setSelected(false);
		configPanel.add(checkboxImplicitHToExplicitOnProductProcess);
		
		checkboxExplicitHToImplicitOnProductProcess = 
				new JCheckBox("ExplicitHToImplicitOnProductProcess");
		checkboxExplicitHToImplicitOnProductProcess.setSelected(true);
		configPanel.add(checkboxExplicitHToImplicitOnProductProcess);
		
		checkboxApplyStereoTransformation = 
				new JCheckBox("ApplyStereoTransformation");
		checkboxApplyStereoTransformation.setSelected(false);
		configPanel.add(checkboxApplyStereoTransformation);
		
		checkboxHAtomsTransformation = 
				new JCheckBox("HAtomsTransformation");
		checkboxHAtomsTransformation.setSelected(false);
		configPanel.add(checkboxHAtomsTransformation);
		
		checkboxAromaticityTransformation = 
				new JCheckBox("AromaticityTransformation");
		checkboxAromaticityTransformation.setSelected(false);
		configPanel.add(checkboxAromaticityTransformation);
		
		checkboxSingleBondAromaticityNotSpecified = 
				new JCheckBox("SingleBondAromaticityNotSpecified");
		checkboxSingleBondAromaticityNotSpecified.setSelected(false);
		configPanel.add(checkboxSingleBondAromaticityNotSpecified);
		
		checkboxDoubleBondAromaticityNotSpecified = 
				new JCheckBox("DoubleBondAromaticityNotSpecified");
		checkboxDoubleBondAromaticityNotSpecified.setSelected(false);
		configPanel.add(checkboxDoubleBondAromaticityNotSpecified);
		
		/*
		checkbox = 
				new JCheckBox("");
		checkbox.setSelected();
		configPanel.add(checkbox);
	
	HandleHAtoms FlagHAtomsTransformationMode = HandleHAtoms.IMPLICIT; 
	SmartsConst.SSM_MODE FlagSSMode = SmartsConst.SSM_MODE.SSM_NON_OVERLAPPING;
	SmartsConst.SSM_MODE FlagSSModeForSingleCopyForEachPos = SmartsConst.SSM_MODE.SSM_NON_IDENTICAL;

		 */
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
		
		structureTable.clearTable();
		
		if (result != null)
		{	
			for (IAtomContainer mol: result.atomContainers())
				structureTable.addMolecule(mol);
		}
			
		
	}

	private void applySMIRKSReaction(String smirks, IAtomContainer target) throws Exception
	{
		result = null;
		smrkMan.setFlagSSMode(FlagSSMode);

		// Product processing flags
		smrkMan.setFlagProcessResultStructures(checkboxProductProcessing.isSelected());
		smrkMan.setFlagClearHybridizationBeforeResultProcess(checkboxClearHybridizationOnProductProcess.isSelected());
		smrkMan.setFlagClearImplicitHAtomsBeforeResultProcess(checkboxClearImplicitHAtomsBeforeProductProcess.isSelected());
		smrkMan.setFlagClearAromaticityBeforeResultProcess(checkboxClearAromaticityBeforeProcess.isSelected());
		smrkMan.setFlagAddImplicitHAtomsOnResultProcess(checkboxAddImplicitHAtomsOnProductProcess.isSelected());
		smrkMan.setFlagConvertAddedImplicitHToExplicitOnResultProcess(checkboxImplicitHToExplicitOnProductProcess.isSelected());
		smrkMan.setFlagConvertExplicitHToImplicitOnResultProcess(checkboxExplicitHToImplicitOnProductProcess.isSelected());
		smrkMan.setFlagApplyStereoTransformation(checkboxApplyStereoTransformation.isSelected());
		smrkMan.setFlagHAtomsTransformation(checkboxHAtomsTransformation.isSelected());
		smrkMan.setFlagHAtomsTransformationMode(FlagHAtomsTransformationMode);
		smrkMan.setFlagAromaticityTransformation(checkboxAromaticityTransformation.isSelected());

		smrkMan.getSmartsParser().mSupportSingleBondAromaticityNotSpecified = checkboxSingleBondAromaticityNotSpecified.isSelected();
		smrkMan.getSmartsParser().mSupportDoubleBondAromaticityNotSpecified = checkboxDoubleBondAromaticityNotSpecified.isSelected();

		SMIRKSReaction reaction = smrkMan.parse(smirks);
		if (!smrkMan.getErrors().equals("")) {
			System.out.println(smrkMan.getErrors());
			return;
		}

		if (checkboxTargetProcessing.isSelected())
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
				IAtomContainerSet resSet = new AtomContainerSet();
				resSet.addAtomContainer(target);
			}	
			else
				System.out.println("Reaction not appicable!");
			break;

		case CombinedOverlappedPos:
			result = smrkMan	.applyTransformationWithCombinedOverlappedPos(target, null,
					reaction);
			if (result == null)
				System.out.println("Reaction not appicable!");
			else {
				System.out
				.println("Reaction application With Combined Overlapped Positions: ");
				for (int i = 0; i < result.getAtomContainerCount(); i++)
					System.out.println(SmartsHelper.moleculeToSMILES(
							result.getAtomContainer(i), true));
			}
			break;

		case SingleCopyForEachPos:
			result = smrkMan	.applyTransformationWithSingleCopyForEachPos(target, null,
					reaction, FlagSSModeForSingleCopyForEachPos);
			
			if (result == null)
				System.out.println("Reaction not appicable!");
			else {
				System.out
				.println("Reaction application With Single Copy For Each Position: ");
				for (int i = 0; i < result.getAtomContainerCount(); i++)
					System.out.println(SmartsHelper.moleculeToSMILES(
							result.getAtomContainer(i), true));
			}
			break;
		}
	}

	public void preProcess(IAtomContainer mol) throws Exception
	{
		if (checkboxClearAromaticityBeforeProcess.isSelected()) {
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
		if (checkboxExplicitHAtoms.isSelected())
			MoleculeTools.convertImplicitToExplicitHydrogens(mol);

		if (checkboxCheckAromaticityOnTargetProcess.isSelected())	
			CDKHueckelAromaticityDetector.detectAromaticity(mol);
	}

}
