package com.edulectronics.tinycircuit.Models.Scenarios;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Powersource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Maaike on 12-12-2016.
 * This is the abstract class for design scenario's. The design scenario is a scenario in which
 * the user can modify a circuit in order to complete a task.
 */

public abstract class DesignScenario implements IScenario {

    protected Component[] initialComponents;

    public DesignScenario() {}

    public DesignScenario(Circuit circuit) {
        this.initialComponents = circuit.getAllComponents();
    }

    // Determines whether the user completed the task. Different implementation for each scenario.
    @Override
    public boolean isCompleted(Circuit circuit) {
        for (Component component : circuit.getAllComponents()) {
            if (component.getClass() == Powersource.class) {
                return component.hasOutputConnection(((Powersource) component).getInput()) && circuit.getComponentCount(component) == 1;
            }
        }
        return false;
    }

    // The component types that the user is allowed to use. Can differ for each scenario.
    public Set<Component> getAvailableComponents() {
        return new HashSet<>();
    }

    // The preset components that are already part of the scenario.
    @Override
    public ArrayList<Component> loadComponents() {
        if(this.initialComponents != null) {
            return new ArrayList(Arrays.asList(initialComponents));
        }
        return null;
    }

    // Get the Id of a message explaining to the user what they need to do
    @Override
    public abstract int getPrompt();

    // Get the scenario id
    @Override
    public int getID(){
        return 0;
    }

    public abstract int getHint();
}
