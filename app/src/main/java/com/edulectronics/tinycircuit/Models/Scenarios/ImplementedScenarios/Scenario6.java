package com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
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
import com.edulectronics.tinycircuit.Views.CircuitActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import static com.edulectronics.tinycircuit.R.id.circuit;

/**
 * Created by Jesper on 1/22/2017.
 */

public class Scenario6 extends DesignScenario {
    private boolean isFullCircle;

    @Override
    public int getPrompt() {
        return R.string.scenario6_explanation;
    }

    @Override
    public int getCompletePrompt() {
        return R.string.scenario_complete;
    }

    @Override
    public boolean isCompleted(Circuit circuit) {
        isFullCircle = super.isCompleted(circuit);
        if (!isFullCircle) return false;

        boolean lightsOn = true;
        boolean Switches = true;
        boolean twoLamps = false;
        boolean twoSwitches = false;

        for (Component component : circuit.getAllComponents()) {
            if (component != null) {
                if (component.getClass() == Lightbulb.class){
                    twoLamps = circuit.getComponentCount(component) == 2;
                    if (!((Lightbulb) component).isOn){
                        lightsOn = false;
                    }
                } else if (component.getClass() == Switch.class ){
                    twoSwitches = circuit.getComponentCount(component) == 2;
                    if (!component.hasOutputConnection(component.getConnectionPointByIndex(1))){
                        Switches = false;
                    }
                }
            }
        }

        return (lightsOn && Switches && twoLamps && twoSwitches);
    }


    public Set<Component> getAvailableComponents() {
        Component[] components = {new Powersource(), new Resistor(), new Lightbulb(), new Switch()};
        return new HashSet<>(Arrays.asList(components));
    }

    public ArrayList<Component> loadComponents() {
        ArrayList<Component> components = new ArrayList<>();
        Powersource powersource = new Powersource();
        Resistor resistor = new Resistor();
        Lightbulb bulb = new Lightbulb();
        Switch switch1 = new Switch();

        Connector.connect(powersource.getOutput(), resistor.getConnectionPointByIndex(1));
        Connector.connect(resistor.getConnectionPointByIndex(0), bulb.getConnectionPointByIndex(0));

        components.add(powersource);
        components.add(resistor);
        components.add(bulb);
        components.add(switch1);
        return components;
    }

    public int getID() {
        return 6;
    }

    public int getHint() {
        if (!isFullCircle) {
            return R.string.no_full_circle;
        }
        return 0;
    }
}
