package com.edulectronics.tinycircuit.Controllers;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Factories.ScenarioFactory;
import com.edulectronics.tinycircuit.Models.Scenarios.IScenario;
import com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios.Scenario2;

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

    public boolean levelIsCompleted(Circuit circuit) {
        return scenario.isCompleted(circuit);
    }

    public int getScenarioID() {
        return scenario.getID();
    }

    public ArrayList<Component> getAvailableComponents() {
        return scenario.loadComponents();
    }
    
    public IScenario getScenario() {
        return scenario;
    }

    public void setScenario(IScenario scenario) {
        this.scenario = scenario;
    }
}
