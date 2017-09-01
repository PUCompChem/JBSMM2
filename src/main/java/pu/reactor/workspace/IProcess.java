package pu.reactor.workspace;

import javax.swing.*;

import java.io.File;

/**
 * Created by gogo on 22.8.2017 г..
 */
public interface IProcess {
    public String toJsonString();
    public void loadFromJson(File file);
    public JPanel getPanel();
    public JPanel getParametersPanel();
    public void setPanel(JPanel panel);
    public void initProcess() throws Exception;
    public void runProcess() throws Exception;
    public void runProcessSteps(int nSteps);
}
