package com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Components.Resistor;
import com.edulectronics.tinycircuit.Models.Scenarios.DesignScenario;
import com.edulectronics.tinycircuit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Maaike on 14-12-2016.
 */

public class Scenario2 extends DesignScenario {

    public  Scenario2(){}
    public Scenario2(Circuit circuit) {
        super(circuit);
    }

    @Override
    public int getPrompt() {
        return R.string.scenario2_explanation;
    }

    @Override
    public boolean isCompleted(Circuit circuit) {
        boolean hasResistor = false;
        boolean isFullCircle = false;
        boolean lampIsOn = false;

        for (Component component : circuit.getAllComponents()) {
            if(component != null && circuit.getComponentCount(component) == 1) {
                if(component.getClass() == Resistor.class) {
                    hasResistor = component.hasOutputConnection(component.getConnectionPointByIndex(1));
                } else if (component.getClass() == Powersource.class) {
                    isFullCircle = component.hasOutputConnection(((Powersource) component).getInput());
                } else if(component.getClass() == Lightbulb.class) {
                    lampIsOn = ((Lightbulb) component).isOn;
                }
            }
        }
        return (isFullCircle && hasResistor && lampIsOn);
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

        components.add(powersource);
        components.add(bulb);
        return components;
    }
}
