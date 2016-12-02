package com.edulectronics.tinycircuit.Models;

import com.edulectronics.tinycircuit.Models.Components.Component;

import java.io.Serializable;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class Circuit implements Serializable{

    public Component[] components;
    public int size;
    public int width;

    public Circuit(int width, int height){
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
