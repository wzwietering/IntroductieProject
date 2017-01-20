package com.edulectronics.tinycircuit.Models;

import com.edulectronics.tinycircuit.Models.Components.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Circuit implements Serializable {

    private Component[] components;
    public int size;
    public int width;

    public Circuit(int width, int height) {
        components = new Component[width * height];
        this.size = width * height;
        this.width = width;
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
        ArrayList<Component> componentList = new ArrayList<>(Arrays.asList(components));
        componentList.removeAll(Collections.singleton(null));
        return componentList.toArray(new Component[componentList.size()]);
    }

    public int getSize() {
        return size;
    }

    public int getComponentCount(Component component) {
        int componentCount = 0;
        for (Component comp : getAllComponents()) {
            if (comp.getClass() == component.getClass()) {
                componentCount++;
            }
        }
        return componentCount;
    }
}
