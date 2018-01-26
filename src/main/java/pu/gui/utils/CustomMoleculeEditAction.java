package pu.gui.utils;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.jchempaint.editor.JChemPaintDialog;
import ambit2.jchempaint.editor.MoleculeEditAction;

public class CustomMoleculeEditAction extends MoleculeEditAction
{
	ICustomActionHandler handler = null;
	Rectangle rect = null;
	
	public CustomMoleculeEditAction(IAtomContainer molecule, ICustomActionHandler handler) 
	{
		super(molecule);
		this.handler = handler;
	}
	
	public CustomMoleculeEditAction(IAtomContainer molecule, ICustomActionHandler handler, Rectangle rect) 
	{
		super(molecule);
		this.handler = handler;
		this.rect = rect;
	}

	public void editMolecule(boolean editable, Component frame) 
	{

		if (jcpDialog == null) 
		{
			jcpModel.setMoleculeSet(molecules);
			jcpDialog = new JChemPaintDialog(getParent(frame), false,
					jcpModel) {
				private static final long serialVersionUID = -492805673357520991L;

				@Override
				public IAtomContainer okAction() 
				{	
					updateMolecule(super.okAction());
					try
					{
						handler.handleAction(new Object[] {molecule});
					}
					catch (Exception e){};

					/*
					 * updatedMolecule.setProperties(dataContainer.getMolecule
					 * ().getProperties());
					 * getDataContainer().setEnabled(true);
					 * getDataContainer().setMolecule(updatedMolecule);
					 * getActions().allActionsEnable(true);
					 */
					dispose();
					jcpDialog = null;
					return (IAtomContainer) molecule;
				};

				@Override
				public void cancelAction() {
					super.cancelAction();

					// data.getDataContainer().setEnabled(true);
					// data.getActions().allActionsEnable(true);
					dispose();
					jcpDialog = null;

				}

			};
			jcpDialog.setTitle("JChemPaint structure diagram editor");
			if (rect == null)
				jcpDialog.setBounds(40, 40, 500, 500);
			else
				jcpDialog.setBounds(rect);
			
			jcpDialog.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent arg0) {
					super.windowClosing(arg0);
					// getDataContainer().setEnabled(true);
					// getActions().allActionsEnable(true);
					jcpDialog = null;
				}
			});
			
		} else
			jcpModel.setMoleculeSet(molecules);

		jcpDialog.cleanup();
		jcpDialog.toFront();
		// dataContainer.setEnabled(false);
		// getActions().allActionsEnable(false);
		jcpDialog.setVisible(true);
	}


}
