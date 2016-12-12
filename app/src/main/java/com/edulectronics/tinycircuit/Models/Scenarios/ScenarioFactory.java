package com.edulectronics.tinycircuit.Models.Scenarios;

import java.security.InvalidParameterException;

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
            default:
                throw new IllegalArgumentException();
        }
    }
}
