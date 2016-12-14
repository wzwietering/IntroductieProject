package com.edulectronics.tinycircuit.Models;

import android.graphics.Point;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;

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

    public int getWidth() {
        return width;
    }

    public Point getNodeLocation(int position, ConnectionPointOrientation orientation) {
        return new Point(getXLocation(position, orientation), getYLocation(position, orientation));
    }

    private int getXLocation(int position, ConnectionPointOrientation orientation) {
        switch (orientation) {
            case Top:
            case Bottom:
                return (getColumn(position) - 1) * cellSize + (int) (0.5 * cellSize);
            case Left:
                return (getColumn(position) - 1) * cellSize;
            default:
                return getColumn(position) * cellSize;
        }
    }

    private int getYLocation(int position, ConnectionPointOrientation orientation) {
        switch (orientation) {
            case Left:
            case Right:
                return (getRow(position) - 1) * cellSize + (int) (0.5 * cellSize);
            case Top:
                return (getRow(position) - 1) * cellSize;
            default:
                return getColumn(position) * cellSize;
        }
    }

    private int getRow(int position) {
        return (position / width) + 1;
    }

    private int getColumn(int position) {
        return (position % width) + 1;
    }
}
