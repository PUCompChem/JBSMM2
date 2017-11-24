package pu.gui.utils.contextmenus;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.MouseEvent;

/**
 * Created by gogo on 21.11.2017 г..
 */
public class ReactionContextMenu extends GenericContextMenu {
JTree tree;
    public ReactionContextMenu(JTree tree) {
        popMenu = new JPopupMenu();
        JMenuItem item = new JMenuItem("Test");
        popMenu.add(item);
        this.tree = tree;

    }
@Override
    public void mousePressed(MouseEvent ev) {
        if(SwingUtilities.isRightMouseButton(ev)){
            int selRow = tree.getRowForLocation(ev.getX(), ev.getY());
            TreePath selPath = tree.getPathForLocation(ev.getX(), ev.getY());

                 System.out.println(selPath.getLastPathComponent());

            tree.setSelectionPath(selPath);
            if (selRow>-1){
                tree.setSelectionRow(selRow);
                popMenu.show(ev.getComponent(), ev.getX(), ev.getY());
            }

        }



    }



}
