package com.edulectronics.tinycircuit.Models;

import com.edulectronics.tinycircuit.Models.Components.Component;

import java.util.List;

/**
 * Created by Maaike on 28-11-2016.
 */

public class Input {
    private Component parentComponent;
    private List<Output> connectedOutputs;
    private double voltage;

    public Input(Component parent) {
        this.parentComponent = parent;
    }

    public void connect(Output output) {
        if(!connectedOutputs.contains(output)) {
            connectedOutputs.add(output);
        }
    }

    public void disconnect(Output output) {
        if(connectedOutputs.contains(output)) {
            connectedOutputs.remove(output);
        }
    }

    public boolean hasOutputConnection() {
        return parentComponent.hasOutputConnection();
    }

    public void handleInputVoltageChange() {
        /* Do something to calculate voltage from multiple output voltages */
        this.parentComponent.handleInputChange();
    }
}
