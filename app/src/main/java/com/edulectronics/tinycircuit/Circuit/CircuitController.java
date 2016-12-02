package com.edulectronics.tinycircuit.Circuit;

import com.edulectronics.tinycircuit.Models.Components.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class CircuitController implements Serializable {

    public CircuitModel circuit;
    private Set<Component> availableComponents;

    // When a new component is created, we save it here. It hasn't been dragged to the circuit yet.
    public Component newComponent;

    public CircuitController(Set<Component> s, int width, int size){
        this.circuit = new CircuitModel(width, size);
        this.availableComponents = s;
    }

    public void addComponent(Component component, int position){
        /*Only add if tile is available and allowed*/
        if(!circuit.occupied(position)){ // && availableComponents.contains(component)
            circuit.add(component, position);
        }
    }

    public void removeComponent(int position){
        if(circuit.occupied(position)) {
            circuit.remove(position);
        }
    }

    public Component getComponent(int position){
        return circuit.components[position];
    }

    public Component[]getComponents(){
        return circuit.components;
    }
}
