package pu.reactor;

import ambit2.base.data.Property;
import ambit2.base.data.StructureRecord;
import ambit2.reactions.Reaction;
import ambit2.reactions.ReactionDataBase;
import ambit2.reactions.retrosynth.StartingMaterialsDataBase;
import ambit2.smarts.SmartsHelper;
import ambit2.ui.Panel2D;

import org.openscience.cdk.interfaces.IAtomContainer;

import pu.gui.utils.ReactionToolBar;
import pu.gui.utils.PredefinedArrangements;
import pu.gui.utils.trees.MoleculeSetTree;
import pu.gui.utils.trees.ReactionSetTree;
import pu.gui.utils.WorkCaseTabSet;
import pu.io.FileUtilities;
import pu.reactor.json.PreferencesJsonParser;
import pu.reactor.workspace.Preferences;
import pu.reactor.workspace.gui.PreferencesWindow;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

import static pu.gui.utils.trees.MoleculeSetTree.moleculeClassProperty;

public class ReactorMainFrame extends JFrame {

	private static final long serialVersionUID = 7285903480279645290L;

	// Areas
	ArrayList<JPanel> areas = new ArrayList<JPanel>();
	ArrayList<JSplitPane> splitter = new ArrayList<JSplitPane>();

	// GUI Components and helper variables
	WorkCaseTabSet workCases; // Work cases - all open(active) objects
	JTabbedPane workCasesTabPane;
	ReactionSetTree reactionSetTree;
	MoleculeSetTree moleculeTree;
	ArrayList<Panel2D> p2dList = new ArrayList<Panel2D>();
	
	PreferencesWindow preferencesWindow = null;

	// Menu components
	JMenuBar menuBar;

	JMenu menuFile;
	JMenuItem miOpenMolecule;
	JMenuItem miExit;
	JMenuItem miOpenWorkspace;
	JMenuItem miSaveWorkspace;

	JMenu menuReact;
	JMenuItem miApplyReaction;
	JMenuItem miFindAllReactions;


	JMenu menuProcess;
	JMenuItem miWorkspaceProcess;


	JMenu menuSettings; 
	JMenuItem menuPreferences;
	
	private JMenuItem miWorkspaceSettings;
	private JMenu menuProjectSettings;
	private JMenuItem miProjectSettings;
	private JMenuItem miProcessSettings;

	private ReactionToolBar reactionToolBar;
	
	//Data, containers
	String preferencesFilePath = null;
	Preferences preferences = null;
	ReactionDataBase reactionDB = null;
	StartingMaterialsDataBase startingMaterialsDataBase = null;
	JTabbedPane treesTabPane;
	private JTabbedPane bottomCenterTabbedPanel;
	private JTextArea consoleFieldPanel;

	public ReactorMainFrame() throws Exception {
		super();
		initGUI();
	}
	
	public ReactorMainFrame(String preferencesFilePath) throws Exception {
		super();
		this.preferencesFilePath = preferencesFilePath;
		initGUI();
	}

	private void initGUI() throws Exception 
	{	
		setPreferences();
		preferencesWindow = new PreferencesWindow(preferences, preferencesFilePath);
		preferencesWindow.setSize(new Dimension(1000,800));
		preferencesWindow.setVisible(false);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("JBSMM Reactor");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				eh_rootWindowClosing(evt);
			}
		});

		setBounds(5, 5, 1000, 800);

		createMenus();

		// Setting the splitters and other GUI components
		prepareGUIAreas();

		//
		setReactionDB();
		setStartingMaterialsDB();
	//	setReactionTree
		if (reactionDB != null)
		{
			reactionSetTree = new ReactionSetTree(reactionDB.reactions);
			  treesTabPane = new JTabbedPane();
			treesTabPane.add("reactions",reactionSetTree);



			areas.get(0).setLayout(new BorderLayout());
			areas.get(0).add( treesTabPane, BorderLayout.CENTER);
		}

		reactionToolBar = new ReactionToolBar();
		this.add(reactionToolBar, BorderLayout.NORTH);

		/**
		 * set Molecules Tree
		 */

		List<String> smiles = new ArrayList<String>();
		List<String> molClass = new ArrayList<String>();

		 FileUtilities f = new FileUtilities();

		smiles = f.readSmilesSetFromFile(new File(preferences.startingMaterialsPath));
		if(smiles == null){
			smiles = new ArrayList<String>();
			smiles.add("CCCC");
			System.out.println("Materials path is wrong");
		}
		List<StructureRecord> structureRecords = new ArrayList<StructureRecord>();

			for (int i = 0; i < smiles.size(); i++) {
				StructureRecord sr = new StructureRecord();
				sr.setDataEntryID(i);
				sr.setSmiles(smiles.get(i));

				sr.setRecordProperty(new Property(moleculeClassProperty), "class1");
				structureRecords.add(sr);
			}

		moleculeTree = new MoleculeSetTree(structureRecords);

		areas.get(2).setLayout(new BorderLayout());

		bottomCenterTabbedPanel = new JTabbedPane();
	 	treesTabPane.add("molecules", moleculeTree);
		bottomCenterTabbedPanel.add("selected molecule",moleculeTree.getMoleculePanel());
		areas.get(2).add(bottomCenterTabbedPanel,BorderLayout.CENTER);

		/**
		 * END setMoleculesTree
		 */

		/**
		 * Start  setConsole
		 */
		consoleFieldPanel = new JTextArea();
		consoleFieldPanel.setLayout(new BorderLayout());
		bottomCenterTabbedPanel.add("Console",consoleFieldPanel);
				/**
				 * End setConsole
				 */

		// Setting the work cases tab pane
		workCasesTabPane = new JTabbedPane();
		workCases = new WorkCaseTabSet();
		workCases.setTabbedPane(workCasesTabPane);
		areas.get(1).setLayout(new BorderLayout());
		areas.get(1).add(workCasesTabPane, BorderLayout.CENTER);


	}



	void setPreferences() throws Exception
	{
		if (preferencesFilePath == null)
		{	
			preferences = new Preferences();
			return;
		}
		
		PreferencesJsonParser prefPar = new PreferencesJsonParser();
		preferences = prefPar.loadFromJSON(new File(preferencesFilePath));
		
		if (!prefPar.getErrors().isEmpty())
			throw new Exception("Preferences configuration errors:\n" 
					+ prefPar.getAllErrorsAsString());
		
	}
	
	void setReactionDB()
	{
		try
		{
			reactionDB = new  ReactionDataBase(new File(preferences.reactionDBPath));
		}
		catch (Exception x)
		{	
			System.out.println(x.getMessage());
		}
	}
	private void setStartingMaterialsDB() {
		try
		{
			startingMaterialsDataBase = new StartingMaterialsDataBase();
		}
		catch (Exception x)
		{
			System.out.println(x.getMessage());
		}
	}

	private void createMenus() { 
		menuBar = new JMenuBar();

		// menu File -----------------------
		menuFile = new JMenu("File");
		menuFile.setMnemonic('F');
		menuBar.add(menuFile);

		miOpenMolecule = new JMenuItem("Open Molecule");
		menuFile.add(miOpenMolecule);
		miOpenMolecule.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		miOpenMolecule.setMnemonic('O');
		miOpenMolecule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

			}
		});

		menuFile.addSeparator();

		miOpenWorkspace = new JMenuItem("Open Workspace");
		menuFile.add(miOpenWorkspace);
		miOpenWorkspace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		miOpenWorkspace.setMnemonic('W');
		miOpenWorkspace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// miOpenMolecule(evt);
			}
		});
		menuFile.addSeparator();

		miSaveWorkspace = new JMenuItem("Save Workspace");
		menuFile.add(miSaveWorkspace);
		miSaveWorkspace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		miSaveWorkspace.setMnemonic('S');
		miSaveWorkspace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// miOpenMolecule(evt);
			}
		});

		menuFile.addSeparator();
		miExit = new JMenuItem("Exit");
		menuFile.add(miExit);
		miExit.setMnemonic('X');
		miExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				miExitActionPerformed(evt);
			}
		});

		// menu React -----------------------
		menuReact = new JMenu("React");
		menuReact.setMnemonic('R');
		menuBar.add(menuReact);

		miApplyReaction = new JMenuItem("Apply Reaction");
		menuReact.add(miApplyReaction);
		miApplyReaction.setMnemonic('A');
		miApplyReaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// TODO
			}
		});


		//menu Process
		menuProcess = new JMenu("Process");
		menuBar.add(menuProcess);

		miWorkspaceProcess = new JMenuItem("New Process");
		menuProcess.add(miWorkspaceProcess);
		miWorkspaceProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

			}
		});



		menuProcess.addSeparator();

		// menu Settings 
		menuSettings = new JMenu("Settings"); 
		menuBar.add(menuSettings);  
		
		miWorkspaceSettings = new JMenuItem("Workspace settings");
		menuSettings.add(miWorkspaceSettings); 
		miWorkspaceSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				
			}
		});
		
	
		
		menuSettings.addSeparator();
		
		miProjectSettings = new JMenuItem("Project settings");
		menuSettings.add(miProjectSettings); 
		miProjectSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				
			}
		});
		
		menuSettings.addSeparator();
		
		miProcessSettings = new JMenuItem("Process settings");
		menuSettings.add(miProcessSettings); 
		miProcessSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				
			}
		});
	menuSettings.addSeparator();
		
		menuPreferences = new JMenuItem("Preferences");
		menuSettings.add(menuPreferences);
		
		menuPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				preferencesWindow.setVisible(true);
			}
		});
		
		setJMenuBar(menuBar);
	}

	private void prepareGUIAreas() {
		PredefinedArrangements.getPredifinedAreasForFrame01(this, areas, splitter);
	}

	// Event handlers ----------------------------------------

	private void eh_rootWindowClosing(WindowEvent evt) {
		System.exit(0);
	}

	// Menu handlers

	private void miExitActionPerformed(ActionEvent evt) {
		System.exit(0);
	}

	// some test utils -------------------------------------




}

