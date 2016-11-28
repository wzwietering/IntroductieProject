package com.edulectronics.tinycircuit;

import java.util.List;

/**
 * Created by Maaike on 28-11-2016.
 */

public class Output {
    private List<Input> connectedInputs;

    public boolean hasOutputConnection() {
        return !connectedInputs.isEmpty();
    }

    protected void Connect(Input input) {
        if(!this.connectedInputs.contains(input)) {
            this.connectedInputs.add(input);
        }
    }

    protected void Disconnect(Input input) {
        if(connectedInputs.contains(input)) {
            connectedInputs.remove(input);
        }
    }
}
