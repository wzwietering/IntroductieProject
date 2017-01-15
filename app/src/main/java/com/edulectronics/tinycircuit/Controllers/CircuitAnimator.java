package com.edulectronics.tinycircuit.Controllers;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.widget.GridView;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Graph;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.Wire;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Maaike on 15-1-2017.
 */
// This animated the circuit, so displayes how the current runs through the circuit.
public class CircuitAnimator {

    // How long to wait before animating the wire (in ms)
    public int delay;

    // Keeps track of the connections that have already been animated
    // (two components share a connection)
    private List<Connection> animatedConnections = new ArrayList<Connection>();

    // The graph to animate
    private Graph graph;

    private Activity circuitActivity;

    public CircuitAnimator(Graph graph, Activity circuitActivity) {
        this.graph = graph;
        this.circuitActivity = circuitActivity;
    }

    // Show how the current runs through the paths. Connections light up in yellow one by one.
    public void highlightPaths() {
        this.delay = 0;

        for (Stack path: graph.findAllPaths() ) {
            this.highlightPath(path, Color.YELLOW, 1500);
        }
        // When we are done, reset which connections have been highlighted.
        // e.g. we want to highlight again in a different color, this needs to be reset.
        reset();
    }

    // Reset which connections have been highlighted already
    public void reset() {
        animatedConnections = new ArrayList<Connection>();
    }

    // There is no resistance on this path, so make it flash red
    public void highlightPath(Stack path, int color, int interval) {
        // start at the source
        Component currentComponent = graph.source;
        Component nextComponent;

        while(!path.empty()) {
            nextComponent = (Component) path.pop();
            this.highlightBetween(currentComponent, nextComponent, color, interval);

            // move to the next component
            currentComponent = nextComponent;
        }

        // Path is empty, so we (attempt to) highlight the last connection, going back to the source.
        nextComponent = graph.source;
        this.highlightBetween(currentComponent, nextComponent, color, interval);
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

    //Only handle input for the connected elements. Do this after a delay!
    public void handleHighInputs(Object[] elements) {
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
                    ((Component) element).handleInputHigh();
                }
                ((GridView)circuitActivity.findViewById(R.id.circuit)).invalidateViews();
            }
        }

        // Create a new thread to execute the runnable after a delay.
        new Handler(Looper.getMainLooper()).postDelayed(
                new highInputRunnable(elements), this.delay);
    }

    public void animateCurrentFlow(Stack path) {
    }
}
