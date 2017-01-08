package com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Components.Switch;
import com.edulectronics.tinycircuit.Models.Scenarios.DesignScenario;
import com.edulectronics.tinycircuit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;


/**
 * Created by Jesper on 1/4/2017.
 */

public class Scenario3 extends DesignScenario {
    public  Scenario3(){}
    public Scenario3(Circuit circuit) {
        super(circuit);
    }
    public ArrayList<Component> components = new ArrayList<>();

    @Override
    public int getPrompt() {
        return R.string.scenario3_explanation;
    }

    @Override
    public boolean isCompleted(Circuit circuit) {
        boolean has2Batterys = false;
        boolean isFullCircle = false;
//      int batterycount = 0;

        for (Component component : circuit.getAllComponents()) {
            if(component != null) {
                if(Collections.frequency(components, Powersource.class) == 2){
//                    batterycount++;
//                    if (batterycount == 2)
                        has2Batterys = true;
                    continue;
                }
                if (component.getClass() == Powersource.class) {
                    if (component.hasOutputConnection(((Powersource) component).getInput())) {
                        isFullCircle = true;
                    }

                }
            }
        }
        return (isFullCircle && has2Batterys);
    }

    public Set<Component> getAvailableComponents() {
        Set set = super.getAvailableComponents();
        set.add(new Lightbulb());
        set.add(new Powersource(5));
        set.add(new Switch());

        return set;
    }

    public ArrayList<Component> loadComponents() {
        // If this scenario already has components in its base that means it was instantiated
        // with a user-designed circuit as an argument. Meaning the user continues this scenario
        // with his own self-made circuit.
        if (super.loadComponents() != null) {
            return super.loadComponents();
        }

        Powersource powersource = new Powersource(5);
        Lightbulb bulb = new Lightbulb();
        Switch switch1 = new Switch();

        Connector.connect(powersource.getOutput(), bulb.getConnectionPointByIndex(1));
        Connector.connect(bulb.getConnectionPointByIndex(0),switch1.getConnectionPointByIndex(0));
        Connector.connect(powersource.getInput(), switch1.getConnectionPointByIndex(1));

        components.add(powersource);
        components.add(bulb);
        components.add(switch1);
        return components;
    }
}
