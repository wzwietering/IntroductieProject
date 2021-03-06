package com.edulectronics.tinycircuit.Controllers;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Factories.ScenarioFactory;
import com.edulectronics.tinycircuit.Models.Graph;
import com.edulectronics.tinycircuit.Models.Scenarios.IScenario;

import java.util.ArrayList;
import java.util.Set;


public class LevelController {
    private IScenario scenario;

    public LevelController(String scenarioNr) {
        ScenarioFactory factory = new ScenarioFactory();
        scenario = factory.getScenario(scenarioNr);
    }

    public boolean levelIsCompleted(Circuit circuit, Graph graph) {
        return scenario.isCompleted(circuit, graph);
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
