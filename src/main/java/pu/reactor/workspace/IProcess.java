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
    public void setPanel(JPanel panel);
    public void initProcess();
    public void runProcess();
    public void runProcessSteps(int nSteps);
}
