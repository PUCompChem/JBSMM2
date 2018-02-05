package pu.reactor.workspace.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ambit2.reactions.retrosynth.SyntheticStrategy;
import pu.gui.utils.ButtonTabComponent;
import pu.reactor.workspace.ReactionSequenceProcess;


public class ReactionBrowser extends JFrame 
{
	private static final long serialVersionUID = 3734241163743648132L;
	
	ReactionBrowserPanel rbPanel = null;
	ReactionSequenceProcessPanel rspPanel = null;
	JButton applyButton;
	JButton cancelButton;
	JPanel buttonsPanel;	

	public ReactionBrowser(ReactionSequenceProcessPanel rspPanel) 
	{	
		this.rspPanel = rspPanel;
		initGUI();
	}
	
	private void initGUI() 
	{
		setLayout(new BorderLayout());
		setBounds(50,50,800,600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		rbPanel = new ReactionBrowserPanel();
		//rbPanel.setPreferredSize(new Dimension(1000,800));
		add(rbPanel, BorderLayout.CENTER);
		
		buttonsPanel = new JPanel(new FlowLayout());
		this.add(buttonsPanel,BorderLayout.SOUTH);
		
		setCancelButton();
		setApplyButton();
	}
	
	private void setCancelButton(){
		cancelButton = new JButton("Cancel");
		buttonsPanel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();			
			}
		});
	}
	
	public void setApplyButton(){
		applyButton = new JButton("Apply reaction");
		buttonsPanel.add(applyButton);
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyButtonEvent();
			}
		} );
	}
	
	void cancelButtonEvent()
	{
		//TODO
		dispose();
	}
	
	void applyButtonEvent()
	{
		//TODO
		dispose();
	}
	
	

}
