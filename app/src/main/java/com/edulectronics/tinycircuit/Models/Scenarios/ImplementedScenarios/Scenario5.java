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
        lampRequirementsMet = false;
        isFullCircle = false;
        hasSwitch = false;
        hasResistor = false;

        for (Component component : circuit.getAllComponents()) {
            if(component != null) {
                if (component.getClass() == Lightbulb.class) {
                    if (((Lightbulb) component).isOn && !((Lightbulb) component).isBroken()) {
                        lampRequirementsMet = true;
                    }
                }
                if (component.getClass() == Powersource.class) {
                    if (component.hasOutputConnection(((Powersource) component).getInput())) {
                        isFullCircle = true;
                    }
                }
                if (component.getClass() == Switch.class){
                    if (component.hasOutputConnection(((Switch) component).getConnectionPointByIndex(1))){
                        hasSwitch = true;
                    }
                }
                if (component.getClass() == Resistor.class){
                    if (component.hasOutputConnection(((Resistor) component).getConnectionPointByIndex(1))){
                        hasResistor = true;
                    }
                }
            }
        }

        return (isFullCircle && lampRequirementsMet && hasSwitch && hasResistor);
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
        return components;
    }

    @Override
    public int getID(){
        return 5;
    }

    public int getHint() {
        if(!lampRequirementsMet){
            return R.string.lamp_off;
        }
        if(!isFullCircle){
            return R.string.no_full_circle;
        }
        if(!hasSwitch){
            return R.string.missing_component;
        }
        if(!hasResistor){
            return R.string.resistance_required;
        }
        return 0;
    }
}
