package com.edulectronics.tinycircuit.Models.Components.Connectors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maaike on 28-11-2016.
 */

public class Output {
    private double voltage;
    private List<Input> connectedInputs = new ArrayList<Input>();

    public void setOutputVoltage(double voltage) {
        this.voltage = voltage;
        for (Input input: connectedInputs) {
            input.handleInputVoltageChange();
        }
    }

    public double getOutputVoltage() {
        return voltage;
    }

    public boolean hasOutputConnection() {
        if (!connectedInputs.isEmpty()) {
            for (Input input : connectedInputs) {
                if (input.hasOutputConnection()) {
                    return true;
                }
            }
        }
        return false;
    }

    void connect(Input input) {
        if(!this.connectedInputs.contains(input)) {
            this.connectedInputs.add(input);
        }
    }

    void disconnect(Input input) {
        if(connectedInputs.contains(input)) {
            connectedInputs.remove(input);
        }
    }
}
