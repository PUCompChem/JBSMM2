package pu.gui.utils.reactionUtils;

import javax.swing.JPanel;
import java.awt.*;

public class ReactionPanel extends JPanel 
{	
	
	private static final long serialVersionUID = -5450610246649612250L;
	StructurePanel structurePanel = new StructurePanel();
	public ReactionPanel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		structurePanel.setPreferredSize(new Dimension(500,300));
		add(structurePanel,BorderLayout.NORTH);

	}
}
