package com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Bell;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Components.Resistor;
import com.edulectronics.tinycircuit.Models.Components.Switch;
import com.edulectronics.tinycircuit.Models.Graph;
import com.edulectronics.tinycircuit.Models.Scenarios.DesignScenario;
import com.edulectronics.tinycircuit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Scenario5 extends DesignScenario {

    @Override
    public int getPrompt() {
        return R.string.scenario5_explanation;
    }

    boolean lampRequirementsMet;
    boolean isFullCircle;
    boolean hasSwitch;
    boolean hasResistor;
    boolean componentCount;
    boolean hasBell;

    @Override
    public boolean isCompleted(Circuit circuit, Graph graph) {
        isFullCircle = super.isCompleted(circuit, graph);
        if (!isFullCircle) return false;

        lampRequirementsMet = false;
        hasSwitch = false;
        hasResistor = false;
        componentCount = false;
        hasBell = false;

        for (Component component : circuit.getAllComponents()) {
            if (component.getClass() == Lightbulb.class) {
                lampRequirementsMet = (((Lightbulb) component).isOn && !((Lightbulb) component).isBroken());
                componentCount = super.componentCount(circuit, component);
                if(!componentCount){
                    return false;
                }
            } else if (component.getClass() == Switch.class) {
                hasSwitch = component.hasOutputConnection(component.getConnectionPointByIndex(1)) && component.isConductive();
                componentCount = super.componentCount(circuit, component);
                if(!componentCount){
                    return false;
                }
            } else if (component.getClass() == Resistor.class) {
                hasResistor = component.hasOutputConnection(component.getConnectionPointByIndex(1));
                componentCount = super.componentCount(circuit, component);
                if(!componentCount){
                    return false;
                }
            } else if (component.getClass() == Bell.class) {
                hasBell = component.hasOutputConnection(component.getConnectionPointByIndex(1));
                componentCount = super.componentCount(circuit, component);
                if(!componentCount){
                    return false;
                }
            }
        }

        return (lampRequirementsMet && hasSwitch && hasResistor && componentCount && isFullCircle && hasBell);
    }

    public Set<Component> getAvailableComponents() {
        Component[] components = {new Lightbulb(), new Powersource(), new Resistor(), new Switch(), new Bell()};
        return new HashSet<>(Arrays.asList(components));
    }

    public ArrayList<Component> loadComponents() {
        if (super.loadComponents() != null) {
            return super.loadComponents();
        }
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
        if (!hasSwitch) {
            return R.string.switch_required;
        }
        if (!hasResistor) {
            return R.string.resistance_required;
        }
        if (!isFullCircle) {
            return R.string.no_full_circle;
        }
        if(!componentCount){
            return R.string.component_count;
        }
        if (!hasBell) {
            return R.string.no_bell;
        }
        return 0;
    }
}
