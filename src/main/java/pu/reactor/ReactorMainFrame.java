package pu.reactor;

import ambit2.reactions.ReactionDataBase;
import ambit2.smarts.SmartsHelper;
import ambit2.ui.Panel2D;
import org.openscience.cdk.interfaces.IAtomContainer;
import pu.gui.utils.gui.utils.trees.MoleculeSetTree;
import pu.gui.utils.PredefinedArrangements;
import pu.gui.utils.gui.utils.trees.ReactionSetTree;
import pu.gui.utils.WorkCaseTabSet;
import pu.reactor.json.PreferencesJsonParser;
import pu.reactor.workspace.Preferences;
import pu.reactor.workspace.gui.PreferencesWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

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
	
	JMenu menuSettings; 
	JMenuItem menuPreferences;
	
	private JMenuItem miWorkspaceSettings;
	private JMenu menuProjectSettings;
	private JMenuItem miProjectSettings;
	private JMenuItem miProcessSettings;
	
	//Data, containers
	String preferencesFilePath = null;
	Preferences preferences = null;
	ReactionDataBase reactionDB = null;
	JTabbedPane treesTabPane;
	
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
	//	setReactionTree
		if (reactionDB != null)
		{
			reactionSetTree = new ReactionSetTree(reactionDB.reactions);
			  treesTabPane = new JTabbedPane();
			treesTabPane.add("reactions",reactionSetTree);



			areas.get(0).setLayout(new BorderLayout());
			areas.get(0).add( treesTabPane, BorderLayout.CENTER);
		}

		//set Molecules Tree

			   moleculeTree = new MoleculeSetTree();

			treesTabPane.add("molecules", moleculeTree);







		// Setting the work cases tab pane
		workCasesTabPane = new JTabbedPane();
		workCases = new WorkCaseTabSet();
		workCases.setTabbedPane(workCasesTabPane);
		areas.get(1).setLayout(new BorderLayout());
		areas.get(1).add(workCasesTabPane, BorderLayout.CENTER);

		// some test code (to be removed) -------------------------
		workCases.addMoleculeCase(null, "Mol 1", null);
		workCases.addMoleculeCase(null, "Mol 2", null);
		workCases.addMoleculeCase(null, "Mol 3", "Info for Mol 3");
		workCases.addMoleculeCase(null, "Mol 4", "Tip for Mol 4");
		workCases.removeWorkCase(workCases.getWorkCases().get(1));
		workCases.addMoleculeCase(null, "Mol 5", null);
		workCases.addMoleculeCase(null, "Mol 6", null);
		workCases.removeWorkCase(3);

		add2DMolecule(areas.get(2), "CCCCC");
		add2DMolecule(areas.get(2), "CCCCNNC");
		// End of testCode
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

	private void add2DMolecule(JPanel panel, String smiles) {
		try {
			IAtomContainer mol = SmartsHelper.getMoleculeFromSmiles(smiles);
			add2DMolecule(panel, mol);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void add2DMolecule(JPanel panel, IAtomContainer struct) {
		Panel2D p = new Panel2D();
		p.setAtomContainer(struct);
		p.setBorder(BorderFactory.createLineBorder(Color.green));

		p2dList.add(p);

		//

		panel.addComponentListener(new ComponentListener() {

			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void componentResized(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("componentResized events");
				Panel2D p = p2dList.get(p2dList.size() - 1);
				p.setPreferredSize(arg0.getComponent().getSize());
			}

			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		panel.add(p);
		//p.setPreferredSize(panel.getSize());
		p.updateUI();

	}

}

