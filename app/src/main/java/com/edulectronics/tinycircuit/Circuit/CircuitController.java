package com.edulectronics.tinycircuit.Circuit;

import com.edulectronics.tinycircuit.Models.Components.Component;

import java.util.ArrayList;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class CircuitController {

    public CircuitModel circuitModel;
    public final boolean[][] occupation = new boolean[5][5];

    public CircuitController(){
        circuitModel = new CircuitModel();
    }

    public void addComponent(Component c, int x, int y){
        if(!occupation[x][y]){
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

    public ArrayList<Component> components(){
        return circuitModel.components;
    }
}
