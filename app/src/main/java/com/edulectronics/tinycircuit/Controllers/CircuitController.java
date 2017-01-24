package com.edulectronics.tinycircuit.Controllers;

import android.app.Activity;
import android.graphics.Color;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Components.Resistor;
import com.edulectronics.tinycircuit.Models.Graph;
import com.edulectronics.tinycircuit.Views.Wire;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;
public class CircuitController implements Serializable {
    public Circuit circuit;
    public Component newComponent; // When a new component is created, we save it here. It hasn't been dragged to the circuit yet.

    public CircuitController(int width, int size) {
        this.circuit = new Circuit(width, size);
    }

    // a wire animator to animate the wires when the user runs the circuit.
    CircuitAnimator animator;

    // Set the circuit to some predefined circuit passed as arguments.
    public CircuitController(int width, int size, ArrayList<Component> components) {
        this.circuit = new Circuit(width, size);
        // TODO: Move positioning of components to the scenario. Either based on relative positions (depending on grid size) or lock the grid to a default size.
        for (Component component : components) {
            int position = component.position;
            addComponent(component, position);
        }
    }

    public void addComponent(Component component, int position) {
        if (placementAllowed(position)) {
            circuit.add(component, position);
        }
    }

    private boolean placementAllowed(int position) {
        return !circuit.occupied(position);
    }

    public void removeComponent(int position) {
        if (circuit.occupied(position)) {
            circuit.remove(position);
        }
    }

    public Component getComponent(int position) {
        return circuit.getComponent(position);
    }

    public Component[] getComponents() {
        return circuit.getAllComponents();
    }

    public boolean handleClick(int position) {
        if (position == -1) {
            // This happens when a new component is created. Component source has index -1
            return false;
        }

        Component component = getComponent(position);
        if (component != null) {
            return component.handleClick();
        }
        return false;
    }

    // Gets all the unique connections in a circuit
    public ArrayList<Connection> getAllConnections() {
        ArrayList<Connection> connections = new ArrayList<Connection>();
        for (Component component : getComponents()) {
            for (ConnectionPoint cp : component.getConnectionPoints()) {
                for (Connection c : cp.getConnections()) {
                    if (!connections.contains(c)) {
                        connections.add(c);
                    }
                }
            }
        }
        return connections;
    }

    public int getCircuitSize() {
        return circuit.getSize();
    }

    // Reset all the components to their standard values (eg. lightbulbs turned off and not broken)
    public void reset() {
        for (Component component : circuit.getAllComponents())
            component.reset();
    }

    // Run the circuit!
    public int run(Activity circuitActivity) {
        reset();

        // Check if there are outgoing connections.
        for (Component component : this.getComponents()) {
            if (component != null && component.getClass() == Powersource.class) {
                if (((Powersource) component).hasOutputConnection()) {
                    // If yes, create graph.
                    Graph graph = new Graph((Powersource) component, this.getAllConnections());

                    // Animate the current through the wires.
                    animator = new CircuitAnimator(graph, circuitActivity);
                    animator.highlightPaths();

                    // Now check all paths on the graph to see if there is resistance.
                    checkPaths(graph);
                }
            }
        }
        return animator == null ? 0 : animator.delay;
    }

    public Graph getGraph() {
        for (Component component : this.getComponents()) {
            if (component != null && component.getClass() == Powersource.class) {
                if (((Powersource) component).hasOutputConnection()) {
                    return new Graph(((Powersource) component), this.getAllConnections());
                }
            }
        }
        return null;
    }
    // Check all paths on the graph to see if there is resistance
    private void checkPaths(Graph graph) {
        // First check if there are paths that don't have a resistor.
        for (Stack path : graph.findAllPaths()) {
            boolean pathHasResistor = false;

            Object[] elements = path.toArray();
            for (Object element : elements) {
                if (element.getClass() == Resistor.class) {
                    pathHasResistor = true;
                    break;
                }
            }

            // Highlight them in red.
            if (!pathHasResistor) {
                animator.highlightPath(path, Color.RED, Wire.WireDrawingMode.flashingHighlight);
                for (Object element : elements) {
                    ((Component) element).setResistance(false);
                }
            }
        }

        // Now check if there are any paths that DO have a resistor.
        for (Stack path : graph.findAllPaths()) {
                boolean pathHasResistor = false;

                Object[] elements = path.toArray();
                for (Object element : elements) {
                    if (element.getClass() == Resistor.class) {
                        pathHasResistor = true;
                        break;
                    }
                }

                if (pathHasResistor) {
                    animator.animateCurrentFlow(path);
                }

                animator.handleHighInputs(elements);
        }
    }

    public int getComponentCount(Component component) {
        return circuit.getComponentCount(component);
    }
}
