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

/**
 * Created by Maaike on 12-12-2016.
 */

public class FreePlayScenario extends DesignScenario {
    @Override
    public int getPrompt() {
        return R.string.scenario_freeplay;
    }

    public int getHint() {
        return 0;
    }

    @Override
    public Set<Component> getAvailableComponents() {
        Component[] components = {new Lightbulb(), new Powersource(), new Resistor(), new Switch()};
        return new HashSet<>(Arrays.asList(components));
    }

    @Override
    public ArrayList<Component> loadComponents() {
        ArrayList<Component> components = new ArrayList<Component>();
        components.add(new Lightbulb());
        components.add(new Powersource());
        components.add(new Resistor());
        components.add(new Switch());
        return components;
    }
}
