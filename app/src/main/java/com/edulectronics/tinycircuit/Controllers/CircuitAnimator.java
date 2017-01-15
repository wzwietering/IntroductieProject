package com.edulectronics.tinycircuit.Controllers;

import android.graphics.Color;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Graph;
import com.edulectronics.tinycircuit.Views.Wire;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Maaike on 15-1-2017.
 */
// This animated the circuit, so displayes how the current runs through the circuit.
public class CircuitAnimator {

    // How long to wait before animating the wire (in ms)
    public int delay;
    // Components that have already been animated
    // (paths can contain the same components multiple times)
    private List<Component> animatedComponents = new ArrayList<Component>();
    // Connections that have already been animated (two components share a connection)
    private List<Connection> animatedConnections = new ArrayList<Connection>();
    // The graph to animate
    private Graph graph;

    public CircuitAnimator(Graph graph) {
        this.graph = graph;
    }

    // Show how the current runs through the paths. Connections light up in yellow one by one.
    public void highlightPaths() {
        this.delay = 0;

        for (Stack path: graph.findAllPaths() ) {
            this.highlightPath(path, Color.YELLOW, 1000);
        }
        // When we are done, reset which components and connections have been highlighted.
        // e.g. we want to highlight again in a different color, this needs to be reset.
        reset();
    }

    // Reset which components and connections have been highlighted already
    public void reset() {
        animatedComponents = new ArrayList<Component>();
        animatedConnections = new ArrayList<Connection>();
    }

    // There is no resistance on this path, so make it flash red
    public void highlightPath(Stack path, int color, int interval) {
        // start at the source
        Component currentComponent = graph.source;
        Component nextComponent;

        while(!path.empty()) {
            nextComponent = (Component) path.pop();
            if (!animatedComponents.contains(currentComponent)) {
                this.highlightBetween(currentComponent, nextComponent, color, interval);
                animatedComponents.add(currentComponent);
            }

            // move to the next component
            currentComponent = nextComponent;
        }

        // Path is empty, so we highlight the last connection, going to the base.
        nextComponent = graph.base;
        this.highlightBetween(currentComponent, nextComponent, Color.RED, interval);
    }

    // Highlight connection(s) between two components.
    private void highlightBetween(Component a, Component b, int color, int interval) {
        List<ConnectionPoint> currentConnectionPoints = a.getConnectionPoints();
        List<ConnectionPoint> nextConnectionPoints = b.getConnectionPoints();

        for (ConnectionPoint cp : currentConnectionPoints) {
            for (Connection c: cp.getConnections()) {
                if(nextConnectionPoints.contains(c.getOtherPoint(cp))) {
                    if(!animatedConnections.contains(c)) {
                        animateConnection(c, color);
                        animatedConnections.add(c);

                        delay += interval;
                    }
                }
            }
        }
    }

    // Highlight all wires of this connection.
    private void animateConnection(Connection c, int color) {
        for (Wire wire: c.getWires()) {
            wire.highLight(color, this.delay);
        }
    }
}
