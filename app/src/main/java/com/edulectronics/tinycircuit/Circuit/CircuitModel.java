package com.edulectronics.tinycircuit.Circuit;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.IComponent;
import com.edulectronics.tinycircuit.Models.Components.Powersource;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class CircuitModel implements Serializable{

    public Component[] components;
    public int size;
    public int width;

    public CircuitModel(int width, int height){
        components = new Component[width * height];
        this.size = width * height;
        this.width = width;
    }

    public boolean isCompleteCircle() {
        return false;
    }

    public void add(Component component, int i) {
        components[i] = component;
    }

    public void remove(int i) {
        components[i] = null;
    }

    public boolean occupied(int i) {
        return components[i] != null;
    }

    public Component getComponent(int i) {
        return components[i];
    }
}
