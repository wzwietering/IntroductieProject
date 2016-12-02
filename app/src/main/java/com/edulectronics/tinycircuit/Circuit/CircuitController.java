package com.edulectronics.tinycircuit.Circuit;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class CircuitController implements Serializable {

    public Circuit circuit;
    private Set<Component> availableComponents;

    public CircuitController(Set<Component> s, int width, int size){
        this.circuit = new Circuit(width, size);
        this.availableComponents = s;
    }

    public void addComponent(Component component, int position){
        /*Only add if tile is available and allowed*/
        if(!circuit.occupied(position)){ // && availableComponents.contains(component)
            circuit.add(component, position);
        }
    }

    public void removeComponent(int x, int y){
        if(circuit.occupied(x)) {
            circuit.remove(x);
        }
    }

    public Component[]getComponents(){
        return circuit.components;
    }
}
