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


/**
 * Created by Jesper on 1/4/2017.
 */

public class Scenario3 extends DesignScenario {
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
        boolean hasConnectedSwitch = false;
        boolean isFullCircle = false;
        boolean lampIsOn = false;

        for (Component component : circuit.getAllComponents()) {
            if(component != null) {
                if (component.getClass() == Powersource.class) {
                    if (component.hasOutputConnection(((Powersource) component).getInput())) {
                        isFullCircle = true;
                    }
                }
                if(component.getClass() == Lightbulb.class) {
                    if (((Lightbulb) component).isOn) {
                        lampIsOn = true;
                    }
                }
                if(component.getClass() == Switch.class){
                    if (component.hasOutputConnection(((Switch) component).getConnectionPointByIndex(1)))
                        hasConnectedSwitch = true;
                }
            }
        }
        return (isFullCircle && hasConnectedSwitch && lampIsOn);
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
        Connector.connect(bulb.getConnectionPointByIndex(0),resistor.getConnectionPointByIndex(0));
        Connector.connect(powersource.getInput(), resistor.getConnectionPointByIndex(1));

        components.add(powersource);
        components.add(bulb);
        components.add(resistor);
        return components;
    }

    public int getID(){
        return 3;
    }
}
