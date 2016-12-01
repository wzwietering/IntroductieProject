package com.edulectronics.tinycircuit.Models;

import com.edulectronics.tinycircuit.Models.Components.Component;

import java.io.Serializable;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class Circuit implements Serializable{

    public Component[][] components;
    public int width, height;

    public Circuit(int width, int height){
        components = new Component[width][height];
        this.width = width;
        this.height = height;
    }

    public boolean isCompleteCircle() {
        return false;
    }

    public void add(Component component, int x, int y) {
        components[x][y] = component;
    }

    public void remove(int x, int y) {
        components[x][y] = null;
    }

    public boolean occupied(int x, int y) {
        return components[x][y] != null;
    }
}
