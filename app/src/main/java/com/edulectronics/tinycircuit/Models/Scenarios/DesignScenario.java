package com.edulectronics.tinycircuit.Models.Scenarios;

import android.graphics.Point;
import android.util.ArraySet;
import android.view.Display;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by Maaike on 12-12-2016.
 * This is the abstract class for design scenario's. The design scenario is a scenario in which
 * the user can modify a circuit in order to complete a task.
 */

public abstract class DesignScenario implements IScenario {

    // Determines whether the user completed the task. Different implementation for each scenario.
    @Override
    public boolean isCompleted(Circuit circuit) {
        return false;
    }

    // The component types that the user is allowed to use. Can differ for each scenario.
    public Set<Component> getAvailableComponents() {
        return new HashSet<Component>();
    }

    // The preset components that are already part of the scenario.
    // TODO: change implementation to a GET from database
    @Override
    public ArrayList<Component> loadComponents() {

        return null;
    }

    // Get the Id of a message explaining to the user what they need to do
    // TODO: get message from database
    @Override
    public abstract int getPrompt();
}
