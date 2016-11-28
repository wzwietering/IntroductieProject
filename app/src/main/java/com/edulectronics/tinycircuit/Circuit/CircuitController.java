package com.edulectronics.tinycircuit.Circuit;

import com.edulectronics.tinycircuit.Models.Components.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class CircuitController {

    public CircuitModel circuitModel;
    public final boolean[][] occupation = new boolean[5][5]; //Grid dimensions
    private Set<Component> availableComponents;

    public CircuitController(Set<Component> s){
        this.circuitModel = new CircuitModel();
        this.availableComponents = s;
    }

    public void addComponent(Component c, int x, int y){
        /*Only add if tile is available and allowed*/
        if(!occupation[x][y] && availableComponents.contains(c)){
            occupation[x][y] = true;
            circuitModel.components.add(c);
        }
    }

    public void removeComponent(Component c, int x, int y){
        if(occupation[x][y]) {
            occupation[x][y] = false;
            circuitModel.components.remove(c);
        }
    }

    public ArrayList<Component> getComponents(){
        return circuitModel.components;
    }
}
