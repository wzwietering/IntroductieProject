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
import java.util.Set;

public class Scenario4 extends DesignScenario {

    @Override
    public int getPrompt() {
        return R.string.scenario4_explanation;
    }

    @Override
    public boolean isCompleted(Circuit circuit) {
        boolean lampRequirementsMet = true;
        boolean isFullCircle = false;
        boolean hasSwitch = false;

        for (Component component : circuit.getAllComponents()) {
            if(component != null) {
                if (component.getClass() == Lightbulb.class) {
                    if (!((Lightbulb) component).isOn  || circuit.getComponentCount(component) != 2) {
                        lampRequirementsMet = false;
                    }
                }
                if (component.getClass() == Powersource.class && circuit.getComponentCount(component) == 1) {
                    if (component.hasOutputConnection(((Powersource) component).getInput())) {
                        isFullCircle = true;
                    }
                }
                if (component.getClass() == Switch.class && circuit.getComponentCount(component) == 1){
                    if (component.hasOutputConnection(((Switch) component).getConnectionPointByIndex(1))){
                        hasSwitch = true;
                    }
                }
            }
        }

        return (isFullCircle && lampRequirementsMet && hasSwitch);
    }

    public Set<Component> getAvailableComponents() {
        Set set = super.getAvailableComponents();
        set.add(new Lightbulb());
        set.add(new Powersource());
        set.add(new Resistor());
        set.add(new Switch());

        return set;
    }

    public ArrayList<Component> loadComponents() {
        ArrayList<Component> components = new ArrayList<>();
        Powersource powersource = new Powersource();

        components.add(powersource);

        return components;
    }
}