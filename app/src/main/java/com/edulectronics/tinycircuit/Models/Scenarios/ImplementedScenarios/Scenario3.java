package com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
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
import java.util.Stack;

public class Scenario3 extends DesignScenario {
    public ArrayList<Component> components = new ArrayList<>();

    @Override
    public int getPrompt() {
        return R.string.scenario3_explanation;
    }

    boolean hasConnectedSwitch;
    boolean isFullCircle;
    boolean lampIsOn;
    boolean hasResistor;
    boolean componentCount;

    @Override
    public boolean isCompleted(Circuit circuit, Graph graph) {
        isFullCircle = super.isCompleted(circuit, graph);
        if (!isFullCircle) return false;

        hasConnectedSwitch = false;
        lampIsOn = false;
        hasResistor = false;
        componentCount = false;

        for (Component component : circuit.getAllComponents()) {
            if (component.getClass() == Lightbulb.class) {
                lampIsOn = ((Lightbulb) component).isOn;
                componentCount = super.componentCount(circuit, component);
                if(!componentCount){
                    return false;
                }
            } else if (component.getClass() == Switch.class) {
                hasConnectedSwitch = component.hasOutputConnection(component.getConnectionPointByIndex(1));
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
            }
        }

        for (Stack path: graph.findAllPaths()) {
            boolean hasSwitch = false;
            boolean hasLightBulb = false;

            while(!path.empty()) {
                Component c = (Component)path.pop();
                if (c.getClass() == Switch.class)
                    hasSwitch = true;
                else if(c.getClass() == Lightbulb.class)
                    hasLightBulb = true;
            }
            if((hasSwitch && !hasLightBulb) || (!hasSwitch && hasLightBulb)) {
                hasConnectedSwitch = false;
                break;
            }
        }

        return (hasConnectedSwitch && lampIsOn && hasResistor && componentCount && isFullCircle);
    }

    public Set<Component> getAvailableComponents() {
        Component[] components = {new Lightbulb(), new Powersource(), new Resistor(), new Switch()};
        return new HashSet<>(Arrays.asList(components));
    }

    public ArrayList<Component> loadComponents() {
        // If this scenario already has components in its base that means it was instantiated
        // with a user-designed circuit as an argument. Meaning the user continues this scenario
        // with his own self-made circuit.
        if (super.loadComponents() != null) {
            return super.loadComponents();
        }

        Powersource powersource = new Powersource();
        Lightbulb bulb = new Lightbulb();
        Resistor resistor = new Resistor();

        Connector.connect(powersource.getOutput(), bulb.getConnectionPointByIndex(1));
        Connector.connect(bulb.getConnectionPointByIndex(0), resistor.getConnectionPointByIndex(1));
        Connector.connect(powersource.getInput(), resistor.getConnectionPointByIndex(0));

        components.add(powersource);
        components.add(bulb);
        components.add(resistor);
        return components;
    }

    @Override
    public int getID() {
        return 3;
    }

    public int getHint() {
        if (!hasConnectedSwitch) {
            return R.string.switch_required;
        }
        if (!isFullCircle) {
            return R.string.no_full_circle;
        }
        if (!lampIsOn) {
            return R.string.lamp_off;
        }
        if(!hasResistor){
            return R.string.resistance_required;
        }
        if(!componentCount){
            return R.string.component_count;
        }
        return 0;
    }
}
