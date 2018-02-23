package pu.gui.utils.trees;

import ambit2.base.data.Property;
import ambit2.base.data.StructureRecord;
import ambit2.reactions.retrosynth.StartingMaterialsDataBase;
import ambit2.reactions.retrosynth.StartingMaterialsDataBase.StartMaterialData;
import ambit2.smarts.SmartsHelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import org.openscience.cdk.interfaces.IAtomContainer;

import pu.gui.utils.MoleculeDrawer;
import pu.gui.utils.panels.StructurePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;


public class MoleculeSetTree extends SetTree
{
	public static final String moleculeClassProperty = "MoleculeClass";
	
	private List<StructureRecord> structureRecords = new ArrayList<StructureRecord>();
	private StartingMaterialsDataBase startingMaterialsDataBase = null;
	//private List<IAtomContainer> molecules = new ArrayList<IAtomContainer>();
	private String rootNodeText = "Starting materials";
	//private MoleculeDrawer moleculeDrawer = new MoleculeDrawer();
	//private MoleculePanel moleculePanel = new MoleculePanel();
	private StructurePanel structurePanel = new StructurePanel();
	

	//private String dbFilePath;
	private  TableInfoPanel tableInfoPanel;
	DefaultMutableTreeNode root;
	
	public MoleculeSetTree(List<StructureRecord> structureRecords)
	{
		this.structureRecords = structureRecords;
		initGUI();
	}

	public MoleculeSetTree(List<String> smiles, List<String> moleculeClass)
	{	
		for (int i = 0; i < smiles.size(); i++)
		{
			StructureRecord sr = new StructureRecord();
			sr.setDataEntryID(i);
			sr.setSmiles(smiles.get(i));

			sr.setRecordProperty(new Property(moleculeClassProperty), moleculeClass.get(i));
			this.structureRecords.add(sr);
		}

		initGUI();
	}
	
	public MoleculeSetTree(StartingMaterialsDataBase startingMaterialsDataBase)
	{
		this.startingMaterialsDataBase = startingMaterialsDataBase;
		initGUI();
	}

	/*
	public String getDbFilePath() {
		return dbFilePath;
	}

	public void setDbFilePath(String dbFilePath) {
		this.dbFilePath = dbFilePath;
	}
	*/
	
	public List<StructureRecord> getStructureRecords(){
		return structureRecords;
	}
	
	
	public StartingMaterialsDataBase getStartingMaterialsDataBase() {
		return startingMaterialsDataBase;
	}

	
	/*
	public MoleculePanel getMoleculePanel() {
		return moleculePanel;
	}

	public void setMoleculePanel(MoleculePanel moleculePanel) {
		this.moleculePanel = moleculePanel;
	}
	*/
	
	
	private void initGUI() {
		infoPanel = new InfoPanel();
		tree = new JTree();
		setIcons("pu/images/molecule.png");

		this.add(tree);
		JScrollPane scrollBar = new JScrollPane(tree);
		this.setLayout(new BorderLayout());
		tableInfoPanel = new TableInfoPanel();
		dataToTree();
		searchBoxSet();
		setTreeSelectionListener();
		expandAllNodes(tree, 0, tree.getRowCount());
        //setMoleculePanel();
		
		JPanel southPanel = new JPanel();
		this.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new BorderLayout());
		structurePanel.setVisible(true);
		southPanel.add(structurePanel, BorderLayout.CENTER);
		southPanel.add(tableInfoPanel, BorderLayout.SOUTH);
		

		this.add(scrollBar, BorderLayout.CENTER);
		//this.add(tableInfoPanel, BorderLayout.SOUTH);
	}

	

	private void dataToTree()
	{
		root = new DefaultMutableTreeNode(rootNodeText);
		tree.setModel(new DefaultTreeModel(root));
		
		if (startingMaterialsDataBase != null)
		{
			Map<String, StartMaterialData> materials = startingMaterialsDataBase.getMaterials();
			
			for (Entry entry : materials.entrySet())
			{
				String inchiKey = (String)entry.getKey();
				StartMaterialData sm = (StartMaterialData)entry.getValue();
				//System.out.println(inchiKey + "  " + sm.id + "   " + sm.smiles);
				DefaultMutableTreeNode moleculeNode = new DefaultMutableTreeNode(sm.smiles);
				root.add(moleculeNode);
			}
			return;
		}
		
		for (int i = 0; i < structureRecords.size(); i++) {
			String reactionClass = getMoleculeClass(structureRecords.get(i));
			String[] levels = reactionClass.split(Pattern.quote("."));
			DefaultMutableTreeNode currentLevelNode = root;
			for (int j = 0; j < levels.length; j++) {
				String currentLevel = levels[j];
				DefaultMutableTreeNode nextLevelNode =  searchChildrenNode(currentLevel, currentLevelNode);
				if (nextLevelNode == null)
				{
					nextLevelNode = new DefaultMutableTreeNode(currentLevel);
					currentLevelNode.add(nextLevelNode);
				}
				currentLevelNode = nextLevelNode;
			}
			DefaultMutableTreeNode moleculeNode = new DefaultMutableTreeNode(getMoleculeName(structureRecords.get(i)));
			currentLevelNode.add(moleculeNode);
		}
	}


	String getMoleculeClass(StructureRecord mol)
	{
		Property propMolClass = new Property(moleculeClassProperty);
		return mol.getRecordProperty(propMolClass).toString();
	}

	
	private void setTreeSelectionListener()
	{
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				handle_TreeSelectionEventValueChanged(e);
			}
		});
	}
	
	private void handle_TreeSelectionEventValueChanged(TreeSelectionEvent e)
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				tree.getLastSelectedPathComponent();
		if (node == null){
			return;
		}
		else {
			if (node == root)
			{	
				tableInfoPanel.write("");
				return;
			}	
			
			String nodeText = getNodeInfoText(node);
			tableInfoPanel.write(nodeText);
			
			//Set Panel2D
			if (startingMaterialsDataBase != null)
			{
				//moleculePanel.removeAll();
				//moleculeDrawer.add2DMolecule(moleculePanel,nodeText);
				try {
					IAtomContainer mol = SmartsHelper.getMoleculeFromSmiles(nodeText);
					structurePanel.display(mol);
					
				} catch (Exception x) {
					x.printStackTrace();
				}
			}
			else
			{
				List<StructureRecord> strs =  structureRecords;
				for (StructureRecord str : strs){
					if (node.toString().equals(getMoleculeName(str))){
						//moleculePanel.removeAll();
						//moleculeDrawer.add2DMolecule(moleculePanel, str.getSmiles());
						try {
							IAtomContainer mol = SmartsHelper.getMoleculeFromSmiles(str.getSmiles());
							structurePanel.display(mol);
							
						} catch (Exception x) {
							x.printStackTrace();
						}
					}
				}
			}
			
		}
	}
	
	/*
	 * private void setMoleculePanel() 
	{
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
						tree.getLastSelectedPathComponent();

				List<StructureRecord> strs =  structureRecords;
				for (StructureRecord str : strs){
					if (node.toString().equals(getMoleculeName(str))){
						moleculePanel.removeAll();
						moleculeDrawer.add2DMolecule(moleculePanel, str.getSmiles());
					}
				}
			}
		});

	}
	 */
	
	
	String getNodeInfoText(DefaultMutableTreeNode node)
	{
		if (startingMaterialsDataBase != null)
		{
			if (node == tree.getModel().getRoot())
				return "root";
			else
				return node.toString();
		}
		
		StructureRecord r = null;
		for(StructureRecord str : structureRecords){
			if (node.toString().equals(Integer.toString(str.getDataEntryID())))
			{
				r = str;
				break;
			}
		}
		if (r == null)
			return "Name: " + node.toString();
		else
		{
			StringBuffer sb = new StringBuffer();
			sb.append("Smiles: " );
			sb.append(r.getSmiles() + "\n");
			return sb.toString();
		}
	}
	
	
	public String getMoleculeName(StructureRecord structureRecord)
	{
		if(structureRecord.getFormula() != null){
			return structureRecord.getFormula();
		}else if(structureRecord.getSmiles() != null){
			return structureRecord.getSmiles();
		}else return Integer.toString(structureRecord.getDataEntryID());
	}

}



