package com.edulectronics.tinycircuit.Models;

import com.edulectronics.tinycircuit.Models.Components.Component;

/**
 * Created by Maaike on 2-1-2017.
 */

public class Edge {
    public Component a;
    public Component b;

    public Edge(Component a, Component b) {
        this.a = a;
        this.b = b;
    }
}
