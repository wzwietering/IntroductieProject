package com.edulectronics.tinycircuit;

import java.util.List;

/**
 * Created by Maaike on 28-11-2016.
 */

public abstract class Component implements IComponent {
    public List<Input> inputs;
    public List<Output> outputs;
}
