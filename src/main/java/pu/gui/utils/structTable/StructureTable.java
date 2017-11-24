package pu.gui.utils.structTable;

import ambit2.base.data.StructureRecord;
import org.openscience.cdk.AtomContainer;
import pu.gui.utils.MoleculeDrawer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StructureTable extends JPanel {

   private int numberOfColumns;
   private int currentColumn = 0;
   private int currentRow = 0;
   private ArrayList<JPanel> panels = new ArrayList<JPanel>();
   //private GridBagConstraints gc = new GridBagConstraints();
   GridLayout layout;
   private MoleculeDrawer drawer = new MoleculeDrawer();



    public StructureTable(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
        layout = new GridLayout(0,numberOfColumns);
        setLayout(layout);

        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
//        gc.fill = GridBagConstraints.HORIZONTAL;
//        gc.ipady = 40;
//
//        gc.weightx = 1.0;
//
//        gc.gridwidth = 1;

     
        this.addMoleculeAsString("CCCC=O");
        this.addMoleculeAsString("CCCC=C");
     
        Draw();
    }

    public void addMoleculeAsString(String molString){
        StructureRecord structureRecord = new StructureRecord();
        structureRecord.setSmiles(molString);
        Add(structureRecord);
    }
    
    public void Add(StructureRecord structureRecord){
            JPanel currentPanel = new JPanel(new BorderLayout());

            drawer.add2DMolecule(currentPanel,structureRecord.getSmiles());
        currentPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            panels.add(currentPanel);
    }
    private void Draw(){
        for (JPanel panel : panels){
            if(currentColumn>=numberOfColumns){
                currentRow++;
                currentColumn = 0;
            }

//            gc.gridx=currentColumn;
//            gc.gridy=currentRow;


            this.add(panel,BorderLayout.CENTER);
            currentColumn++;

        }
    }


}
