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


/**
 * Created by Jesper on 1/22/2017.
 */

public class Scenario6 extends DesignScenario {
    private boolean isFullCircle;

    boolean lightsOn, Switches, twoLamps, twoSwitches, isParallel;

    @Override
    public int getPrompt() {
        return R.string.scenario5_explanation;
    }

    @Override
    public int getCompletePrompt() {
        return R.string.scenario_complete;
    }

    @Override
    public boolean isCompleted(Circuit circuit, Graph graph) {
        isFullCircle = super.isCompleted(circuit, graph);
        if (!isFullCircle) return false;

        lightsOn = true;
        Switches = true;
        twoLamps = false;
        twoSwitches = false;
        isParallel = false;

        for (Stack path: graph.findAllPaths()) {
            Component firstLightbulb = null;
            boolean pathIsParallel = true;

            while(!path.empty()) {
                Component c = (Component)path.pop();
                if (c.getClass() == Lightbulb.class) {
                    if(firstLightbulb == null || c == firstLightbulb) {
                        firstLightbulb = c;
                    }
                    // As soon as we find a path containing two lightbulbs, we know it´s not parallel.
                    else {
                        pathIsParallel = false;
                        break;
                    }
                }
            }
            if(pathIsParallel) {
                isParallel = true;
                break;
            }
        };

        for (Component component : circuit.getAllComponents()) {
            if (component != null) {
                if (component.getClass() == Lightbulb.class){
                    twoLamps = circuit.getComponentCount(component) == 2;
                    if (!((Lightbulb) component).isOn){
                        lightsOn = false;
                    }
                } else if (component.getClass() == Switch.class) {
                    twoSwitches = circuit.getComponentCount(component) == 2;
                    if (!component.hasOutputConnection(component.getConnectionPointByIndex(1))){
                        Switches = false;
                    }
                }
            }
        }

        return (lightsOn && Switches && twoLamps && twoSwitches && isParallel);
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

        Connector.connect(powersource.getOutput(), resistor.getConnectionPointByIndex(0));
        Connector.connect(resistor.getConnectionPointByIndex(1), bulb.getConnectionPointByIndex(1));
        Connector.connect(resistor.getConnectionPointByIndex(1), switch1.getConnectionPointByIndex(1));

        powersource.setPosition(19);
        resistor.setPosition(21);
        bulb.setPosition(6);
        switch1.setPosition(30);

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
        if (!twoLamps){
            return R.string.lamp_required;
        }
        if (!lightsOn) {
            return R.string.lamp_off;
        }
        if (!twoSwitches){
            return R.string.switch_required;
        }
        return R.string.wrong_connection;

    }
}
