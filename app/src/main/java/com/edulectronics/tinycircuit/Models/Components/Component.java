package com.edulectronics.tinycircuit.Models.Components;

import com.edulectronics.tinycircuit.Models.Input;
import com.edulectronics.tinycircuit.Models.Output;

import java.util.List;

/**
 * Created by Maaike on 28-11-2016.
 */

/**
 * This is the component base class. All components have to be derived from this base.
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
