package com.edulectronics.tinycircuit.Controllers;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Components.Resistor;
import com.edulectronics.tinycircuit.Models.Factories.ComponentFactory;
import com.edulectronics.tinycircuit.Models.Graph;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.CircuitActivity;
import com.edulectronics.tinycircuit.Views.Draggables.GridCell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class CircuitController implements Serializable {
    public Circuit circuit;
    public Component newComponent; // When a new component is created, we save it here. It hasn't been dragged to the circuit yet.

    // Set the circuit to some predefined circuit passed as arguments.
    public CircuitController(int width, int size, ArrayList<Component> components) {
        this.circuit = new Circuit(width, size);
        int position = width / 2;
        // TODO: Move positioning of components to the scenario. Either based on relative positions (depending on grid size) or lock the grid to a default size.
        for (Component component : components) {
            addComponent(component, position);
            position+= 10;
        }
    }

    public void addNewComponent(String componentName, CircuitActivity activity) {
        Component component = ComponentFactory.CreateComponent(componentName, 5.0);

        FrameLayout componentHolder = (FrameLayout) activity.findViewById(R.id.component_source_frame);
        componentHolder.setVisibility(View.VISIBLE);

        if (componentHolder != null) {
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams (LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT,
                    Gravity.CENTER);
            GridCell newView = new GridCell(activity);
            newView.setComponent(component);
            componentHolder.addView(newView, lp);
            newView.mCellNumber = -1;

            // Have this activity listen to touch and click events for the view.
            newView.setOnClickListener(activity);
            newView.setOnLongClickListener(activity);
            newView.setOnTouchListener(activity);
        }
    }

    public void addComponent(Component component, int position) {
        if(placementAllowed(position)) {
            circuit.add(component, position);
        }
    }

    private boolean placementAllowed(int position) {
        return !circuit.occupied(position);
    }

    public void removeComponent(int position) {
        if(circuit.occupied(position)) {
            circuit.remove(position);
        }
    }

    public Component getComponent(int position){
        return circuit.getComponent(position);
    }

    public Component[] getComponents(){
        return circuit.getAllComponents();
    }

    public boolean handleClick(int position) {
        if (position == -1) {
            // This happens when a new component is created. Component source has index -1
            return false;
        }

        Component component = getComponent(position);
        if(component != null) {
            return component.handleClick();
        }
        return false;
    }

    // Gets all the unique connections in a circuit
    public ArrayList<Connection> getAllConnections() {
        ArrayList<Connection> connections = new ArrayList<Connection>();
        for (Component component: getComponents()) {
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
        for (Component component: circuit.getAllComponents()) {
            component.reset();
        }
    }

    // Run the circuit!
    public void run() {
        reset();

        // Check if there are outgoing connections.
        for (Component component: this.getComponents()) {
            if(component != null && component.getClass() == Powersource.class) {
                if(((Powersource) component).hasOutputConnection()) {
                    Graph graph = new Graph((Powersource) component, this.getAllConnections());

                    // Now check all paths on the graph.
                    checkPaths(graph);
                }
            }
        }
    }

    // Check all paths on the graph to see if there is resistance
    private void checkPaths(Graph graph) {
        for (Stack path: graph.findAllPaths()) {
            boolean pathHasResistor =  false;

            Object[] elements = path.toArray();
            for (Object element : elements) {
                if (element.getClass() == Resistor.class) {
                    pathHasResistor = true;
                    break;
                }
            }

            if (!pathHasResistor) {
                for (Object element : elements) {
                    ((Component) element).setResistance(false);
                }
            }
        }
        for (Component node: graph.nodes) {
            node.handleInputHigh();
        }
    }
}
