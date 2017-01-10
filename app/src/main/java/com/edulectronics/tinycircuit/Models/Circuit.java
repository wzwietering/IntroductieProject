package com.edulectronics.tinycircuit.Models;

import com.edulectronics.tinycircuit.Models.Components.Component;

import java.io.Serializable;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class Circuit implements Serializable{

    Component[] components;
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
        component.setPosition(i);
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

    public Component[] getAllComponents() {
        return this.components;
    }

    public int getPosition(Component component) {
        for  (int i = 0; i < components.length; i++) {
            if(components[i] == component) {
                return i;
            }
        }
        return -1;
    }

    public int getComponentCount(Component component) {
        int componentCount = 0;
        for (Component comp : components){
            if(comp != null) {
                if (comp.getClass() == component.getClass()) {
                    componentCount++;
                }
            }
        }
        return componentCount;
    }
}
