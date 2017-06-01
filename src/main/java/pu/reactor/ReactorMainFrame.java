package pu.reactor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Console;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import pu.reactor.workspace.gui.*;

import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.smarts.SmartsHelper;
import ambit2.ui.Panel2D;
import pu.gui.utils.PredefinedArrangements;
import pu.gui.utils.ReactionSetTree;
import pu.gui.utils.WorkCase;
import pu.gui.utils.WorkCaseTabSet;

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
	ArrayList<Panel2D> p2dList = new ArrayList<Panel2D>();

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

	JMenuItem menuPreferences;

	ReactorMainFrame() throws Exception {
		super();
		initGUI();
	}

	private void initGUI() throws Exception {
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

		reactionSetTree = new ReactionSetTree();
		areas.get(0).setLayout(new BorderLayout());
		areas.get(0).add(reactionSetTree, BorderLayout.CENTER);

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

		// menu Preferences
		menuPreferences = new JMenuItem("Preferences");
		menuBar.add(menuPreferences);

		menuPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				PreferencesWindow prefWindow = new PreferencesWindow();
				prefWindow.setVisible(true);
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
		p.setPreferredSize(panel.getSize());
		p.updateUI();

	}

}

