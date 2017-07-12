package pu.gui.utils.gui.utils.trees;

import pu.gui.utils.InfoPanel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by gogo on 12.7.2017 г..
 */
abstract public class SetTree extends JPanel {
    protected JTree tree;
    protected InfoPanel infoPanel;
    protected JTextField treeSearchBox;
    protected JButton treeSearchButton;

     void searchBoxSet() {
        treeSearchBox = new JTextField(13);
        treeSearchButton = new JButton("Search");
        JPanel searchPanel = new JPanel();
        searchPanel.add(treeSearchBox, BorderLayout.WEST);
        searchPanel.add(treeSearchButton,BorderLayout.EAST);
        this.add( searchPanel , BorderLayout.NORTH);

        treeSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = treeSearchBox.getText();
                if(search.trim().length() > 0 ) {

                    DefaultMutableTreeNode node = findNode(search);
                    if( node != null ) {
                        TreePath path = new TreePath(node.getPath());
                        tree.setSelectionPath(path);
                        tree.scrollPathToVisible(path);

                    }
                }
            }
        });


    }
    protected DefaultMutableTreeNode searchChildrenNode(String nodeObj, DefaultMutableTreeNode node)
    {
        for (int i = 0; i < node.getChildCount(); i++)
        {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
            if (nodeObj.equals(child.getUserObject()))
                return child;
        }
        return null;
    }
    protected DefaultMutableTreeNode findNode(String searchString) {

        java.util.List<DefaultMutableTreeNode> searchNodes = getSearchNodes((DefaultMutableTreeNode)tree.getModel().getRoot());
        DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

        DefaultMutableTreeNode foundNode = null;
        int bookmark = -1;

        if( currentNode != null ) {
            for(int index = 0; index < searchNodes.size(); index++) {
                if( searchNodes.get(index) == currentNode ) {
                    bookmark = index;
                    break;
                }
            }
        }

        for(int index = bookmark + 1; index < searchNodes.size(); index++) {
            if(searchNodes.get(index).toString().toLowerCase().contains(searchString.toLowerCase())) {
                foundNode = searchNodes.get(index);
                break;
            }
        }

        if( foundNode == null ) {
            for(int index = 0; index <= bookmark; index++) {
                if(searchNodes.get(index).toString().toLowerCase().contains(searchString.toLowerCase())) {
                    foundNode = searchNodes.get(index);
                    break;
                }
            }
        }
        return foundNode;
    }

    protected final java.util.List<DefaultMutableTreeNode> getSearchNodes(DefaultMutableTreeNode root) {
        java.util.List<DefaultMutableTreeNode> searchNodes = new ArrayList<DefaultMutableTreeNode>();

        Enumeration<?> e = root.preorderEnumeration();
        while(e.hasMoreElements()) {
            searchNodes.add((DefaultMutableTreeNode)e.nextElement());
        }
        return searchNodes;
    }

    public JTree getTree() {
        return tree;
    }
}
