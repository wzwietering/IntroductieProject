package com.edulectronics.tinycircuit.Models;

import com.edulectronics.tinycircuit.Models.Components.Component;

/**
 * Created by Wilmer on 11-12-2016.
 */

public class Node {
    public int x, y;
    public Component component;
    public boolean occupied;

    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void occupy(Component component){
        occupied = true;
        this.component = component;
    }

    public void release(){
        occupied = false;
    }

    public boolean isOccupied(){
        return occupied;
    }
}
