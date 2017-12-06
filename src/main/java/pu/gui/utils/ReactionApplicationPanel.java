package pu.gui.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
		this.setSize(new Dimension(400,600));
		this.setLayout(new BorderLayout());
		
		targetPanel = new JPanel(new BorderLayout());
		reactionPanel = new JPanel(new BorderLayout());
		this.add(targetPanel,BorderLayout.NORTH);
		this.add(targetPanel,BorderLayout.SOUTH);
		
		JPanel smilesPanel = new JPanel(new FlowLayout());
		JLabel smilesInputLabel = new JLabel("Molecule (Smiles/InChI):");
		JTextField smilesField = new JTextField(20);
		smilesPanel.add(smilesInputLabel);
		smilesPanel.add(smilesField);
		targetPanel.add(smilesPanel, BorderLayout.NORTH);
		
		panel2d = new Panel2D();
		targetPanel.add(panel2d, BorderLayout.CENTER);
		try {
			IAtomContainer mol = SmartsHelper.getMoleculeFromSmiles("CCCc1ccccc1N");
			panel2d.setAtomContainer(mol);
		} catch (Exception e) {}


	}
	
}
