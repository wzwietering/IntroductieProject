package com.edulectronics.tinycircuit.Models;

import com.edulectronics.tinycircuit.Models.Components.Component;

import java.lang.reflect.Array;

/**
 * Created by Maaike on 2-1-2017.
 */

public class Edge {
    public Component a;
    public Component b;

    public Edge(Component a, Component b) {
        a = a;
        b = b;
    }
}
