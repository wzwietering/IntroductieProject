package com.edulectronics.tinycircuit.Models;

import com.edulectronics.tinycircuit.Models.Components.Component;

public class Edge {
    public Component a;
    public Component b;

    public Edge(Component a, Component b) {
        this.a = a;
        this.b = b;
    }
}
