package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;

import java.util.ArrayList;

public class CircuitBuilder {
    public static CircuitController getCircuitController() {
        CircuitController controller = new CircuitController(10, 10);
        return controller;
    }

    public static CircuitController getCircuitController(ArrayList<Component> components) {
        CircuitController controller = new CircuitController(10, 10, components);
        return controller;
    }
}
