package pu.reactor;

import ambit2.base.data.Property;
import ambit2.base.data.StructureRecord;
import ambit2.reactions.ReactionDataBase;
import ambit2.reactions.retrosynth.StartingMaterialsDataBase;
import ambit2.smarts.SMIRKSManager;
import ambit2.ui.Panel2D;
import pu.gui.utils.chemtable.SmartChemTable;
import pu.gui.utils.trees.MoleculeSetTree;
import pu.gui.utils.trees.ReactionSetTree;
import pu.gui.utils.PredefinedArrangements;
import pu.io.FileUtilities;
import pu.reactor.helpinfo.ReactorAboutWindow;
import pu.reactor.json.PreferencesJsonParser;
import pu.reactor.workspace.Preferences;
import pu.reactor.workspace.ProcessCommonChemData;
import pu.reactor.workspace.gui.PreferencesWindow;
import pu.reactor.workspace.gui.ProcessPanel;
import pu.reactor.workspace.gui.ReactionToolBar;
import pu.reactor.workspace.gui.wizards.BasicReactorWizard;
import pu.reactor.workspace.gui.wizards.ReactionSequenceWizard;
import pu.reactor.workspace.gui.wizards.SingleReactionWizard;
import pu.reactor.workspace.gui.ReactorProcessTabsSet;

import javax.swing.*;

import org.openscience.cdk.silent.SilentChemObjectBuilder;

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
	ReactionSetTree reactionSetTree;
	MoleculeSetTree moleculeTree;
	ArrayList<Panel2D> p2dList = new ArrayList<Panel2D>();

	//PreferencesWindow preferencesWindow = null;
    ActionListener actionListener;
	
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
	JMenuItem miRun;
	JMenuItem miStop;
	JMenuItem miNextStep;
	JMenuItem miReset;
	private ActionListener nextButtonActionListener;
	
	JMenu menuProcess;
	JMenuItem miSingleReaction;
	JMenuItem miBasicReactor;
	JMenuItem miReactionSequence;
	
	JMenuItem miWorkspaceProcess;

	JMenu menuSettings;
	JMenuItem menuPreferences;


	private JMenu menuProjectSettings;
	private JMenuItem miWorkspaceSettings;
	private JMenuItem miProjectSettings;
	private JMenuItem miProcessSettings;

	private JMenu menuHelp;
	private JMenuItem miAbout;

	private ReactionToolBar reactionToolBar;


	//private BasicReactorWizard newProcessWizard;

	//Data containers
	String preferencesFilePath = null;
	Preferences preferences = null;
	
	ProcessCommonChemData processChemData = new ProcessCommonChemData();
	//ReactionDataBase reactionDB = null;
	//StartingMaterialsDataBase startingMaterialsDataBase = null;
	
	JTabbedPane treesTabPane;
	private JTabbedPane bottomCenterTabbedPanel;
	private JTextArea consoleFieldPanel;

	private ReactorProcessTabsSet processTabs = new ReactorProcessTabsSet();


	public ReactorMainFrame(Preferences preferences,
			ProcessCommonChemData processChemData) throws Exception 
	{
		super();
		this.preferences = preferences;
		this.processChemData = processChemData;
		initGUI();
	}
	
	public ReactorMainFrame(String preferencesFilePath) throws Exception {
		super();
		this.preferencesFilePath = preferencesFilePath;
		preferences = getPreferences(preferencesFilePath);
		setReactionDB();
		setStartingMaterialsDB(preferences, processChemData);
		initGUI();
	}

	private void initGUI() throws Exception
	{
		//preferencesWindow = new PreferencesWindow(preferences, preferencesFilePath);
		//preferencesWindow.setVisible(false);

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
		areas.get(1).setLayout(new BorderLayout());
		processTabs.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		areas.get(1).add(processTabs);
		
		treesTabPane = new JTabbedPane();
		
		//setReactionTree
		if (processChemData.getReactionDB() != null)
		{
			reactionSetTree = new ReactionSetTree(processChemData.getReactionDB().genericReactions);
			treesTabPane.add("reactions",reactionSetTree);
			areas.get(0).setLayout(new BorderLayout());
			areas.get(0).add( treesTabPane, BorderLayout.CENTER);
		}

		reactionToolBar = new ReactionToolBar();
		reactionToolBar.getNextButton().addActionListener(nextButtonActionListener);
		miNextStep.addActionListener(nextButtonActionListener);
		this.add(reactionToolBar, BorderLayout.NORTH);
		
		//set Molecules Tree
		if (processChemData.getStructureRecords() != null)
			moleculeTree = new MoleculeSetTree(processChemData.getStructureRecords());
		else
			moleculeTree = new MoleculeSetTree(processChemData.getStartingMaterialsDataBase());
			
		//TODO handle starting materials
		
		areas.get(2).setLayout(new BorderLayout());
		bottomCenterTabbedPanel = new JTabbedPane();
		if (moleculeTree != null)
		{	
			treesTabPane.add("molecules", moleculeTree);
			bottomCenterTabbedPanel.add("selected molecule",moleculeTree.getMoleculePanel());
		}	

		areas.get(2).add(bottomCenterTabbedPanel,BorderLayout.CENTER);
		consoleFieldPanel = new JTextArea();
		consoleFieldPanel.setLayout(new BorderLayout());
		bottomCenterTabbedPanel.add("Console",consoleFieldPanel);
	}

	public static Preferences getPreferences(String prefFileName) throws Exception
	{
		if (prefFileName == null)
		{
			return new Preferences();
		}

		PreferencesJsonParser prefPar = new PreferencesJsonParser();
		Preferences pref = null;
		try
		{
			pref = prefPar.loadFromJSON(new File(prefFileName));
		}
		catch (Exception x) {
			System.out.println("Preferences loading error: " + x.getMessage());
		}

		if (!prefPar.getErrors().isEmpty())
			throw new Exception("Preferences configuration errors:\n"
					+ prefPar.getAllErrorsAsString());
		return pref;
	}

	void setReactionDB()
	{
		try
		{
			 ReactionDataBase reactionDB = new  ReactionDataBase(preferences.reactionDBPath);
			 SMIRKSManager smrkMan0 = new SMIRKSManager(SilentChemObjectBuilder.getInstance()); 
			 reactionDB.configureGenericReactions(smrkMan0);
			 processChemData.setReactionDB(reactionDB);
		}
		catch (Exception x)
		{
			System.out.println("ReactionDB path is not valid");
			System.out.println(x.getMessage());

		}
	}
	
	public static void setStartingMaterialsDB(Preferences pref, ProcessCommonChemData pccd) 
	{
		try
		{
			//TODO set starting materials
			if (pref.startingMaterialsPath.endsWith(".smi"))
			{
				//Setting structure records
				List<String> smiles = null;
				FileUtilities f = new FileUtilities();
				if (pref != null)
				{	
					System.out.println("Loading structures from smiles file...");
					smiles = f.readSmilesSetFromFile(new File(pref.startingMaterialsPath));
				}	

				if(smiles == null){
					smiles = new ArrayList<String>();
					System.out.println("Materials path " + pref.startingMaterialsPath +  " is wrong");
					System.out.println("Loaded demo molecules!");
					smiles.add("CCC");
					smiles.add("CCCC");
					smiles.add("CCCCC");
				}
				else
					System.out.println("Loaded " + smiles.size() + " structures");
				List<StructureRecord> structureRecords = new ArrayList<StructureRecord>();

				for (int i = 0; i < smiles.size(); i++) {
					StructureRecord sr = new StructureRecord();
					sr.setDataEntryID(i);
					sr.setSmiles(smiles.get(i));

					sr.setRecordProperty(new Property(moleculeClassProperty), "class1");
					structureRecords.add(sr);
				}
				pccd.setStructureRecords(structureRecords);
			}
			else
			{
				System.out.println("Loading starting material data base...");
				StartingMaterialsDataBase startingMaterialsDataBase = 
						new StartingMaterialsDataBase(new File(pref.startingMaterialsPath));
				pccd.setStartingMaterialsDataBase(startingMaterialsDataBase);	
				System.out.println("starting material data base loaded!");
			}
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

		miRun = new JMenuItem("Run");
		menuReact.add(miRun);
		miRun.setMnemonic('R');
		miRun.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.CTRL_MASK));
		miRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				miRunActionPerformed(evt);
			}
		});

		miStop = new JMenuItem("Stop");
		menuReact.add(miStop);
		miStop.setMnemonic('S');
		miStop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, ActionEvent.CTRL_MASK));
		miStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// TODO
			}
		});

		miNextStep = new JMenuItem("Next Step");
		menuReact.add(miNextStep);
		miNextStep.setMnemonic('N');
		miNextStep.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, ActionEvent.CTRL_MASK));
		nextButtonActionListener = (new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				miNextStepActionPerformed(evt);
			}
		});

		miNextStep.addActionListener(nextButtonActionListener);

		miReset = new JMenuItem("Reset");
		miReset.setMnemonic('T');
		menuReact.add(miReset);
		miReset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, ActionEvent.CTRL_MASK));
		miReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				miResetActionPerformed(evt);
			}
		});
		
		//menu Process
		menuProcess = new JMenu("Process");
		menuBar.add(menuProcess);

		//miWorkspaceProcess = new JMenu("New Process");
		//menuProcess.add(miWorkspaceProcess);

		miSingleReaction = new JMenuItem("New Single Reaction");
		miSingleReaction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SingleReactionWizard wizard = new SingleReactionWizard();
			}
		});


		miBasicReactor = new JMenuItem("New Basic Reactor");
		miBasicReactor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BasicReactorWizard wizard 
					= new BasicReactorWizard(processTabs, processChemData);
			}
		});
		
		
		miReactionSequence = new JMenuItem("New Reaction Sequence");
		miReactionSequence.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ReactionSequenceWizard wizard 
					= new ReactionSequenceWizard(processTabs, processChemData);
			}
		});
		
		menuProcess.add(miSingleReaction);
		menuProcess.add(miBasicReactor);
		menuProcess.add(miReactionSequence);
		//menuProcess.addSeparator();

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
				PreferencesWindow prefWin = new PreferencesWindow(preferences, preferencesFilePath);
				prefWin.setVisible(true);
			}
		});


		menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);
		miAbout = new JMenuItem("About");
		menuHelp.add(miAbout);

		miAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ReactorAboutWindow aboutWindow = new ReactorAboutWindow();
				aboutWindow.setVisible(true);
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
	
	private void miRunActionPerformed(ActionEvent evt){
		System.out.println("Run");
		if (processTabs.getComponentCount() == 0)
			return;
		int processIndex = processTabs.getSelectedIndex();
		ProcessPanel pp = (ProcessPanel)processTabs.getComponentAt(processIndex);
		try
		{
			System.out.println("Run process: " + pp.getProcess().getName());
			pp.getProcess().runProcess();
			pp.updatePanel();
		}
		catch (Exception x)
		{
			System.out.println("Process error: " + x.getMessage());
		}
	}
	
	private void miStopActionPerformed(ActionEvent evt){
		System.out.println("Stop");
	}
	
	private void miNextStepActionPerformed(ActionEvent evt){
		System.out.println("Next Step");
		if (processTabs.getComponentCount() == 0)
			return;
		int processIndex = processTabs.getSelectedIndex();
		ProcessPanel pp = (ProcessPanel)processTabs.getComponentAt(processIndex);
		try
		{
			System.out.println("Next Steps for process: " + pp.getProcess().getName());
			pp.getProcess().runProcessNextSteps();
			pp.updatePanel();
		}
		catch (Exception x)
		{
			System.out.println("Process error: " + x.getMessage());
		}
	}
	
	
	private void miResetActionPerformed(ActionEvent evt){
		System.out.println("Reset");
		if (processTabs.getComponentCount() == 0)
			return;
		int processIndex = processTabs.getSelectedIndex();
		ProcessPanel pp = (ProcessPanel)processTabs.getComponentAt(processIndex);
		try
		{
			System.out.println("Reset process: " + pp.getProcess().getName());
			pp.getProcess().resetProcess();
			pp.updatePanel();
		}
		catch (Exception x)
		{
			System.out.println("Process error: " + x.getMessage());
		}
	}
	// some test utils -------------------------------------

}