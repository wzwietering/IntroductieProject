package com.edulectronics.tinycircuit;

import java.util.List;

/**
 * Created by Maaike on 28-11-2016.
 */

public abstract class Component implements IComponent {
    private List<Input> inputs;
    private List<Output> outputs;

    public boolean hasOutputConnection() {
        for (Output o : outputs
             ) {
            if (o.hasOutputConnection() )
            {
                return true;
            }
        }
        return false;
    }
}
