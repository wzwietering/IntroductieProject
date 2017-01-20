package com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
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
 * Created by Wilmer on 18-1-2017.
 */

public class Scenario5 extends DesignScenario {

    @Override
    public int getPrompt() {
        return R.string.scenario5_explanation;
    }

    boolean lampRequirementsMet;
    boolean isFullCircle;
    boolean hasSwitch;
    boolean hasResistor;

    @Override
    public boolean isCompleted(Circuit circuit) {
        isFullCircle = super.isCompleted(circuit);
        if (!isFullCircle) return false;

        lampRequirementsMet = false;
        hasSwitch = false;
        hasResistor = false;

        for (Component component : circuit.getAllComponents()) {
            if (component != null && circuit.getComponentCount(component) == 1) {
                if (component.getClass() == Lightbulb.class) {
                    lampRequirementsMet = (((Lightbulb) component).isOn && !((Lightbulb) component).isBroken());
                } else if (component.getClass() == Switch.class) {
                    hasSwitch = component.hasOutputConnection(component.getConnectionPointByIndex(1)) && component.isConductive();
                } else if (component.getClass() == Resistor.class) {
                    hasResistor = component.hasOutputConnection(component.getConnectionPointByIndex(1));
                }
            }
        }

        return (lampRequirementsMet && hasSwitch && hasResistor);
    }

    public Set<Component> getAvailableComponents() {
        Component[] components = {new Lightbulb(), new Powersource(), new Resistor(), new Switch()};
        return new HashSet<>(Arrays.asList(components));
    }

    public ArrayList<Component> loadComponents() {
        ArrayList<Component> components = new ArrayList<>();
        return components;
    }

    @Override
    public int getID() {
        return 5;
    }

    public int getHint() {
        if (!lampRequirementsMet) {
            return R.string.lamp_off;
        }
        if (!isFullCircle) {
            return R.string.no_full_circle;
        }
        if (!hasSwitch) {
            return R.string.switch_required;
        }
        if (!hasResistor) {
            return R.string.resistance_required;
        }
        return 0;
    }
}
