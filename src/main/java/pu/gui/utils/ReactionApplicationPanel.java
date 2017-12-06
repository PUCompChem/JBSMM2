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

import ambit2.smarts.SmartsHelper;
import ambit2.ui.Panel2D;

public class ReactionApplicationPanel extends JPanel
{
	
	private static final long serialVersionUID = 112332423349877L;
	
	JPanel targetPanel, matchPanel, reactionPanel;
	Panel2D panel2d;
	
	public ReactionApplicationPanel()
	{
		initGUI();
	}
	
	private void initGUI()
	{
		this.setSize(new Dimension(800,1000));
		this.setLayout(new BorderLayout());
		
		//Target panel
		targetPanel = new JPanel(new BorderLayout());
		this.add(targetPanel, BorderLayout.NORTH);	
		targetPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		JPanel smilesPanel = new JPanel(new FlowLayout());
		JLabel smilesInputLabel = new JLabel("Molecule (Smiles/InChI): ");
		JTextField smilesField = new JTextField(30);
		smilesPanel.add(smilesInputLabel);
		smilesPanel.add(smilesField);
		targetPanel.add(smilesPanel, BorderLayout.NORTH);
		
		panel2d = new Panel2D();
		panel2d.setPreferredSize(new Dimension(200,200));
		targetPanel.add(panel2d, BorderLayout.CENTER);
		try {
			IAtomContainer mol = SmartsHelper.getMoleculeFromSmiles("CCCc1ccccc1N");
			panel2d.setAtomContainer(mol);
		} catch (Exception e) {}
		
		//Reaction panel
		reactionPanel = new JPanel(new BorderLayout());
		this.add(reactionPanel, BorderLayout.CENTER);
		reactionPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		
		JPanel smirksPanel = new JPanel(new FlowLayout());
		JLabel smirksLabel = new JLabel("SMIRKS: ");
		JTextField smirksField = new JTextField(30);
		smirksPanel.add(smirksLabel);
		smirksPanel.add(smirksField);
		reactionPanel.add(smirksPanel, BorderLayout.NORTH);
		
		JPanel productsPanel = new JPanel(new BorderLayout());
		JLabel productsLabel = new JLabel("Reaction products:");
		productsPanel.add(productsLabel, BorderLayout.NORTH);
		reactionPanel.add(productsPanel, BorderLayout.CENTER);
		

	}
	
}
