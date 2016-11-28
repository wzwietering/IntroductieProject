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
}
