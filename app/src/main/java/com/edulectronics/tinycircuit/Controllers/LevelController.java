package com.edulectronics.tinycircuit.Controllers;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Factories.ScenarioFactory;
import com.edulectronics.tinycircuit.Models.Scenarios.IScenario;

import java.util.ArrayList;

/**
 * Created by bernd on 09/01/2017.
 */

public class LevelController {
    private IScenario scenario;

    public LevelController(String scenarioNr) {
        ScenarioFactory factory = new ScenarioFactory();
        scenario = factory.getScenario(scenarioNr);
    }

    public ArrayList<Component> getAvailableComponents() {
        return scenario.loadComponents();
    }
}
