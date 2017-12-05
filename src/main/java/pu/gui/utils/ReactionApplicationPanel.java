package pu.gui.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ReactionApplicationPanel extends JPanel
{
	JPanel targetPanel, matchPanel, reactionPanel;
	
	public ReactionApplicationPanel()
	{
		initGUI();
	}
	
	private void initGUI()
	{
		setSize(new Dimension(400,600));
		setLayout(new BorderLayout());
		targetPanel = new JPanel(new FlowLayout());
		reactionPanel = new JPanel(new FlowLayout());
		add(targetPanel,BorderLayout.NORTH);
		JLabel smilesInputLabel = new JLabel("Molecule (Smiles/InChI):");
		JTextField smilesField = new JTextField(20);
		targetPanel.add(smilesInputLabel);
		targetPanel.add(smilesField);
		
	}
	
}
