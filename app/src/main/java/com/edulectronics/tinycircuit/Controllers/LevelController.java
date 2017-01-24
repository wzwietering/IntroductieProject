package com.edulectronics.tinycircuit.Controllers;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Factories.ScenarioFactory;
import com.edulectronics.tinycircuit.Models.Scenarios.DesignScenario;
import com.edulectronics.tinycircuit.Models.Scenarios.IScenario;
import com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios.Scenario2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

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

    public Set<Component> getAvailableComponents() {
        return scenario.getAvailableComponents();
    }

    public ArrayList<Component> loadComponents() {
        return scenario.loadComponents();
    }

    public IScenario getScenario() {
        return scenario;
    }

    public void setScenario(IScenario scenario) {
        this.scenario = scenario;
    }

    public void goToNextLevel() {
        int nextScenario = scenario.getID() + 1;
        ScenarioFactory factory = new ScenarioFactory();
        scenario = factory.getScenario(Integer.toString(nextScenario));
    }
}
