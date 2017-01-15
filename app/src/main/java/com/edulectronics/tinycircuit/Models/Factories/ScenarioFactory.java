package com.edulectronics.tinycircuit.Models.Factories;

import com.edulectronics.tinycircuit.Models.Scenarios.IScenario;
import com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios.FreePlayScenario;
import com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios.NonExistingScenario;
import com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios.Scenario1;
import com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios.Scenario2;
import com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios.Scenario3;
import com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios.Scenario4;

/**
 * Created by Maaike on 12-12-2016.
 * Scenario factory. It makes scenario's.
 * TODO: implement creation of quiz scenario's. They will not be separate classes so need a
 * different implementation.
 */

public class ScenarioFactory {

    public IScenario getScenario(String scenario) {
        switch(scenario) {
            case "freeplay":
                    return new FreePlayScenario();
            case "1":
                    return new Scenario1();
            case "2":
                    return new Scenario2();
            case "3":
                    return new Scenario3();
            case "4":
                    return new Scenario4();
            default:
                    return new NonExistingScenario();

        }
    }
}
