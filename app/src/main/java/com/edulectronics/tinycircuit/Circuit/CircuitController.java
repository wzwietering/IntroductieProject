package com.edulectronics.tinycircuit.Circuit;

import com.edulectronics.tinycircuit.Components.Component;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class CircuitController {

    CircuitModel circuitModel;

    public CircuitController(){
        circuitModel = new CircuitModel();
    }

    public void addComponent(Component c){
        circuitModel.components.add(c);
    }

    public void removeComponent(Component c){
        circuitModel.components.remove(c);
    }
}
