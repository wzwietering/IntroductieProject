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
        boolean hasLightbulb = false;
        boolean hasSwitch = false;
        boolean isFullCircle = false;

        for (Component component : circuit.getAllComponents()) {
            if(component != null) {
                if (component.getClass() == Lightbulb.class) {
                    hasLightbulb = true;
                    continue;
                }
                if(component.getClass() == Switch.class) {
                    hasSwitch = true;
                    continue;
                }
                if (component.getClass() == Powersource.class) {
                    if (component.hasOutputConnection(((Powersource) component).getInput())) {
                        isFullCircle = true;
                    }
                }
            }
        }

        return (isFullCircle && hasLightbulb && hasSwitch);
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

        ArrayList<Component> components = new ArrayList<>();
        Powersource powersource = new Powersource(5);
        Lightbulb bulb = new Lightbulb();

        Connector.connect(powersource.getOutput(), bulb.getConnectionPointByIndex(0));
        Connector.connect(powersource.getInput(), bulb.getConnectionPointByIndex(1));

        components.add(powersource);
        components.add(bulb);
        return components;
    }
}
