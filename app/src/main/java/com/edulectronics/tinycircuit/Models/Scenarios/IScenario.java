package com.edulectronics.tinycircuit.Models.Scenarios;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

/**
 * The scenario interface.
 */

public interface IScenario extends Serializable {

    // Determines whether to reset circuit  to scenario default when user starts this scenario.
    public boolean resetCircuitOnStart();

    // Get the components that the user is allowed to use in this scenario
    public Set<Component> getAvailableComponents();

    // Returns true if the user has completed the scenario.
    public boolean isCompleted(Circuit circuit);

    // Show a message to the user (explaining what to do, or a quiz question)
    public int getPrompt();

    // Show a message to the user when the scenario is completed
    public int getCompletePrompt();

    // Load the preset components for displaying the circuit that is part of this scenario.
    ArrayList<Component> loadComponents();

    // Returns the id of the scenario, which is its number.
    public int getID();

}
