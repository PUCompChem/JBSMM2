package pu.reactor.workspace.gui;

import javax.swing.*;

/**
 * Created by gogo on 27.7.2017 г..
 */
public class BasicReactorProcessPanel extends JPanel{

    private String processName;
    
    BasicReactorParametersPanel parametersPanel = new BasicReactorParametersPanel();

    public BasicReactorProcessPanel() {

    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

}
