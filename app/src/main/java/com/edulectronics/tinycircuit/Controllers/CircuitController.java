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

    private Circuit circuit;
    private LevelController levelController;

    public CircuitController(LevelController levelController, int width, int height) {
        this.levelController = levelController;
        circuit = new Circuit(width, height);
    }

    public CircuitController(int width, int height) {
        circuit = new Circuit(width, height);
    }

    public void addComponent(Component component, int position) {
        if (position < circuit.getSize() && position >= 0)
            if (!circuit.occupied(position) && levelController.available(component))
                circuit.add(component, position);
    }

    public void removeComponent(int position) {
        if (position < circuit.getSize() && position >= 0)
            if (levelController != null && levelController.ownedByLevel(position))
                circuit.remove(position);
    }

    public Component getComponent(int position) {
        return circuit.getComponent(position);
    }

    public boolean isComplete() {
        return circuit.isCompleteCircle();
    }

    public int getCircuitWidth() {
        return circuit.getWidth();
    }

    public int getCircuitSize() {
        return circuit.getSize();
    }
}
