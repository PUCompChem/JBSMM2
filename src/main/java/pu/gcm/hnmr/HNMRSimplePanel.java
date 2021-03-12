package pu.gcm.hnmr;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.smarts.SmartsHelper;
import ambit2.ui.Panel2D;
import pu.gui.utils.chemtable.StructureTable;

public class HNMRSimplePanel extends JPanel 
{

	//GUI components
	JPanel leftArea, rightArea;
	JPanel targetPanel, matchPanel, reactionPanel, buttonsPanel, configPanel;
	JTextField smilesField;
	JTextArea resultTextArea;
	Panel2D panel2d;	
	JButton runButton;
	
	//Chem data
	IAtomContainer targetMol = null;
	
	
	public HNMRSimplePanel() throws Exception
	{
		initGUI();

		//Setup initial demo chem data
		try {
			targetMol = SmartsHelper.getMoleculeFromSmiles("c1ccncc1");
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
		
	}
	
	
	
	private void handleRunButton(ActionEvent e)
	{
		try {
			targetMol = SmartsHelper.getMoleculeFromSmiles(smilesField.getText());
			panel2d.setAtomContainer(targetMol);
		} catch (Exception x) {
			System.out.println("Incorrect input smiles: " + x.getMessage());
		}
	}	
	
}
