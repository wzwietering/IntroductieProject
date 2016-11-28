package com.edulectronics.tinycircuit.Circuit;

import com.edulectronics.tinycircuit.Models.Components.Component;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class CircuitController {

    public CircuitModel circuit;
    private Set<Component> availableComponents;

    public CircuitController(Set<Component> s, int width, int height){
        this.circuit = new CircuitModel(width, height);
        this.availableComponents = s;
    }

    public void addComponent(Component c, int x, int y){
        /*Only add if tile is available and allowed*/
        if(!circuit.occupied(x, y) && availableComponents.contains(c)){
            circuit.components.add(c);
        }
    }

    public void removeComponent(Component c, int x, int y){
        if(occupation[x][y]) {
            occupation[x][y] = false;
            circuit.components.remove(c);
        }
    }

    public ArrayList<Component> getComponents(){
        return circuit.components;
    }
}
