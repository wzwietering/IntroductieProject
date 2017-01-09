package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;

import java.util.ArrayList;

/**
 * Created by Maaike on 6-1-2017.
 */

public class CircuitBuilder {
    public static CircuitController getCircuitController() {
        Circuit circuit = new Circuit(10, 10);
        CircuitController controller = CircuitController.getInstance();
        controller.circuit = circuit;
        return controller;
    }

    public static CircuitController getCircuitController(ArrayList<Component> components) {
        CircuitController controller = CircuitController.getInstance();
        controller.setProperties(10, 10, components);
        return controller;
    }
}
