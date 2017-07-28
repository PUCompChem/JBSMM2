package pu.gui.utils.trees;

import pu.gui.utils.BasicReactorProcessPanel;

import javax.swing.*;

/**
 * Created by gogo on 28.7.2017 г..
 */
public class ReactorProcessTabsSet extends JTabbedPane {

    public void add(BasicReactorProcessPanel processPanel){
        this.add(processPanel.getProcessName(),processPanel);
    }
}
