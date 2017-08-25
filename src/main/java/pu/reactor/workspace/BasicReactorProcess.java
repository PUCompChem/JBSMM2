package pu.reactor.workspace;

import ambit2.reactions.reactor.Reactor;
import ambit2.reactions.reactor.ReactorStrategy;
import pu.reactor.workspace.gui.BasicReactorProcessPanel;

import javax.swing.*;
import java.io.File;

/**
 * Created by gogo on 22.8.2017 г..
 */
public class BasicReactorProcess implements IProcess {
    BasicReactorProcessPanel panel = new BasicReactorProcessPanel(this);

    Reactor reactor = new Reactor();
    ReactorStrategy strategy =  new ReactorStrategy();
    public ReactorStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(ReactorStrategy strategy) {
        this.strategy = strategy;
    }


    public BasicReactorProcess() throws Exception {



    }


    public void startReactorReactor(){

    }

    @Override
    public String toJsonString() {
        return null;
    }

    @Override
    public void loadFromJson(File file) {

    }

    @Override
    public JPanel getPanel() {
        return this.panel;
    }

    @Override
    public void setPanel(JPanel panel) {
        if(panel instanceof BasicReactorProcessPanel){
            this.panel = (BasicReactorProcessPanel)panel;
        }

    }

    @Override
    public void initProcess() {

    }

    @Override
    public void runProcess() {

    }

    @Override
    public void runProcessSteps(int nSteps) {

    }
}
