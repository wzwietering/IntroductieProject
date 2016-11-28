package com.edulectronics.tinycircuit.Circuit;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.IComponent;
import com.edulectronics.tinycircuit.Models.Components.Powersource;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class CircuitModel {

    public Component[][] components;
    private int width, height;

    public CircuitModel(int width, int height){
        components = new Component[width][height];
        this.width = width;
        this.height = height;
    }

    /*Checks if the Circuit has a connection from the first powersource*/
    public boolean noComponentsWithoutOutput(){
        for (Object object: components) {
            if (object != null) {
                Component c = (Component) object;
                if (!c.hasOutputConnection()) { return false; }
            }
        }
        return true;
    }

    public boolean isCompleteCircle() {
        return false;
    }

    public void add(Component component, int x, int y) {
        components[x][y] = component;
    }

    public void remove(Component component) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (components[x][y] == component) {
                    components[x][y] = null;
                }
            }
        }
    }

    public boolean occupied(int x, int y) {
        return components[x][y] != null;
    }
}
