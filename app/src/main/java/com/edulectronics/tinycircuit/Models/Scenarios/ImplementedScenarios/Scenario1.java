package com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Scenarios.DesignScenario;
import com.edulectronics.tinycircuit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * First scenario. Easy-peasy.
 * Circuit:     A power source and a lightbulb. Powersource and lightbulb are connected only
 * on one side.
 * Complete:    if user connects the non-connected side of powersource to none-connected side
 * of lightbulb.
 */

public class Scenario1 extends DesignScenario {
    private boolean hasLightbulb, isFullCircle;

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

        hasLightbulb = false;

        for (Component component : circuit.getAllComponents()) {
            if (component != null) {
                if (component.getClass() == Lightbulb.class) {
                    hasLightbulb = true;
                    continue;
                }
            }
        }

        return (hasLightbulb);
    }

    public Set<Component> getAvailableComponents() {
        Component[] components = {new Lightbulb(), new Powersource()};
        return new HashSet<>(Arrays.asList(components));
    }

    public ArrayList<Component> loadComponents() {
        ArrayList<Component> components = new ArrayList<>();
        Powersource powersource = new Powersource();
        Lightbulb bulb = new Lightbulb();

        Connector.connect(powersource.getOutput(), bulb.getConnectionPointByIndex(1));

        components.add(powersource);
        components.add(bulb);
        return components;
    }

    public int getID() {
        return 1;
    }

    public int getHint() {
        if (!hasLightbulb) {
            return R.string.lamp_required;
        }
        if (!isFullCircle) {
            return R.string.no_full_circle;
        }
        return 0;
    }
}
