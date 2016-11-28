package com.edulectronics.tinycircuit.Models.Components.Connectors;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Powersource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maaike on 28-11-2016.
 */

public class Input {
    private Component parentComponent;
    private List<Output> connectedOutputs = new ArrayList<Output>();
    private double voltage;

    public Input(Component parent) {
        this.parentComponent = parent;
    }

    public double getInputVoltage(){
        return this.voltage;
    }

    void connect(Output output) {
        if(!connectedOutputs.contains(output)) {
            connectedOutputs.add(output);
        }
    }

    void disconnect(Output output) {
        if(connectedOutputs.contains(output)) {
            connectedOutputs.remove(output);
        }
    }

    public boolean hasOutputConnection() {
        if(parentComponent.getClass() == Powersource.class) {
            return true;
        }
        return parentComponent.hasOutputConnection();
    }

    public void handleInputVoltageChange() {
        this.voltage = 0;
        for (Output output: connectedOutputs
             ) {
            voltage += output.getOutputVoltage();
        }

        this.parentComponent.handleInputChange();
    }
}
