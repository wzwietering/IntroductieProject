package com.edulectronics.tinycircuit;

import java.util.List;

/**
 * Created by Maaike on 28-11-2016.
 */

public class Input {
    private List<Output> connectedOutputs;

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
}
