package pu.gui.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import org.openscience.cdk.interfaces.IAtomContainer;

import pu.gui.utils.chemtable.StructureTable;
import ambit2.smarts.SmartsHelper;
import ambit2.ui.Panel2D;

public class ReactionApplicationPanel extends JPanel
{
	
	private static final long serialVersionUID = 112332423349877L;
	
	JPanel targetPanel, matchPanel, reactionPanel, buttonsPanel;
	JTextField smilesField, smirksField;
	Panel2D panel2d;
	StructureTable structureTable;
	
	public ReactionApplicationPanel()
	{
		initGUI();
		
		try {
			IAtomContainer mol = SmartsHelper.getMoleculeFromSmiles("CCCc1ccccc1N");
			panel2d.setAtomContainer(mol);
		} catch (Exception e) {}
		
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
		JLabel smilesInputLabel = new JLabel("Molecule (Smiles/InChI): ");
		smilesField = new JTextField(30);
		smilesPanel.add(smilesInputLabel);
		smilesPanel.add(smilesField);
		targetPanel.add(smilesPanel, BorderLayout.NORTH);
		panel2d = new Panel2D();
		panel2d.setPreferredSize(new Dimension(200,200));
		targetPanel.add(panel2d, BorderLayout.CENTER);
		
		//Reaction panel
		reactionPanel = new JPanel(new BorderLayout());
		this.add(reactionPanel, BorderLayout.CENTER);
		reactionPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		
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
		buttonsPanel = new JPanel(new BorderLayout());
		this.add(buttonsPanel, BorderLayout.SOUTH);
		reactionPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		StructureTable structureTable = new StructureTable(3);

	}
	
}
