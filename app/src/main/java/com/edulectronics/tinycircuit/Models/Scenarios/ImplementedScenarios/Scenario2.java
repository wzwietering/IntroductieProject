package com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Components.Resistor;
import com.edulectronics.tinycircuit.Models.Graph;
import com.edulectronics.tinycircuit.Models.Scenarios.DesignScenario;
import com.edulectronics.tinycircuit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Scenario2 extends DesignScenario {
    public Scenario2() {
    }

    public Scenario2(Circuit circuit) {
        super(circuit);
    }

    @Override
    public int getPrompt() {
        return R.string.scenario2_explanation;
    }

    boolean hasResistor, lampIsOn, isFullCircle, componentCount;

    @Override
    public boolean isCompleted(Circuit circuit, Graph graph) {
        isFullCircle = super.isCompleted(circuit, graph);
        if (!isFullCircle) return false;

        hasResistor = false;
        lampIsOn = false;
        componentCount = false;

        for (Component component : circuit.getAllComponents()) {
            if (component.getClass() == Resistor.class) {
                hasResistor = true;
                componentCount = super.componentCount(circuit, component);
                if(!componentCount){
                    return false;
                }
            } else if (component.getClass() == Lightbulb.class) {
                if (((Lightbulb) component).isOn) {
                    lampIsOn = true;
                }
                componentCount = super.componentCount(circuit, component);
                if(!componentCount){
                    return false;
                }
            }
        }

        return (hasResistor && lampIsOn && componentCount && isFullCircle);
    }

    public Set<Component> getAvailableComponents() {
        Component[] components = {new Lightbulb(), new Powersource(), new Resistor()};
        return new HashSet<>(Arrays.asList(components));
    }

    public ArrayList<Component> loadComponents() {
        // If this scenario already has components in its base that means it was instantiated
        // with a user-designed circuit as an argument. Meaning the user continues this scenario
        // with his own self-made circuit.
        if (super.loadComponents() != null) {
            return super.loadComponents();
        }

        ArrayList<Component> components = new ArrayList<>();
        Powersource powersource = new Powersource();
        Lightbulb bulb = new Lightbulb();

        Connector.connect(powersource.getInput(), bulb.getConnectionPointByIndex(0));
        Connector.connect(powersource.getOutput(), bulb.getConnectionPointByIndex(1));

        powersource.setPosition(13);
        bulb.setPosition(32);

        components.add(powersource);
        components.add(bulb);
        return components;
    }

    public int getID() {
        return 2;
    }

    public int getHint() {
        if(!componentCount){
            return R.string.component_count;
        }
        if (!hasResistor) {
            return R.string.resistance_required;
        }
        if (!isFullCircle) {
            return R.string.no_full_circle;
        }
        if (!lampIsOn) {
            return R.string.lamp_off;
        }
        return 0;
    }
}
