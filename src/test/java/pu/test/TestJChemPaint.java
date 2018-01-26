package pu.test;

import ambit2.jchempaint.editor.AbstractMoleculeAction;
import ambit2.jchempaint.editor.JChemPaintDialog;
import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.jchempaint.editor.MoleculeEditAction;
import ambit2.smarts.SmartsHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestJChemPaint {




	public static void main(String[] args) throws Exception
	{
		testJCP("C1CCCCC1");

	}
	
	public static void testJCP(String smiles) throws Exception
	{
		System.out.println("JCP input smiles: " + smiles);
		IAtomContainer mol = SmartsHelper.getMoleculeFromSmiles(smiles);
		JFrame frame = new JFrame();

		frame.setSize(1000,1000);
        MyMoleculeEditAction molEdAction = new MyMoleculeEditAction(mol);
		molEdAction.setMolecule(mol);
		molEdAction.editMolecule(true, frame);

		IAtomContainer resultMol = molEdAction.getMolecule();
		String resultSmiles = SmartsHelper.moleculeToSMILES(resultMol, true);
		System.out.println("JCP result smiles: " + resultSmiles);


	}

}
class MyMoleculeEditAction  extends MoleculeEditAction{
    public IAtomContainer endMolecule =  jcpDialog.okAction();

	public MyMoleculeEditAction(IAtomContainer molecule) {
		super(molecule);
	}
    public void editMolecule(boolean editable, Component frame) {

        if (molecules != null) {
            if (jcpDialog == null) {
                jcpModel.setMoleculeSet(molecules);

                jcpDialog = new JChemPaintDialog(getParent(frame), false,
                        jcpModel) {
                    private static final long serialVersionUID = -492805673357520991L;

                    @Override
                    public IAtomContainer okAction() {
                        updateMolecule(super.okAction());

                        molecules = jcpep.getChemModel().getMoleculeSet();

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
                jcpDialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent arg0) {
                        super.windowClosing(arg0);
                        // getDataContainer().setEnabled(true);
                        // getActions().allActionsEnable(true);
                        jcpDialog = null;
                    }
                });
                // TODO center it
                // TODO nonmodal
            } else
                jcpModel.setMoleculeSet(molecules);

            jcpDialog.cleanup();
            jcpDialog.toFront();
            // dataContainer.setEnabled(false);
            // getActions().allActionsEnable(false);
            jcpDialog.setVisible(true);

			/*
			 * while (jcpDialog != null) { try { wait(); } catch
			 * (InterruptedException e) { e.printStackTrace(); } }
			 */
        }
    }



	@Override
	public void actionPerformed(ActionEvent e) {

	}
}