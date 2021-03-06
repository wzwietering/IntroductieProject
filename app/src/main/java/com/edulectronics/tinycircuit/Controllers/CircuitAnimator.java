package com.edulectronics.tinycircuit.Controllers;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.widget.GridView;

import com.edulectronics.tinycircuit.Models.Components.Bell;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Graph;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.CircuitActivity;
import com.edulectronics.tinycircuit.Views.Wire;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
// This animated the circuit, so displayes how the current runs through the circuit.
public class CircuitAnimator {

    // How long to wait before animating the wire (in ms)
    public int delay = 0;

    // Keeps track of the connections that have already been animated
    // (two components share a connection)
    private List<Connection> animatedConnections = new ArrayList<Connection>();

    // The graph to animate
    private Graph graph;

    private Activity circuitActivity;

    // Some variabled for sorting wires.
    private Wire closestWire = null;
    private int currentDistance = 0;
    private int currentEndOfWireDistance = 0;
    List<Wire> sortedWires = new ArrayList<>();

    public CircuitAnimator(Graph graph, Activity circuitActivity) {
        this.graph = graph;
        this.circuitActivity = circuitActivity;
    }

    // Show how the current runs through the paths. Connections light up in yellow one by one.
    public void highlightPaths() {
        this.delay = 0;

        for (Stack path : graph.findAllPaths()) {
            this.runningHighlightPath(path);
        }
        // When we are done, reset which connections have been highlighted.
        // e.g. we want to highlight again in a different color, this needs to be reset.
        reset();
    }

    private void runningHighlightPath(Stack path) {
        this.highlightPath(path, Color.YELLOW, Wire.WireDrawingMode.runningHighlight);
    }

    // Reset which connections have been highlighted already
    public void reset() {
        animatedConnections = new ArrayList<Connection>();
    }

    // Highlight a path in a certain mode and color.
    public void highlightPath(Stack path, int color, Wire.WireDrawingMode drawingMode) {
        // start at the source
        Component currentComponent = graph.source;
        Component nextComponent;

        while (!path.empty()) {
            nextComponent = (Component) path.pop();
            // They should all have output connections!
            if(nextComponent.hasInputAndOutputConnection()
                && currentComponent.hasInputAndOutputConnection())
            this.highlightBetween(currentComponent, nextComponent, color, drawingMode);

            // move to the next component
            currentComponent = nextComponent;
        }

        // Path is empty, so we (attempt to) highlight the last connection, going back to the source.
        nextComponent = graph.source;
        this.highlightBetween(currentComponent, nextComponent, color, drawingMode);
    }

    // Highlight connection(s) between two components.
    private void highlightBetween(Component a,
                                  Component b,
                                  int color,
                                  Wire.WireDrawingMode drawingMode) {
        List<ConnectionPoint> currentConnectionPoints = a.getConnectionPoints();
        List<ConnectionPoint> nextConnectionPoints = b.getConnectionPoints();

        for (ConnectionPoint cp : currentConnectionPoints) {
            // Skip the source output; that should be the last connectionpoint we highlight.
            if (a != graph.source || (a == graph.source && cp != graph.source.getInput())) {
                for (Connection c : cp.getConnections()) {
                    if (nextConnectionPoints.contains(c.getOtherPoint(cp))) {
                        if (!animatedConnections.contains(c)) {
                            animateConnection(a, c, color, drawingMode);
                            animatedConnections.add(c);
                        }
                    }
                }
            }
        }
    }

    // Highlight all wires of this connection.
    private void animateConnection(Component origin,
                                   Connection c,
                                   int color,
                                   Wire.WireDrawingMode drawingMode) {
        for (Wire wire : sortWires(origin, new ArrayList<Wire>(c.getWires()))) {
            wire.scheduleHighLight(color, this.delay, drawingMode);

            // Increase the delay depending on the type of highlight (some take longer than others)
            if (drawingMode == drawingMode.runningHighlight)
                delay += wire.getLength() * 2;
        }
    }

    // Sort the wires so they are highlighted in correct order.
    private List<Wire> sortWires(Component origin, List<Wire> wires) {
        sortedWires = new ArrayList<>();
        // Shift the coordinates down 1 so the origin coordinates are closer to the center
        // of the grid cell than to the center of the gridcell above it.
        // otherwise a vertical line spanning 1 gridcell gets animated wrong
        Point originCoordinates = new Point(origin.coordinates.x, origin.coordinates.y + 1);

        while (wires.size() > 0) {
            for (Wire wire : wires) {
                // Get distance between wire endpoints and component
                int distanceA = getDistance(wire.a, originCoordinates);
                int distanceB = getDistance(wire.b, originCoordinates);

                // If any end point is closer to the component than the current endpoint, take
                // that as the new closest wire.
                if (closestWire == null || distanceA < currentDistance || distanceB < currentDistance) {
                    setNewClosestWire(wire, distanceA, distanceB);
                }

                // Distance is equal to current, so check if the other end of the wire is closer
                // than current wire.
                else if (distanceA == currentDistance) {
                    if (distanceB < currentEndOfWireDistance) {
                        setNewClosestWire(wire, distanceA, distanceB);
                    }
                } else if (distanceB == currentDistance) {
                    if (distanceA < currentEndOfWireDistance) {
                        setNewClosestWire(wire, distanceB, distanceA);
                    }
                }
            }
            // Add the wire that turns out to be the closest wire to the sorted list.
            sortedWires.add(closestWire);
            // ... and remove from the list that needs to be sorted.
            wires.remove(closestWire);
            // ... and repeat until all wires are sorted.
            closestWire = null;
        }
        return sortedWires;
    }

    // This wire is the new closest wire in the sorting process.
    private void setNewClosestWire(Wire wire, int distanceA, int distanceB) {
        closestWire = wire;
        currentDistance = Math.min(distanceA, distanceB);
        currentEndOfWireDistance = Math.max(distanceA, distanceB);

        if (distanceA < distanceB) {
            wire.setDrawDirection(wire.a, wire.b);
        } else {
            wire.setDrawDirection(wire.b, wire.a);
        }
    }

    // Get distance between two points (pythagoras)
    private int getDistance(Point a, Point b) {
        int distX = Math.abs(b.x - a.x);
        int distY = Math.abs(b.y - a.y);
        return distX * distX + distY * distY;
    }

    //Only handle input for the connected elements. Do this after a delay!
    public void handleHighInputs(Object[] elements ) {
        // We declare a runnable which takes our elements as input.
        class highInputRunnable implements Runnable {
            Object[] elements;

            public highInputRunnable(Object[] elements) {
                this.elements = elements;
            }

            //When this runs, all elements get a high input, and then the grid is invalidated.
            @Override
            public void run() {
                for (Object element : elements) {
                    Component component = (Component) element;
                    if(component.hasInputAndOutputConnection())
                        component.handleInputHigh();
                    if(component.getClass() == Bell.class){
                        ((CircuitActivity) circuitActivity).ringBell((Bell) component);
                    }
                }
                ((GridView) circuitActivity.findViewById(R.id.circuit)).invalidateViews();
            }
        }

        // Create a new thread to execute the runnable after a delay.
        new Handler(Looper.getMainLooper()).postDelayed(
                new highInputRunnable(elements), this.delay);
    }

    public void animateCurrentFlow(Stack path) {
        highlightPath(path, Color.YELLOW, Wire.WireDrawingMode.staticHighlight);
    }
}
