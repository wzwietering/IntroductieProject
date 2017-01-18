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

public class Scenario4 extends DesignScenario {

    @Override
    public int getPrompt() {
        return R.string.scenario4_explanation;
    }

    boolean lampRequirementsMet;
    boolean isFullCircle;
    boolean hasSwitch;

    @Override
    public boolean isCompleted(Circuit circuit) {
        lampRequirementsMet = false;
        isFullCircle = false;
        hasSwitch = false;

        for (Component component : circuit.getAllComponents()) {
            if(component != null) {
                if (component.getClass() == Lightbulb.class) {
                    lampRequirementsMet = (((Lightbulb) component).isOn && circuit.getComponentCount(component) == 2);
                } else if (component.getClass() == Powersource.class && circuit.getComponentCount(component) == 1) {
                    isFullCircle = component.hasOutputConnection(((Powersource) component).getInput());
                } else if (component.getClass() == Switch.class && circuit.getComponentCount(component) == 1){
                    hasSwitch = component.hasOutputConnection(component.getConnectionPointByIndex(1)) && component.isConductive();
                }
            }
        }
        return (isFullCircle && lampRequirementsMet && hasSwitch);
    }

    public Set<Component> getAvailableComponents() {
        Component[] components = {new Lightbulb(), new Powersource(), new Resistor(), new Switch()};
        return new HashSet<>(Arrays.asList(components));
    }

    public ArrayList<Component> loadComponents() {
        ArrayList<Component> components = new ArrayList<>();
        Powersource powersource = new Powersource();

        components.add(powersource);

        return components;
    }

    @Override
    public int getID(){
        return 4;
    }

    public int getHint() {
        if(!lampRequirementsMet){
            return R.string.lamp_off;
        }
        if(!isFullCircle){
            return R.string.no_full_circle;
        }
        if(!hasSwitch){
            return R.string.switch_required;
        }
        return 0;
    }
}
