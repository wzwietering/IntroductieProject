package com.edulectronics.tinycircuit.Controllers;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Factories.ComponentFactory;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.CircuitActivity;
import com.edulectronics.tinycircuit.Views.Draggables.GridCell;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class CircuitController implements Serializable {

    private static CircuitController ourInstance = new CircuitController();

    public static CircuitController getInstance() {
        return ourInstance;
    }

    private CircuitController() {
    }

    public Circuit circuit;
    private Set<Component> availableComponents;

    // When a new component is created, we save it here. It hasn't been dragged to the circuit yet.
    public Component newComponent;

    public void setProperties(Set<Component> s, int width, int size){
        this.circuit = new Circuit(width, size);
        this.availableComponents = s;
    }

    public void addNewComponent(String componentName, CircuitActivity activity)
    {
        Component component = ComponentFactory.CreateComponent(componentName, 5.0);

        FrameLayout componentHolder = (FrameLayout) activity.findViewById
                (R.id.component_source_frame);
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

    public void addComponent(Component component, int position){
        // Only add if tile is available and allowed
        if(!circuit.occupied(position)) {
            circuit.add(component, position);
        }
    }

    public void removeComponent(int position){
        if(circuit.occupied(position)) {
            circuit.remove(position);
        }
    }

    public Component getComponent(int position){
        return circuit.components[position];
    }

    public Component[] getComponents(){
        return circuit.components;
    }

    public int getCircuitWidth() {
        return circuit.getWidth();
    }
}
