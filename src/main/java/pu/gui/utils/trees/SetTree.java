package pu.gui.utils.trees;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by gogo on 12.7.2017 г..
 */
abstract public class SetTree extends JPanel {
    protected JTree tree;
    protected InfoPanel infoPanel;
    protected JTextField treeSearchBox;
    protected JButton treeSearchButton;

    public SetTree() {


    }

    void searchBoxSet() {
        treeSearchBox = new JTextField(13);
        treeSearchButton = new JButton("Search");
        JPanel searchPanel = new JPanel();
        searchPanel.add(treeSearchBox, BorderLayout.WEST);
        searchPanel.add(treeSearchButton, BorderLayout.EAST);
        this.add(searchPanel, BorderLayout.NORTH);

        treeSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = treeSearchBox.getText();
                if (search.trim().length() > 0) {

                    DefaultMutableTreeNode node = findNode(search);
                    if (node != null) {
                        TreePath path = new TreePath(node.getPath());
                        tree.setSelectionPath(path);
                        tree.scrollPathToVisible(path);

                    }
                }
            }
        });


    }

    protected DefaultMutableTreeNode searchChildrenNode(String nodeObj, DefaultMutableTreeNode node) {
        for (int i = 0; i < node.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
            if (nodeObj.equals(child.getUserObject()))
                return child;
        }
        return null;
    }

    protected DefaultMutableTreeNode findNode(String searchString) {

        java.util.List<DefaultMutableTreeNode> searchNodes = getSearchNodes((DefaultMutableTreeNode) tree.getModel().getRoot());
        DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        DefaultMutableTreeNode foundNode = null;
        int bookmark = -1;

        if (currentNode != null) {
            for (int index = 0; index < searchNodes.size(); index++) {
                if (searchNodes.get(index) == currentNode) {
                    bookmark = index;
                    break;
                }
            }
        }

        for (int index = bookmark + 1; index < searchNodes.size(); index++) {
            if (searchNodes.get(index).toString().toLowerCase().contains(searchString.toLowerCase())) {
                foundNode = searchNodes.get(index);
                break;
            }
        }

        if (foundNode == null) {
            for (int index = 0; index <= bookmark; index++) {
                if (searchNodes.get(index).toString().toLowerCase().contains(searchString.toLowerCase())) {
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
        while (e.hasMoreElements()) {
            searchNodes.add((DefaultMutableTreeNode) e.nextElement());
        }
        return searchNodes;
    }

    protected void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
        for (int i = startingIndex; i < rowCount; ++i) {
            tree.expandRow(i);
        }

        if (tree.getRowCount() != rowCount) {
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }

    public JTree getTree() {
        return tree;
    }

    public static BufferedImage getScaledImage(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }

    public void setIcons(String leafImagePath, String closeNodeImagePath, String openNodeImagePath) {
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();

        renderer.setLeafIcon(fromImageToIcon(leafImagePath));
        renderer.setClosedIcon(fromImageToIcon(closeNodeImagePath));
        renderer.setOpenIcon(fromImageToIcon(openNodeImagePath));
    }
    private ImageIcon fromImageToIcon(String path){
        BufferedImage image = null;
        try {
            image = ImageIO.read(ClassLoader.getSystemResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        image = getScaledImage(image,20,20);
        ImageIcon icon = new ImageIcon(image);
        return icon;
    }
}
