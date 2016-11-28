package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Components.Component;

import java.util.List;

/**
 * Created by Maaike on 28-11-2016.
 */

public class Input {
    private Component parentComponent;
    private List<Output> connectedOutputs;

    public Input(Component parent) {
        this.parentComponent = parent;
    }

    public void Connect (Output output) {
        if(!connectedOutputs.contains(output)) {
            connectedOutputs.add(output);
        }
    }

    public void Disconnect (Output output) {
        if(connectedOutputs.contains(output)) {
            connectedOutputs.remove(output);
        }
    }

    public boolean hasOutputConnection() {
        return parentComponent.hasOutputConnection();
    }
}
