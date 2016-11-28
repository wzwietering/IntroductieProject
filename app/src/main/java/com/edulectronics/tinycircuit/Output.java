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

    public void Connect(Input input) {
        if(!this.connectedInputs.contains(input)) {
            this.connectedInputs.add(input);
        }
    }

    public void Disconnect(Input input) {
        if(connectedInputs.contains(input)) {
            connectedInputs.remove(input);
        }
    }
}
