package com.edulectronics.tinycircuit.Models.Components;

import android.support.annotation.NonNull;

import com.edulectronics.tinycircuit.Models.Components.Connectors.Input;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Output;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Maaike on 28-11-2016.
 */

/**
 * This is the component base class. All components have to be derived from this base.
 */
public abstract class Component implements IComponent {
    protected List<Input> inputs = new ArrayList<Input>();
    protected List<Output> outputs = new ArrayList<Output>();

       protected double outputVoltage;

    public Component() {
        this.inputs.add(new Input(this));
        this.outputs.add(new Output());
    }

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

    public Input getInputByIndex(int i) {
        if(i < inputs.size()) {
            return inputs.get(i);
        }
        return null;
    }

    public Output getOutputByIndex(int i) {
        if(i < outputs.size()) {
            return outputs.get(i);
        }
        return null;
    }
}
