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
    public int width, height;

    public CircuitController(Set<Component> s, int width, int height){
        this.circuit = new CircuitModel(width, height);
        this.width = width;
        this.height = height;
        this.availableComponents = s;
    }

    public void addComponent(Component component, int x, int y){
        /*Only add if tile is available and allowed*/
        if(!circuit.occupied(x, y) && availableComponents.contains(component)){
            circuit.add(component, x, y);
        }
    }

    public void removeComponent(Component c, int x, int y){
        if(circuit.occupied(x, y)) {
            circuit.remove(c);
        }
    }

    public Component[][] getComponents(){
        return circuit.components;
    }
}
