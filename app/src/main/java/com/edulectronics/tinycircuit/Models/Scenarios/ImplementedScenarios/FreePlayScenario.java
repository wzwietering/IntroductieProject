package com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Components.Resistor;
import com.edulectronics.tinycircuit.Models.Components.Switch;
import com.edulectronics.tinycircuit.Models.Scenarios.DesignScenario;
import com.edulectronics.tinycircuit.R;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Maaike on 12-12-2016.
 */

public class FreePlayScenario extends DesignScenario {

    public FreePlayScenario(){}
    public FreePlayScenario(Circuit circuit){
        super (circuit);
    }

    @Override
    public int getPrompt() {
        return R.string.scenario_freeplay;
    }

    public Set<Component> getAvailableComponents() {

        Set set = super.getAvailableComponents();

        set.add(new Lightbulb());
        set.add(new Powersource());
        set.add(new Resistor());
        set.add(new Switch());

        return set;
    }
}
