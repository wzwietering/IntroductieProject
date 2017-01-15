package com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Scenarios.DesignScenario;
import com.edulectronics.tinycircuit.R;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Maaike on 12-12-2016.
 * First scenario. Easy-peasy.
 * Circuit:     A power source and a lightbulb. Powersource and lightbulb are connected only
 *              on one side.
 * Complete:    if user connects the non-connected side of powersource to none-connected side
 *              of lightbulb.
 */

public class Scenario1 extends DesignScenario {

    @Override
    public int getPrompt() {
        return R.string.scenario1_explanation;
    }

    private boolean hasLightbulb, isFullCircle;

    @Override
    public boolean isCompleted(Circuit circuit) {
        hasLightbulb = false;
        isFullCircle = false;

        for (Component component : circuit.getAllComponents()) {
            if(component != null) {
                if (component.getClass() == Lightbulb.class) {
                    hasLightbulb = true;
                    continue;
                }
                if (component.getClass() == Powersource.class) {
                    if (component.hasOutputConnection(((Powersource) component).getInput())) {
                        isFullCircle = true;
                    }
                }
            }
        }

        return (isFullCircle && hasLightbulb);
    }

    public Set<Component> getAvailableComponents() {
        Set set = super.getAvailableComponents();
        set.add(new Lightbulb());
        set.add(new Powersource());

        return set;
    }

    public ArrayList<Component> loadComponents() {
        ArrayList<Component> components = new ArrayList<>();
        Powersource powersource = new Powersource();
        Lightbulb bulb = new Lightbulb();

        Connector.connect(powersource.getOutput(), bulb.getConnectionPointByIndex(1));

        components.add(powersource);
        components.add(bulb);
        return components;
    }

    public int getHint(){
        if (!hasLightbulb){
            return R.string.missing_component;
        }
        if (!isFullCircle){
            return R.string.no_full_circle;
        }
        return 0;
    }
}
