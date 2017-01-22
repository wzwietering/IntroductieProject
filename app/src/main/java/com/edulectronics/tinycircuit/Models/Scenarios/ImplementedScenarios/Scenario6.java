package com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Components.Resistor;
import com.edulectronics.tinycircuit.Models.Components.Switch;
import com.edulectronics.tinycircuit.Models.Scenarios.DesignScenario;
import com.edulectronics.tinycircuit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jeper on 1/22/2017.
 */

public class Scenario6 extends DesignScenario {
    private boolean isFullCircle;

    @Override
    public int getPrompt() {
        return R.string.scenario1_explanation;
    }

    @Override
    public int getCompletePrompt() {
        return R.string.scenario1_complete;
    }

    @Override
    public boolean isCompleted(Circuit circuit) {
        isFullCircle = super.isCompleted(circuit);
        if (!isFullCircle) return false;

        for (Component component : circuit.getAllComponents()) {
            if (component != null) {

                }
            }
        return isFullCircle;
    }


    public Set<Component> getAvailableComponents() {
        Component[] components = {new Lightbulb(), new Switch()};
        return new HashSet<>(Arrays.asList(components));
    }

    public ArrayList<Component> loadComponents() {
        ArrayList<Component> components = new ArrayList<>();
        Powersource powersource = new Powersource();
        Resistor resistor = new Resistor();

        Connector.connect(powersource.getOutput(), resistor.getConnectionPointByIndex(1));

        components.add(powersource);
        components.add(resistor);
        return components;
    }

    public int getID() {
        return 1;
    }

    public int getHint() {
        if (!isFullCircle) {
            return R.string.no_full_circle;
        }
        return 0;
    }
}
